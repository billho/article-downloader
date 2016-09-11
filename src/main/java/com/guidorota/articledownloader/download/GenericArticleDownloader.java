package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleMapping;
import com.guidorota.articledownloader.html.HtmlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

public final class GenericArticleDownloader implements ArticleDownloader {

    private static final Logger log = LoggerFactory.getLogger(GenericArticleDownloader.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    private static final String REFERRER = "https://www.google.com";

    @Override
    public Stream<Article> download(String url, ArticleMapping mapping) {
        try {
            return extractFromUrl(url, mapping);
        } catch (Exception e) {
            log.error("Error downloading the article: " + e.getMessage());
            return Stream.empty();
        }
    }

    private Stream<Article> extractFromUrl(String url, ArticleMapping mapping) throws IOException {
        log.info("Downloading article " + url);
        Document document = Jsoup
                .connect(url)
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .get();

        String title = extractTitle(document, mapping.getTitleSelector());
        String content = extractContent(document, mapping.getContentSelector());

        if (stringIsEmpty(title) || stringIsEmpty(content)) {
            log.info(url + " has no title or no content");
            return Stream.empty();
        } else {
            return Stream.of(new Article(
                    url,
                    new Date(),
                    title,
                    content
            ));
        }
    }

    private String extractTitle(Document document, String titleSelector) {
        Element te = document.select(titleSelector).get(0);
        return HtmlUtils.extractText(te);
    }

    private String extractContent(Document document, String contentSelector) {
        return HtmlUtils.extractText(document.select(contentSelector));
    }

    private boolean stringIsEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
