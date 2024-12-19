package com.laba.solvd;

import com.laba.solvd.exceptions.CourseAlreadyExistsException;
import com.laba.solvd.exceptions.CourseNotFoundException;
import com.laba.solvd.exceptions.InvalidGradeException;
import com.laba.solvd.exceptions.StudentNotEnrolledException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManagement {
    private static List<Course> courseList = new ArrayList<>();  // Static list to store courses
    private static Map<Student, Map<Course, Character>> studentGrades = new HashMap<>();  // Map to store student grades for courses

    // Add new course
    public static void addCourse(String courseName, String professorName, int courseId, int maxCapacity) throws CourseNotFoundException {
        // Create a new course object
        Course course = new Course(courseId, courseName, professorName, maxCapacity);

        // Check if a course with the same ID already exists
        if (getCourseById(courseId) != null) {
            throw new CourseAlreadyExistsException("Course with ID " + courseId + " already exists: " + courseName);
        } else {
            // Add the course to the course list
            courseList.add(course);
            System.out.println("Course added: " + courseName);
        }
    }

    public static Course getCourseById(int courseId) throws CourseNotFoundException {
        // Iterate through the course list to find a course with the given ID
        for (Course course : courseList) {
            if (course.getId() == courseId) {
                return course;  // Return the course if it exists
            }
        }
        throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
    }

    // Add a student on a course
    public static void enrollStudent(Student student, Course course) {
        // Check if the student is already enrolled
        boolean isAlreadyEnrolled = course.getEnrolledStudents().stream()
                .anyMatch(s -> s.getStudentId() == student.getStudentId());

        if (isAlreadyEnrolled) {
            System.out.println("Student " + student.getName() + " is already enrolled in course: " + course.getNameOfCourse());
        } else {
            if (course.getEnrolledStudents().size() < course.getMaxCapacity()) {
                course.addStudent(student);
                System.out.println("Student " + student.getName() + " successfully enrolled in course: " + course.getNameOfCourse());
            } else {
                System.out.println("Course " + course.getNameOfCourse() + " is full.");
            }
        }
    }

    // Assign grade to student for a course
    public static void assignGrade(Student student, Course course, char grade) {
        // Check if the student is enrolled in the course
        if (!student.getEnrolledCourses().containsKey(course)) {
            throw new StudentNotEnrolledException("Student " + student.getName() + " is not enrolled in course: " + course.getNameOfCourse());
        }

        // Validate grade
        if (grade != 'A' && grade != 'B' && grade != 'C' && grade != 'D' && grade != 'F') {
            throw new InvalidGradeException("Invalid grade: " + grade + ". Allowed grades are A, B, C, D, F.");
        }

        // Assign grade to the student for the course
        student.assignGradeToCourse(course, grade);
        System.out.println("Grade " + grade + " assigned to student " + student.getName() + " for course " + course.getNameOfCourse());

        // Update the student grades map
        if (!studentGrades.containsKey(student)) {
            studentGrades.put(student, new HashMap<>());
        }
        studentGrades.get(student).put(course, grade);
    }

    // Calculate overall grade for a student
    public static double calculateOverallGrade(Student student) {
        Map<Course, Character> enrolledCourses = student.getEnrolledCourses();
        int totalGrades = 0;
        int numberOfCourses = enrolledCourses.size();

        if (numberOfCourses == 0) {
            throw new StudentNotEnrolledException("Student " + student.getName() + " is not enrolled in any course.");
        }

        // Calculate the total grade points
        for (Character grade : enrolledCourses.values()) {
            if (grade != null) {
                totalGrades += gradeToPoints(grade);  // Convert grade to points
            }
        }
        displayAllStudentsGrades();

        // Calculate and return the average grade
        return (double) totalGrades / numberOfCourses;
    }

    public static boolean removeStudentFromCourse(int studentId, int courseId) throws CourseNotFoundException {
        Course course = getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found with ID " + courseId);
            return false;
        }

        boolean removed = course.getEnrolledStudents().removeIf(student -> student.getStudentId() == studentId);
        if (removed) {
            System.out.println("Student with ID " + studentId + " removed from course " + course.getNameOfCourse());
        } else {
            System.out.println("Student with ID " + studentId + " not found in course " + course.getNameOfCourse());
        }
        return removed;
    }

    // Display details of a course along with its enrolled students
    public static void displayCourseWithStudents(int courseId) {
        try {
            Course course = getCourseById(courseId);
            System.out.println(course.toString());

            List<Student> students = course.getEnrolledStudents();
            if (students.isEmpty()) {
                System.out.println("No students enrolled in this course.");
            } else {
                for (Student student : students) {
                    System.out.println("Student ID: " + student.getStudentId() + ", Name: " + student.getName() + ", Grade: " + student.getEnrolledCourses().get(course));
                }
            }
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Convert a letter grade to numeric points (A=4, B=3, C=2, D=1, F=0)
    private static int gradeToPoints(char grade) {
        switch (grade) {
            case 'A': return 4;
            case 'B': return 3;
            case 'C': return 2;
            case 'D': return 1;
            case 'F': return 0;
            default: return 0;
        }
    }

    // Display all available courses
    public static void displayAllCourses() {
        if (courseList.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("List of available courses:");
        for (Course course : courseList) {
            System.out.println(course);
        }
    }

    // Display all students and their overall grades
    private static void displayAllStudentsGrades() {
        for (Student student : studentGrades.keySet()) {
            double overallGrade = calculateOverallGrade(student);
            System.out.println(student.getName() + " - Overall Grade: " + overallGrade);
        }
    }

    // Calculate the overall grade for a specific course based on its enrolled students
    public static double calculateOverallGradeForCourse(int courseId) throws CourseNotFoundException {
        Course course = getCourseById(courseId);

        List<Student> students = course.getEnrolledStudents();

        if (students.isEmpty()) {
            throw new IllegalStateException("No students enrolled in course: " + course.getNameOfCourse());
        }

        int totalPoints = 0;
        int totalGrades = 0;

        // Calculate total points and grades for students with valid grades
        for (Student student : students) {
            Character grade = student.getEnrolledCourses().get(course);
            if (grade != null) { // Include only students with grades
                totalPoints += gradeToPoints(grade);
                totalGrades++;
            }
        }

        if (totalGrades == 0) {
            throw new IllegalStateException("No grades available for course: " + course.getNameOfCourse());
        }

        // Return the average grade for the course
        return (double) totalPoints / totalGrades;
    }
}
