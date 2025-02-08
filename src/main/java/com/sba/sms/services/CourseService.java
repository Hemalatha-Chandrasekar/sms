package com.sba.sms.services;

import com.sba.sms.dao.CourseDAO;
import com.sba.sms.model.Course;

import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    public void createCourse(Course course) {
        courseDAO.save(course);
        System.out.println("Course created successfully: " + course.getName());
    }

    public Course getCourseById(int id) {
        return courseDAO.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAll();
    }

    public void updateCourse(Course course) {
        courseDAO.update(course);
        System.out.println("Course updated successfully: " + course.getName());
    }

    public void deleteCourse(int id) {
        courseDAO.delete(id);
        System.out.println("Course deleted with ID: " + id);
    }
}