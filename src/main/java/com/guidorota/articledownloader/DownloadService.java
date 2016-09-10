package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.ArticleDetailsDownloader;
import com.guidorota.articledownloader.download.MultipleSourceArticleDownloader;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DownloadService {

    private static final List<String> feedUrls = Arrays.asList(
            "http://www.huffingtonpost.com/feeds/news.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/World.xml"
    );

    private final ArticleDetailsDownloader articleDetailsDownloader;
    private final MultipleSourceArticleDownloader multipleSourceArticleDownloader;
    private final ArticleRepository articleRepository;

    @Autowired
    public DownloadService(
            ArticleDetailsDownloader articleDetailsDownloader,
            MultipleSourceArticleDownloader multipleSourceArticleDownloader,
            ArticleRepository articleRepository) {
        this.articleDetailsDownloader = articleDetailsDownloader;
        this.multipleSourceArticleDownloader = multipleSourceArticleDownloader;
        this.articleRepository = articleRepository;
    }

    public List<Article> run() {
        return feedUrls.stream()
                .flatMap(this::createUrl)
                .flatMap(articleDetailsDownloader::getArticleDetails)
                .parallel()
                .filter(ad -> !articleRepository.containsUrl(ad.getUrl()))
                .flatMap(multipleSourceArticleDownloader::download)
                .collect(Collectors.toList());
    }

    private Stream<URL> createUrl(String url) {
        try {
            return Stream.of(new URL(url));
        } catch (MalformedURLException e) {
            return Stream.empty();
        }
    }

}
