package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.ArticleFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class GenericFeedReader implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(GenericFeedReader.class);

    private final RssFeedReader rssUrlProvider;

    @Autowired
    public GenericFeedReader(RssFeedReader rssUrlProvider) {
        this.rssUrlProvider = rssUrlProvider;
    }

    @Override
    public Stream<String> getArticleUrls(ArticleFeed feed) {
        String sourceUrl = feed.getUrl();
        ArticleFeed.Type type = feed.getType();

        switch (type) {
            case RSS:
                return rssUrlProvider.getArticleUrls(feed);
            default:
                String error = "Unknown provider type " + type;
                log.error(error);
                throw new RuntimeException(error);
        }
    }

}
