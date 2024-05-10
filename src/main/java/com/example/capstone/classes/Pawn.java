package com.example.capstone.classes;

public class Pawn extends Inventory implements Placeable {
    private int health;
    private int hunger;
    private int[] location = new int[2];
    Object assignedWarehouse;
    Object assignedResource;
    Object assignedHouse;
    // [x][y] base 1
    final private int MAX_HEALTH = 10;
    final private int MAX_HUNGER = 10;
    private int moveSpeed = 1;
    private boolean alive;
    private String name = "NA";
    private Map map;

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
        if (time > 6 && time < 20) {
            work();
            System.out.println("Working");
        } else if (time < 6 | time > 20) {
            sleep();
            System.out.println("Sleeping");
        }
        if (time % 2 == 0) {
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

    public void work() {
        Resource resource = (Resource) assignedResource;
        if(assignedResource != null) {
            int workMode = 0; // 1 for harvest resource 2 for return to warehouse
            if (resource.getResourceType().equals("tree")) {
                if (super.getWood() < super.getWoodMax()) {
                    workMode = 1;
                } else {
                    workMode = 2;
                }
            } else if (resource.getResourceType().equals("bush")) {
                if (super.getFood() < super.getFoodMax()) {
                    workMode = 1;
                } else {
                    workMode = 2;
                }
            }
            if (workMode == 1) {
                if (isAtLocation(assignedResource)) {
                    harvest();
                } else {
                    goTo(assignedResource);
                }
            } else if (workMode == 2) {
                if (isAtLocation(assignedWarehouse)) {
                    deliverResources();
                } else {
                    goTo(assignedWarehouse);
                }
            }
        }
    }

    public void harvest() {
        // testing
        System.out.println("Harvest!!");
        Resource resource = (Resource) assignedResource;
        if (resource.getResourceType().equals("tree")) {
            super.addWood(1);
        } else {
            super.addFood(1);
        }
    }

    public void deliverResources() {
        Warehouse warehouse = (Warehouse) assignedWarehouse;
        // testing
        System.out.println("delivering resources!!");
        super.useFood(super.getFood() - warehouse.addFood(super.getFood()));
        super.useWood(super.getWood() - warehouse.addWood(super.getWood()));
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

    public void setName(String name) throws PawnException {
        if (map.pawnExists(name)) {
            throw new PawnException("A pawn with that name already exists");
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void assignHouse(Object house) {
        this.assignedHouse = house;
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
            alive = false;
        } else {
            health -= damage;
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
        int spacesMoved = 0;
        if (object instanceof House) {
            House target = (House) object;
            targetX = target.getX();
            targetY = target.getY();
        } else if (object instanceof Warehouse) {
            Warehouse target = (Warehouse) object;
            targetX = target.getX();
            targetY = target.getY();
        } else if (object instanceof Resource) {
            Resource target = (Resource) object;
            targetX = target.getX();
            targetY = target.getY();
        }
        do {
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
                spacesMoved++;
            }
        } while (spacesMoved < moveSpeed);

    }

    // Must be checked for type object, already checked by assign...
    private boolean isAtLocation(Object object) {
        if (object instanceof House) {
            House house = (House) object;
            if (house.getX() == location[0] && house.getY() == location[1]) {
                return true;
            } else {
                return false;
            }
        } else if (object instanceof Warehouse) {
            Warehouse wareHouse = (Warehouse) object;
            if (wareHouse.getX() == location[0] && wareHouse.getY() == location[1]) {
                return true;
            } else {
                return false;
            }
        } else if (object instanceof Resource) {
            Resource resource = (Resource) object;
            if (resource.getX() == location[0] && resource.getY() == location[1]) {
                return true;
            } else {
                return false;
            }
        } else { // Id like something else here, an exception doesnt feel right nor does just
            // returning false
            // But should never encounter a bad object here
            return false;
        }
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
