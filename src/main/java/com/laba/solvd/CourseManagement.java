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
        Course course = new Course(courseId, courseName, professorName, maxCapacity);

        if (getCourseById(courseId) != null) {
            throw new CourseAlreadyExistsException("Course with ID " + courseId + " already exists: " + courseName);
        }

        courseList.add(course);
        System.out.println("Course added: " + courseName);
    }

    public static Course getCourseById(int courseId) throws CourseNotFoundException {
        for (Course course : courseList) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        // Throw CourseNotFoundException if the course is not found
        throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
    }

    // Enroll student in a course
    public static void enrollStudent(Student student, Course course) {
        // Check if the student is enrolled for the course
        if (student.getEnrolledCourses().containsKey(course)) {
            throw new IllegalArgumentException("Student " + student.getName() + " is already enrolled in course: " + course.getNameOfCourse());
        }

        // Enroll
        student.getEnrolledCourses().put(course, null); // The initial grade is null
        System.out.println("Student " + student.getName() + " enrolled in course: " + course.getNameOfCourse());
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

        // Calculate and return the average grade
        return (double) totalGrades / numberOfCourses;
    }

    // Convert letter grade to numeric points (A=4, B=3, C=2, D=1, F=0)
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

    // Display all courses
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
    public static void displayAllStudentsGrades() {
        for (Student student : studentGrades.keySet()) {
            double overallGrade = calculateOverallGrade(student);
            System.out.println(student.getName() + " - Overall Grade: " + overallGrade);
        }
    }
}
