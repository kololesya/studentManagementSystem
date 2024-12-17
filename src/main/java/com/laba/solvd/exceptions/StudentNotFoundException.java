package com.laba.solvd.exceptions;

public class StudentNotFoundException extends RuntimeException {
    // Constructor with only the message
    public StudentNotFoundException(String message) {
        super(message);
    }

    // Constructor with both message and cause
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
