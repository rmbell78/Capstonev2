package com.example.capstone.classes;

/**
 * Exception thrown when an operation related to the House class fails.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class HouseException extends Exception {
    /**
     * Constructs a new HouseException with the specified detail message.
     *
     * @param message the detail message
     */
    public HouseException(String message) {
        super(message);
    }
}