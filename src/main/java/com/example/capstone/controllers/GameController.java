package com.example.capstone.controllers;

import com.example.capstone.classes.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.scene.paint.Color.DODGERBLUE;

/**
 * Main game controller class facilitating interaction between the user and the model via gui elements,
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
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


    private Map map;
    private ArrayList<Button> pawnButtonList;
    private String selectedPawn;

    /**
     * Initializes the game controller.
     * <p>
     * This method sets up the game by starting the game, drawing the map, and adding pawn buttons.
     * It also initializes the pawn button list and sets the scroll pane to the bottom.
     */
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


    /**
     * Adds a button for the specified pawn to the Pawn_List and sets up its action handler.
     * <p>
     * This method creates a new button for the given pawn, sets its action handler to the handle method,
     * assigns an ID based on the pawn's name, and adds it to the Pawn_List and pawnButtonList.
     *
     * @param pawn The pawn object for which the button is to be created and added.
     */
    private void addPawnButton(Object pawn) {
        Pawn workingPawn = (Pawn) pawn;
        Button pawnButton = new Button(workingPawn.getName());
        pawnButton.setOnAction(this::handle);
        pawnButton.idProperty();
        pawnButton.setId(workingPawn.getName());
        Pawn_List.getChildren().add(pawnButton);
        pawnButtonList.add(pawnButton);
    }

    /**
     * Starts the game by initializing the map and generating initial resources and structures.
     * <p>
     * This method creates a new map, generates resources, and attempts to place a house at the center of the map.
     * If the initial placement fails due to map parameters, it adjusts the x-coordinate until a valid location is found.
     *
     * @return The initialized map with generated resources and structures.
     */
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

    /**
     * Draws the map and updates the game canvas with the current state of the game.
     * <p>
     * This method retrieves the list of pawns, houses, resources, and warehouses from the map.
     * It then iterates through each list to draw the respective objects on the game canvas.
     * The method also updates the population count, food count, wood count, house count, and time display.
     * If any pawn does not have an assigned resource or house, a message is added to the message box.
     */
    private void drawMap() {
        ArrayList<Object> pawns = map.getPawns();
        ArrayList<Object> houses = map.getHouses();
        ArrayList<Object> resources = map.getResources();
        ArrayList<Object> warehouses = map.getWarehouses();
        int x = map.getXWidth();
        int y = map.getYHeight();
        GraphicsContext gc = Game_Canvas.getGraphicsContext2D();
        int boxDimensions = 22;
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

    /**
     * Sets the statistics for the specified pawn and updates the UI elements with the pawn's details.
     * <p>
     * This method retrieves the pawn object using the provided pawn name and updates various UI elements
     * with the pawn's health, food, hunger, wood, job, house location, resource assignment, current task,
     * and current location.
     *
     * @param setPawnName The name of the pawn whose statistics are to be set and displayed.
     */
    private void setStats(String setPawnName) {
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

    /**
     * Adds a message to the message box and scrolls to the bottom.
     * <p>
     * This method creates a new Text node with the provided message,
     * adds it to the Text_Box, and ensures the Text_Scroll_Pane is
     * scrolled to the bottom to display the latest message.
     *
     * @param message The message to be added to the message box.
     */
    private void addMessage(String message) {
        Text messageText = new Text(message);
        Text_Box.getChildren().add(messageText);
        Text_Scroll_Pane.setVvalue(1.0);
    }

    /**
     * Checks if the event source is a pawn button.
     * <p>
     * This method verifies if the event source is an instance of Button and if it exists in the pawnButtonList.
     * If both conditions are met, it returns true, indicating that the event source is a pawn button.
     * Otherwise, it returns false.
     *
     * @param event The event to be checked.
     * @return {@code true} if the event source is a pawn button, {@code false} otherwise.
     */
    private boolean isPawnButton(Event event) {
        if (event.getSource() instanceof Button) {
            for (Button pawnButton : pawnButtonList) {
                if (pawnButton == event.getSource()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Exits the application when the exit button is clicked.
     * <p>
     * This method is triggered by the exit button click event and terminates the application.
     */
    @FXML
    public void onExitButtonClick() {
        System.exit(0);
    }

    /**
     * Advances the game state to the next tick when the next button is clicked.
     * <p>
     * This method clears the game canvas, advances the game time by one tick, updates the selected pawn's statistics,
     * and redraws the map to reflect the new game state.
     */
    @FXML
    public void onNextButtonClick() {
        GraphicsContext gc = Game_Canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, Game_Canvas.getHeight(), Game_Canvas.getWidth());
        map.tick();
        if (selectedPawn != null) {
            setStats(selectedPawn);
        }
        drawMap();
    }


    /**
     * Checks if the event source is a button for assigning a resource.
     * <p>
     * This method verifies if the event source is an instance of Button and if its ID starts with "assign_resource".
     * If both conditions are met, it returns true, indicating that the event source is a resource assignment button.
     * Otherwise, it returns false.
     *
     * @param event The event to be checked.
     * @return {@code true} if the event source is a resource assignment button, {@code false} otherwise.
     */
    private boolean isSetResourceButton(Event event) {
        if (event.getSource() instanceof Button) {
            String idString = ((Button) event.getSource()).getId();
            return idString.substring(0, idString.indexOf(":")).equals("assign_resource");
        }
        return false;
    }

    /**
     * Checks if the event source is a button for assigning a house.
     * <p>
     * This method verifies if the event source is an instance of Button and if its ID starts with "assign_house".
     * If both conditions are met, it returns true, indicating that the event source is a house assignment button.
     * Otherwise, it returns false.
     *
     * @param event The event to be checked.
     * @return {@code true} if the event source is a house assignment button, {@code false} otherwise.
     */
    private boolean isSetHouseButton(Event event) {
        if (event.getSource() instanceof Button) {
            String idString = ((Button) event.getSource()).getId();
            return idString.substring(0, idString.indexOf(":")).equals("assign_house");
        }
        return false;
    }

    /**
     * Handles various events triggered by the user interface.
     * <p>
     * This method processes events from different sources such as TextFields and Buttons.
     * Depending on the event source, it performs actions like adding a house, creating a pawn,
     * assigning resources, or assigning houses. It also updates the game state and UI elements
     * accordingly.
     *
     * @param event The event triggered by the user interface.
     */
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

    /**
     * Assigns a resource to the selected pawn.
     * <p>
     * This method creates a new window with a list of available resources (trees and bushes).
     * The user can select a resource to assign to the currently selected pawn.
     * If a resource is selected, it assigns the resource to the pawn and updates the game state.
     */
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

    /**
     * Assigns a house to the selected pawn.
     * <p>
     * This method creates a new window with a list of available houses.
     * The user can select a house to assign to the currently selected pawn.
     * If a house is selected, it assigns the house to the pawn and updates the game state.
     */
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

    /**
     * Opens a window to add a new house to the map.
     * <p>
     * This method creates a new window with a text field for entering the coordinates (X, Y) of the new house.
     * When the user enters the coordinates and presses Enter, the handle method is triggered to process the input.
     * The new house is then added to the map at the specified coordinates if they are valid.
     */
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

    /**
     * Opens a window to create a new pawn.
     * <p>
     * This method creates a new window with a text field for entering the name of the new pawn.
     * When the user enters the name and presses Enter, the handle method is triggered to process the input.
     * The new pawn is then added to the map with the specified name if it is valid.
     */
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