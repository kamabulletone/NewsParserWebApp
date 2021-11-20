package com.webapp.newsparser.Controllers;

import com.webapp.newsparser.Filters.NewsRecordFilter;
import com.webapp.newsparser.Models.NewsRecord;
import com.webapp.newsparser.Services.NewsParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/news")
public class ParseNewsController {

    @Autowired
    NewsParserService newsParserService;

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getNewsTags() {
        List<String> newsTags = newsParserService.getNewsTags();
        if (newsTags.isEmpty()) {
            return new ResponseEntity<>(newsTags, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsTags, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllNews(NewsRecordFilter filter,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdOn"));

        Page<NewsRecord> news = newsParserService.getAll(filter, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("news", news.getContent());
        response.put("currentPage", news.getNumber());
        response.put("totalItems", news.getTotalElements());
        response.put("totalPages", news.getTotalPages());

        if (news.getContent().isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
