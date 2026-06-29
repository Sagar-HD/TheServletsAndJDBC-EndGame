package com.xworkz.dto;

public class CourseDto {
	private int id;
	private String course_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public CourseDto(int id, String course_name) {
		super();
		this.id = id;
		this.course_name = course_name;
	}
	public CourseDto() {
		super();
	}
	
	

}
