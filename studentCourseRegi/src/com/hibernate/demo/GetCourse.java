package com.hibernate.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

import org.hibernate.Session;
import com.hibernate.entity.Course;
import com.hibernate.entity.Student;

public class GetCourse {

	public static void main(String[] args) {
		
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		
		try {
			session.beginTransaction();
			
			String courseName = "Computer Network";
			List<Course> theCourses = session.createQuery("from Course c where c.courseName = '" + courseName + "'").getResultList();
			getStudent(theCourses);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}

	}
	private static void getStudent(List<Course> theCourses) {
		for (Course tempCourse : theCourses) {
			System.out.println("The student who take " + tempCourse.getCourseName() + ":" +
					tempCourse.getStudents());
		}
	}

}
