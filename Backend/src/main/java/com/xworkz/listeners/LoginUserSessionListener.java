package com.xworkz.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
@WebListener()
public class LoginUserSessionListener implements HttpSessionListener {
	  @Override
	    public void sessionCreated(HttpSessionEvent se) {
            //we can get context by session event also , req.getContext also , and by servletconfig also
	        ServletContext context = se.getSession().getServletContext();

	        AtomicInteger counter =
	                (AtomicInteger) context.getAttribute("onlineUsers");

	        int count = counter.incrementAndGet();

	        System.out.println("Online Users : " + count);
	    }

	    @Override
	    public void sessionDestroyed(HttpSessionEvent se) {

	    	ServletContext context = se.getSession().getServletContext();

	        AtomicInteger counter =
	                (AtomicInteger) context.getAttribute("onlineUsers");

	        if (counter != null) {
	            int count = counter.updateAndGet(value -> Math.max(0, value - 1));
	            
	        }
	        //clean up of jdbc connection if not donr
	        try {
	            AbandonedConnectionCleanupThread.checkedShutdown();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
