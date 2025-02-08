package com.sba.sms.services;

import com.sba.sms.dao.StudentDAO;
import com.sba.sms.model.Course;
import com.sba.sms.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    public void registerStudent(Student student) {
        studentDAO.save(student);
        System.out.println("Student registered successfully: " + student.getEmail());
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentDAO.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    public void enrollStudentInCourse(String studentEmail, Course course) {
        Optional<Student> studentOpt = studentDAO.findByEmail(studentEmail);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.getCourses().add(course);
            studentDAO.update(student);
            System.out.println("Successfully enrolled " + student.getName() + " in " + course.getName());
        } else {
            System.out.println("Student not found: " + studentEmail);
        }
    }

    public void deleteStudent(String email) {
        studentDAO.delete(email);
        System.out.println("Student deleted: " + email);
    }
}