package com.laba.solvd;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private String studentId;
    private int dateOfBirth;
    private Map<Course, Character> enrolledCourses; // Course as key, Grade as value

    public Student(String name, String studentId, int dateOfBirth) {
        this.name = name;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.enrolledCourses = new HashMap<>();
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<Course, Character> getEnrolledCourses() {
        return enrolledCourses;
    }

    // Method to enroll student in a course
    public void enrollInCourse(Course course) {
        if (!enrolledCourses.containsKey(course)) {
            enrolledCourses.put(course, null); // Initially grade is null
            System.out.println("Enrolled in course: " + course.getNameOfCourse());
        } else {
            System.out.println("Already enrolled in: " + course.getNameOfCourse());
        }
    }

    // Method to assign/update grade for a course
    public void assignGradeToCourse(Course course, char grade) {
        if (enrolledCourses.containsKey(course)) {
            enrolledCourses.put(course, grade);
            System.out.println("Grade " + grade + " assigned to course: " + course.getNameOfCourse());
        } else {
            System.out.println("Student is not enrolled in course: " + course.getNameOfCourse());
        }
    }

    // Display student details including courses and grades
    public void displayDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Enrolled Courses and Grades:");
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (Map.Entry<Course, Character> entry : enrolledCourses.entrySet()) {
                Course course = entry.getKey();
                Character grade = entry.getValue();
                System.out.println("- " + course.getNameOfCourse() + " (Professor: " + course.getNameOfProfessor() + ") - Grade: " + (grade != null ? grade : "Not Assigned"));
            }
        }
    }
}
