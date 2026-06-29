package com.xworkz.service.impl;

import java.util.List;

import com.xworkz.dao.CourseDao;
import com.xworkz.dto.CourseDto;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.CourseService;

public class CourseServiceImpl implements CourseService{
	private CourseDao dao;
	

	public CourseServiceImpl(CourseDao dao) {
		super();
		this.dao = dao;
	}


	@Override
	public int addCourses(int student_id, CourseDto[] courses) throws Exception {
		int courses_added=0;
		if(student_id>0&&courses!=null) {
			courses_added=dao.addCourses(student_id, courses);
			
		}
		else {
			throw new ValidationException("pass all valid required fields");
		}
		
		return courses_added;
	}


	@Override
	public List<CourseDto> getCourses(int student_id) throws Exception {
		List<CourseDto> list=null;
		if(student_id>0) {
			list=dao.getCourses(student_id);
			
		}
		else {
			//this cannot happen bcs we are getting from session
			//just to learn handling in controller i added this
		throw new ValidationException("invalid id");
		}
		return list;
	}

}
