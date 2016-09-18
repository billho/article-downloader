package com.guidorota.articledownloader;

import com.guidorota.articledownloader.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatsService {

    private final ArticleRepository articleRepository;

    @Autowired
    public StatsService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

}
