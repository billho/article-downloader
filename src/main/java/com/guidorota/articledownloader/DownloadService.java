package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.*;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleSource;
import com.guidorota.articledownloader.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DownloadService {

    private static final Logger log = LoggerFactory.getLogger(DownloadService.class);

    private final FeedReader feedReader;
    private final ArticleDownloader articleDownloader;
    private final ArticleRepository articleRepository;

    @Autowired
    public DownloadService(
            GenericFeedReader genericUrlProvider,
            ArticleRepository articleRepository) {
        this.feedReader = genericUrlProvider;
        articleDownloader = new GenericArticleDownloader();
        this.articleRepository = articleRepository;
    }

    public List<Article> run(ArticleSource source) {
        return downloadFromSingleSource(source)
                .collect(Collectors.toList());
    }

    private Stream<Article> downloadFromSingleSource(ArticleSource source) {
        return source.getArticleFeeds().parallelStream()
                .flatMap(feedReader::getArticleUrls)
                .filter(this::notInRepository)
                .flatMap(url -> articleDownloader.download(url, source.getArticleMapping()));
    }

    private boolean notInRepository(String url) {
        return !articleRepository.containsUrl(url);
    }

}
