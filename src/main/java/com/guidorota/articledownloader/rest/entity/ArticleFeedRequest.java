package com.guidorota.articledownloader.rest.entity;

import com.guidorota.articledownloader.entity.ArticleFeed;

import static java.util.Objects.requireNonNull;

public final class ArticleFeedRequest {

    public final String url;
    public final ArticleFeed.Type type;

    public ArticleFeedRequest() {
        this.url = null;
        this.type = null;
    }

    public ArticleFeedRequest(String url, ArticleFeed.Type type) {
        this.url = url;
        this.type = type;
    }

    public ArticleFeed toArticleFeed() {
        requireNonNull(url, "url");
        requireNonNull(type, "type");

        return new ArticleFeed(url, type);
    }

    @Override
    public String toString() {
        return "ArticleFeedRequest{" +
                "url='" + url + '\'' +
                ", type=" + type +
                '}';
    }

}
