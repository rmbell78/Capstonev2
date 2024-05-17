package com.example.capstone.classes;

/**
 * The {@code Placeable} interface represents an object that can be placed on a map.
 * It provides methods to retrieve the x and y coordinates of the object.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public interface Placeable {

    /**
     * Returns the x-coordinate of the object.
     *
     * @return the x-coordinate of the object
     */
    int getX();

    /**
     * Returns the y-coordinate of the object.
     *
     * @return the y-coordinate of the object
     */
    int getY();
}