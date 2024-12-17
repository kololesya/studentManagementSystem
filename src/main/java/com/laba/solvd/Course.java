package com.laba.solvd;

import java.util.Objects;

public class Course {
    private int id;
    private String nameOfCourse;
    private String nameOfProfessor;
    private int maxCapacity;  // Maximum capacity for students
    private static int totalEnrolledStudents = 0;  // Static variable to track the total number of enrolled students
    // Constructor
    public Course(int id, String nameOfCourse, String nameOfProfessor, int maxCapacity) {
        this.id = id;
        this.nameOfCourse = nameOfCourse;
        this.nameOfProfessor = nameOfProfessor;
        this.maxCapacity = maxCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfCourse() {
        return nameOfCourse;
    }

    public void setNameOfCourse(String nameOfCourse) {
        this.nameOfCourse = nameOfCourse;
    }

    public String getNameOfProfessor() {
        return nameOfProfessor;
    }

    public void setNameOfProfessor(String nameOfProfessor) {
        this.nameOfProfessor = nameOfProfessor;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Static method to get the total number of enrolled students across all courses
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    // Method to enroll a student in the course
    public boolean enrollStudent() {
        if (totalEnrolledStudents < maxCapacity) {
            totalEnrolledStudents++;
            return true;  // Enrollment successful
        } else {
            return false;  // Course is full
        }
    }

    // Method to display course details
    public void displayCourseDetails() {
        System.out.println("Course Code: " + id);
        System.out.println("Course Name: " + nameOfCourse);
        System.out.println("Professor: " + nameOfProfessor);
        System.out.println("Max Capacity: " + maxCapacity);
        System.out.println("Total Enrolled Students: " + totalEnrolledStudents);
    }

    @Override
    public String toString() {
        return "Course ID: " + id + ", Name: " + nameOfCourse + ", Professor: " + nameOfProfessor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
