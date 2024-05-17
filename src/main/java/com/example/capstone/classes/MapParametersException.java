package com.example.capstone.classes;

/**
 * Exception thrown when there is an issue with map parameters.
 */
public class MapParametersException extends Exception{
    /**
     * Constructs a new MapParametersException with the specified detail message.
     *
     * @param message the detail message
     */
    public MapParametersException(String message) {
        super(message);
    }
}