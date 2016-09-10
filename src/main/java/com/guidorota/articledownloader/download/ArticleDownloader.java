package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleDetails;

import java.util.stream.Stream;

public interface ArticleDownloader {

    boolean canDownloadFromUrl(String url);

    Stream<Article> download(ArticleDetails details);

}
