package com.laba.solvd;

import java.util.Scanner;

public class AdministratorInterface {

    private Scanner scanner;

    public AdministratorInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        int choice;
        while (true) {
            displayMainMenu();
            choice = getIntegerInput("Choose an option: ");
            switch (choice) {
                case 1:
                    handleStudentMenu();  // Handle student-related options
                    break;
                case 2:
                    handleCourseMenu();  // Handle course-related options
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    return;  // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleStudentMenu() {
        int choice;
        while (true) {
            displayStudentMenu();
            choice = getIntegerInput("Enter your choice: ");
            switch (choice) {
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
                    viewAllStudents();
                    break;
                case 5:
                    return;  // Go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleCourseMenu() {
        int choice;
        while (true) {
            displayCourseMenu();
            choice = getIntegerInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    enrollStudentInCourse();
                    break;
                case 3:
                    assignGradeToStudent();
                    break;
                case 4:
                    viewAllCourses();
                    break;
                case 5:
                    return;  // Go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nStudent Record and Course Management System");
        System.out.println("1. Student Information");
        System.out.println("2. Course Information");
        System.out.println("3. Exit");
    }

    private void displayStudentMenu() {
        System.out.println("\n--- Student Information ---");
        System.out.println("1. Add New Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. View Student Details");
        System.out.println("4. View All Students");
        System.out.println("5. Go Back");
    }

    private void displayCourseMenu() {
        System.out.println("\n--- Course Information ---");
        System.out.println("1. Add New Course");
        System.out.println("2. Enroll Student in Course");
        System.out.println("3. Assign Grade to Student");
        System.out.println("4. View All Courses");
        System.out.println("5. Go Back");
    }

    private int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();  // Clear the invalid input
        }
        return scanner.nextInt();
    }

    // Placeholder methods for student and course operations
    private void addNewStudent() {
        System.out.println("Adding new student...");
    }

    private void updateStudentInformation() {
        System.out.println("Updating student information...");
    }

    private void viewStudentDetails() {
        System.out.println("Viewing student details...");
    }

    private void viewAllStudents() {
        System.out.println("Viewing all students...");
    }

    private void addNewCourse() {
        System.out.println("Adding new course...");
    }

    private void enrollStudentInCourse() {
        System.out.println("Enrolling student in course...");
    }

    private void assignGradeToStudent() {
        System.out.println("Assigning grade to student...");
    }

    private void viewAllCourses() {
        System.out.println("Viewing all courses...");
    }
}
