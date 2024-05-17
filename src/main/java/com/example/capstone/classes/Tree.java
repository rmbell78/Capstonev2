package com.example.capstone.classes;

/**
 * Represents a Tree resource on the map.
 * <p>
 * The Tree class extends the Resource class and provides the coordinates
 * (x, y) for the tree's location on the map.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class Tree extends Resource {

    /**
     * Constructs a new Tree at the specified coordinates.
     *
     * @param x the x-coordinate of the tree's location
     * @param y the y-coordinate of the tree's location
     */
    public Tree(int x, int y) {
        super(x, y);
    }
}