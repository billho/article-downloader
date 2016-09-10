package com.guidorota.articledownloader;

import com.guidorota.articledownloader.download.ArticleDetailsDownloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class Main {

	private static final List<String> feedUrls = Arrays.asList(
			"http://www.huffingtonpost.com/feeds/news.xml",
			"http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml"
	);

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Main.class, args);

		ArticleDetailsDownloader articleDetailsDownloader = ctx.getBean(ArticleDetailsDownloader.class);
		feedUrls.stream()
				.flatMap(Main::createUrl)
				.flatMap(articleDetailsDownloader::getArticleDetails)
				.forEach(System.out::println);
	}

	private static Stream<URL> createUrl(String url) {
		try {
			return Stream.of(new URL(url));
		} catch (MalformedURLException e) {
			return Stream.empty();
		}
	}

}
