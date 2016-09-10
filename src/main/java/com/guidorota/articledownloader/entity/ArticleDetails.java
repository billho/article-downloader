package com.guidorota.articledownloader.entity;

import java.util.Date;

public final class ArticleDetails {

    private final String url;
    private final Date publishedDate;
    private final String title;

    public ArticleDetails(String url, Date publishedDate, String title) {
        this.url = url;
        this.publishedDate = publishedDate;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getTitle() {
        return title;
    }

}
