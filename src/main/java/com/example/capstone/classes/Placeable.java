package com.example.capstone.classes;

/**
 * The {@code Placeable} interface represents an object that can be placed on a map.
 * It provides methods to retrieve the x and y coordinates of the object.
 */
public interface Placeable{

    /**
     * Returns the x-coordinate of the object.
     *
     * @return the x-coordinate of the object
     */
    public int getX();

    /**
     * Returns the y-coordinate of the object.
     *
     * @return the y-coordinate of the object
     */
    public int getY();
}