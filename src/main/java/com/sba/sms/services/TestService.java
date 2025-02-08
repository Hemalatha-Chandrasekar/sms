package com.sba.sms.services;

import com.sba.sms.model.Course;
import com.sba.sms.model.Student;
import com.sba.sms.services.CourseService;
import com.sba.sms.services.StudentService;

public class TestService {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();

        // Create courses
        Course javaCourse = new Course("Java", "Phillip Witkin");
        Course sqlCourse = new Course("SQL", "Phillip Witkin");

        courseService.createCourse(javaCourse);
        courseService.createCourse(sqlCourse);

        // Create students
        Student student1 = new Student("john@example.com", "John Doe", "password123");
        Student student2 = new Student("reema@gmail.com", "Reema Brown", "password");

        studentService.registerStudent(student1);
        studentService.registerStudent(student2);

        // Get all students
        System.out.println("\nAll Students:");
        studentService.getAllStudents().forEach(s -> System.out.println(s.getEmail()));

        // Get all courses
        System.out.println("\nAll Courses:");
        courseService.getAllCourses().forEach(c -> System.out.println(c.getName()));

        // Enroll student in a course
        studentService.enrollStudentInCourse("reema@gmail.com", sqlCourse);

        // Fetch updated student details
        studentService.getStudentByEmail("reema@gmail.com")
                .ifPresent(s -> System.out.println("\nReema's Courses: " + s.getCourses().size()));

        // Delete student
        studentService.deleteStudent("john@example.com");
    }
}