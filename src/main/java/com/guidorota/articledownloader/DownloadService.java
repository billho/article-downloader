package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.GenericArticleDownloader;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleSource;
import com.guidorota.articledownloader.entity.UrlSource;
import com.guidorota.articledownloader.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DownloadService {

    private static final List<ArticleSource> articleSources = Arrays.asList(
            new ArticleSource(
                    new UrlSource(
                            "http://www.huffingtonpost.com/feeds/news.xml",
                            UrlSource.Type.RSS
                    ),
                    "h1.headline__title",
                    "div.entry__body p"
            )
//            "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml",
//            "http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml",
//            "http://www.nytimes.com/services/xml/rss/nyt/InternationalHome.xml",
//            "http://www.nytimes.com/services/xml/rss/nyt/World.xml"
    );

    private final ArticleRepository articleRepository;

    @Autowired
    public DownloadService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> run() {
        return articleSources.parallelStream()
                .map(GenericArticleDownloader::new)
                .flatMap(this::downloadFromSingleSource)
                .collect(Collectors.toList());
    }

    private Stream<Article> downloadFromSingleSource(GenericArticleDownloader downloader) {
        return downloader.getUrls().parallel()
                .filter(url -> !articleRepository.containsUrl(url))
                .flatMap(downloader::download);
    }

}
