package com.example.project_for_university;

import com.example.project_for_university.enums.Component;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static StringProperty host = new SimpleStringProperty("http://localhost:9000");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Component.MAIN.getPath()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Расчет материалов");
        stage.setScene(scene);
        stage.setWidth(1250);
        stage.setMinWidth(1000);
        stage.setHeight(825);
        stage.setMinHeight(650);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}