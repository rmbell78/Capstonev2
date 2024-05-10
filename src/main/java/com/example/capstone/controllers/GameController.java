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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameController {
    @FXML
    public Text Pawn_Schedule;

    @FXML
    public Canvas Game_Canvas;

    @FXML
    public VBox Pawn_List;

    @FXML
    public Text Pawn_Warehouse;

    @FXML
    public Text Pawn_House;

    @FXML
    public Text Pawn_Resource;

    @FXML
    public Text Pawn_Wood;

    @FXML
    public Text Pawn_Food;

    @FXML
    public Text Pawn_Job;

    @FXML
    public Text Pawn_Hunger;

    @FXML
    public Text Pawn_Health;

    @FXML
    public Rectangle House_Count;

    @FXML
    public Text Pawn_Name;


    int boxDimensions = 22;
    Map map;
    Button pawnButton;
    ArrayList<Button> pawnButtonList;

    public void initialize() {
        map = startGame();
        drawMap();
        pawnButtonList = new ArrayList<>();
        ArrayList<Object> pawnList = map.getPawns();
        for (Object pawn : pawnList) {
            Pawn workingPawn = (Pawn) pawn;
            pawnButton = new Button(workingPawn.getName());
            pawnButton.setOnAction(eventHandler);
            pawnButton.idProperty();
            pawnButton.setId(workingPawn.getName());
            Pawn_List.getChildren().add(pawnButton);
            pawnButtonList.add(pawnButton);
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
        GraphicsContext gc = Game_Canvas.getGraphicsContext2D();
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

    public void setStats(String setPawnName) {
        Pawn pawn = (Pawn) map.getPawn(setPawnName);
        Pawn_Name.setText(setPawnName);
        Pawn_Health.setText("Health: " + String.valueOf(pawn.getHealth()) + " / " + String.valueOf(pawn.getMaxHealth()));
        Pawn_Food.setText("Food: " + String.valueOf(pawn.getFood()) + " / " + String.valueOf(pawn.getFoodMax()));
        Pawn_Hunger.setText("Hunger: " + String.valueOf(pawn.getHunger()) + " / " + String.valueOf(pawn.getMaxHunger()));
        Pawn_Wood.setText("Wood: " + String.valueOf(pawn.getWood()) + " / " + String.valueOf(pawn.getWoodMax()));
        Pawn_Job.setText("Job: " + pawn.getJob());

        House pawn_House = (House) pawn.getAssignedHouse();
        Resource pawn_Resource = (Resource) pawn.getAssignedResource();


        Pawn_House.setText("House at X: " + pawn_House.getX() + ", Y: " + pawn_House.getY());

        switch (pawn_Resource) {
            case null -> Pawn_Resource.setText("None");
            case Tree tree ->
                    Pawn_Resource.setText("Tree at X:" + pawn_Resource.getX() + ", Y: " + pawn_Resource.getY());
            case Bush bush ->
                    Pawn_Resource.setText("Tree at X:" + pawn_Resource.getX() + ", Y: " + pawn_Resource.getY());
            default -> {
            }
        }


    }

    public boolean isPawnButton(Event event) {
        if (event.getSource() instanceof Button) {
            for (Button pawnButton : pawnButtonList) {
                if (pawnButton == event.getSource()) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    protected void onMenuButtonClick() {
        System.out.println("Pressed");
    }

    @FXML
    protected void onNextButtonClick() {
        GraphicsContext gc = Game_Canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, Game_Canvas.getHeight(), Game_Canvas.getWidth());
        map.tick();
        drawMap();
    }

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            if (isPawnButton(event)) {
                Button pawnButton = (Button) event.getSource();
                setStats(pawnButton.getId());
            }

        }
    };
}