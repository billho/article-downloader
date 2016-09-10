package com.guidorota.articledownloader.rest;

import com.guidorota.articledownloader.DownloadService;
import com.guidorota.articledownloader.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController()
@RequestMapping("rest/article")
public class ArticleController {

    private final DownloadService downloadService;

    @Autowired
    public ArticleController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @RequestMapping(path = "/", method = GET)
    public List<Article> getArticles() {
        return downloadService.run();
    }

}
