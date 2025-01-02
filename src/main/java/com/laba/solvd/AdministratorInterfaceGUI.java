package com.laba.solvd;

import com.laba.solvd.exceptions.CourseAlreadyExistsException;
import com.laba.solvd.exceptions.CourseNotFoundException;
import com.laba.solvd.exceptions.StudentAlreadyEnrolledException;
import com.laba.solvd.exceptions.StudentNotEnrolledException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

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
        Button calculateStudentGradeButton = new Button("Calculate Overall Student Grade");
        Button backButton = new Button("Back");

        addStudentButton.setOnAction(event -> addNewStudent(outputArea));  // outputArea as a parameter
        updateStudentButton.setOnAction(event -> updateStudentInformation(outputArea));
        viewStudentButton.setOnAction(event -> viewStudentDetails(outputArea));
        calculateStudentGradeButton.setOnAction(event -> calculateStudentGrade());
        backButton.setOnAction(event -> studentMenuStage.close());

        VBox studentMenuButtons = new VBox(20);
        studentMenuButtons.getChildren().addAll(addStudentButton, updateStudentButton, viewStudentButton,
                calculateStudentGradeButton, backButton);

        // Add buttons and output area to the layout
        studentMenuLayout.setTop(outputArea);
        studentMenuLayout.setCenter(studentMenuButtons);

        // Create a scene and display the window
        Scene studentMenuScene = new Scene(studentMenuLayout, 600, 400);  // Set a larger window size
        studentMenuStage.setTitle("Student Menu");
        studentMenuStage.setScene(studentMenuScene);

        // Set minimum size for the window
        studentMenuStage.setMinWidth(600);
        studentMenuStage.setMinHeight(400);

        studentMenuStage.show();
    }

    // Course Menu
    private void openCourseMenu() {
        Stage courseMenuStage = new Stage();
        BorderPane courseMenuLayout = new BorderPane();

        Button addCourseButton = new Button("Add New Course");
        Button enrollStudentButton = new Button("Enroll Student in Course");
        Button removeStudentFromCourseButton = new Button("Remove Student from Course");
        Button assignGradeButton = new Button("Assign Grade to Student");
        Button viewAllCoursesButton = new Button("View All Courses");
        Button calculateCourseOverallGradeButton = new Button("Calculate Overall Grade for a Course");
        Button displayCourseWithStudentsButton = new Button("Display a Course with Students");
        Button backButton = new Button("Back");

        addCourseButton.setOnAction(event -> addNewCourse());
        enrollStudentButton.setOnAction(event -> enrollStudentInCourse());
        removeStudentFromCourseButton.setOnAction(event -> removeStudentFromCourse());
        assignGradeButton.setOnAction(event -> assignGradeToStudent());
        viewAllCoursesButton.setOnAction(event -> viewAllCourses());
        calculateCourseOverallGradeButton.setOnAction(event -> calculateCourseOverallGrade());
        displayCourseWithStudentsButton.setOnAction(event -> displayCourseWithStudents());
        backButton.setOnAction(event -> courseMenuStage.close());

        VBox courseMenuButtons = new VBox(20);
        courseMenuButtons.getChildren().addAll(addCourseButton, enrollStudentButton, assignGradeButton,
                viewAllCoursesButton, calculateCourseOverallGradeButton, displayCourseWithStudentsButton,
                backButton);

        courseMenuLayout.setCenter(courseMenuButtons);

        // Set a larger window size (width 600, height 400)
        Scene courseMenuScene = new Scene(courseMenuLayout, 600, 400);

        // Set minimum size for the window
        courseMenuStage.setMinWidth(600);
        courseMenuStage.setMinHeight(400);

        courseMenuStage.setTitle("Course Menu");
        courseMenuStage.setScene(courseMenuScene);
        courseMenuStage.show();
    }

    private void addNewStudent(TextArea outputArea) {
        // Create the dialog window for student's ID input
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

                // Check if the student already exists
                Student existingStudent = studentManagement.getStudentById(studentId); // Assuming this method is available in studentManagement
                if (existingStudent != null) {
                    outputArea.appendText("Student with ID " + studentId + " already exists.\n");
                    return;  // Exit the method if the student already exists
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
        // Create the dialog for choosing an option
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
                            outputArea.appendText(CourseManagement.displayStudentDetails(student)); // Используем метод displayDetails из CourseManagement
                        }
                    } catch (NumberFormatException e) {
                        outputArea.appendText("Invalid input. Student ID must be a natural number (positive integer).\n");
                    }
                });
            }
        });
    }

    private void calculateStudentGrade() {
        // Create a dialog to input student ID
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Calculate Overall Grade");
        idDialog.setHeaderText("Calculate Overall Grade for a Student");
        idDialog.setContentText("Enter Student ID:");

        idDialog.showAndWait().ifPresent(idInput -> {
            try {
                // Validate the input to ensure it's a positive integer
                int studentId = Integer.parseInt(idInput);
                if (studentId <= 0) {
                    throw new NumberFormatException("ID must be a positive integer.");
                }

                // Retrieve the student by ID
                Student student = studentManagement.getStudentById(studentId);

                if (student == null) {
                    showAlert(Alert.AlertType.ERROR, "Student Not Found", "No student found with ID: " + studentId);
                    return;
                }

                // Calculate overall grade for the student
                double overallGrade = courseManagement.calculateOverallGradeForStudent(student);

                // Display the result
                showAlert(Alert.AlertType.INFORMATION,
                        "Overall Grade",
                        "The overall grade for " + student.getName() + "(ID: " + studentId + ") is: " + overallGrade);

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid positive integer for Student ID.");
            } catch (StudentNotEnrolledException e) {
                showAlert(Alert.AlertType.WARNING, "No Courses Enrolled", "The student is not enrolled in any courses.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            }
        });
    }

    private void addNewCourse() {
        Dialog<Course> courseDialog = new Dialog<>();
        courseDialog.setTitle("Add New Course");
        courseDialog.setHeaderText("Enter Course Details");

        // Set up dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");
        TextField courseNameField = new TextField();
        courseNameField.setPromptText("Course Name");
        TextField professorNameField = new TextField();
        professorNameField.setPromptText("Professor's Name");
        TextField maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Maximum Capacity");

        grid.add(new Label("Course ID:"), 0, 0);
        grid.add(courseIdField, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1);
        grid.add(courseNameField, 1, 1);
        grid.add(new Label("Professor's Name:"), 0, 2);
        grid.add(professorNameField, 1, 2);
        grid.add(new Label("Maximum Capacity:"), 0, 3);
        grid.add(maxCapacityField, 1, 3);

        courseDialog.getDialogPane().setContent(grid);
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        courseDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Validate inputs when OK is pressed
        Node addButton = courseDialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Add listeners for validation
        ChangeListener<String> validationListener = (observable, oldValue, newValue) -> {
            boolean isValid = isPositiveInteger(courseIdField.getText()) &&
                    !courseNameField.getText().trim().isEmpty() &&
                    isValidName(professorNameField.getText()) &&
                    isPositiveInteger(maxCapacityField.getText());
            addButton.setDisable(!isValid);
        };

        courseIdField.textProperty().addListener(validationListener);
        courseNameField.textProperty().addListener(validationListener);
        professorNameField.textProperty().addListener(validationListener);
        maxCapacityField.textProperty().addListener(validationListener);

        courseDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Course(
                        Integer.parseInt(courseIdField.getText()),
                        courseNameField.getText(),
                        professorNameField.getText(),
                        Integer.parseInt(maxCapacityField.getText())
                );
            }
            return null;
        });

        Optional<Course> result = courseDialog.showAndWait();

        result.ifPresent(course -> {
            try {
                // Add the course using CourseManagement
                CourseManagement.addCourse(
                        course.getNameOfCourse(),
                        course.getProfessorName(),
                        course.getId(),
                        course.getMaxCapacity()
                );
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
            } catch (CourseAlreadyExistsException | CourseNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Course already exists: " + e.getMessage());
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void enrollStudentInCourse() {
        Dialog<Pair<Integer, Integer>> enrollmentDialog = new Dialog<>();
        enrollmentDialog.setTitle("Enroll Student in Course");
        enrollmentDialog.setHeaderText("Enter Enrollment Details");

        // Set up dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(studentIdField, 1, 0);
        grid.add(new Label("Course ID:"), 0, 1);
        grid.add(courseIdField, 1, 1);

        enrollmentDialog.getDialogPane().setContent(grid);
        ButtonType enrollButtonType = new ButtonType("Enroll", ButtonBar.ButtonData.OK_DONE);
        enrollmentDialog.getDialogPane().getButtonTypes().addAll(enrollButtonType, ButtonType.CANCEL);

        // Validate inputs when OK is pressed
        Node enrollButton = enrollmentDialog.getDialogPane().lookupButton(enrollButtonType);
        enrollButton.setDisable(true);

        // Add listeners for validation
        ChangeListener<String> validationListener = (observable, oldValue, newValue) -> {
            boolean isValid = isPositiveInteger(studentIdField.getText()) &&
                    isPositiveInteger(courseIdField.getText());
            enrollButton.setDisable(!isValid);
        };

        studentIdField.textProperty().addListener(validationListener);
        courseIdField.textProperty().addListener(validationListener);

        enrollmentDialog.setResultConverter(dialogButton -> {
            if (dialogButton == enrollButtonType) {
                return new Pair<>(Integer.parseInt(studentIdField.getText()), Integer.parseInt(courseIdField.getText()));
            }
            return null;
        });

        Optional<Pair<Integer, Integer>> result = enrollmentDialog.showAndWait();

        result.ifPresent(pair -> {
            int studentId = pair.getKey();
            int courseId = pair.getValue();

            try {
                // Retrieve the student and course
                Student student = studentManagement.getStudentById(studentId);
                Course course = CourseManagement.getCourseById(courseId);

                if (student == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Student not found with ID " + studentId);
                    return;
                }
                if (course == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Course not found with ID " + courseId);
                    return;
                }

                // Check if the student is already enrolled in the course
                if (CourseManagement.isStudentEnrolledInCourse(student, course)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Student is already enrolled in this course.");
                } else {
                    // Enroll the student in the course
                    CourseManagement.enrollStudent(student, course);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student enrolled in course successfully!");
                }

            } catch (StudentAlreadyEnrolledException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Student is already enrolled in this course.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // Assign grade to student
    private void assignGradeToStudent() {
        Stage assignGradeStage = new Stage();
        assignGradeStage.setTitle("Assign Grade to Student");

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setVgap(10);
        layout.setHgap(10);

        Label studentIdLabel = new Label("Student ID:");
        TextField studentIdField = new TextField();
        Label courseIdLabel = new Label("Course ID:");
        TextField courseIdField = new TextField();
        Label gradeLabel = new Label("Grade (A, B, C, D, F):");
        TextField gradeField = new TextField();

        Button assignButton = new Button("Assign Grade");
        Button cancelButton = new Button("Cancel");

        layout.add(studentIdLabel, 0, 0);
        layout.add(studentIdField, 1, 0);
        layout.add(courseIdLabel, 0, 1);
        layout.add(courseIdField, 1, 1);
        layout.add(gradeLabel, 0, 2);
        layout.add(gradeField, 1, 2);
        layout.add(assignButton, 0, 3);
        layout.add(cancelButton, 1, 3);

        Scene scene = new Scene(layout, 350, 200);
        assignGradeStage.setScene(scene);
        assignGradeStage.show();

        // Event handler for assigning the grade
        assignButton.setOnAction(event -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim());
                int courseId = Integer.parseInt(courseIdField.getText().trim());
                String gradeInput = gradeField.getText().trim().toUpperCase();

                // Validate grade
                if (!gradeInput.matches("[ABCDF]")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Grade", "Please enter a valid grade (A, B, C, D, F).");
                    return;
                }
                char grade = gradeInput.charAt(0);

                // Retrieve the student
                Student student = studentManagement.getStudentById(studentId);
                if (student == null) {
                    showAlert(Alert.AlertType.ERROR, "Student Not Found", "No student found with ID " + studentId);
                    return;
                }

                // Retrieve the course
                Course course = CourseManagement.getCourseById(courseId);
                if (course == null) {
                    showAlert(Alert.AlertType.ERROR, "Course Not Found", "No course found with ID " + courseId);
                    return;
                }

                // Check enrollment
                if (!CourseManagement.isStudentEnrolled(student, course)) {
                    showAlert(Alert.AlertType.ERROR, "Enrollment Error", "Student is not enrolled in the course.");
                    return;
                }

                // Assign the grade
                CourseManagement.assignGrade(student, course, grade);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Grade " + grade + " assigned to " + student.getName() +
                        " for course " + course.getNameOfCourse());

                assignGradeStage.close();

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for Student ID and Course ID.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Unexpected Error", "An unexpected error occurred: " + e.getMessage());
            }
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> assignGradeStage.close());
    }

    // Remove student from a course
    private void removeStudentFromCourse() {
        Stage removeStudentStage = new Stage();
        removeStudentStage.setTitle("Remove Student From Course");

        // Set up the layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setVgap(10);
        layout.setHgap(10);

        Label studentIdLabel = new Label("Student ID:");
        TextField studentIdField = new TextField();
        Label courseIdLabel = new Label("Course ID:");
        TextField courseIdField = new TextField();

        Button removeButton = new Button("Remove Student");
        Button cancelButton = new Button("Cancel");

        layout.add(studentIdLabel, 0, 0);
        layout.add(studentIdField, 1, 0);
        layout.add(courseIdLabel, 0, 1);
        layout.add(courseIdField, 1, 1);
        layout.add(removeButton, 0, 2);
        layout.add(cancelButton, 1, 2);

        Scene scene = new Scene(layout, 350, 200);
        removeStudentStage.setScene(scene);
        removeStudentStage.show();

        // Event handler for removing the student
        removeButton.setOnAction(event -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim());
                int courseId = Integer.parseInt(courseIdField.getText().trim());

                // Retrieve the student by ID
                Student student = studentManagement.getStudentById(studentId);
                if (student == null) {
                    showAlert(Alert.AlertType.ERROR, "Student Not Found", "No student found with ID " + studentId);
                    return;
                }

                // Retrieve the course by ID
                Course course = CourseManagement.getCourseById(courseId);
                if (course == null) {
                    showAlert(Alert.AlertType.ERROR, "Course Not Found", "No course found with ID " + courseId);
                    return;
                }

                // Check if the student is enrolled in the course
                if (!CourseManagement.isStudentEnrolled(student, course)) {
                    showAlert(Alert.AlertType.ERROR, "Not Enrolled", "Student is not enrolled in the specified course.");
                    return;
                }

                // Remove the student from the course
                CourseManagement.removeStudentFromCourse(student, course);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student removed from course successfully.");

                removeStudentStage.close();

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for Student ID and Course ID.");
            } catch (Exception | CourseNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Unexpected Error", "An unexpected error occurred: " + e.getMessage());
            }
        });

        // Event handler for cancel button
        cancelButton.setOnAction(event -> removeStudentStage.close());
    }

    private void viewAllCourses() {
        Stage viewCoursesStage = new Stage();
        viewCoursesStage.setTitle("All Courses");

        // Layout for the window
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("All Courses");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea coursesTextArea = new TextArea();
        coursesTextArea.setEditable(false); // Make text area read-only
        coursesTextArea.setPrefHeight(400);
        coursesTextArea.setPrefWidth(500);

        // Use the modified CourseManagement method to get course info
        String allCourses = CourseManagement.getAllCourses();
        coursesTextArea.setText(allCourses);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> viewCoursesStage.close());

        layout.getChildren().addAll(titleLabel, coursesTextArea, closeButton);

        Scene scene = new Scene(layout, 550, 500);
        viewCoursesStage.setScene(scene);
        viewCoursesStage.show();
    }

    private void calculateCourseOverallGrade() {
        // Create a new window for calculating the course overall grade
        Stage calculateGradeStage = new Stage();
        calculateGradeStage.setTitle("Calculate Course Overall Grade");

        // Layout for the window
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Enter Course ID to Calculate Overall Grade");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");

        Button calculateButton = new Button("Calculate Grade");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false); // Make the result area read-only
        resultArea.setPrefHeight(100);

        // Handle calculation button click event
        calculateButton.setOnAction(event -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                // Use the existing method to calculate overall grade for the course
                double overallGrade = CourseManagement.calculateOverallGradeForCourse(courseId);

                resultArea.setText("Overall grade for the course (ID: " + courseId + "): " + overallGrade);
            } catch (NumberFormatException e) {
                resultArea.setText("Invalid course ID. Please enter a valid number.");
            } catch (CourseNotFoundException e) {
                resultArea.setText("Error: " + e.getMessage());
            } catch (IllegalStateException e) {
                resultArea.setText("Error: " + e.getMessage());
            } catch (Exception e) {
                resultArea.setText("An unexpected error occurred: " + e.getMessage());
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> calculateGradeStage.close());

        layout.getChildren().addAll(titleLabel, courseIdField, calculateButton, resultArea, closeButton);

        Scene scene = new Scene(layout, 350, 250);
        calculateGradeStage.setScene(scene);
        calculateGradeStage.show();
    }

    private void displayCourseWithStudents() {
        // Create a new window to display course with students
        Stage displayCourseStage = new Stage();
        displayCourseStage.setTitle("Display Course with Students");

        // Layout for the window
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label titleLabel = new Label("Enter Course ID to Display Students");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");

        Button displayButton = new Button("Display Students");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false); // Make the result area read-only
        resultArea.setPrefHeight(200);

        // Handle display button click event
        displayButton.setOnAction(event -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());

                // Capture the output from the displayCourseWithStudents method
                // Redirecting the output of the method to the TextArea
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                System.setOut(printStream);

                // Call the CourseManagement method to display course with students
                CourseManagement.displayCourseWithStudents(courseId);

                // Set the result in the TextArea
                resultArea.setText(outputStream.toString());

            } catch (NumberFormatException e) {
                resultArea.setText("Invalid course ID. Please enter a valid number.");
            } catch (Exception e) {
                resultArea.setText("An unexpected error occurred: " + e.getMessage());
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> displayCourseStage.close());

        layout.getChildren().addAll(titleLabel, courseIdField, displayButton, resultArea, closeButton);

        Scene scene = new Scene(layout, 350, 300);
        displayCourseStage.setScene(scene);
        displayCourseStage.show();
    }


    // Helper methods
    private boolean isPositiveInteger(String input) {
        try {
            return Integer.parseInt(input) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    public static void main(String[] args) {
        launch(args);
    }
    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
