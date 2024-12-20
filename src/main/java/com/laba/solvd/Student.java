package com.laba.solvd;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private String name;
    private int studentId;
    private int dateOfBirth;

    public Student(int studentId, String name, int dateOfBirth) {
        this.studentId = studentId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Display student details including courses and grades
    public void displayDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Date of Birth: " + dateOfBirth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId; // Используйте уникальный идентификатор
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
