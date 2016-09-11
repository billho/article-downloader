package com.guidorota.articledownloader.entity;

import java.util.List;

public final class ArticleSource {

    private List<ArticleFeed> articleFeeds;
    private ArticleMapping articleMapping;

    public ArticleSource(List<ArticleFeed> articleFeeds, ArticleMapping articleMapping) {
        this.articleFeeds = articleFeeds;
        this.articleMapping = articleMapping;
    }

    public List<ArticleFeed> getArticleFeeds() {
        return articleFeeds;
    }

    public ArticleMapping getArticleMapping() {
        return articleMapping;
    }

}
