package com.laba.solvd.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    // Constructor that accepts a message
    public CourseAlreadyExistsException(String message) {
        super(message);  // Passing message to the parent Exception class
    }
}
