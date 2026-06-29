package com.xworkz.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xworkz.dao.AuthDao;
import com.xworkz.dao.impl.AuthDaoImpl;
import com.xworkz.dto.StudentDto;
import com.xworkz.exceptions.InvalidCredentialsException;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.AuthService;
import com.xworkz.service.impl.AuthServiceImpl;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthDao dao = new AuthDaoImpl();
    private final AuthService service = new AuthServiceImpl(dao);

	

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
			 Map<String, String> result = new HashMap<>();
			 StudentDto student = mapper.readValue(req.getReader(), StudentDto.class);
			// Existing session don't create a new one , destroy it and then create
			    HttpSession oldSession = req.getSession(false);

			    if (oldSession != null) {
			        oldSession.invalidate();  
			        //if he has any old session still wants to login to diffrent account iam clearing olld session
			    }
			    
			 try {
				 StudentDto resStudent=service.login(student);
				 result.put("message", "login sucsess");
				 HttpSession session=req.getSession();
				 session.setAttribute("user", resStudent.getName());
				 session.setAttribute("id", resStudent.getId());
				 
				 
		    	 resp.setStatus(HttpServletResponse.SC_OK);
				 
			 }
			 catch(InvalidCredentialsException e) {
				 result.put("message", "invalid name or pass");
		    	 resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				 
			 }
			 catch(ValidationException e) {
				 result.put("message", "both username and pass required");
		    	 resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 result.put("message", "internal server error");
		    	 resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 }

		        mapper.writeValue(resp.getWriter(), result);
		
	}

}
