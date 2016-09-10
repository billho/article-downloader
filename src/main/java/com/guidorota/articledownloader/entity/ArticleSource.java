package com.guidorota.articledownloader.entity;

import java.util.Collections;
import java.util.List;

public final class ArticleSource {

    private List<UrlSource> urlSources;
    private String titleSelector;
    private String contentSelector;

    public ArticleSource(UrlSource urlSource, String titleSelector, String contentSelector) {
        this.urlSources = Collections.singletonList(urlSource);
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
    }

    public ArticleSource(List<UrlSource> urlSources, String titleSelector, String contentSelector) {
        this.urlSources = urlSources;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
    }

    public List<UrlSource> getUrlSources() {
        return urlSources;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

}
