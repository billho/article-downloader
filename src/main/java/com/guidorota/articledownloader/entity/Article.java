package com.guidorota.articledownloader.entity;

public final class Article {

    private final ArticleDetails articleDetails;
    private final String article;

    public Article(ArticleDetails articleDetails, String article) {
        this.articleDetails = articleDetails;
        this.article = article;
    }
}
