package com.guidorota.articledownloader.entity;

public final class ArticleFeed {

    private final String url;
    private final Type type;

    public ArticleFeed(String url, Type type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {

        RSS("rss");

        private String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

}
