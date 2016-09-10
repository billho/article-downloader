package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.ArticleDetails;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

import static java.lang.String.format;

@Component
public final class ArticleDetailsDownloader {

    private static final Logger log = LoggerFactory.getLogger(ArticleDetailsDownloader.class);

    public Stream<ArticleDetails> getArticleDetails(URL feedUrl) {
        try {
            return readFeed(feedUrl);
        } catch (IOException | FeedException e) {
            log.error(format("Could not read feed for URL '%s'", feedUrl), e);
            return Stream.empty();
        }
    }

    private Stream<ArticleDetails> readFeed(URL feedUrl) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        return feed.getEntries().stream()
                .map(this::syndEntryToArticleDetails);
    }

    private ArticleDetails syndEntryToArticleDetails(SyndEntry entry) {
        return new ArticleDetails(
                entry.getLink(),
                entry.getPublishedDate(),
                entry.getTitle()
        );
    }

}
