package com.hibernate.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.Session;
import com.hibernate.entity.Course;
import com.hibernate.entity.Student;

public class CreateStudentsAndCourseDemo {

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
			
			Course tempCourse1 = new Course("Operation System", "Computer Science");
			Course tempCourse2 = new Course("Intro to Power System", "Electrial Engineering");
			
			tempCourse1.addStudent(tempStudent);
			tempCourse2.addStudent(tempStudent);
			
			// save the courses
			session.save(tempCourse1);
			session.save(tempCourse2);
			
//			// create new student
//			Student tempStudent1 = new Student("Bruce", "Lee", "bruce@test.com");
//			Student tempStudent2 = new Student("Tom", "Kat", "tom@test.com");
//			// add student to the course
//			tempCourse.addStudent(tempStudent1);
//			tempCourse.addStudent(tempStudent2);
//			session.save(tempStudent1);
//			session.save(tempStudent2);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}

	}

}
