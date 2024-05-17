package com.example.capstone.controllers;

import com.example.capstone.classes.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import static javafx.scene.paint.Color.DODGERBLUE;

public class GameController {
    @FXML
    public Text Pawn_Currently_At;

    @FXML
    public Canvas Game_Canvas;

    @FXML
    public VBox Pawn_List;

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
    public Text House_Count;

    @FXML
    public Text Pawn_Name;

    @FXML
    public Text Population_Count;

    @FXML
    public Text Food_Count;

    @FXML
    public Text Wood_Count;

    @FXML
    public Text Pawn_Current_Task;
    public Text Time;
    public VBox Text_Box;
    public ScrollPane Text_Scroll_Pane;


    int boxDimensions = 22;
    Map map;
    Button pawnButton;
    ArrayList<Button> pawnButtonList;
    String selectedPawn;

    public void initialize() {
        map = startGame();
        drawMap();
        pawnButtonList = new ArrayList<>();
        ArrayList<Object> pawnList = map.getPawns();
        for (Object pawn : pawnList) {
            addPawnButton(pawn);
        }
        Text_Scroll_Pane.setVvalue(1.0);

    }


    public void addPawnButton(Object pawn) {
        Pawn workingPawn = (Pawn) pawn;
        pawnButton = new Button(workingPawn.getName());
        pawnButton.setOnAction(this::handle);
        pawnButton.idProperty();
        pawnButton.setId(workingPawn.getName());
        Pawn_List.getChildren().add(pawnButton);
        pawnButtonList.add(pawnButton);
    }

