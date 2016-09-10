package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.ArticleDetailsDownloader;
import com.guidorota.articledownloader.download.MultipleSourceArticleDownloader;
import com.guidorota.articledownloader.entity.ArticleDetails;
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
public class Runner {

    private static final List<String> feedUrls = Arrays.asList(
            "http://www.huffingtonpost.com/feeds/news.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml",
            "http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml"
    );

    private final ArticleDetailsDownloader articleDetailsDownloader;
    private final MultipleSourceArticleDownloader multipleSourceArticleDownloader;
    private final ArticleRepository articleRepository;

    @Autowired
    public Runner(
            ArticleDetailsDownloader articleDetailsDownloader,
            MultipleSourceArticleDownloader multipleSourceArticleDownloader,
            ArticleRepository articleRepository) {
        this.articleDetailsDownloader = articleDetailsDownloader;
        this.multipleSourceArticleDownloader = multipleSourceArticleDownloader;
        this.articleRepository = articleRepository;
    }

    public void run() {
        feedUrls.stream()
                .flatMap(this::createUrl)
                .flatMap(articleDetailsDownloader::getArticleDetails)
                .filter(ad -> !articleRepository.containsUrl(ad.getUrl()))
                .flatMap(multipleSourceArticleDownloader::download)
                .forEach(System.out::println);
//                .forEach(articleRepository::addArticle);
    }

    private Stream<URL> createUrl(String url) {
        try {
            return Stream.of(new URL(url));
        } catch (MalformedURLException e) {
            return Stream.empty();
        }
    }

}
