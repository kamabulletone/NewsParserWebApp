package com.webapp.newsparser.Controllers;

import com.webapp.newsparser.Models.NewsRecord;
import com.webapp.newsparser.Services.NewsParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/news")
public class ParseNewsController {

    @Autowired
    NewsParserService newsParserService;

    @GetMapping
    public List<NewsRecord> getNews() {
        return newsParserService.getLastNews(); //get last 4 news
    }
}
