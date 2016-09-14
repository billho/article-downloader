package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.ArticleFeed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

import static com.guidorota.articledownloader.entity.ArticleFeed.Type.RSS;
import static java.lang.String.format;

@Component
public final class RssFeedReader implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);

    @Override
    public Stream<String> getArticleUrls(ArticleFeed feed) {
        if (feed.getType() != RSS) {
            String error = format("Cannot parse fee type '%s'.", feed.getType());
            log.error(error);
            throw new IllegalArgumentException(error);
        }

        String url = feed.getUrl();
        log.info("Fetching RSS feed from " + url);
        try {
            URL feedUrl = new URL(url);
            return readFeed(feedUrl);
        } catch (MalformedURLException e) {
            log.warn(format("Malformed URL '%s'", url), e);
            return Stream.empty();
        } catch (IOException | FeedException e) {
            log.warn(format("Could not read feed for URL '%s'", url), e);
            return Stream.empty();
        }
    }

    private Stream<String> readFeed(URL feedUrl) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        input.setAllowDoctypes(true);
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        return feed.getEntries().stream()
                .map(SyndEntry::getLink);
    }

}