    private Map startGame() {
        Map map = new Map();
        map.generateResources();
        boolean goodGen = false;
        int createHouseAtX = map.getXWidth() / 2;
        while (!goodGen) {
            try {
                map.createHouseNoResources(createHouseAtX, map.getYHeight() / 2);
                goodGen = true;
            } catch (MapParametersException mpe) {
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
            } catch (MapParametersException ignored) {
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
        Image deadPawn = new Image(String.valueOf(getClass().getResource("images/Tombstone.jpg")), boxDimensions, boxDimensions, false, false);
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
            if (((Pawn) pawn).isAlive()) {
                gc.drawImage(pawnImage, workingPawn.getX() * boxDimensions, workingPawn.getY() * boxDimensions);
            } else {
                gc.drawImage(deadPawn, workingPawn.getX() * boxDimensions, workingPawn.getY() * boxDimensions);
            }
        }

        for (Object house : houses) {
            House workingHouse = (House) house;
            gc.drawImage(houseImage, workingHouse.getX() * boxDimensions, workingHouse.getY() * boxDimensions);
        }

        for (Object resource : resources) {
            if (resource instanceof Tree workingTree) {
                gc.drawImage(treeImage, workingTree.getX() * boxDimensions, workingTree.getY() * boxDimensions);
            } else if (resource instanceof Bush workingBush) {
                gc.drawImage(bushImage, workingBush.getX() * boxDimensions, workingBush.getY() * boxDimensions);
            }
        }

        for (Object warehouse : warehouses) {
            Warehouse workingWareHouse = (Warehouse) warehouse;
            gc.drawImage(wareHouseImage, workingWareHouse.getX() * boxDimensions, workingWareHouse.getY() * boxDimensions);
        }

        Population_Count.setText("Population: " + map.getPawns().size() + " / " + map.getHouses().size() * House.HOUSE_CAPACITY);
        Food_Count.setText("Food: " + ((Warehouse) warehouses.getFirst()).getFood() + " / " + Warehouse.MAX_FOOD);
        Wood_Count.setText("Wood: " + ((Warehouse) warehouses.getFirst()).getWood() + " / " + Warehouse.MAX_WOOD);
        House_Count.setText("Houses: " + map.getHouses().size());
        if (map.getMinutes() == 0) {
            Time.setText("Day: " + map.getDay() + " " + map.getHours() + ":00");
        } else {
            Time.setText("Day: " + map.getDay() + " " + map.getHours() + ":" + map.getMinutes());
        }

        for (Object pawn : pawns) {
            Pawn working_pawn = (Pawn) pawn;
            if (working_pawn.getAssignedResource() == null) {
                addMessage(working_pawn.getName() + " has no job!");
            }
            if (working_pawn.getAssignedHouse() == null) {
                addMessage(working_pawn.getName() + " has no house!");
            }
        }

    }

    public void setStats(String setPawnName) {
        Pawn pawn = (Pawn) map.getPawn(setPawnName);
        Pawn_Name.setText(setPawnName);
        Pawn_Health.setText("Health: " + pawn.getHealth() + " / " + pawn.getMaxHealth());
        Pawn_Food.setText("Food: " + pawn.getFood() + " / " + pawn.getFoodMax());
        Pawn_Hunger.setText("Hunger: " + pawn.getHunger() + " / " + pawn.getMaxHunger());
        Pawn_Wood.setText("Wood: " + pawn.getWood() + " / " + pawn.getWoodMax());
        Pawn_Job.setText("Job: " + pawn.getJob());

        House pawn_House = (House) pawn.getAssignedHouse();
        Resource pawn_Resource = (Resource) pawn.getAssignedResource();


        Pawn_House.setText("House at X: " + pawn_House.getX() + ", Y: " + pawn_House.getY());

        switch (pawn_Resource) {
            case null -> Pawn_Resource.setText("None");
            case Tree ignored1 ->
                    Pawn_Resource.setText("Tree at X:" + pawn_Resource.getX() + ", Y: " + pawn_Resource.getY());
            case Bush ignored ->
                    Pawn_Resource.setText("Bush at X:" + pawn_Resource.getX() + ", Y: " + pawn_Resource.getY());
            default -> {
            }
        }

        switch (pawn.getTask()) {
            case 0:
                Pawn_Current_Task.setText("Sleeping");
                break;
            case 1:
                Pawn_Current_Task.setText("Harvesting resource");
                break;
            case 2:
                Pawn_Current_Task.setText("Returning to warehouse");
                break;
        }

        Pawn_Currently_At.setText(pawn.getX() + ", " + pawn.getY());

    }

    private void addMessage(String message) {
        Text messageText = new Text(message);
        Text_Box.getChildren().add(messageText);
        Text_Scroll_Pane.setVvalue(1.0);
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
    protected void onExitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onNextButtonClick() {
        GraphicsContext gc = Game_Canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, Game_Canvas.getHeight(), Game_Canvas.getWidth());
        map.tick();
        if (selectedPawn != null) {
            setStats(selectedPawn);
        }
        drawMap();
    }


    private boolean isSetResourceButton(Event event) {
        if (event.getSource() instanceof Button) {
            String idString = ((Button) event.getSource()).getId();
            return idString.substring(0, idString.indexOf(":")).equals("assign_resource");
        }
        return false;
    }

    private boolean isSetHouseButton(Event event) {
        if (event.getSource() instanceof Button) {
            String idString = ((Button) event.getSource()).getId();
            return idString.substring(0, idString.indexOf(":")).equals("assign_house");
        }
        return false;
    }

    public void handle(Event event) {
        if (event.getSource() instanceof TextField text) {
            String textString = String.valueOf(text.getCharacters());
            Scene scene = text.getScene();
            Stage stage = (Stage) scene.getWindow();
            switch (((TextField) event.getSource()).getId()) {
                case "addAHouse":
                    int x = 100, y = 100;
                    boolean valid = true;
                    boolean assignment = true;
                    try {
                        x = Integer.parseInt(textString.substring(0, textString.indexOf(",")));
                    } catch (NumberFormatException | StringIndexOutOfBoundsException nfe) {
                        addMessage("Not valid input");
                        valid = false;
                        assignment = false;
                    }
                    try {
                        y = Integer.parseInt(textString.substring(textString.indexOf(",") + 1));
                    } catch (NumberFormatException | StringIndexOutOfBoundsException nfe) {
                        addMessage("Not valid input");
                        valid = false;
                        assignment = false;
                    }
                    if (valid) {
                        try {
                            map.createHouse(x, y);
                        } catch (MapParametersException | HouseException e) {
                            addMessage(e.getMessage());
                            assignment = false;
                        }
                    }
                    if (assignment) {
                        addMessage("House added successfully");
                    }

                    stage.close();
                    drawMap();
                    break;
                case "createPawn":
                    text = (TextField) event.getSource();
                    textString = String.valueOf(text.getCharacters());
                    int tryHouse = 0;
                    int tryX = 10;
                    boolean goodAssignment = false;
                    ArrayList<Object> houseList = map.getHouses();
                    do {
                        try {
                            map.createPawnAssignHouse(textString, tryX, 10, (House) houseList.get(tryHouse));
                            Pawn newPawn = (Pawn) map.getPawns().getLast();
                            Warehouse warehouse = (Warehouse) map.getWarehouses().getFirst();
                            System.out.println("Assign warehouse at: " + warehouse.getX() + " , " + warehouse.getY());
                            try {
                                newPawn.assignWarehouse(warehouse.getX(), warehouse.getY());
                            } catch (MapParametersException e) {
                                throw new RuntimeException(e);
                            }
                            addPawnButton(map.getPawns().getLast());
                            goodAssignment = true;
                        } catch (PawnException e) {
                            addMessage(e.getMessage());
                            break;
                        } catch (HouseException e) {
                            tryHouse++;
                            if (tryHouse > houseList.size() - 1) {
                                addMessage("Build more housing first!");
                                break;
                            }
                        } catch (MapParametersException e) {
                            tryX++;
                        }
                    } while (!goodAssignment);


                    stage.close();
                    drawMap();
                    break;
            }
        } else if (isPawnButton(event)) {
            Button pawnButton = (Button) event.getSource();
            setStats(pawnButton.getId());
            selectedPawn = pawnButton.getId();
        } else if (isSetResourceButton(event)) {
            boolean assignment = true;
            Button resourceButton = (Button) event.getSource();
            String idString = resourceButton.getId();
            Pawn pawn = (Pawn) map.getPawn(selectedPawn);
            int x = Integer.parseInt(idString.substring(idString.indexOf(" ") + 1, idString.indexOf(",")));
            int y = Integer.parseInt(idString.substring(idString.indexOf(",") + 1));
            try {
                pawn.assignResource(x, y);
            } catch (MapParametersException e) {
                assignment = false;
            }
            setStats(selectedPawn);
            if (assignment) {
                addMessage("Resource assignment for " + selectedPawn + " Succeeded");
            } else {
                addMessage("Resource assignment for " + selectedPawn + " Failed");
            }
            Scene scene = resourceButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();

        } else if (isSetHouseButton(event)) {
            boolean assignment = true;
            Button houseButton = (Button) event.getSource();
            String idString = houseButton.getId();
            Pawn pawn = (Pawn) map.getPawn(selectedPawn);
            House oldHouse = (House) pawn.getAssignedHouse();
            int x = Integer.parseInt(idString.substring(idString.indexOf(" ") + 1, idString.indexOf(",")));
            int y = Integer.parseInt(idString.substring(idString.indexOf(",") + 1));
            try {
                pawn.assignHouse(map.getHouseAt(x, y));
            } catch (HouseException he) {
                addMessage(he.getMessage());
                assignment = false;
            }
            if (assignment) {
                addMessage("House assignment successful");
                oldHouse.removeOccupant(pawn);
            }
            setStats(selectedPawn);
            Scene scene = houseButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        }

    }

    public void assignResource() {
        if (selectedPawn != null) {
            ArrayList<Object> resources = map.getResources();
            VBox box = new VBox();

            StackPane topLabel = new StackPane();
            Rectangle topRectangle = new Rectangle(200, 20);
            topRectangle.setFill(DODGERBLUE);
            Text lText = new Text("Assign resource for: " + selectedPawn);
            topLabel.getChildren().addAll(topRectangle, lText);
            box.getChildren().add(topLabel);

            for (Object resource : resources) {
                String buttonText;
                Button button = null;
                if (resource instanceof Tree) {
                    buttonText = "Tree at X: " + ((Tree) resource).getX() + " Y: " + ((Tree) resource).getY();
                    button = new Button(buttonText);
                    button.setId("assign_resource: " + ((Tree) resource).getX() + "," + ((Tree) resource).getY());
                    button.setOnAction(this::handle);

                } else if (resource instanceof Bush) {
                    buttonText = "Bush at X: " + ((Bush) resource).getX() + " Y: " + ((Bush) resource).getY();
                    button = new Button(buttonText);
                    button.setId("assign_resource: " + ((Bush) resource).getX() + "," + ((Bush) resource).getY());
                    button.setOnAction(this::handle);
                }
                box.getChildren().add(button);
            }


            Scene assignResource = new Scene(box, 200, 450);

            Stage stage = new Stage();
            stage.setScene(assignResource);
            stage.show();
        }


    }

    public void assignHouse() {
        if (selectedPawn != null) {
            ArrayList<Object> houses = map.getHouses();
            VBox box = new VBox();

            StackPane topLabel = new StackPane();
            Rectangle topRectangle = new Rectangle(200, 20);
            topRectangle.setFill(DODGERBLUE);
            Text lText = new Text("Assign house for: " + selectedPawn);
            topLabel.getChildren().addAll(topRectangle, lText);
            box.getChildren().add(topLabel);

            for (Object house : houses) {
                String buttonText;
                Button button;
                buttonText = "House at X: " + ((House) house).getX() + " Y: " + ((House) house).getY();
                button = new Button(buttonText);
                button.setId("assign_house: " + ((House) house).getX() + "," + ((House) house).getY());
                button.setOnAction(this::handle);
                box.getChildren().add(button);
            }


            Scene assignHouse = new Scene(box, 200, 450);

            Stage stage = new Stage();
            stage.setScene(assignHouse);
            stage.show();
        }
    }

    public void addAHouse() {
        VBox box = new VBox();

        StackPane topLabel = new StackPane();
        Rectangle topRectangle = new Rectangle(200, 20);
        topRectangle.setFill(DODGERBLUE);
        Text lText = new Text("Add a house");
        topLabel.getChildren().addAll(topRectangle, lText);
        box.getChildren().add(topLabel);
        TextField tf = new TextField("X, Y");
        tf.setId("addAHouse");
        tf.setOnAction(this::handle);
        box.getChildren().add(tf);

        Scene addHouse = new Scene(box, 200, 200);
        Stage stage = new Stage();
        stage.setScene(addHouse);
        stage.show();
    }

    public void createPawn() {
        VBox box = new VBox();
        StackPane topLabel = new StackPane();
        Rectangle topRectangle = new Rectangle(200, 20);
        topRectangle.setFill(DODGERBLUE);
        Text lText = new Text("Add a pawn");
        topLabel.getChildren().addAll(topRectangle, lText);
        box.getChildren().add(topLabel);

        TextField tf = new TextField("Pawns name");
        tf.setId("createPawn");
        tf.setOnAction(this::handle);

        box.getChildren().add(tf);

        Scene createPawn = new Scene(box, 200, 200);
        Stage stage = new Stage();
        stage.setScene(createPawn);
        stage.show();

    }
}