package com.example.capstone.classes;

import java.util.ArrayList;

/**
 * Represents a House that can be placed in the game.
 * A House has a specific location and can hold occupants up to a defined capacity.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class House implements Placeable {
    /**
     * The maximum number of occupants a house can hold.
     */
    public static int HOUSE_CAPACITY = 3;

    /**
     * The location of the house represented by x and y coordinates.
     */
    private final int[] location = new int[2];

    /**
     * The list of occupants currently residing in the house.
     */
    private final ArrayList<Object> houseOccupants = new ArrayList<>();

    /**
     * Constructs a new House at the specified coordinates.
     *
     * @param x the x-coordinate of the House
     * @param y the y-coordinate of the House
     */
    public House(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    /**
     * Adds a new occupant to the house.
     *
     * @param newOccupant the new occupant to be added
     * @throws HouseException if the house is already full
     */
    public void addOccupants(Object newOccupant) throws HouseException {
        if (houseOccupants.size() >= HOUSE_CAPACITY) {
            throw new HouseException("That house is already full");
        } else {
            houseOccupants.add(newOccupant);
        }
    }

    /**
     * Removes an occupant from the house.
     *
     * @param oldOccupant the occupant to be removed
     */
    public void removeOccupant(Object oldOccupant) {
        houseOccupants.remove(oldOccupant);
    }

    /**
     * Checks if the house is full.
     *
     * @return true if the house is full, false otherwise
     */
    public boolean isFull() {
        return houseOccupants.size() == HOUSE_CAPACITY;
    }

    /**
     * Gets the x-coordinate of the house's location.
     *
     * @return the x-coordinate of the house's location
     */
    @Override
    public int getX() {
        return location[0];
    }

    /**
     * Gets the y-coordinate of the house's location.
     *
     * @return the y-coordinate of the house's location
     */
    @Override
    public int getY() {
        return location[1];
    }
}