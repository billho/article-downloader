package com.guidorota.articledownloader.repository;

import com.guidorota.articledownloader.entity.Article;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.stream.StreamSupport;

@Component
public final class MongoArticleRepository implements ArticleRepository {

    private final MongoClient client = new MongoClient();
    private final MongoDatabase db = client.getDatabase("article-downloader");

    @PreDestroy
    private void cleanUp() {
        client.close();
    }

    @Override
    public boolean containsUrl(String url) {
        FindIterable<Document> it = db.getCollection("article").find(
                new Document("url", url)
        ).limit(1);

        return StreamSupport.stream(it.spliterator(), false)
                .findAny()
                .isPresent();
    }

    @Override
    public void storeArticle(Article article) {
        if (containsUrl(article.getUrl())) {
            return;
        }

        Document bsonArticle = new Document()
                .append("url", article.getUrl())
                .append("downloadDate", article.getDownloadDate())
                .append("title", article.getTitle())
                .append("article", article.getContent());

        db.getCollection("article").insertOne(bsonArticle);
    }

}
