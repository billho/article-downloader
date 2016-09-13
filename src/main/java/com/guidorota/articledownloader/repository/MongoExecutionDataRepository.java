package com.guidorota.articledownloader.repository;

import com.guidorota.articledownloader.entity.ExecutionData;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MongoExecutionDataRepository implements ExecutionDataRepository {

    private final MongoClient client = new MongoClient();
    private final MongoDatabase db = client.getDatabase("article-downloader");

    @PreDestroy
    private void cleanUp() {
        client.close();
    }

    @Override
    public void store(ExecutionData executionData) {
        Document d = new Document()
                .append("startDate", executionData.getStartDate())
                .append("endDate", executionData.getFinishDate())
                .append("command", executionData.getCommand());

        db.getCollection("execution-data").insertOne(d);
    }

    @Override
    public List<ExecutionData> getAll() {
        return streamAllExecutionDataDocuments()
                .map(this::documentToExecutionData)
                .collect(Collectors.toList());
    }

    private Stream<Document> streamAllExecutionDataDocuments() {
        return StreamSupport.stream(
                db.getCollection("execution-data").find().spliterator(),
                false
        );
    }

    private ExecutionData documentToExecutionData(Document document) {
        return new ExecutionData(
                document.getDate("startDate"),
                document.getDate("finishDate"),
                document.getString("command")
        );
    }

}
