package org.task.news.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String headline;

    @Column(columnDefinition = "Text")
    private String description;

    @Column(nullable = false)
    private LocalDateTime publicationTime;

    public News(Long id, String headline, String description, LocalDateTime publicationTime) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.publicationTime = publicationTime;
    }

    public News() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }
}
