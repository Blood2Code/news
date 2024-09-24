package org.task.newsfx.dto;


import java.time.LocalDateTime;

public class NewsDto {
    private Long id;
    private String headline;
    private String description;
    private LocalDateTime publicationTime;

    public NewsDto(Long id, String headline, String description, LocalDateTime publicationTime) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.publicationTime = publicationTime;
    }

    public NewsDto() {}

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
