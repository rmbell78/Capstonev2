package com.example.capstone.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startLoader = new FXMLLoader(GameApplication.class.getResource("start-view.fxml"));
        Scene startMenuView = new Scene(startLoader.load(), 500, 500);
        stage.setTitle("Start Menu");
        stage.setScene(startMenuView);
        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }
}
