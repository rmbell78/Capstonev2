package com.example.capstone.controllers;

import com.example.capstone.classes.Map;
import com.example.capstone.classes.MapParametersException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
//TODO finish implementing all displays
//TODO Add a currently doing view, going home, gathering resource, delivering resource . . .
//TODO Implement working time and end time
//TODO Implement changing stuff for pawns. Resource, House, work time . . .


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
