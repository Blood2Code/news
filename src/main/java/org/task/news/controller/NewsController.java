package org.task.news.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.news.dto.NewsDto;
import org.task.news.dto.ResponseDto;
import org.task.news.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * @param period
     * @return News by selected day time period
     */
    @GetMapping("/period")
    public ResponseEntity<ResponseDto<List<NewsDto>>> getAllNewsByPeriod(@RequestParam String period) {
        try {
            List<NewsDto> newsList = newsService.getByPeriod(period);
            return ResponseEntity.ok(new ResponseDto<>(true, "News fetched successfully", newsList, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>(false, "Something went wrong: " + e.getMessage(), null));
        }
    }


    /**
     * @return All news in database
     */
    @GetMapping()
    public ResponseEntity<ResponseDto<List<NewsDto>>> getAllNews() {
        try {
            List<NewsDto> newsList = newsService.getAll();
            return ResponseEntity.ok(new ResponseDto<>(true, "All news fetched successfully", newsList, 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>("Something went wrong: " + e.getMessage()));
        }
    }


}
