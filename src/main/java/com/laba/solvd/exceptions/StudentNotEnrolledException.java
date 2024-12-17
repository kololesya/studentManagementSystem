package com.laba.solvd.exceptions;
public class StudentNotEnrolledException extends RuntimeException {
    // Constructor that accepts a message
    public StudentNotEnrolledException(String message) {
        super(message);
    }
}
