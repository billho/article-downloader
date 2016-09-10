package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleDetails;
import org.springframework.stereotype.Component;

@Component
public final class NyTimesArticleDownloader implements ArticleDownloader {

    @Override
    public boolean canDownloadFromUrl(String url) {
        return url.startsWith("http://www.nytimes.com");
    }

    @Override
    public Article download(ArticleDetails details) {
        return new Article(details, "nytimes");
    }

}
