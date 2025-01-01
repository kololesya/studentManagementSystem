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

    public Student getStudentById(int id) {
        for (Student student : studentList) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;  // Return null if no student with the given ID is found
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
        if (student.getStudentId() == 0 ) {
            throw new IllegalArgumentException("Student ID cannot be 0.");
        }
        if (student.getDateOfBirth() <= 0) {
            throw new IllegalArgumentException("Date of Birth must be a positive integer.");
        }

        // Add student to the list
        studentList.add(student);
        System.out.println("Student " + student.getName() + " added successfully.");
    }

    // Update student details
    public boolean updateStudent(int studentId, String name, int dateOfBirth) {
        for (Student student : studentList) {
            if (student.getStudentId() == studentId) {
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
        return dateOfBirth > 0 && dateOfBirth <= currentYear;
    }

    // Get a student by ID
    public Student getStudent(int studentId) {
        for (Student student : studentList) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
    }

    // Display all students
    public String displayAllStudents() {
        if (studentList.isEmpty()) {
            return "No students found.";
        }

        for (Student student : studentList) {
            return student.toString();
        }
        return "Student List:";
    }

    // Get total student count
    public int getTotalStudents() {
        return studentList.size();
    }
}
