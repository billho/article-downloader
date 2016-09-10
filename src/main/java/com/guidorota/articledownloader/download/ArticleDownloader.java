package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;

import java.util.stream.Stream;

public interface ArticleDownloader {

    Stream<String> getUrls();

    Stream<Article> download(String url);

}
