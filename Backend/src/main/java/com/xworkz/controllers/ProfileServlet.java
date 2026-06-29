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

import com.xworkz.dao.ProfileDao;

import com.xworkz.dao.impl.ProfileDaoImpl;
import com.xworkz.dto.StudentDto;

import com.xworkz.service.ProfileService;
import com.xworkz.service.impl.AuthServiceImpl;
import com.xworkz.service.impl.ProfileServiceImpl;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final ObjectMapper mapper = new ObjectMapper();
	    private final ProfileDao dao = new ProfileDaoImpl();
	    private final ProfileService service = new ProfileServiceImpl(dao);
  
    public ProfileServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			Map<String, Object> result = new HashMap<>();
			HttpSession session=req.getSession(false);
			if(session!=null) {
				String name=(String) session.getAttribute("user");
				try {
					StudentDto dto=service.getProfile(name);
					resp.setStatus(HttpServletResponse.SC_ACCEPTED);
					result.put("message", "success");
					result.put("data",  dto);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					result.put("message", "something went wrong");
				}
				
			}
			else {
				 result.put("message", "unauthorized");
		    	 resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			   
			mapper.writeValue(resp.getWriter(), result);

			 
	}

	

}
