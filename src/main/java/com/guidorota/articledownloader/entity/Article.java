package com.guidorota.articledownloader.entity;

import java.util.Date;

public final class Article {

    private final ArticleDetails articleDetails;
    private final String article;

    public Article(ArticleDetails articleDetails, String article) {
        this.articleDetails = articleDetails;
        this.article = article;
    }

    public String getUrl() {
        return articleDetails.getUrl();
    }

    public String getTitle() {
        return articleDetails.getTitle();
    }

    public Date getPublishedDate() {
        return articleDetails.getPublishedDate();
    }

    public String getArticle() {
        return article;
    }

}
