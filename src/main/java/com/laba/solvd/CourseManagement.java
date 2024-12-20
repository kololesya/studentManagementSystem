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
    private Course course;
    private static List<Course> courseList = new ArrayList<>();  // Static list to store courses
    private static Map<Student, Map<Course, Character>> studentGrades = new HashMap<>();  // Map to store student grades for courses

    // Add new course
    public static void addCourse(String courseName, String professorName, int courseId, int maxCapacity) throws CourseNotFoundException {
        // Create a new course object
        Course course = new Course(courseId, courseName, professorName, maxCapacity);

        if (courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive integer.");
        }
        // Check if a course with the same ID already exists
        if (getCourseById(courseId) != null) {
            throw new CourseAlreadyExistsException("Course with ID " + courseId + " already exists: " + courseName);
        } else {
            // Add the course to the course list
            courseList.add(course);
            System.out.println("Course added: " + courseName);
        }
    }

    public static Course getCourseById(int courseId) {
        // Iterate through the course list to find a course with the given ID
        for (Course course : courseList) {
            if (course.getId() == courseId) {
                return course;  // Return the course if it exists
            }
        }
        return null;  // Return null if no course with the given ID is found
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
                studentGrades.computeIfAbsent(student, k -> new HashMap<>()); // Создаем запись для студента
                System.out.println("Student " + student.getName() + " successfully enrolled in course: " + course.getNameOfCourse());
            } else {
                System.out.println("Course " + course.getNameOfCourse() + " is full.");
            }
        }
    }

    // Assign grade to student for a course
    public static void assignGrade(Student student, Course course, char grade) {
        // Check if the student is enrolled in the course
        boolean isEnrolled = course.getEnrolledStudents().stream()
                .anyMatch(s -> s.getStudentId() == student.getStudentId());

        if (!isEnrolled) {
            throw new StudentNotEnrolledException("Student " + student.getName() + " is not enrolled in course: " + course.getNameOfCourse());
        }

        // Validate grade
        if (grade != 'A' && grade != 'B' && grade != 'C' && grade != 'D' && grade != 'F') {
            throw new InvalidGradeException("Invalid grade: " + grade + ". Allowed grades are A, B, C, D, F.");
        }

        // Assign grade to the student for the course
        studentGrades.get(student).put(course, grade);
        course.assignGradeToStudent(student, grade); // Также сохраняем в курсе
        System.out.println("Grade " + grade + " assigned to student " + student.getName() + " for course " + course.getNameOfCourse());

        // Update the student grades map
        if (!studentGrades.containsKey(student)) {
            studentGrades.put(student, new HashMap<>());
        }
        studentGrades.get(student).put(course, grade);
    }

    // Calculate overall grade for a student
    public static double calculateOverallGradeForStudent(Student student) {
        Map<Course, Character> studentCourses = studentGrades.get(student);
        if (studentCourses == null || studentCourses.isEmpty()) {
            throw new StudentNotEnrolledException("Student " + student.getName() + " is not enrolled in any course.");
        }
        int totalGrades = 0;
        int numberOfCourses = studentCourses.size();

        for (Character grade : studentCourses.values()) {
            if (grade != null) {
                totalGrades += gradeToPoints(grade); // Преобразуем оценку в баллы
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

    public static void displayCourseWithStudents(int courseId) {
        // Retrieve a course by ID
        Course course = getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found with ID " + courseId);
            return;
        }

        // Output info about course
        System.out.println("Course ID: " + course.getId() + ", Name: " + course.getNameOfCourse() +
                ", Professor: " + course.getProfessorName() + ", Capacity: " + course.getMaxCapacity() +
                ", Enrolled Students: " + course.getEnrolledStudents().size() + "/" + course.getMaxCapacity());

        // Retrieve a list af enrolled students
        List<Student> students = course.getEnrolledStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled in this course.");
        } else {
            System.out.println("Enrolled Students:");
            for (Student student : students) {
                // Retrieve student's grade
                Character grade = course.getStudentGrades().get(student);
                // Output info about the student and his grades (если оценка отсутствует, выводим "Not Assigned")
                System.out.println("Student ID: " + student.getStudentId() + ", Name: " + student.getName() +
                        ", Grade: " + (grade != null ? grade : "Not Assigned"));
            }
        }
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
    private static void displayAllStudentsGrades() {
        for (Student student : studentGrades.keySet()) {
            double overallGrade = calculateOverallGradeForStudent(student);
            System.out.println(student.getName() + " - Overall Grade: " + overallGrade);
        }
    }

    public static double calculateOverallGradeForCourse(int courseId) throws CourseNotFoundException {
        // Получаем курс по ID
        Course course = getCourseById(courseId);

        if (course == null) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        }

        // Получаем список зачисленных студентов
        List<Student> students = course.getEnrolledStudents();

        if (students.isEmpty()) {
            throw new IllegalStateException("No students enrolled in course: " + course.getNameOfCourse());
        }

        // Инициализируем переменные для подсчета
        int totalPoints = 0;
        int totalGrades = 0;

        // Получаем мапу оценок студентов
        Map<Student, Character> studentGrades = course.getStudentGrades();

        for (Map.Entry<Student, Character> entry : studentGrades.entrySet()) {
            Character grade = entry.getValue();
            if (grade != null) { // Учитываем только студентов с назначенными оценками
                totalPoints += gradeToPoints(grade);
                totalGrades++;
            }
        }

        if (totalGrades == 0) {
            throw new IllegalStateException("No grades available for course: " + course.getNameOfCourse());
        }

        // Возвращаем средний балл
        return (double) totalPoints / totalGrades;
    }

    public void displayDetails(){
        //course.displayDetails();
        System.out.println("All student's courses with grades " + studentGrades.get(course).toString());
    }
}
