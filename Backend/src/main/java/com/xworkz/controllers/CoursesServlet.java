package com.xworkz.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.dao.CourseDao;
import com.xworkz.dao.impl.CourseDaoImpl;
import com.xworkz.dto.CourseDto;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.CourseService;
import com.xworkz.service.impl.CourseServiceImpl;

/**
 * Servlet implementation class CoursesServlet
 */
@WebServlet("/course")
public class CoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final ObjectMapper mapper = new ObjectMapper();
    private final CourseDao dao=new CourseDaoImpl();
    private final CourseService service=new CourseServiceImpl(dao);
   
    public CoursesServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		 Map<String, Object> result = new HashMap<>();
		 //get session that matches from cookie
		 //we make sure using filter that session is not null that we will do later
		 //we can also inject id to req object using filter
		 HttpSession session=req.getSession(false);
		 int student_id=(int) session.getAttribute("id");
		 
		 try {
			 List<CourseDto> list=service.getCourses(student_id);
			 res.setStatus(HttpServletResponse.SC_OK);
			 result.put("message", "success");
			 result.put("data", list);
			 
			 
			 
		 }
		 catch(ValidationException e) {
			 res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			 result.put("message", "invalid input");
			 
			 
		 }
		 catch(Exception e) {
			 res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 result.put("message", "internal server error");
		 }
		 mapper.writeValue(res.getWriter(), result);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
			 Map<String, Object> result = new HashMap<>();
			 //get session that matches from cookie
			 //we make sure using filter that session is not null that we will do later
			 //we can also inject id to req object using filter
			 HttpSession session=req.getSession(false);
			 int student_id=(int) session.getAttribute("id");
			 CourseDto[] courses = mapper.readValue(req.getReader(), CourseDto[].class);
			 try {
				 service.addCourses(student_id, courses);
				 res.setStatus(HttpServletResponse.SC_OK);
				 result.put("message", "courses added successfully");
				
			 }
			 catch(ValidationException e) {
				 res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				 result.put("message", "invalid input");
				 
				 
			 }
			 catch(Exception e) {
				 res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				 result.put("message", "internal server error");
			 }
			 mapper.writeValue(res.getWriter(), result);
			 
			 
	}

}
