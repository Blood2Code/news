package org.task.news;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.task.news.dto.NewsDto;
import org.task.news.dto.ResponseDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NewsApp extends Application {

    private int currentIndex = 0;
    private List<NewsDto> newsList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button filterButton = new Button("Filter");

        Label headlineLabel = new Label();
        Label descriptionLabel = new Label();
        Label dateTime = new Label();

        ComboBox<String> timePeriodComboBox = new ComboBox<>();
        timePeriodComboBox.getItems().addAll("Morning", "Day", "Evening");
        timePeriodComboBox.setValue("Morning");

        prevButton.setOnAction(event -> showPreviousNews(headlineLabel, descriptionLabel, dateTime));
        nextButton.setOnAction(event -> showNextNews(headlineLabel, descriptionLabel, dateTime));
        filterButton.setOnAction(event -> loadNewsByPeriod(timePeriodComboBox.getValue(), headlineLabel, descriptionLabel, dateTime));

        VBox vbox = new VBox(10, headlineLabel, descriptionLabel, dateTime, prevButton, nextButton, timePeriodComboBox, filterButton);
        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.setTitle("News Viewer");
        stage.show();

        loadNews(headlineLabel, descriptionLabel, dateTime);
    }

    /**
     * Method to show news by selected period
     * @param period
     * @param headlineLabel
     * @param descriptionLabel
     * @param dateTime
     */
    private void loadNewsByPeriod(String period, Label headlineLabel, Label descriptionLabel, Label dateTime) {
        String url = "http://localhost:8080/api/news/period?period=" + period.toLowerCase();
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ResponseDto<List<NewsDto>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseDto<List<NewsDto>>>() {}
            );

            if ((response.getBody() != null) && response.getBody().getSuccess() && (!response.getBody().getData().isEmpty())) {
                newsList = response.getBody().getData();
                displayNews(headlineLabel, descriptionLabel, dateTime);
            } else {
                headlineLabel.setText("There is no new news for the selected period.");
                descriptionLabel.setText("");
            }
        } catch (Exception e) {
            System.err.println("Error loading news: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *    Method to load news from the Spring Boot backend
     *
     * @param headlineLabel
     * @param descriptionLabel
     *
     */
    private void loadNews(Label headlineLabel, Label descriptionLabel, Label dateTime) {
        String url = "http://localhost:8080/api/news";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ResponseDto<List<NewsDto>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ResponseDto<List<NewsDto>>>() {}
            );

            if (response.getBody() != null && response.getBody().getSuccess()) {
                newsList = response.getBody().getData();

                if (!newsList.isEmpty()) {
                    displayNews(headlineLabel, descriptionLabel, dateTime);
                }
            } else {
                System.out.println("Error fetching news or empty response");
            }
        } catch (Exception e) {
            System.err.println("Error loading news: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to get previous news
     * @param headline
     * @param description
     */
    private void showPreviousNews(Label headline, Label description, Label dateTime) {
        if (currentIndex > 0) {
            currentIndex--;
            displayNews(headline, description, dateTime);
        }
    }

    /**
     * Method to get next news
     * @param headline
     * @param description
     */
    private void showNextNews(Label headline, Label description, Label dateTime) {
        if (currentIndex < newsList.size() - 1) {
            currentIndex++;
            displayNews(headline, description, dateTime);
        }
    }

    /**
     * Method to display news
     * @param headline
     * @param description
     */
    private void displayNews(Label headline, Label description, Label dateTime) {
        NewsDto news = newsList.get(currentIndex);
        headline.setText("Headline: " + news.getHeadline());
        description.setText("Description: " + news.getDescription());
        dateTime.setText("Published time: " + news.getPublicationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    /**
     * FX launcher
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
