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

}
