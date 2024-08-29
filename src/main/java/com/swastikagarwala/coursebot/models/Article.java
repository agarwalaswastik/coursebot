package com.swastikagarwala.coursebot.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(name = "link_url", nullable = false, columnDefinition = "TEXT")
    private String linkUrl;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "updated", nullable = false)
    private LocalDate updated;

    public Article() {}

    public Article(String linkUrl, String summary, LocalDate updated) {
        this.linkUrl = linkUrl;
        this.summary = summary;
        this.updated = updated;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "{ " +
                "articleId=" + articleId +
                ", summary='" + summary + '\'' +
                " }\n";
    }
}
