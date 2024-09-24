module org.task.newsfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires spring.web;
    requires spring.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens org.task.newsfx to javafx.fxml;
    exports org.task.newsfx;
    exports org.task.newsfx.dto;

}