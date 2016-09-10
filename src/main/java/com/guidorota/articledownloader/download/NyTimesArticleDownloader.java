package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleDetails;
import com.guidorota.articledownloader.html.TextExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public final class NyTimesArticleDownloader implements ArticleDownloader {

    @Override
    public boolean canDownloadFromUrl(String url) {
        return url.startsWith("http://www.nytimes.com");
    }

    @Override
    public Stream<Article> download(ArticleDetails details) {
        String articleUrl = details.getUrl();
        try {
            Document document = Jsoup.connect(articleUrl).get();
            String text = TextExtractor.extractText(document.select("p.story-body-text"));
            return Stream.of(new Article(details, text));
        } catch (Exception e) {
            return Stream.empty();
        }
    }

}
