package com.guidorota.articledownloader.entity;

import java.util.Date;

public final class Article {

    private final String url;
    private final Date downloadDate;
    private final String title;
    private final String content;

    public Article(String url, Date downloadDate, String title, String content) {
        this.url = url;
        this.downloadDate = downloadDate;
        this.title = title;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
