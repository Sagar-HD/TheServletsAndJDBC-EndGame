package com.xworkz.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener()
public class MyContextListener implements ServletContextListener{
	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
            //atomic integer vcs its used all over app so to avoid concurrency issues
	        AtomicInteger onlineUsers = new AtomicInteger(0);
            //setting attribute on app startup while context initilisation
	        sce.getServletContext().setAttribute("onlineUsers", onlineUsers);

	        System.out.println("Application Started");
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        System.out.println("Application Stopped");
	    }
	

}
