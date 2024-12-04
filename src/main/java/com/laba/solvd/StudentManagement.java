package com.laba.solvd;

import java.util.ArrayList;

public class StudentManagement {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static int totalStudents = 0;

    public static void addStudent(Student student) {
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
        return false;
    }

    public static Student getStudent(String studentId) {
        for (Student student : studentList) {
            if (student.studentId.equals(studentId)) {
                return student;
            }
        }
        return null;
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
