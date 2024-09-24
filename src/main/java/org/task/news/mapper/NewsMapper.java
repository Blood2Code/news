package org.task.news.mapper;

import org.task.news.dto.NewsDto;
import org.task.news.entity.News;

public class NewsMapper {

    public static News newsDtoToNews(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setHeadline(newsDto.getHeadline());
        news.setDescription(news.getDescription());
        news.setPublicationTime(newsDto.getPublicationTime());
        return news;
    }

    public static NewsDto newsToNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setHeadline(news.getHeadline());
        newsDto.setDescription(news.getDescription());
        newsDto.setPublicationTime(news.getPublicationTime());
        return newsDto;
    }
}
