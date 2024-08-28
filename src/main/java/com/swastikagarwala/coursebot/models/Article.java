package com.swastikagarwala.coursebot.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "article")
public class Article {
    public static class StringListConverter implements AttributeConverter<List<String>, String> {
        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            return attribute != null ? String.join(",", attribute) : null;
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            return dbData != null ? Arrays.stream(dbData.split(",")).collect(Collectors.toList()) : null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(name = "link_url", nullable = false)
    private String linkUrl;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "topics", nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> topics;

    @Column(name = "updated", nullable = false)
    private LocalDate updated;

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

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }
}
