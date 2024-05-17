package com.example.capstone.classes;

/**
 * The {@code Pawn} class represents a pawn in the game, which can perform various tasks
 * such as harvesting resources, returning to a warehouse, and sleeping. Pawns have health
 * and hunger attributes that need to be managed to keep them alive.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class Pawn extends Inventory implements Placeable {
    private final int[] location = new int[2];
    // [x][y] base 1
    final private int MAX_HEALTH = 10;
    final private int MAX_HUNGER = 10;
    private final String name;
    private final Map map;
    private Object assignedWarehouse;
    private Object assignedResource;
    private Object assignedHouse;
    private int health;
    private int hunger;
    private boolean alive;
    private int task; // 0 for sleep, 1 for harvest resources, 2 for return to warehouse.

    /**
     * Constructs a new Pawn with the specified name, location, house, and map.
     * <p>
     * Initializes the pawn's health and hunger to their maximum values.
     * Sets the pawn's location to the specified coordinates.
     * Assigns the pawn to the specified house and map.
     * Sets the pawn's alive status to true.
     *
     * @param name  the name of the pawn
     * @param x     the x-coordinate of the pawn's location
     * @param y     the y-coordinate of the pawn's location
     * @param house the house to assign the pawn to
     * @param map   the map to assign the pawn to
     */
    public Pawn(String name, int x, int y, House house, Map map) {
        super(0, 0, 5, 5);
        health = MAX_HEALTH;
        hunger = MAX_HUNGER;
        location[0] = x;
        location[1] = y;
        this.map = map;
        this.assignedHouse = house;
        this.name = name;
        alive = true;
    }

    /**
     * Updates the state of the pawn based on the current game time.
     * <p>
     * If the pawn is alive, it performs different tasks depending on the time of day:
     * - Between 6 AM and 8 PM, the pawn works.
     * - Outside of these hours, the pawn sleeps.
     * <p>
     * The pawn's hunger decreases at 8 AM and 4 PM. If hunger drops to 3 or below, the pawn eats.
     * If hunger reaches 0, the pawn takes damage.
     */
    public void tick() {
        int time = map.getHours();
        if (alive) {
            if (time > 6 && time < 20) {
                work();
            } else if (time < 6 || time > 20) {
                setTask(0);
                sleep();
            }
            if (time == 8 || time == 16) {
                if (hunger > 0) {
                    hunger--;
                }
            }
            if (hunger <= 3) {
                eat();
            }
            if (hunger == 0) {
                damage(1);
            }
        }
    }

    /**
     * Performs the work task assigned to the pawn.
     * <p>
     * The pawn will either gather resources or deliver them to a warehouse based on its current task.
     * If the pawn is assigned to a resource, it will check if it needs to gather wood or food.
     * If the resource is a tree and the pawn's wood quantity is below the maximum, it will set the task to gather wood.
     * If the resource is a bush and the pawn's food quantity is below the maximum, it will set the task to gather food.
     * If the resource quantities are at their maximum, it will set the task to deliver resources to a warehouse.
     * <p>
     * The pawn will move to the assigned resource or warehouse and perform the appropriate action when it arrives.
     */
    public void work() {
        Resource resource = (Resource) assignedResource;
        if (assignedResource != null) {
            if (resource instanceof Tree) {
                if (super.getWood() < super.getWoodMax()) {
                    setTask(1);
                } else {
                    setTask(2);
                }
            } else if (resource instanceof Bush) {
                if (super.getFood() < super.getFoodMax()) {
                    setTask(1);
                } else {
                    setTask(2);
                }
            }
            if (task == 1) {
                if (isAtLocation(assignedResource)) {
                    harvest();
                } else {
                    goTo(assignedResource);
                }
            } else if (task == 2) {
                if (isAtLocation(assignedWarehouse)) {
                    deliverResources();
                } else {
                    goTo(assignedWarehouse);
                }
            }
        }
    }

    /**
     * Harvests resources from the assigned resource.
     * <p>
     * If the assigned resource is a tree, the pawn will add one unit of wood to its inventory.
     * If the assigned resource is a bush, the pawn will add one unit of food to its inventory.
     */
    public void harvest() {
        Resource resource = (Resource) assignedResource;
        if (resource instanceof Tree) {
            super.addWood(1);
        } else {
            super.addFood(1);
        }
    }

    /**
     * Delivers resources to the assigned warehouse.
     * <p>
     * This method transfers the pawn's food and wood quantities to the assigned warehouse.
     * The pawn's food and wood quantities are reset to zero after the transfer.
     */
    public void deliverResources() {
        Warehouse warehouse = (Warehouse) assignedWarehouse;
        super.useFood(warehouse.addFood(super.getFood()));
        super.useWood(warehouse.addWood(super.getWood()));
    }

    /**
     * Makes the pawn sleep.
     * <p>
     * If the pawn is not at its assigned house, it will move towards it.
     */
    public void sleep() {
        if (!isAtLocation(assignedHouse)) {
            goTo(assignedHouse);
        }
    }

    /**
     * Makes the pawn eat food from the assigned warehouse.
     * <p>
     * If the warehouse has at least one unit of food, the pawn consumes it and
     * resets its hunger to the maximum value.
     */
    public void eat() {
        Warehouse warehouse = (Warehouse) assignedWarehouse;
        if (warehouse.useFood(1) == 1) {
            hunger = MAX_HUNGER;
        }
    }

    /**
     * Retrieves the name of the pawn.
     *
     * @return the name of the pawn
     */
    public String getName() {
        return name;
    }

    /**
     * Assigns the pawn to a new house.
     * <p>
     * If the pawn is already a member of the specified house, a HouseException is thrown.
     * If the specified house is full, a HouseException is thrown.
     * Otherwise, the pawn is assigned to the new house and added to its occupants.
     *
     * @param house the house to assign the pawn to
     * @throws HouseException if the pawn is already a member of the house or if the house is full
     */
    public void assignHouse(Object house) throws HouseException {
        House workingHouse = (House) house;
        if (assignedHouse.equals(workingHouse)) {
            throw new HouseException("Already a member of that house");
        } else if (workingHouse.isFull()) {
            throw new HouseException("That house is full");

        } else {
            this.assignedHouse = house;
            workingHouse.addOccupants(this);
        }
    }

    /**
     * Assigns the pawn to a warehouse at the specified coordinates.
     * <p>
     * Validates the coordinates to ensure they are within the map boundaries.
     * If the coordinates are out of range, a MapParametersException is thrown.
     * If there is no warehouse at the specified location, a MapParametersException is thrown.
     * Otherwise, the pawn is assigned to the warehouse at the specified location.
     *
     * @param x the x-coordinate of the warehouse's location
     * @param y the y-coordinate of the warehouse's location
     * @throws MapParametersException if the coordinates are out of range or no warehouse is found
     */
    public void assignWarehouse(int x, int y) throws MapParametersException {
        Object warehouse = map.getObjectAt(x, y);
        if (x <= 0 | x > map.getXWidth() | y <= 0 | y > map.getYHeight()) {
            throw new MapParametersException("Cant select a warehouse not on the map");
        } else if (!(warehouse instanceof Warehouse)) {
            throw new MapParametersException("No Warehouse at that location");
        } else {
            assignedWarehouse = warehouse;
        }
    }

    /**
     * Assigns the pawn to a resource at the specified coordinates.
     * <p>
     * Validates the coordinates to ensure they are within the map boundaries.
     * If the coordinates are out of range, a MapParametersException is thrown.
     * If there is no resource at the specified location, a MapParametersException is thrown.
     * Otherwise, the pawn is assigned to the resource at the specified location.
     *
     * @param x the x-coordinate of the resource's location
     * @param y the y-coordinate of the resource's location
     * @throws MapParametersException if the coordinates are out of range or no resource is found
     */
    public void assignResource(int x, int y) throws MapParametersException {
        Object resource = map.getObjectAt(x, y);
        if (x <= 0 | x > map.getXWidth() | y <= 0 | y > map.getYHeight()) {
            throw new MapParametersException("Cant select a resource not on the map");
        } else if (!(resource instanceof Resource)) {
            throw new MapParametersException("No resource at that location");
        } else {
            assignedResource = resource;
        }
    }

    public void damage(int damage) {
        if (health - damage <= 0) {
            health = 0;
            alive = false;
        } else {
            health = health - damage;
        }
    }

    public Object getAssignedHouse() {
        return assignedHouse;
    }

    public Object getAssignedResource() {
        return assignedResource;
    }

    /**
     * Retrieves the current job of the pawn.
     * <p>
     * The job is determined based on the type of resource the pawn is assigned to.
     * If the pawn is assigned to a tree, the job is "Gather wood".
     * If the pawn is assigned to a bush, the job is "Gather food".
     * If the pawn is not assigned to any resource, the job is "None".
     *
     * @return the current job of the pawn
     */
    public String getJob() {
        String job = "None";
        if (assignedResource instanceof Tree) {
            job = "Gather wood";
        }
        if (assignedResource instanceof Bush) {
            job = "Gather food";
        }
        return job;
    }

    /**
     * Retrieves the current hunger level of the pawn.
     *
     * @return the current hunger level
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Retrieves the maximum hunger level of the pawn.
     *
     * @return the maximum hunger level
     */
    public int getMaxHunger() {
        return MAX_HUNGER;
    }

    /**
     * Retrieves the current health level of the pawn.
     *
     * @return the current health level
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retrieves the maximum health level of the pawn.
     *
     * @return the maximum health level
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    /**
     * Moves the pawn towards the specified object.
     * <p>
     * The pawn will move one step at a time towards the target object's location.
     * The movement is determined by comparing the pawn's current location with the target's location.
     * The pawn will prioritize moving along the axis with the greater distance to the target.
     *
     * @param object the target object to move towards
     */
    public void goTo(Object object) {
        int targetX = 0, targetY = 0;
        if (object instanceof House target) {
            targetX = target.getX();
            targetY = target.getY();
        } else if (object instanceof Warehouse target) {
            targetX = target.getX();
            targetY = target.getY();
        } else if (object instanceof Resource target) {
            targetX = target.getX();
            targetY = target.getY();
        }
        int deltaX = location[0] - targetX;
        int deltaY = location[1] - targetY;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX < 0) {
                location[0]++;
            } else if (deltaX > 0) {
                location[0]--;
            }
        } else {
            if (deltaY < 0) {
                location[1]++;
            } else if (deltaY > 0) {
                location[1]--;
            }
        }
    }

    /**
     * Checks if the pawn is at the specified location.
     * <p>
     * This method compares the pawn's current location with the target object's location.
     * It supports checking against House, Warehouse, and Resource objects.
     * If the object is null or of an unsupported type, it returns false.
     *
     * @param object the target object to check location against
     * @return true if the pawn is at the target object's location, false otherwise
     */
    private boolean isAtLocation(Object object) {
        switch (object) {
            case House house -> {
                return house.getX() == location[0] && house.getY() == location[1];
            }
            case Warehouse wareHouse -> {
                return wareHouse.getX() == location[0] && wareHouse.getY() == location[1];
            }
            case Resource resource -> {
                return resource.getX() == location[0] && resource.getY() == location[1];
            }
            case null, default -> {
                return false;
            }
        }
    }

    /**
     * Retrieves the current task of the pawn.
     *
     * @return the current task of the pawn
     */
    public int getTask() {
        return task;
    }

    /**
     * Sets the task for the pawn.
     * <p>
     * The task determines what action the pawn will perform. Valid tasks are:
     * 0 for sleep, 1 for harvest resources, and 2 for return to warehouse.
     *
     * @param task the task to set for the pawn
     */
    private void setTask(int task) {
        this.task = task;
    }

    /**
     * Checks if the pawn is alive.
     *
     * @return true if the pawn is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Retrieves the x-coordinate of the pawn's location.
     *
     * @return the x-coordinate of the pawn's location
     */
    @Override
    public int getX() {
        return location[0];
    }

    /**
     * Retrieves the y-coordinate of the pawn's location.
     *
     * @return the y-coordinate of the pawn's location
     */
    @Override
    public int getY() {
        return location[1];
    }
}
