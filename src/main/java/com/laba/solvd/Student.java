package com.laba.solvd;

public class Student {
    String name;
    String studentId;
    int age;
    String grade;

    public Student(String name, String studentId, int age, String grade) {
        this.name = name;
        this.studentId = studentId;
        this.age = age;
        this.grade = grade;
    }

    public void displayDetails() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Age: " + age);
        System.out.println("Grade: " + grade);
    }

    public void updateDetails(String name, int age, String grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
}
