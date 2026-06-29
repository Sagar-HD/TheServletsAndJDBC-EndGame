package com.xworkz.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebFilter("/*")
public class CorsFilter implements Filter {

   

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletResponse res = (HttpServletResponse) response;
	        HttpServletRequest req = (HttpServletRequest) request;

	        res.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
	        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	        res.setHeader("Access-Control-Allow-Credentials", "true");

	        // Handle preflight request
	        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
	            res.setStatus(HttpServletResponse.SC_OK);
	            return;
	        }

	        chain.doFilter(request, response);
		
	}
}