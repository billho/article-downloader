package com.guidorota.articledownloader.rest;

import com.guidorota.articledownloader.DownloadService;
import com.guidorota.articledownloader.StatsService;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.entity.ArticleSource;
import com.guidorota.articledownloader.rest.entity.ArticleSourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("rest/article")
public final class ArticleController {

    private final DownloadService downloadService;
    private final StatsService statsService;

    @Autowired
    public ArticleController(
            DownloadService downloadService,
            StatsService statsService) {
        this.downloadService = downloadService;
        this.statsService = statsService;
    }

    @RequestMapping(path = "/count", method = GET)
    public long count() {
        return statsService.getArticleCount();
    }

    @RequestMapping(path = "/fetch", method = POST)
    public void fetch(@RequestBody List<ArticleSourceRequest> requests) {
        List<ArticleSource> sources = requests.stream()
                .map(ArticleSourceRequest::toArticleSource)
                .collect(Collectors.toList());

        downloadService.downloadAndStore(sources);
    }

    @RequestMapping(path = "/test", method = POST)
    public List<Article> test(@RequestBody List<ArticleSourceRequest> requests) {
        List<ArticleSource> sources = requests.stream()
                .map(ArticleSourceRequest::toArticleSource)
                .collect(Collectors.toList());

        return downloadService.downloadAndReturn(sources);
    }

}
