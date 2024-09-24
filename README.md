# News
News viewer (Spring boot + JavaFx)


This project consists of a Spring Boot backend and a JavaFX-based frontend (`news-fx` module). The backend interacts with a MySQL database running in Docker Compose, while the frontend displays news data by consuming the backend API.

## Prerequisites

- **Java 17** or higher
- **Gradle** installed on your system
- **Docker** and **Docker Compose** for running MySQL
- **MySQL** as part of Docker Compose

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Blood2Code/news.git
```

### 2. Start MySQL using Docker Compose
```back 
docker-compose up -d
```
This will start MySQL in the background.


### 3. Running the Spring Boot Backend
```back
./gradlew bootRun
```
The Spring Boot server will be running on http://localhost:8080.

### 4. Running the news-fx Module
```bach
cd news-fx
gradle run
```
This will launch the JavaFX application, which connects to the backend and displays the news.

### 5. Accessing the Application
* **Backend API**: The Spring Boot backend is available at:
  http://localhost:8080/api/news

- **Frontend**: The JavaFX application (news-fx) will open a window displaying the news fetched from the backend.

## Notes
- **Ensure MySQL is running: Make sure the MySQL container is running before starting the Spring Boot application.
  Database Configuration: Ensure that the MySQL connection settings in application.yml or application.properties match your Docker Compose setup.**
