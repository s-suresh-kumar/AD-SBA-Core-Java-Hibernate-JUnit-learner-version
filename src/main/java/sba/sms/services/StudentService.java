package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.List;

public class StudentService  implements StudentI {
    @Override
    public List<Student> getAllStudents(){
        SessionFactory sessionFactory;
        Session session = null;
        List<Student> students = null;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();

            students =
                    session.createQuery("FROM Student", Student.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return students;
    }

    @Override
    public void createStudent(Student student){
        SessionFactory sessionFactory;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(student);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

@Override
    public Student getStudentByEmail(String email){
        SessionFactory sessionFactory;
        Session session = null;
        Student student = null;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();

            student = session.createQuery("FROM Student WHERE email = :email",
                            Student.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return student;
    }

    @Override
    public boolean validateStudent(String email, String password){
        SessionFactory sessionFactory;
        Session session = null;
        Student student = null;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();

            student = session.createQuery("FROM Student WHERE email = " +
                            ":email AND password = :password", Student.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return student != null;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId){
        SessionFactory sessionFactory;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Student student = session.createQuery("FROM Student WHERE " +
                            "email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResult();

            Course course = session.get(Course.class, (long) courseId);

            if (student != null && course != null) {
                student.addCourse(course);
                session.persist(student);
                session.persist(course);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
