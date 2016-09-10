package com.guidorota.articledownloader.entity;

public final class Article {

    private final String url;
    private final String title;
    private final String content;

    public Article(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

}
