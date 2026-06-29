package com.xworkz.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UserCount
 */
@WebServlet("/usercount")
public class UserCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final ObjectMapper mapper = new ObjectMapper();
  
    public UserCount() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		AtomicInteger counter = (AtomicInteger) req.getServletContext().getAttribute("onlineUsers");

		int onlineUsers = counter.get();

		 Map<String, Object> result = new HashMap<>();
		 res.setStatus(200);
		 result.put("count", onlineUsers);
		 mapper.writeValue(res.getWriter(), result);
	
	}



}
