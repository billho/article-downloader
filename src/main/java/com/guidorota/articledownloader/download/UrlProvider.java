package com.guidorota.articledownloader.download;

import java.util.stream.Stream;

public interface UrlProvider {

    Stream<String> getArticleUrls(String source);

}
