package com.swastikagarwala.coursebot.controllers;

import com.swastikagarwala.coursebot.models.Article;
import com.swastikagarwala.coursebot.services.ArticleService;
import com.swastikagarwala.coursebot.services.OpenAIService;
import com.swastikagarwala.coursebot.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private OpenAIService openAIService;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody String link_url) {
        if (!link_url.contains("medium.com")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medium Article Required");
        }

        List<String> articleContent;

        try {
            articleContent = scraperService.getArticleContent(link_url);
        } catch (UnknownHostException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid URL");
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }

        String summary = openAIService.getSummary(articleContent);
        Article article = new Article(link_url, summary, LocalDate.now());

        articleService.saveArticle(article);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }
}
