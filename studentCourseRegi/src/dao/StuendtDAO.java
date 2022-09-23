package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.entity.Course;
import com.hibernate.entity.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

public class StuendtDAO {
	
	// 2.1) Add a new student along with their course registrations
	public void add(String firstName, String lastName, String email, String courseName) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		Student tempStudent = new Student(firstName, lastName, email);
		
		List<Course> theCourses = session.createQuery("from Course c where c.courseName = '" + courseName + "'").getResultList();
		Course tempCourse = theCourses.get(0);
		
		tempCourse.addStudent(tempStudent);
		
		session.save(tempStudent);
		session.getTransaction().commit();
		session.close();
	}
	
	// 2.2) Delete a student.
	public void delete(int id) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		int studentId = id;
		
		// One way to delete a student
//		Student tempStudent = session.get(Student.class, studentId);
//		session.delete(tempStudent);
		
		// Another way to delete a student, which does not need get the student information 
		// from database at first
		session.createQuery("delete from Student where id="+id).executeUpdate();
		
		
		session.getTransaction().commit();
		session.close();
	}
	
	// 2.3) Get all students, sorted by their first name, for a given course with course name as input.
	public void getAll(String courseName) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		List<Course> theCourses = session.createQuery("from Course c where c.courseName = '" + courseName + "'").getResultList();
		Course tempCourse = theCourses.get(0);
		List<Student> theStudents = tempCourse.getStudents();
		
		Collections.sort(theStudents, new Comparator<Student>(){
			public int compare(Student s1, Student s2) {
				return s1.getFirstName().compareTo(s2.getFirstName());
			}
		});
		System.out.println("The students who take the course " + courseName + " : " + theStudents);
		
		session.getTransaction().commit();
		
	}
	// 2.4) We can create a new entity named StudentCourseRegistration. And set ManyToMany relations to Student and Course table.
	// In this way, when we register a new course for a student, we can set score to this class through 
	// StudentCourseRegistration class. Besides that, we can set more attributes in this way, suck as the semester that
	// student takes the course and so on.
	
	
	// 2.5) Find all student who don't register for a given course
	public void findStudent(String courseName) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		List<Course> theCourses = session.createQuery("from Course c where c.courseName = '" + courseName + "'").getResultList();
		Course tempCourse = theCourses.get(0);
		List<Student> registeredStudent = tempCourse.getStudents();
		
		List<Student> allStudent = session.createQuery("from Student").getResultList();
		
		List<Student> leftStudent = new ArrayList<Student>(allStudent);
		leftStudent.removeAll(registeredStudent);
		System.out.println("The student who do not take the course " + courseName + " : " + leftStudent);
		
		
		
	}
	
}
