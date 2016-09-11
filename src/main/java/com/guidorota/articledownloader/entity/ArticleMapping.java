package com.guidorota.articledownloader.entity;

public final class ArticleMapping {

    private String titleSelector;
    private String contentSelector;

    public ArticleMapping(String titleSelector, String contentSelector) {
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

}
