package org.task.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.news.entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT * FROM news n WHERE n.publication_time BETWEEN ?1 AND ?2", nativeQuery = true)
    List<News> findByPublicationTimeBetween(String start, String end);
}
