package com.sba.sms.services;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import com.sba.sms.model.Course;
import com.sba.sms.util.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseServiceTest {
    private static CourseService courseService;
    private static Session session;

    @BeforeAll
    static void setup() {
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
    void testCreateCourse() {
        Course course = new Course("Spring Boot", "John Smith");
        courseService.createCourse(course);

        List<Course> courses = courseService.getAllCourses();
        assertFalse(courses.isEmpty());
        assertEquals("Spring Boot", courses.get(0).getName());
    }

    @Test
    @Order(2)
    void testGetAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        assertFalse(courses.isEmpty());
    }

    @Test
    @Order(3)
    void testDeleteCourse() {
        List<Course> courses = courseService.getAllCourses();
        if (!courses.isEmpty()) {
            courseService.deleteCourse(courses.get(0).getId());
        }
        List<Course> updatedCourses = courseService.getAllCourses();
        assertTrue(updatedCourses.isEmpty());
    }
}