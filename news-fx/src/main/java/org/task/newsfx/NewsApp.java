package org.task.newsfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.task.newsfx.dto.NewsDto;
import org.task.newsfx.dto.ResponseDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method(String.valueOf(HttpMethod.GET), HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                ResponseDto<List<Map<String, Object>>> responseDto = objectMapper.readValue(responseBody, new TypeReference<>() {
                });

                if (responseDto.getSuccess() && !responseDto.getData().isEmpty()) {
                    newsList = new ArrayList<>();
                    display(headlineLabel, descriptionLabel, dateTime, responseDto);
                } else {
                    headlineLabel.setText("There is no new news for the selected period.");
                    descriptionLabel.setText("");
                }
            } else {
                System.err.println("Error loading news: HTTP status code " + response.statusCode());
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

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method(String.valueOf(HttpMethod.GET), HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                ResponseDto<List<Map<String, Object>>> responseDto = objectMapper.readValue(responseBody, new TypeReference<ResponseDto<List<Map<String, Object>>>>() {});

                if (responseDto.getSuccess()) {
                    display(headlineLabel, descriptionLabel, dateTime, responseDto);
                } else {
                    System.out.println("Error fetching news or empty response: " + responseDto.getMessage());
                }
            } else {
                System.err.println("Error loading news: HTTP status code " + response.statusCode());
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
