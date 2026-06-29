package com.xworkz.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
@WebFilter(urlPatterns = {"/course", "/profile","/usercount"})
public class AuthFilter implements Filter{
	 private final ObjectMapper mapper = new ObjectMapper();
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("request intercepted to check auth");
		//casting to http request to session session if only exists
		
		HttpServletRequest httpReq=(HttpServletRequest)req;
		//getting res to set content type to json
		HttpServletResponse httpRes=(HttpServletResponse ) res;
//allowing cors to my frontend
httpRes.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
httpRes.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
httpRes.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
httpRes.setHeader("Access-Control-Allow-Credentials", "true");
if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
	httpRes.setStatus(HttpServletResponse.SC_OK);
    return;
}
		
		
		
		httpRes.setContentType("application/json");
        httpRes.setCharacterEncoding("UTF-8");
        
		 Map<String, Object> result = new HashMap<>();
		HttpSession session=httpReq.getSession(false);
		if(session==null) {
			httpRes.setStatus(401);
			result.put("message", "unauthorised please login");
			 mapper.writeValue(httpRes.getWriter(), result);
			 return;
			
		}
		
		chain.doFilter(httpReq, httpRes);
		
		
	}

}
