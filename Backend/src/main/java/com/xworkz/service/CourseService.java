package com.xworkz.service;

import java.util.List;

import com.xworkz.dto.CourseDto;

public interface CourseService {
	int addCourses(int student_id,CourseDto[] courses)throws Exception;
	List<CourseDto> getCourses(int student_id) throws Exception;
	
}
