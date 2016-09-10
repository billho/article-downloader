package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import org.springframework.stereotype.Component;

@Component
public final class HuffingtonArticleDownloader implements ArticleDownloader {

    @Override
    public boolean canDownloadFromUrl(String url) {
        return url.startsWith("http://www.huffingtonpost.com");
    }

    @Override
    public Article download(String url) {
        System.out.println("huffington");
        return null;
    }

}
