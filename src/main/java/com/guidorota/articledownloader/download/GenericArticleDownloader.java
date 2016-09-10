package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleSource;
import com.guidorota.articledownloader.entity.UrlSource;
import com.guidorota.articledownloader.html.TextExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Stream;

public final class GenericArticleDownloader implements ArticleDownloader {

    private static final Logger log = LoggerFactory.getLogger(GenericArticleDownloader.class);
    private static final UrlProvider RSS_URL_PROVIDER = new RssUrlProvider();

    private final ArticleSource articleSource;

    public GenericArticleDownloader(ArticleSource articleSource) {
        this.articleSource = articleSource;
    }

    @Override
    public Stream<String> getUrls() {
        return articleSource.getUrlSources().stream()
                .flatMap(this::getUrlsFromSource);
    }

    private Stream<String> getUrlsFromSource(UrlSource source) {
        String sourceUrl = source.getUrl();
        UrlSource.Type type = source.getType();

        switch (source.getType()) {
            case RSS:
                return RSS_URL_PROVIDER.getArticleUrls(sourceUrl);
            default:
                String error = "Unknown provider type " + type;
                log.error(error);
                throw new RuntimeException(error);
        }

    }

    @Override
    public Stream<Article> download(String url) {
        try {
            return extractFromUrl(url);
        } catch (Exception e) {
            return Stream.empty();
        }
    }

    private Stream<Article> extractFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        String title = extractTitle(document);
        String content = extractContent(document);

        if (stringIsEmpty(title) || stringIsEmpty(content)) {
            return Stream.empty();
        } else {
            return Stream.of(new Article(
                    url,
                    title,
                    content
            ));
        }
    }

    private String extractTitle(Document document) {
        Element te = document.select(articleSource.getTitleSelector()).get(0);
        return TextExtractor.extractText(te);
    }

    private String extractContent(Document document) {
        return TextExtractor.extractText(
                document.select(articleSource.getContentSelector())
        );
    }

    private boolean stringIsEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
