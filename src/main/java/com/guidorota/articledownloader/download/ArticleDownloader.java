package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleMapping;

import java.util.stream.Stream;

public interface ArticleDownloader {

    Stream<Article> download(String url, ArticleMapping mapping);

}
