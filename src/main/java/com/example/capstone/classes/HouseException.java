package com.example.capstone.classes;

/**
 * Exception thrown when an operation related to the House class fails.
 */
public class HouseException extends Exception{
    /**
     * Constructs a new HouseException with the specified detail message.
     *
     * @param message the detail message
     */
    public HouseException(String message){
        super(message);
    }
}