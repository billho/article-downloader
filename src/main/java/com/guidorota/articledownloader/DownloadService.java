package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.ArticleDownloader;
import com.guidorota.articledownloader.download.FeedReader;
import com.guidorota.articledownloader.download.GenericArticleDownloader;
import com.guidorota.articledownloader.download.GenericFeedReader;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleSource;
import com.guidorota.articledownloader.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class DownloadService {

    private static final Logger log = LoggerFactory.getLogger(DownloadService.class);

    private static final ExecutorService executor =
            Executors.newSingleThreadExecutor();

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

    public void downloadAndStore(List<ArticleSource> sources) {
        executor.submit(() -> {
            log.info("Downloading and storing articles...");
            sources.parallelStream()
                    .flatMap(this::downloadIfNotInRepository)
                    .forEach(articleRepository::storeArticle);
            log.info("Download complete");
        });
    }

    private Stream<Article> downloadIfNotInRepository(ArticleSource source) {
        return source.getArticleFeeds().stream()
                .flatMap(feedReader::getArticleUrls)
                .filter(this::notInRepository)
                .parallel()
                .flatMap(url -> articleDownloader.download(url, source.getArticleMapping()));
    }

    private boolean notInRepository(String url) {
        return !articleRepository.containsUrl(url);
    }

    public List<Article> downloadAndReturn(List<ArticleSource> sources) {
        return sources.parallelStream()
                .flatMap(this::downloadFromSingleSource)
                .collect(Collectors.toList());
    }

    private Stream<Article> downloadFromSingleSource(ArticleSource source) {
        return source.getArticleFeeds().stream()
                .flatMap(feedReader::getArticleUrls)
                .parallel()
                .flatMap(url -> articleDownloader.download(url, source.getArticleMapping()));
    }

}
