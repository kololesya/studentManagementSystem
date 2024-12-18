package com.laba.solvd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private int id;
    private String nameOfCourse;
    private String nameOfProfessor;
    private int maxCapacity;
    private List<Student> enrolledStudents;
    private static int totalEnrolledStudents;  // Static variable to track the total number of enrolled students
    // Constructor
    public Course(int id, String nameOfCourse, String nameOfProfessor, int maxCapacity) {
        this.id = id;
        this.nameOfCourse = nameOfCourse;
        this.nameOfProfessor = nameOfProfessor;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
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

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Static method to get the total number of enrolled students across all courses
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    public boolean addStudent(Student student) {
        // Check if there is space in the course
        if (enrolledStudents.size() < maxCapacity) {
            enrolledStudents.add(student);
            totalEnrolledStudents++;  // Increment the total enrolled students across all courses
            return true;  // Student added successfully
        } else {
            System.out.println("Course is full, cannot add student " + student.getName());
            return false;  // Return false if course is full
        }
    }

    @Override
    public String toString() {
        return "Course ID: " + id + ", Name: " + nameOfCourse + ", Professor: " + nameOfProfessor + "Total students: " + totalEnrolledStudents;
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
