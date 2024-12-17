package com.laba.solvd;

import com.laba.solvd.exceptions.*;

import java.util.*;

public class AdministratorInterface {
    private Scanner scanner;
    private StudentManagement studentManagement; // Using StudentManagement to handle students
    private CourseManagement courseManagement;   // Using CourseManagement to handle courses

    public AdministratorInterface(Scanner scanner) {
        this.scanner = scanner;
        this.studentManagement = new StudentManagement();  // Initializing StudentManagement
        this.courseManagement = new CourseManagement();    // Initializing CourseManagement
    }

    public void run() throws CourseNotFoundException {
        while (true) {
            displayMainMenu();
            System.out.println("Choose an option: ");
            int choice = getIntegerInput();
            switch (choice) {
                case 1:
                    handleStudentMenu();
                    break;
                case 2:
                    handleCourseMenu();
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleStudentMenu() {
        while (true) {
            displayStudentMenu();
            System.out.println("Enter your choice: ");
            int choice = getIntegerInput();
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleCourseMenu() throws CourseNotFoundException {
        while (true) {
            displayCourseMenu();
            System.out.println("Enter your choice: ");
            int choice = getIntegerInput();
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
                    return;
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

    private int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();  // Clear the invalid input
        }
        return scanner.nextInt();
    }

    // Adding a new student
    private void addNewStudent() {
        System.out.print("Enter student's ID: ");
        int id = getIntegerInput();

        System.out.print("Enter student's name: ");
        String name = scanner.next();

        System.out.println("Enter year of birth");
        int yearOfBirth = getIntegerInput();

        Student newStudent = new Student(id, name, yearOfBirth);
        studentManagement.addStudent(newStudent);
        System.out.println("Student added successfully!");
    }

    // Updating student information (e.g., name or ID)
    private void updateStudentInformation() {
        try {
            System.out.print("Enter student's ID to update: ");
            int id = getIntegerInput();

            // Update student details by calling the method from StudentManagement
            System.out.print("Enter new name: ");
            String newName = scanner.next();

            System.out.print("Enter new year of birth: ");
            int yearOfBirth = getIntegerInput();

            // Call the update method in StudentManagement
            boolean success = studentManagement.updateStudent(id, newName, yearOfBirth);
            if (success) {
                System.out.println("Student information updated successfully!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());  // For invalid arguments like empty name or future DOB
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());  // When the student is not found
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());  // For any other unforeseen errors
        }
    }


    // Viewing student details by ID
    private void viewStudentDetails() {
        System.out.print("Enter student's ID to view details: ");
        int id = getIntegerInput();
        Student student = studentManagement.getStudentById(id);
        if (student != null) {
            student.displayDetails();  // Directly calling displayDetails method from Student class
        } else {
            System.out.println("Student not found.");
        }
    }


    // Viewing all students
    private void viewAllStudents() {
        System.out.println("--- All Students ---");
        studentManagement.displayAllStudents();  // Directly calling the displayAllStudents method from StudentManagement
    }


    // Adding a new course
    private void addNewCourse() {
        System.out.print("Enter course name: ");
        String courseName = scanner.next();
        System.out.print("Enter professor name: ");
        String professorName = scanner.next();
        System.out.print("Enter course ID: ");
        int courseId = getIntegerInput();
        System.out.print("Enter maximum capacity: ");
        int maxCapacity = getIntegerInput();

        try {
            // Call the static addCourse method from CourseManagement to add the course
            CourseManagement.addCourse(courseName, professorName, courseId, maxCapacity);
            System.out.println("Course added successfully!");
        } catch (CourseAlreadyExistsException | CourseNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Enrolling student in a course
    private void enrollStudentInCourse() throws CourseNotFoundException {
        System.out.print("Enter student's ID to enroll: ");
        int studentId = getIntegerInput();
        System.out.print("Enter course code to enroll in: ");
        int courseCode = getIntegerInput();

        // Retrieve the student by id
        Student student = studentManagement.getStudentById(studentId);
        // Retrieve the course by ID
        Course course = courseManagement.getCourseById(courseCode);

        if (student != null && course != null) {
            try {
                // Use the method enrollStudent
                CourseManagement.enrollStudent(student, course);
                System.out.println("Student enrolled in course successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid student or course.");
        }
    }

    // Assigning grade to a student in a course
    // Assigning grade to a student in a course
    private void assignGradeToStudent() throws CourseNotFoundException {
        System.out.print("Enter student's ID to assign grade: ");
        int studentId = getIntegerInput();  // Get student ID
        System.out.print("Enter course ID: ");
        int courseId = getIntegerInput();  // Get course ID as an integer
        System.out.print("Enter grade (A, B, C, D, F): ");
        String gradeInput = scanner.next().toUpperCase();  // Get grade and convert to uppercase

        // Validate the grade
        if (gradeInput.length() != 1 || !"ABCDF".contains(gradeInput)) {
            System.out.println("Invalid grade. Allowed grades are A, B, C, D, F.");
            return;
        }
        char grade = gradeInput.charAt(0);  // Convert the grade input to a character

        Student student = studentManagement.getStudentById(studentId);
        Course course = CourseManagement.getCourseById(courseId);

        if (student != null && course != null) {
            try {
                // Use the method in CourseManagement to assign the grade
                CourseManagement.assignGrade(student, course, grade);
                System.out.println("Grade assigned successfully!");
            } catch (StudentNotEnrolledException | InvalidGradeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid student or course.");
        }
    }

    // Viewing all courses
    private void viewAllCourses() {
        System.out.println("--- All Courses ---");
        CourseManagement.displayAllCourses();  // Directly calling the method from CourseManagement to display courses
    }
}
