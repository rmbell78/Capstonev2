package com.example.capstone.classes;

/**
 * Exception thrown when an operation related to the Pawn class fails.
 *
 * @author Ryan Bell w7346754@student.miracosta.edu
 * @version 2.0
 */
public class PawnException extends Exception {
    public PawnException(String message) {
        super(message);
    }
}