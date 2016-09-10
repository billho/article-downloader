package com.guidorota.articledownloader.entity;

public final class UrlSource {

    private final String url;
    private final Type type;

    public UrlSource(String url, Type type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

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
