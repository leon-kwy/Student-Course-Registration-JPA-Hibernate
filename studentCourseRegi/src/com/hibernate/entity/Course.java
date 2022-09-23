package com.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.*;


@Entity
@Table(name="course")
public class Course {

	 @Id
	 // make the primary key auto-incremental
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name="course_id")
	 private int courseId;
	 
	 @Column(name="course_name")
	 private String courseName;
	 
	 @Column(name="dept")
	 private String dept;
	 
	 @ManyToMany(fetch=FetchType.LAZY,
			 cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					 CascadeType.DETACH, CascadeType.REFRESH})
	 @JoinTable(
			 name="student_course",
			 joinColumns=@JoinColumn(name="course_id"),
			 inverseJoinColumns=@JoinColumn(name="student_id")
			 )
	 private List<Student> students;
	 
	 public Course() {
		 
	 }

	public Course(String courseName, String dept) {
		this.courseName = courseName;
		this.dept = dept;
	}
	
	
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void addStudent(Student theStudent) {
		
		if (students == null) {
			students = new ArrayList<>();
		}
		
		students.add(theStudent);
	}
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", dept=" + dept + "]";
	}
	
	
	 
}
