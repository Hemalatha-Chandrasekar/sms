package com.sba.sms.util;

import com.sba.sms.model.Course;
import com.sba.sms.model.Student;
import com.sba.sms.services.CourseService;
import com.sba.sms.services.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CommandLine {
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final Scanner scanner = new Scanner(System.in);
    private Student loggedInStudent = null;

    public void start() {
        while (true) {
            System.out.println("\nSelect # from menu:");
            System.out.println("1. Student Login");
            System.out.println("2. Quit");
            System.out.print("> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    studentLogin();
                    break;
                case 2:
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private void studentLogin() {
        System.out.print("\nEnter student email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<Student> studentOpt = studentService.getStudentByEmail(email);
        if (studentOpt.isPresent() && studentOpt.get().getPassword().equals(password)) {
            loggedInStudent = studentOpt.get();
            System.out.println("\nWelcome, " + loggedInStudent.getName() + "!");
            studentMenu();
        } else {
            System.out.println("Invalid email or password. Try again.");
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\nSelect # from menu:");
            System.out.println("1. View Enrolled Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Logout");
            System.out.print("> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewEnrolledCourses();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    loggedInStudent = null;
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private void viewEnrolledCourses() {
        System.out.println("\n" + loggedInStudent.getEmail() + " courses:");
        System.out.println("-------------------------------");
        System.out.println("ID | Course            | Instructor");
        System.out.println("----------------------------------");

        if (loggedInStudent.getCourses().isEmpty()) {
            System.out.println("No courses to view.");
        } else {
            for (Course course : loggedInStudent.getCourses()) {
                System.out.printf("%-3d| %-17s | %-15s%n", course.getId(), course.getName(), course.getInstructor());
            }
        }
    }

    private void registerForCourse() {
        System.out.println("\nAll courses:");
        System.out.println("-------------------------------");
        System.out.println("ID | Course            | Instructor");
        System.out.println("----------------------------------");

        List<Course> courses = courseService.getAllCourses();
        for (Course course : courses) {
            System.out.printf("%-3d| %-17s | %-15s%n", course.getId(), course.getName(), course.getInstructor());
        }

        System.out.print("\nSelect course #: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Course selectedCourse = courseService.getCourseById(courseId);
        if (selectedCourse != null) {
            studentService.enrollStudentInCourse(loggedInStudent.getEmail(), selectedCourse);
            System.out.println("Successfully registered " + loggedInStudent.getName() + " for " + selectedCourse.getName());
        } else {
            System.out.println("Invalid course selection.");
        }
    }
}