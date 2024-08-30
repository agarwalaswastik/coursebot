package com.swastikagarwala.coursebot.controllers;

import com.swastikagarwala.coursebot.models.Article;
import com.swastikagarwala.coursebot.services.ArticleService;
import com.swastikagarwala.coursebot.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private OpenAIService openAIService;

    @GetMapping
    public ResponseEntity<?> getCourse(@RequestParam String prompt) {
        List<Article> articles = articleService.getAllArticles();
        List<Article> selectedArticles = openAIService.createCourse(prompt, articles);
        return ResponseEntity.status(HttpStatus.OK).body(selectedArticles);
    }
}
