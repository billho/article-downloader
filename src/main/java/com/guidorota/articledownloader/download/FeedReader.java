package com.guidorota.articledownloader.download;

import com.guidorota.articledownloader.entity.ArticleFeed;

import java.util.stream.Stream;

public interface FeedReader {

    Stream<String> getArticleUrls(ArticleFeed feed);

}
