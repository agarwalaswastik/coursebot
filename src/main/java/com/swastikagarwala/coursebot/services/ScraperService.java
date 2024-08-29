package com.swastikagarwala.coursebot.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {
    public List<String> getArticleContent(String link_url) throws IOException {
        Document document = Jsoup.connect(link_url).get();
        Elements paragraphs = document.select("p.pw-post-body-paragraph");

        List<String> content = new ArrayList<>();

        for (Element paragraph : paragraphs) {
            content.add(paragraph.text());
        }

        return content;
    }
}
