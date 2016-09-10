package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleDetails;

public interface ArticleDownloader {

    boolean canDownloadFromUrl(String url);

    Article download(ArticleDetails details);

}
