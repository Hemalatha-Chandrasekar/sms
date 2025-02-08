package com.sba.sms.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.sba.sms.model.Student;
import com.sba.sms.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class StudentDAO {

    public void save(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Optional<Student> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM Student WHERE email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResult());
        }
    }

    public List<Student> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Student", Student.class).list();
        }
    }

    public void update(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(String email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.createQuery("FROM Student WHERE email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResult();
            if (student != null) {
                session.delete(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}