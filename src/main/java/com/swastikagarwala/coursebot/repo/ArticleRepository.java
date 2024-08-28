package com.swastikagarwala.coursebot.repo;

import com.swastikagarwala.coursebot.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
