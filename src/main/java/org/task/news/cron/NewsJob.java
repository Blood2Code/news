package org.task.news.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.task.news.entity.News;
import org.task.news.repository.NewsRepository;
import org.task.news.service.NewsService;

import java.io.IOException;
import java.util.List;

@Service
public class NewsJob {

    @Autowired
    private NewsService newsApiService;

    @Autowired
    private NewsRepository newsRepository;

    @Scheduled(cron = "* */20 * * * *")
    public void fetchNewsEvery20Minutes() {
        try {
            List<News> newsList = newsApiService.fetchNews();
            newsRepository.saveAll(newsList);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
