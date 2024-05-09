package com.example.capstone.classes;

public class Warehouse extends Inventory implements Placeable {
    private int[] location = new int[2];
    // [x][y] base 1
    static final char DISPLAY_CHAR = 'x';
    static final int STARTING_WOOD = 0;
    static final int STARTING_FOOD = 10;
    static final int MAX_FOOD = 100;
    static final int MAX_WOOD = 100;

    public Warehouse(int x, int y) {
        super(STARTING_WOOD, STARTING_FOOD, MAX_WOOD, MAX_FOOD);
        location[0] = x;
        location[1] = y;
    }

    public int[] getLocation() {
        return location;
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
