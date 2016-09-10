package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public final class MultipleSourceArticleDownloader {

    private final List<ArticleDownloader> articleDownloaders;

    @Autowired
    public MultipleSourceArticleDownloader(
            HuffingtonArticleDownloader huffingtonArticleDownloader,
            NyTimesArticleDownloader nyTimesArticleDownloader) {
        articleDownloaders = Arrays.asList(
                huffingtonArticleDownloader,
                nyTimesArticleDownloader
        );
    }

    public Stream<Article> download(String url) {
        return articleDownloaders.stream()
                .filter(dl -> dl.canDownloadFromUrl(url))
                .map(dl -> dl.download(url));
    }

}
