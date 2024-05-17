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

public class StartController {
    public Button start_Menu_Start_Game;
    public Button start_Menu_Information;
    public Button start_Menu_Exit;

    public void onStartGameButtonClicked(ActionEvent actionEvent) throws IOException {
        Object loadView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("game-view.fxml")));
        Scene loadScene = new Scene((Parent) loadView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(loadScene);
        window.show();
    }


    public void onExitButtonClicked() {
        System.exit(0);
    }

    public void onInformationButtonClicked(ActionEvent actionEvent) throws IOException {
        Object loadView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("information-view.fxml")));
        Scene loadScene = new Scene((Parent) loadView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(loadScene);
        window.show();
    }
}
