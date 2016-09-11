package com.guidorota.articledownloader.rest;

import com.guidorota.articledownloader.DownloadService;
import com.guidorota.articledownloader.entity.Article;
import com.guidorota.articledownloader.rest.entity.ArticleSourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("rest/article")
public final class ArticleController {

    private final DownloadService downloadService;

    @Autowired
    public ArticleController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @RequestMapping(path = "/test", method = POST)
    public List<Article> test(@RequestBody ArticleSourceRequest
            request) {
        return downloadService.run(request.toArticleSource());
    }

}
