package com.xworkz.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","sagarhd");
		return con;
	}
	

}
