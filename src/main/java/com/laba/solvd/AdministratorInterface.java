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
                    calculateStudentGrade();
                    break;
                case 6:
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
        System.out.println("5. Calculate Student Grade");
        System.out.println("6. Go Back");
    }

    private int getIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();  // Clear the invalid input
        }
        return scanner.nextInt();
    }

    // Method to check if the name is valid (only letters and spaces)
    private boolean isValidName(String name) {
        // Regular expression to allow only letters and spaces
        return name.matches("[a-zA-Z\\s]+");
    }

    public void addNewStudent() {
        System.out.print("Enter student's ID: ");
        int studentId = getIntegerInput(); // Get student ID as an integer
        scanner.nextLine(); // Consume the newline left by getIntegerInput()

        String studentName = "";
        boolean isValidName = false;

        // Keep prompting for a valid name until it's valid
        while (!isValidName) {
            System.out.print("Enter student's name: ");
            studentName = scanner.nextLine(); // Read full name (including spaces)

            if (isValidName(studentName)) {
                isValidName = true;
            } else {
                System.out.println("Invalid name. Please enter a name containing only letters and spaces.");
            }
        }

        System.out.print("Enter year of birth: ");
        int yearOfBirth = getIntegerInput(); // Get year of birth as an integer

        // Create student and add it to the list
        Student student = new Student(studentId, studentName, yearOfBirth);
        studentManagement.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void updateStudentInformation() {
        try {
            // Prompt for the student's ID
            System.out.print("Enter student's ID to update: ");
            int studentId = getIntegerInput();  // Get student ID
            scanner.nextLine();

            // Check if the student exists
            Student student = studentManagement.getStudentById(studentId);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            // Prompt for the new name, ensuring it's valid
            String newName = "";
            boolean isValidName = false;

            // Keep prompting for a valid name until it's valid
            while (!isValidName) {
                System.out.print("Enter new name: ");
                newName = scanner.nextLine();  // Read full name (including spaces)

                if (isValidName(newName)) {
                    isValidName = true;
                } else {
                    System.out.println("Invalid name. Please enter a name containing only letters and spaces.");
                }
            }

            // Prompt for the new year of birth and validate it
            System.out.print("Enter new year of birth: ");
            int yearOfBirth = getIntegerInput();

            // Call the update method in StudentManagement to update the student's details
            boolean success = studentManagement.updateStudent(studentId, newName, yearOfBirth);
            if (success) {
                System.out.println("Student information updated successfully!");
            } else {
                System.out.println("Failed to update student information.");
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
        System.out.print("Enter course ID: ");
        int courseId = getIntegerInput();
        scanner.nextLine();  // Consume the newline character left by getIntegerInput()

        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter professor name: ");
        String professorName = scanner.nextLine();

        System.out.print("Enter maximum capacity: ");
        int maxCapacity = getIntegerInput();

        try {
            // Call the static addCourse method from CourseManagement to add the course
            CourseManagement.addCourse(courseName, professorName, courseId, maxCapacity);
            System.out.println("Course added successfully!");
        } catch (CourseAlreadyExistsException | CourseNotFoundException e) {
            System.out.println("Error: " + e.getMessage());  // Specific exception handling
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());  // Catch any other exception
            e.printStackTrace();  // Print stack trace for debugging purposes
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

    private void calculateStudentGrade() {
        try {
            // Ask for student ID to calculate the grade
            System.out.print("Enter student's ID to calculate overall grade: ");
            int studentId = getIntegerInput();  // Get student ID as input

            // Retrieve the student by ID (assuming you have a method in StudentManagement)
            Student student = studentManagement.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student not found with ID " + studentId);
                return;
            }

            // Call the calculateOverallGrade method from CourseManagement to calculate the grade
            double overallGrade = CourseManagement.calculateOverallGrade(student);
            System.out.println("Overall grade for student " + student.getName() + ": " + overallGrade);

        } catch (StudentNotEnrolledException e) {
            System.out.println("Error: " + e.getMessage());  // Student not enrolled in any courses
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());  // Other unforeseen errors
        }
    }

}
