package com.example.capstone.classes;

public class Pawn extends Inventory implements Placeable {
    private int health;
    private int hunger;
    private final int[] location = new int[2];
    Object assignedWarehouse;
    Object assignedResource;
    Object assignedHouse;
    // [x][y] base 1
    final private int MAX_HEALTH = 10;
    final private int MAX_HUNGER = 10;
    private boolean alive;
    private final String name;
    private final Map map;
    private int task; //0 for sleep, 1 for harvest resources, 2 for return to warehouse.

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

    public void tick() {
        int time = map.getHours();
        if(alive){
            if (time > 6 && time < 20) {
                work();
            } else if (time < 6 | time > 20) {
                setTask(0);
                sleep();
            }
            if (time == 8 | time == 16) {
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

    public void work() {
        Resource resource = (Resource) assignedResource;
        if(assignedResource != null) {
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

    public void harvest() {
        Resource resource = (Resource) assignedResource;
        if (resource instanceof Tree) {
            super.addWood(1);
        } else {
            super.addFood(1);
        }
    }

    public void deliverResources() {
        Warehouse warehouse = (Warehouse) assignedWarehouse;
        super.useFood(warehouse.addFood(super.getFood()));
        super.useWood(warehouse.addWood(super.getWood()));
    }

    public void sleep() {
        if (!isAtLocation(assignedHouse)) {
            goTo(assignedHouse);
        }

    }

    public void eat() {
        Warehouse warehouse = (Warehouse) assignedWarehouse;
        if (warehouse.useFood(1) == 1) {
            hunger = MAX_HUNGER;
        }
    }

    public String getName() {
        return name;
    }

    public void assignHouse(Object house) throws HouseException {
        House workingHouse = (House) house;
        if(assignedHouse.equals(workingHouse)){
            throw new HouseException("Already a member of that house");
        } else if(workingHouse.isFull()){
            throw new HouseException("That house is full");

        } else{
            this.assignedHouse = house;
            workingHouse.addOccupants(this);
        }
    }

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

    public Object getAssignedHouse(){return assignedHouse;}

    public Object getAssignedResource() {
        return assignedResource;
    }

    public String getJob(){
        String job = "None";
        if(assignedResource instanceof Tree){
            job = "Gather wood";
        }
        if(assignedResource instanceof Bush){
            job = "Gather food";
        }
        return job;
    }

    public int getHunger() {
        return hunger;
    }

    public int getMaxHunger(){return MAX_HUNGER;}

    public int getHealth() {
        return health;
    }

    public int getMaxHealth(){return MAX_HEALTH;}

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

    // Must be checked for type object, already checked by assign...
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

    private void setTask(int task){
        this.task = task;
    }

    public int getTask(){
        return task;
    }

    public boolean isAlive(){
        return alive;
    }
    @Override
    public int getX() {
        return location[0];
    }

    @Override
    public int getY() {
        return location[1];
    }
}
