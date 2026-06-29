package com.xworkz.controllers;

import java.io.IOException;

import java.sql.SQLException;

import com.xworkz.dao.AuthDao;

import com.xworkz.service.AuthService;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.dao.impl.AuthDaoImpl;
import com.xworkz.dto.StudentDto;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.impl.AuthServiceImpl;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthDao dao = new AuthDaoImpl();
    private final AuthService service = new AuthServiceImpl(dao);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
		 Map<String, String> result = new HashMap<>();
		 StudentDto student = mapper.readValue(req.getReader(), StudentDto.class);
		  
		   
		    try {
		    	service.register(student);
		    	 result.put("message", "saved sucessfully ");
		    	 
		    	
		    	 resp.setStatus(200);
		    	
		    }
		    catch(ValidationException e) {
		    	 result.put("message", "Registration Failed all name , password or department missing");
		    	 resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    	
		    }
		    catch(SQLException | IOException e) {
		    	 result.put("message", "Registration Failed  internal server error");
		    	 e.printStackTrace();
		    	 resp.setStatus(500);
		    	
		    }
		    catch(Exception e) {
		   	 result.put("message", "Registration Failed something went wrong");
	    	 e.printStackTrace();
	    	 resp.setStatus(500);
		    }
		    


	        mapper.writeValue(resp.getWriter(), result);

	       
	
		
		
	}


}
