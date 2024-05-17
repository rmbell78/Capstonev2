package com.example.capstone.classes;

/**
 * The {@code Warehouse} class extends the {@code Inventory} class and implements the {@code Placeable} interface.
 * It represents a warehouse that can store food and wood at specific coordinates on a map.
 * The warehouse starts with predefined quantities of food and wood and has maximum limits for both.
 */
public class Warehouse extends Inventory implements Placeable {
    private final int[] location = new int[2];
    // [x][y] base 1
    static final int STARTING_WOOD = 0;
    static final int STARTING_FOOD = 10;
    public static final int MAX_FOOD = 100;
    public static final int MAX_WOOD = 100;

    /**
     * Constructs a new {@code Warehouse} at the specified coordinates.
     *
     * @param x the x-coordinate of the warehouse's location
     * @param y the y-coordinate of the warehouse's location
     */
    public Warehouse(int x, int y) {
        super(STARTING_WOOD, STARTING_FOOD, MAX_WOOD, MAX_FOOD);
        location[0] = x;
        location[1] = y;
    }

    /**
     * Returns the x-coordinate of the warehouse's location.
     *
     * @return the x-coordinate of the warehouse's location
     */
    @Override
    public int getX() {
        return location[0];
    }

    /**
     * Returns the y-coordinate of the warehouse's location.
     *
     * @return the y-coordinate of the warehouse's location
     */
    @Override
    public int getY() {
        return location[1];
    }
}