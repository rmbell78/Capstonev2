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
        Scene startMenuView = new Scene(startLoader.load(), 500,500);
        stage.setTitle("Start Menu");
        stage.setScene(startMenuView);
        stage.show();

        /*
        FXMLLoader gameLoader = new FXMLLoader(GameApplication.class.getResource("game-view.fxml"));
        Scene gameView = new Scene(gameLoader.load(), 1100, 850);
        stage.setTitle("A Game!");
        stage.setScene(gameView);
        stage.show();
         */
    }

    public static void main(String[] args) {
        launch();
    }
}