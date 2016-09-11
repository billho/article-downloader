package com.guidorota.articledownloader.repository;

import com.guidorota.articledownloader.entity.Article;

public interface ArticleRepository {

    boolean containsUrl(String url);

    void storeArticle(Article article);

}
