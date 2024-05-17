package com.example.capstone.classes;

/**
 * Exception thrown when there is an issue with map parameters.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class MapParametersException extends Exception {
    /**
     * Constructs a new MapParametersException with the specified detail message.
     *
     * @param message the detail message
     */
    public MapParametersException(String message) {
        super(message);
    }
}