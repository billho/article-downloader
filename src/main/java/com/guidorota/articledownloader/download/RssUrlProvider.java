package com.guidorota.articledownloader.download;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

import static java.lang.String.format;

public final class RssUrlProvider implements UrlProvider {

    private static final Logger log = LoggerFactory.getLogger(RssUrlProvider.class);

    @Override
    public Stream<String> getArticleUrls(String source) {
        try {
            URL feedUrl = new URL(source);
            return readFeed(feedUrl);
        } catch (MalformedURLException e) {
            log.error(format("Malformed URL '%s'", source), e);
            return Stream.empty();
        } catch (IOException | FeedException e) {
            log.error(format("Could not read feed for URL '%s'", source), e);
            return Stream.empty();
        }
    }

    private Stream<String> readFeed(URL feedUrl) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        return feed.getEntries().stream()
                .map(SyndEntry::getLink);
    }

}
