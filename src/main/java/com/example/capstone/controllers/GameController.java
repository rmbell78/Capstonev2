package com.example.capstone.controllers;

import com.example.capstone.classes.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameController {
    @FXML
    Canvas GameCanvas;

    @FXML
    VBox PawnList;

    int boxDimensions = 22;
    Map map;
    Button pawnButton;
    ArrayList<Button> buttonList;

    public void initialize() {
        map = startGame();
        drawMap();
        buttonList = new ArrayList<>();
        ArrayList<Object> pawnList = map.getPawns();
        for (Object pawn : pawnList) {
            Pawn workingPawn = (Pawn) pawn;
            pawnButton = new Button(workingPawn.getName());
            pawnButton.setOnAction(eventHandler);
            PawnList.getChildren().add(pawnButton);
            buttonList.add(pawnButton);
        }

    }


    public Map startGame() {
        Map map = new Map();
        map.generateResources();
        boolean goodGen = false;
        int createHouseAtX = map.getXWidth() / 2;
        while (!goodGen) {
            try {
                map.createHouse(createHouseAtX, map.getYHeight() / 2);
                goodGen = true;
            } catch (MapParametersException mpe) {
                System.out.println(mpe);
                createHouseAtX++;
            }
        }

        int createWareHouseAtX = 10;
        goodGen = false;
        while (!goodGen) {
            try {
                map.createWarehouse(createWareHouseAtX, map.getYHeight() / 2);
                goodGen = true;
            } catch (MapParametersException mpe) {
                System.out.println(mpe);
                createWareHouseAtX++;
            }
        }


        map.generateResources();
        map.generateFirstPawns();

        Warehouse warehouse = (Warehouse) map.getWarehouses().getFirst();
        for (Object pawn : map.getPawns()) {
            Pawn workingPawn = (Pawn) pawn;
            try {
                workingPawn.assignWarehouse(warehouse.getX(), warehouse.getY());
            } catch (MapParametersException mpe) {
                System.out.println(mpe);
            }
        }
        return map;
    }

    public void drawMap() {
        ArrayList<Object> pawns = map.getPawns();
        ArrayList<Object> houses = map.getHouses();
        ArrayList<Object> resources = map.getResources();
        ArrayList<Object> warehouses = map.getWarehouses();
        int x = map.getXWidth();
        int y = map.getYHeight();
        GraphicsContext gc = GameCanvas.getGraphicsContext2D();
        Image pawnImage = new Image(String.valueOf(getClass().getResource("images/Pawn.jpg")), boxDimensions, boxDimensions, false, false);
        Image bushImage = new Image(String.valueOf(getClass().getResource("images/Tomato.jpg")), boxDimensions, boxDimensions, false, false);
        Image treeImage = new Image(String.valueOf(getClass().getResource("images/Tree.jpg")), boxDimensions, boxDimensions, false, false);
        Image wareHouseImage = new Image(String.valueOf(getClass().getResource("images/Warehouse.jpeg")), boxDimensions, boxDimensions, false, false);
        Image houseImage = new Image(String.valueOf(getClass().getResource("images/House.png")), boxDimensions, boxDimensions, false, false);
        for (int i = 0; i <= y; i++) {
            for (int j = 0; j <= x; j++) {
                gc.setStroke(Color.LIGHTBLUE);
                gc.setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.2));
                gc.fillRect(i * boxDimensions, j * boxDimensions, boxDimensions, boxDimensions);
                gc.strokeRect(i * boxDimensions, j * boxDimensions, boxDimensions, boxDimensions);
            }
        }

        for (Object pawn : pawns) {
            Pawn workingPawn = (Pawn) pawn;
            gc.drawImage(pawnImage, workingPawn.getX() * boxDimensions, workingPawn.getY() * boxDimensions);
        }

        for (Object house : houses) {
            House workingHouse = (House) house;
            gc.drawImage(houseImage, workingHouse.getX() * boxDimensions, workingHouse.getY() * boxDimensions);
        }

        for (Object resource : resources) {
            if (resource instanceof Tree) {
                Tree workingTree = (Tree) resource;
                gc.drawImage(treeImage, workingTree.getX() * boxDimensions, workingTree.getY() * boxDimensions);
            } else if (resource instanceof Bush) {
                Bush workingBush = (Bush) resource;
                gc.drawImage(bushImage, workingBush.getX() * boxDimensions, workingBush.getY() * boxDimensions);
            }
        }

        for (Object warehouse : warehouses) {
            Warehouse workingWareHouse = (Warehouse) warehouse;
            gc.drawImage(wareHouseImage, workingWareHouse.getX() * boxDimensions, workingWareHouse.getY() * boxDimensions);
        }


    }

    @FXML
    protected void onMenuButtonClick() {
        System.out.println("Pressed");
    }

    @FXML
    protected void onNextButtonClick() {
        GraphicsContext gc = GameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, GameCanvas.getHeight(), GameCanvas.getWidth());
        map.tick();
        drawMap();
    }

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("Handled");
            System.out.println(buttonList);
            System.out.println(event.getSource().getClass());
            if(event.getSource() == pawnButton){
                System.out.println(event.getSource().toString());
                System.out.println("Handled 2");
            }

        }
    };
}