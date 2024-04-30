package com.example.capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InformationController {

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Object loadView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-view.fxml")));
        Scene loadScene = new Scene((Parent) loadView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(loadScene);
        window.show();
    }
}
