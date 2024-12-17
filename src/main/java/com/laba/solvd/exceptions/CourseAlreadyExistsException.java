package com.laba.solvd.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    // Constructor that accepts a message
    public CourseAlreadyExistsException(String message) {
        super(message); // Pass the message to the superclass constructor
    }
}
