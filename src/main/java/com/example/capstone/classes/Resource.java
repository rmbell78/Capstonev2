package com.example.capstone.classes;

/**
 * Abstract class representing a resource that can be placed on the map.
 * Implements the Placeable interface to provide location functionality.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public abstract class Resource implements Placeable {
    private final int[] location = new int[2];

    /**
     * Constructs a Resource with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the resource's location
     * @param y the y-coordinate of the resource's location
     */
    public Resource(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    /**
     * Returns the x-coordinate of the resource's location.
     *
     * @return the x-coordinate of the resource's location
     */
    @Override
    public int getX() {
        return location[0];
    }

    /**
     * Returns the y-coordinate of the resource's location.
     *
     * @return the y-coordinate of the resource's location
     */
    @Override
    public int getY() {
        return location[1];
    }
}