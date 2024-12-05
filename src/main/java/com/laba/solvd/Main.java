package com.laba.solvd;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static void displayMenu() {
        System.out.println("Student Record Management System");
        System.out.println("1. Add New Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. View Student Details");
        System.out.println("4. View All Students");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addNewStudent() {
        try {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine();
            System.out.print("Enter Student Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Student Grade: ");
            String grade = scanner.nextLine();

            Student student = new Student(name, studentId, age, grade);
            StudentManagement.addStudent(student);
            System.out.println("Student added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudentInformation() {
        try {
            System.out.print("Enter Student ID to update: ");
            String studentId = scanner.nextLine();
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new grade: ");
            String grade = scanner.nextLine();

            StudentManagement.updateStudent(studentId, name, age, grade);
            System.out.println("Student information updated successfully.");
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewStudentDetails() {
        try {
            System.out.print("Enter Student ID to view: ");
            String studentId = scanner.nextLine();
            Student student = StudentManagement.getStudent(studentId);
            student.displayDetails();
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int option;

        while (true) {
            displayMenu();
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    updateStudentInformation();
                    break;
                case 3:
                    viewStudentDetails();
                    break;
                case 4:
                    StudentManagement.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}