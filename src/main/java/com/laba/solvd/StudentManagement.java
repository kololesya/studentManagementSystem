package com.laba.solvd;

import java.util.ArrayList;

public class StudentManagement {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static int totalStudents = 0;

    public static void addStudent(Student student) {
        if (student.name.isEmpty() || student.studentId.isEmpty() || student.grade.isEmpty()) {
            throw new IllegalArgumentException("Name, Student ID, and Grade cannot be empty.");
        }
        if (student.age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer.");
        }

        studentList.add(student);
        totalStudents++;
    }

    public static boolean updateStudent(String studentId, String name, int age, String grade) {
        for (Student student : studentList) {
            if (student.studentId.equals(studentId)) {
                student.updateDetails(name, age, grade);
                return true;
            }
        }
        throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
    }

    public static Student getStudent(String studentId) {
        for (Student student : studentList) {
            if (student.studentId.equals(studentId)) {
                return student;
            }
        }
        throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
    }

    public static void displayAllStudents() {
        if (totalStudents == 0) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : studentList) {
            student.displayDetails();
            System.out.println("---------------------------");
        }
    }
}
