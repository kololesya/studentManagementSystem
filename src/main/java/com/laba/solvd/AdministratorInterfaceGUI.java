package com.laba.solvd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdministratorInterfaceGUI extends Application {

    private StudentManagement studentManagement;
    private CourseManagement courseManagement;

    public AdministratorInterfaceGUI() {
        this.studentManagement = new StudentManagement();
        this.courseManagement = new CourseManagement();
    }

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane mainLayout = new BorderPane();

        // Main menu button actions
        Button studentButton = new Button("Student Information");
        Button courseButton = new Button("Course Information");
        Button exitButton = new Button("Exit");

        // Create event handlers for the buttons
        studentButton.setOnAction(event -> openStudentMenu());
        courseButton.setOnAction(event -> openCourseMenu());
        exitButton.setOnAction(event -> primaryStage.close());

        // Layout for the main menu
        VBox mainMenuLayout = new VBox(20);
        mainMenuLayout.getChildren().addAll(studentButton, courseButton, exitButton);
        mainLayout.setCenter(mainMenuLayout);

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 300, 200);
        primaryStage.setTitle("Administrator Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Student Menu
    private void openStudentMenu() {
        Stage studentMenuStage = new Stage();
        BorderPane studentMenuLayout = new BorderPane();

        // Create TextArea for output
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);  // Remove possibility to correct the text
        outputArea.setPrefHeight(100);  // Set the height for output area

        Button addStudentButton = new Button("Add New Student");
        Button updateStudentButton = new Button("Update Student Information");
        Button viewStudentButton = new Button("View Student Details");
        Button backButton = new Button("Back");

        // For button "Add New Student"
        addStudentButton.setOnAction(event -> addNewStudent(outputArea));  // outputArea as a parametr

        updateStudentButton.setOnAction(event -> updateStudentInformation(outputArea));
        viewStudentButton.setOnAction(event -> viewStudentDetails(outputArea));
        backButton.setOnAction(event -> studentMenuStage.close());

        VBox studentMenuButtons = new VBox(20);
        studentMenuButtons.getChildren().addAll(addStudentButton, updateStudentButton, viewStudentButton, backButton);

        // Add buttons and output area to the
        studentMenuLayout.setTop(outputArea);
        studentMenuLayout.setCenter(studentMenuButtons);

        // Create a scene and display a window
        Scene studentMenuScene = new Scene(studentMenuLayout, 400, 300);
        studentMenuStage.setTitle("Student Menu");
        studentMenuStage.setScene(studentMenuScene);
        studentMenuStage.show();
    }


    // Course Menu
    private void openCourseMenu() {
        Stage courseMenuStage = new Stage();
        BorderPane courseMenuLayout = new BorderPane();

        Button addCourseButton = new Button("Add New Course");
        Button enrollStudentButton = new Button("Enroll Student in Course");
        Button assignGradeButton = new Button("Assign Grade to Student");
        Button backButton = new Button("Back");

        addCourseButton.setOnAction(event -> addNewCourse());
        enrollStudentButton.setOnAction(event -> enrollStudentInCourse());
        assignGradeButton.setOnAction(event -> assignGradeToStudent());
        backButton.setOnAction(event -> courseMenuStage.close());

        VBox courseMenuButtons = new VBox(20);
        courseMenuButtons.getChildren().addAll(addCourseButton, enrollStudentButton, assignGradeButton, backButton);

        courseMenuLayout.setCenter(courseMenuButtons);
        Scene courseMenuScene = new Scene(courseMenuLayout, 300, 200);
        courseMenuStage.setTitle("Course Menu");
        courseMenuStage.setScene(courseMenuScene);
        courseMenuStage.show();
    }

    private void addNewStudent(TextArea outputArea) {
        // Создание диалогового окна для ввода ID студента
        TextInputDialog studentIdDialog = new TextInputDialog();
        studentIdDialog.setTitle("Add New Student");
        studentIdDialog.setHeaderText("Enter Student Details");
        studentIdDialog.setContentText("Enter Student ID:");

        studentIdDialog.showAndWait().ifPresent(id -> {
            try {
                // Check if an ID is a natural number
                int studentId = Integer.parseInt(id);
                if (studentId <= 0) {
                    outputArea.appendText("Student ID must be a natural number (positive integer).\n");
                    return;
                }

                // For input the student's name
                TextInputDialog studentNameDialog = new TextInputDialog();
                studentNameDialog.setTitle("Add New Student");
                studentNameDialog.setHeaderText("Enter Student Details");
                studentNameDialog.setContentText("Enter Student Name:");

                studentNameDialog.showAndWait().ifPresent(name -> {
                    // Check if the name contains only latin letters and spaces
                    if (!name.matches("[a-zA-Z\\s]+")) {
                        outputArea.appendText("Student name must only contain Latin letters and spaces.\n");
                        return;
                    }

                    // For input date of birth
                    TextInputDialog studentYearDialog = new TextInputDialog();
                    studentYearDialog.setTitle("Add New Student");
                    studentYearDialog.setHeaderText("Enter Student Details");
                    studentYearDialog.setContentText("Enter Student Year of Birth:");

                    studentYearDialog.showAndWait().ifPresent(year -> {
                        try {
                            // Check if the year is a natural number
                            int yearOfBirth = Integer.parseInt(year);
                            if (yearOfBirth <= 0) {
                                outputArea.appendText("Year of birth must be a natural number (positive integer).\n");
                                return;
                            }

                            // Create a new student and add him to the list
                            Student newStudent = new Student(studentId, name, yearOfBirth);
                            studentManagement.addStudent(newStudent);  // Add a student to the system
                            outputArea.appendText("Student added successfully: " + name + " (ID: " + studentId + ")\n");

                        } catch (NumberFormatException e) {
                            outputArea.appendText("Invalid year format. Please enter a valid year.\n");
                        }
                    });
                });
            } catch (NumberFormatException e) {
                outputArea.appendText("Student ID must be a natural number (positive integer).\n");
            }
        });
    }

    private void updateStudentInformation(TextArea outputArea) {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Update Student Information");
        idDialog.setHeaderText("Search for Student");
        idDialog.setContentText("Enter Student ID:");

        idDialog.showAndWait().ifPresent(id -> {
            try {
                int studentId = Integer.parseInt(id);
                Student student = studentManagement.getStudent(studentId);

                if (student == null) {
                    outputArea.appendText("No student found with ID: " + studentId + "\n");
                    return;
                }

                // Show current student details
                outputArea.appendText("Updating information for: " + student.toString() + "\n");

                // Update name
                TextInputDialog nameDialog = new TextInputDialog(student.getName());
                nameDialog.setTitle("Update Student Name");
                nameDialog.setHeaderText("Current Name: " + student.getName());
                nameDialog.setContentText("Enter new name:");

                nameDialog.showAndWait().ifPresent(newName -> {
                    if (!newName.matches("[a-zA-Z\\s]+")) {
                        outputArea.appendText("Invalid name format. Only Latin letters are allowed.\n");
                        return;
                    }
                    student.setName(newName);
                });

                // Update year of birth
                TextInputDialog yearDialog = new TextInputDialog(String.valueOf(student.getDateOfBirth()));
                yearDialog.setTitle("Update Year of Birth");
                yearDialog.setHeaderText("Current Year of Birth: " + student.getDateOfBirth());
                yearDialog.setContentText("Enter new year of birth:");

                yearDialog.showAndWait().ifPresent(newYear -> {
                    try {
                        int yearOfBirth = Integer.parseInt(newYear);
                        if (yearOfBirth <= 0) {
                            outputArea.appendText("Year of birth must be a positive integer.\n");
                            return;
                        }
                        student.setDateOfBirth(yearOfBirth);
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid year format. Please enter a valid year.\n");
                    }
                });

                outputArea.appendText("Student information updated successfully: " + student.toString() + "\n");

            } catch (NumberFormatException e) {
                outputArea.appendText("Student ID must be a natural number (positive integer).\n");
            }
        });
    }

    // The method for retrieve information about student/students
    private void viewStudentDetails(TextArea outputArea) {
        // Создаем диалог для выбора опции
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("View All Students", "View All Students", "Search Student by ID");
        choiceDialog.setTitle("View Student Details");
        choiceDialog.setHeaderText("Choose an option to view student details:");
        choiceDialog.setContentText("Select an option:");

        choiceDialog.showAndWait().ifPresent(choice -> {
            if (choice.equals("View All Students")) {
                // Display all students
                outputArea.appendText("All Students:\n");
                outputArea.appendText(studentManagement.displayAllStudents());

            } else if (choice.equals("Search Student by ID")) {
                // Find students by ID
                TextInputDialog idDialog = new TextInputDialog();
                idDialog.setTitle("Search Student");
                idDialog.setHeaderText("Search for a Student");
                idDialog.setContentText("Enter Student ID:");

                idDialog.showAndWait().ifPresent(id -> {
                    try {
                        int studentId = Integer.parseInt(id);
                        Student student = studentManagement.getStudentById(studentId);

                        if (student == null) {
                            outputArea.appendText("No student found with ID: " + studentId + "\n");
                        } else {
                            outputArea.appendText("Student Details:\n" + student.toString() + "\n");
                            courseManagement.displayDetails(student); // Используем метод displayDetails из CourseManagement
                        }
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Student ID must be a natural number (positive integer).\n");
                    }
                });
            }
        });
    }



    // Add new course
    private void addNewCourse() {
        // Logic for adding a new course via dialog
        TextInputDialog courseDialog = new TextInputDialog();
        courseDialog.setTitle("Add New Course");
        courseDialog.setHeaderText("Enter Course Details");
        courseDialog.setContentText("Enter Course ID:");

        courseDialog.showAndWait().ifPresent(id -> {
            int courseId = Integer.parseInt(id);
            // Similarly collect other details and add course
            System.out.println("Course added with ID: " + courseId);
        });
    }

    // Enroll student in course
    private void enrollStudentInCourse() {
        // Logic for enrolling student in course
    }

    // Assign grade to student
    private void assignGradeToStudent() {
        // Logic for assigning grade to student
    }

    public static void main(String[] args) {
        launch(args);
    }
}
