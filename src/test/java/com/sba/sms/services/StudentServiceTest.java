package com.sba.sms.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import com.sba.sms.model.Course;
import com.sba.sms.model.Student;
import com.sba.sms.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {
    private static StudentService studentService;
    private static CourseService courseService;
    private static Session session;

    @BeforeAll
    static void setup() {
        studentService = new StudentService();
        courseService = new CourseService();

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("DELETE FROM student_courses").executeUpdate();
        session.createNativeQuery("DELETE FROM students").executeUpdate();
        session.createNativeQuery("DELETE FROM courses").executeUpdate();

        transaction.commit();
        session.close();
    }

    @Test
    @Order(1)
    void testRegisterStudent() {
        Student student = new Student("test@example.com", "Test Student", "password123");
        studentService.registerStudent(student);

        Optional<Student> fetchedStudent = studentService.getStudentByEmail("test@example.com");
        assertTrue(fetchedStudent.isPresent());
        assertEquals("Test Student", fetchedStudent.get().getName());
    }

    @Test
    @Order(2)
    void testEnrollStudentInCourse() {
        Student student = new Student("john@example.com", "John Doe", "password123");
        studentService.registerStudent(student);

        Course course = new Course("Java", "Phillip Witkin");
        courseService.createCourse(course);

        studentService.enrollStudentInCourse("john@example.com", course);

        Optional<Student> fetchedStudent = studentService.getStudentByEmail("john@example.com");
        assertTrue(fetchedStudent.isPresent());
        assertEquals(1, fetchedStudent.get().getCourses().size());
    }

    @Test
    @Order(3)
    void testGetAllStudents() {
        List<Student> students = studentService.getAllStudents();
        assertFalse(students.isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteStudent() {
        studentService.deleteStudent("test@example.com");
        Optional<Student> deletedStudent = studentService.getStudentByEmail("test@example.com");
        assertFalse(deletedStudent.isPresent());
    }
}