package com.example.capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The {@code StartController} class handles the actions for the start menu buttons.
 * It manages the navigation to different scenes such as the game view and information view.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class StartController {
    public Button start_Menu_Start_Game;
    public Button start_Menu_Information;
    public Button start_Menu_Exit;

    /**
     * Handles the action when the "Start Game" button is clicked.
     * Loads the game view and sets it as the current scene.
     *
     * @param actionEvent the event triggered by clicking the button
     * @throws IOException if the game view FXML file cannot be loaded
     */
    public void onStartGameButtonClicked(ActionEvent actionEvent) throws IOException {
        Object loadView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        Scene loadScene = new Scene((Parent) loadView);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(loadScene);
        window.show();
    }

    /**
     * Handles the action when the "Exit" button is clicked.
     * Exits the application.
     */
    public void onExitButtonClicked() {
        System.exit(0);
    }

    /**
     * Handles the action when the "Information" button is clicked.
     * Loads the information view and sets it as the current scene.
     *
     * @param actionEvent the event triggered by clicking the button
     * @throws IOException if the information view FXML file cannot be loaded
     */
    public void onInformationButtonClicked(ActionEvent actionEvent) throws IOException {
        Object loadView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("information-view.fxml")));
        Scene loadScene = new Scene((Parent) loadView);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(loadScene);
        window.show();
    }
}