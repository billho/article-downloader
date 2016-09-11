package com.guidorota.articledownloader.rest.entity;

import com.guidorota.articledownloader.entity.ArticleFeed;
import com.guidorota.articledownloader.entity.ArticleMapping;
import com.guidorota.articledownloader.entity.ArticleSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public final class ArticleSourceRequest {

    public final List<ArticleFeedRequest> articleFeeds;
    public final String titleSelector;
    public final String contentSelector;

    public ArticleSourceRequest() {
        articleFeeds = null;
        titleSelector = null;
        contentSelector = null;
    }

    public ArticleSourceRequest(List<ArticleFeedRequest> articleFeeds, String titleSelector, String contentSelector) {
        this.articleFeeds = articleFeeds;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
    }

    public ArticleSource toArticleSource() {
        requireNonNull(articleFeeds, "articleFeeds");
        requireNonNull(titleSelector, "titleSelector");
        requireNonNull(contentSelector, "contentSelector");

        List<ArticleFeed> afs = articleFeeds.stream()
                .map(ArticleFeedRequest::toArticleFeed)
                .collect(Collectors.toList());

        return new ArticleSource(
                Collections.unmodifiableList(afs),
                new ArticleMapping(titleSelector, contentSelector)
        );
    }

    @Override
    public String toString() {
        return "ArticleSourceRequest{" +
                "articleFeeds=" + articleFeeds +
                ", titleSelector='" + titleSelector + '\'' +
                ", contentSelector='" + contentSelector + '\'' +
                '}';
    }

}
