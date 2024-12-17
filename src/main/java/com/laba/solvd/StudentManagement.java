package com.laba.solvd;

import com.laba.solvd.exceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StudentManagement {
    private List<Student> studentList; // Non-static list

    // Constructor to initialize the student list
    public StudentManagement() {
        this.studentList = new ArrayList<>();
    }

    // Add a new student
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        // Validation of fields
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (student.getStudentId() == null || student.getStudentId().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        if (student.getDateOfBirth() <= 0) {
            throw new IllegalArgumentException("Date of Birth must be a positive integer.");
        }

        // Add student to the list
        studentList.add(student);
        System.out.println("Student " + student.getName() + " added successfully.");
    }

    // Update student details
    public boolean updateStudent(String studentId, String name, int dateOfBirth) {
        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                // Update the name
                if (name != null && !name.trim().isEmpty()) {
                    student.setName(name);
                } else {
                    throw new IllegalArgumentException("Student name cannot be empty.");
                }

                // Update date of birth
                if (isValidDateOfBirth(dateOfBirth)) {
                    student.setDateOfBirth(dateOfBirth);
                } else {
                    throw new IllegalArgumentException("Date of Birth must be a valid positive integer and not in the future.");
                }

                System.out.println("Student with ID " + studentId + " updated successfully.");
                return true;
            }
        }
        throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
    }

    private boolean isValidDateOfBirth(int dateOfBirth) {
        // The date of birth can't be o and can't be in the future
        int currentYear = java.time.LocalDate.now().getYear();
        int birthYear = dateOfBirth;
        return birthYear > 0 && birthYear <= currentYear;
    }

    // Get a student by ID
    public Student getStudent(String studentId) {
        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
    }

    // Display all students
    public void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("Student List:");
        for (Student student : studentList) {
            student.displayDetails();
            System.out.println("---------------------------");
        }
    }

    // Get total student count
    public int getTotalStudents() {
        return studentList.size();
    }
}
