package com.example.capstone.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    static ArrayList<Object> mapObjects = new ArrayList<>();
    private int xWidth, yHeight;
    static final int DEFAULT_SIZE = 30;
    private int hours = 5, day = 1, minutes;
    private final ArrayList<String> firstNames = new ArrayList<>(Arrays.asList("Bob", "Amy", "Jeff", "Doug", "Gertrude", "George"));

    /**
     * Constructs a new Map with default size.
     * <p>
     * Initializes the map with a default size defined by DEFAULT_SIZE.
     * If setting the map size fails, it catches the MapParametersException
     * and prints the exception message along with a specific message for the
     * default constructor.
     */
    public Map() {
        super();
        setMapSize();
    }

    /**
     * Sets the dimensions of the map.
     * <p>
     * Validates the provided dimensions against the maximum and minimum allowed
     * dimensions.
     * If the dimensions are out of the allowed range, it throws a
     * MapParametersException.
     * Otherwise, it sets the map's width and height to the provided dimensions.
     */
    private void setMapSize(){
        this.xWidth = Map.DEFAULT_SIZE;
        this.yHeight = Map.DEFAULT_SIZE;
    }

    /**
     * Creates a pawn on the map with the specified name and location and assigns it
     * to a house.
     * <p>
     * Validates the location of the pawn to ensure it is within the map boundaries
     * and not on the same location as another object. If the pawn's name already
     * exists,
     * a PawnException is thrown. If the location is invalid, a
     * MapParametersException is thrown.
     * Upon successful creation, the pawn is added to the specified house.
     *
     * @param name  the name of the pawn to create
     * @param x     the x-coordinate of the pawn's location
     * @param y     the y-coordinate of the pawn's location
     * @param house the house to assign the pawn to
     * @throws MapParametersException if the pawn's location is out of map
     *                                boundaries or occupied
     * @throws PawnException          if a pawn with the given name already exists
     * @throws HouseException         if adding the pawn to the house fails
     */
    public void createPawnAssignHouse(String name, int x, int y, House house)
            throws MapParametersException, PawnException, HouseException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("Pawn is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        } else if (pawnExists(name)) {
            throw new PawnException("There is already a pawn with that name.");
        } else if (house.isFull()){
            throw new HouseException("House is full");
        }
        else {
            mapObjects.add(new Pawn(name, x, y, house, this));
            house.addOccupants(getObjectAt(x, y));
        }
    }

    /**
     * Creates a warehouse on the map at the specified location.
     * <p>
     * Validates the location of the warehouse to ensure it is within the map
     * boundaries
     * and not on the same location as another object. If the location is invalid,
     * a MapParametersException is thrown.
     *
     * @param x the x-coordinate of the warehouse's location
     * @param y the y-coordinate of the warehouse's location
     * @throws MapParametersException if the warehouse's location is out of map
     *                                boundaries or occupied
     */
    public void createWarehouse(int x, int y) throws MapParametersException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("Warehouse is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        } else {
            mapObjects.add(new Warehouse(x, y));
        }
    }

    /**
     * Creates a house on the map at the specified location without checking for resources.
     * <p>
     * Validates the location of the house to ensure it is within the map boundaries
     * and not on the same location as another object. If the location is invalid,
     * a MapParametersException is thrown.
     *
     * @param x the x-coordinate of the house's location
     * @param y the y-coordinate of the house's location
     * @throws MapParametersException if the house's location is out of map
     *                                boundaries or occupied
     */
    public void createHouseNoResources(int x, int y) throws MapParametersException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("House is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        }
        mapObjects.add(new House(x, y));
    }

    /**
     * Creates a house on the map at the specified location, checking for required resources.
     * <p>
     * Validates the location of the house to ensure it is within the map boundaries
     * and not on the same location as another object. If the location is invalid,
     * a MapParametersException is thrown. If there are not enough resources,
     * a HouseException is thrown.
     *
     * @param x the x-coordinate of the house's location
     * @param y the y-coordinate of the house's location
     * @throws MapParametersException if the house's location is out of map
     *                                boundaries or occupied
     * @throws HouseException         if there are not enough resources to create the house
     */
    public void createHouse(int x, int y) throws MapParametersException, HouseException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("House is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        } else {
            if(((Warehouse) this.getWarehouses().getFirst()).getWood() >= 5){
                ((Warehouse) this.getWarehouses().getFirst()).useWood(5);
                mapObjects.add(new House(x, y));
            } else{
                throw new HouseException("Not enough wood!");
            }
        }
    }

    /**
     * Creates a bush on the map at the specified location.
     * <p>
     * Validates the location of the bush to ensure it is within the map boundaries
     * and not on the same location as another object. If the location is invalid,
     * a MapParametersException is thrown.
     *
     * @param x the x-coordinate of the bush's location
     * @param y the y-coordinate of the bush's location
     * @throws MapParametersException if the bush's location is out of map
     *                                boundaries or occupied
     */
    public void createBush(int x, int y) throws MapParametersException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("Bush is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        } else {
            mapObjects.add(new Bush(x, y));
        }
    }

    /**
     * Creates a tree on the map at the specified location.
     * <p>
     * Validates the location of the tree to ensure it is within the map boundaries
     * and not on the same location as another object. If the location is invalid,
     * a MapParametersException is thrown.
     *
     * @param x the x-coordinate of the tree's location
     * @param y the y-coordinate of the tree's location
     * @throws MapParametersException if the tree's location is out of map
     *                                boundaries or occupied
     */
    public void createTree(int x, int y) throws MapParametersException {
        if (x > xWidth | x <= 0 | y > yHeight | y <= 0) {
            throw new MapParametersException("Tree is not on map");
        } else if (isObjectAt(x, y)) {
            throw new MapParametersException("There is already something at that location");
        } else {
            mapObjects.add(new Tree(x, y));
        }
    }

    /**
     * Generates the initial pawns and assigns them to the first house on the map.
     * <p>
     * This method locates the first house on the map and attempts to create and assign
     * a specified number of starting pawns to it. The pawns are placed around the house
     * in the following order of preference: right, down, left, up. If a location is
     * already occupied or invalid, it moves to the next preferred location.
     * <p>
     * If an exception occurs during the creation or assignment of a pawn, the exception
     * message is printed to the console.
     */
    public void generateFirstPawns() {
        int houseX = 0, houseY = 0;
        House house = null;
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof House) {
                house = (House) mapObject;
                houseX = house.getX();
                houseY = house.getX();
            }
        }
        int NUM_STARTING_PAWNS = 3;
        for (int i = 0; i < NUM_STARTING_PAWNS; i++) {
            if (!isObjectAt(houseX + 1, houseY)) {
                try {
                    createPawnAssignHouse(firstNames.getFirst(), houseX + 1, houseY, house);
                    firstNames.removeFirst();
                } catch (PawnException | HouseException | MapParametersException mpe) {
                    System.out.println(mpe.getMessage());
                }
            } else if (!isObjectAt(houseX, houseY + 1)) {
                try {
                    createPawnAssignHouse(firstNames.getFirst(), houseX, houseY + 1, house);
                    firstNames.removeFirst();
                } catch (MapParametersException | PawnException | HouseException mpe) {
                    System.out.println(mpe.getMessage());
                }
            } else if (houseX - 1 != 0 && !isObjectAt(houseX - 1, houseY)) {
                try {
                    createPawnAssignHouse(firstNames.getFirst(), houseX - 1, houseY, house);
                    firstNames.removeFirst();
                } catch (MapParametersException | PawnException | HouseException mpe) {
                    System.out.println(mpe.getMessage());
                }
            } else if (houseY - 1 != 0 && !isObjectAt(houseX, houseY - 1)) {
                try {
                    createPawnAssignHouse(firstNames.getFirst(), houseX, houseY - 1, house);
                    firstNames.removeFirst();
                } catch (MapParametersException | PawnException | HouseException mpe) {
                    System.out.println(mpe.getMessage());
                }
            }
        }

    }

    /**
     * Retrieves the object located at the specified coordinates.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is located at the given (x, y) coordinates. If an object is found, it is returned.
     * If no object is found at the specified location, null is returned.
     *
     * @param x the x-coordinate of the location to check
     * @param y the y-coordinate of the location to check
     * @return the object at the specified coordinates, or null if no object is found
     */
    public Object getObjectAt(int x, int y) {
        Object returnObject = null;
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof Pawn pawn) {
                if (pawn.getX() == x && pawn.getY() == y) {
                    returnObject = pawn;
                }
            } else if (mapObject instanceof Warehouse wareHouse) {
                if (wareHouse.getX() == x && wareHouse.getY() == y) {
                    returnObject = wareHouse;
                }
            } else if (mapObject instanceof Tree tree) {
                if (tree.getX() == x && tree.getY() == y) {
                    returnObject = tree;
                }
            } else if (mapObject instanceof Bush bush) {
                if (bush.getX() == x && bush.getY() == y) {
                    returnObject = bush;
                }
            } else if (mapObject instanceof House house) {
                if (house.getX() == x && house.getY() == y) {
                    returnObject = house;
                }
            }
        }
        return returnObject;

    }

    /**
     * Checks if there is an object at the specified coordinates.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is located at the given (x, y) coordinates. If an object is found, it returns true.
     * If no object is found at the specified location, it returns false.
     *
     * @param x the x-coordinate of the location to check
     * @param y the y-coordinate of the location to check
     * @return true if an object is found at the specified coordinates, false otherwise
     */
    public boolean isObjectAt(int x, int y) {
        boolean isObjectAt = false;
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof Warehouse wareHouse) {
                if (wareHouse.getX() == x && wareHouse.getY() == y) {
                    isObjectAt = true;
                }
            } else if (mapObject instanceof Tree tree) {
                if (tree.getX() == x && tree.getY() == y) {
                    isObjectAt = true;
                }
            } else if (mapObject instanceof Bush bush) {
                if (bush.getX() == x && bush.getY() == y) {
                    isObjectAt = true;
                }
            } else if (mapObject instanceof House house) {
                if (house.getX() == x && house.getY() == y) {
                    isObjectAt = true;
                }
            } else if (mapObject instanceof Pawn pawn) {
                if (pawn.getX() == x && pawn.getY() == y) {
                    isObjectAt = true;
                }
            }
        }
        return isObjectAt;

    }

    /**
     * Retrieves the house located at the specified coordinates.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is a house located at the given (x, y) coordinates. If a house is found, it is returned.
     * If no house is found at the specified location, null is returned.
     *
     * @param x the x-coordinate of the location to check
     * @param y the y-coordinate of the location to check
     * @return the house at the specified coordinates, or null if no house is found
     */
    public Object getHouseAt(int x, int y){
        Object returnObject = null;
        for(Object object : mapObjects){
            if(object instanceof House house){
                if(house.getX() == x && house.getY() == y){
                    returnObject = house;
                }
            }
        }
        return returnObject;
    }

    /**
     * Generates resources on the map.
     * <p>
     * This method generates bushes and trees at random locations on the map.
     * It ensures that the generated locations are within the map boundaries
     * and not already occupied by another object. If a valid location is found,
     * a bush or tree is created at that location. If an exception occurs during
     * the creation of a resource, the exception message is printed to the console.
     */
    public void generateResources() {
        int MAX_BUSH = 4;
        for (int i = 1; i <= MAX_BUSH; i++) {
            boolean goodGen = false;
            int x, y;
            do {
                x = (int) (Math.random() * xWidth);
                y = (int) (Math.random() * yHeight);
                if (x != 0 && y != 0 && !isObjectAt(x, y)) {
                    goodGen = true;
                }
            } while (!goodGen);
            try {
                createBush(x, y);
            } catch (MapParametersException mpe) {
                System.out.println(mpe.getMessage());
            }
        }
        int MAX_TREE = 4;
        for (int i = 1; i <= MAX_TREE; i++) {
            boolean goodGen = false;
            int x, y;
            do {
                x = (int) (Math.random() * xWidth);
                y = (int) (Math.random() * yHeight);
                if (x != 0 && y != 0 && !isObjectAt(x, y)) {
                    goodGen = true;
                }
            } while (!goodGen);
            try {
                createTree(x, y);
            } catch (MapParametersException mpe) {
                System.out.println(mpe.getMessage());
            }

        }

    }

    /**
     * Gets the width of the map.
     *
     * @return the width of the map
     */
    public int getXWidth() {
        return xWidth;
    }

    /**
     * Gets the height of the map.
     *
     * @return the height of the map
     */
    public int getYHeight() {
        return yHeight;
    }


    /**
     * Retrieves a list of all pawns on the map.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is an instance of the Pawn class. If an object is a pawn, it is added to the
     * pawnList. The method returns the list of all pawns found on the map.
     *
     * @return an ArrayList containing all pawns on the map
     */
    public ArrayList<Object> getPawns() {
        ArrayList<Object> pawnList = new ArrayList<>();
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof Pawn) {
                pawnList.add(mapObject);
            }
        }
        return pawnList;
    }

    /**
     * Retrieves a list of all warehouses on the map.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is an instance of the Warehouse class. If an object is a warehouse, it is added to the
     * warehouseList. The method returns the list of all warehouses found on the map.
     *
     * @return an ArrayList containing all warehouses on the map
     */
    public ArrayList<Object> getWarehouses() {
        ArrayList<Object> warehouseList = new ArrayList<>();
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof Warehouse) {
                warehouseList.add(mapObject);
            }
        }
        return warehouseList;
    }

    /**
     * Retrieves a list of all houses on the map.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is an instance of the House class. If an object is a house, it is added to the
     * houseList. The method returns the list of all houses found on the map.
     *
     * @return an ArrayList containing all houses on the map
     */
    public ArrayList<Object> getHouses() {
        ArrayList<Object> houseList = new ArrayList<>();
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof House) {
                houseList.add(mapObject);
            }
        }
        return houseList;
    }

    /**
     * Retrieves a list of all resources on the map.
     * <p>
     * This method iterates through the list of map objects and checks if any object
     * is an instance of the Resource class. If an object is a resource, it is added to the
     * resourceList. The method returns the list of all resources found on the map.
     *
     * @return an ArrayList containing all resources on the map
     */
    public ArrayList<Object> getResources() {
        ArrayList<Object> resourceList = new ArrayList<>();
        for (Object mapObject : mapObjects) {
            if (mapObject instanceof Resource) {
                resourceList.add(mapObject);
            }
        }
        return resourceList;
    }

    /**
     * Retrieves a pawn by its name.
     * <p>
     * This method iterates through the list of pawns on the map and checks if any pawn
     * has the specified name. If a pawn with the given name is found, it is returned.
     * If no pawn with the specified name is found, null is returned.
     *
     * @param name the name of the pawn to retrieve
     * @return the pawn with the specified name, or null if no such pawn is found
     */
    public Object getPawn(String name) {
        ArrayList<Object> pawnList;
        pawnList = getPawns();
        Pawn returnPawn = null;
        for (Object o : pawnList) {
            Pawn pawn = (Pawn) o;
            if (pawn.getName().equals(name)) {
                returnPawn = pawn;
            }
        }
        return returnPawn;
    }

    /**
     * Checks if a pawn with the specified name exists on the map.
     * <p>
     * This method iterates through the list of pawns on the map and checks if any pawn
     * has the specified name. If a pawn with the given name is found, it returns true.
     * If no pawn with the specified name is found, it returns false.
     *
     * @param name the name of the pawn to check for existence
     * @return true if a pawn with the specified name exists, false otherwise
     */
    public boolean pawnExists(String name) {
        ArrayList<Object> pawnList;
        boolean pawnExists = false;
        pawnList = getPawns();
        for (Object o : pawnList) {
            Pawn pawn = (Pawn) o;
            if (pawn.getName().equals(name)) {
                pawnExists = true;
                break;
            }
        }
        return pawnExists;
    }
    
    /**
     * Advances the game time by 15 minutes and updates all pawns on the map.
     * <p>
     * This method increments the minutes by 15. If the minutes reach 60, they are reset to 0 and the hours are incremented by 1.
     * If the hours reach 24, they are reset to 0 and the day is incremented by 1.
     * <p>
     * After updating the time, this method retrieves all pawns on the map and calls their tick method to update their state.
     */
    public void tick() {
        minutes += 15;
        if (minutes == 60) {
            minutes = 0;
            hours++;
        }
        if (hours == 24) {
            hours = 0;
            day++;
        }

        ArrayList<Object> pawnList;
        pawnList = this.getPawns();
        for (Object o : pawnList) {
            Pawn pawn = (Pawn) o;
            pawn.tick();
        }
    }
    /**
     * Gets the current hour in the game.
     *
     * @return the current hour
     */
    public int getHours() {
        return hours;
    }

    /**
     * Gets the current minutes in the game.
     *
     * @return the current minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Gets the current day in the game.
     *
     * @return the current day
     */
    public int getDay() {
        return day;
    }

}
