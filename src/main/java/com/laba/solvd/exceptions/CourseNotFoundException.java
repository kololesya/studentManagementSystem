package com.laba.solvd.exceptions;

public class CourseNotFoundException extends Throwable {
    public CourseNotFoundException(String message) {
        super(message);  // Passing message to the parent Exception class
    }
}
