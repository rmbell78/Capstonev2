package com.example.capstone.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@code GameApplication} class serves as the entry point for the JavaFX application.
 * It extends the {@link javafx.application.Application} class and overrides the {@code start} method
 * to set up and display the initial stage and scene.
 */
public class GameApplication extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * 
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if an I/O error occurs during loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startLoader = new FXMLLoader(GameApplication.class.getResource("start-view.fxml"));
        Scene startMenuView = new Scene(startLoader.load(), 500, 500);
        stage.setTitle("Start Menu");
        stage.setScene(startMenuView);
        stage.show();
    }

    /**
     * The main method is ignored in correctly deployed JavaFX application.
     * main() serves as fallback in case the application is launched in a way
     * that doesn't support JavaFX.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}