package org.task.news.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.task.news.dto.NewsDto;
import org.task.news.entity.News;
import org.task.news.mapper.NewsMapper;
import org.task.news.repository.NewsRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    /**
     * News api service website
     */
    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines";
    /**
     * Api key for News api service
     */
    private static final String API_KEY = "648aea349892458b8f13458472d6b80b";
    private final HttpClient httpClient;

    public NewsService(NewsRepository newsRepository) {
        this.httpClient = HttpClient.newHttpClient();
        this.newsRepository = newsRepository;
    }

    /**
     * @param period
     * @return List of News as DTO filtered by period
     */
    public List<NewsDto> getByPeriod(String period) {
        String start = null;
        String end = null;

        switch (period.toLowerCase()) {
            case "morning":
                start = LocalDate.now().atTime(6, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                end = LocalDate.now().atTime(12, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
            case "day":
                start = LocalDate.now().atTime(12, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                end = LocalDate.now().atTime(18, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
            case "evening":
                start = LocalDate.now().atTime(18, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                end = LocalDate.now().atTime(23, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                break;
        }
        return newsRepository.findByPublicationTimeBetween(start, end).stream().map(NewsMapper::newsToNewsDto).toList();
    }

    public List<NewsDto> getAll() {
        return newsRepository.findAll().stream().map(NewsMapper::newsToNewsDto).toList();
    }

    public List<News> fetchNews() throws IOException, InterruptedException {
        String requestUrl = NEWS_API_URL + "?country=us&apiKey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return parseNewsResponse(response.body());
    }

    /**
     * @param responseBody
     * @return List of parsed response news
     * @throws IOException
     */
    private List<News> parseNewsResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseBody);
        List<News> newsList = new ArrayList<>();

        JsonNode articles = root.path("articles");
        for (JsonNode article : articles) {
            String headline = article.path("title").asText();
            String description = article.path("description").asText();
            String publicationTime = article.path("publishedAt").asText();

            News news = new News();
            news.setHeadline(headline);
            news.setDescription(description);
            news.setPublicationTime(OffsetDateTime.parse(publicationTime).toLocalDateTime());

            newsList.add(news);
        }

        return newsList;
    }
}
