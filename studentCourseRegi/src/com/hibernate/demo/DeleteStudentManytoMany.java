package com.hibernate.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.Session;
import com.hibernate.entity.Course;
import com.hibernate.entity.Student;

public class DeleteStudentManytoMany {

	public static void main(String[] args) {
		
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		
		try {
			session.beginTransaction();
			
			int studentId = 3;
			Student tempStudent = session.get(Student.class, studentId);
			
			session.delete(tempStudent);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}

	}

}
