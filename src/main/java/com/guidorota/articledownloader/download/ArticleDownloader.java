package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;

public interface ArticleDownloader {

    boolean canDownloadFromUrl(String url);

    Article download(String url);

}
