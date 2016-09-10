package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleDetails;
import com.guidorota.articledownloader.html.TextExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public final class HuffingtonArticleDownloader implements ArticleDownloader {

    @Override
    public boolean canDownloadFromUrl(String url) {
        return url.startsWith("http://www.huffingtonpost.com");
    }

    @Override
    public Stream<Article> download(ArticleDetails details) {
        String articleUrl = details.getUrl();
        try {
            Document document = Jsoup.connect(articleUrl).get();
            return TextExtractor.extractText(document.select("div.entry__body p"))
                    .map(content -> new Article(details, content));
        } catch (Exception e) {
            return Stream.empty();
        }
    }

}
