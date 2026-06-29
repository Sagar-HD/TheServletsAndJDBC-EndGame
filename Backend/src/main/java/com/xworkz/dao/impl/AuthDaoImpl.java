package com.xworkz.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xworkz.dao.AuthDao;
import com.xworkz.dbutil.DbUtil;
import com.xworkz.dto.StudentDto;

public class AuthDaoImpl implements AuthDao{

	@Override
	public int saveStudent(StudentDto student) throws IOException, SQLException {
		System.out.println("saveing user to db");
		if(student==null) {
			throw new IllegalArgumentException("student cant be null");
		}
		int rows=0;
		String insertQuery="insert into student (name,password,email,phone,department_id) values(?,?,?,?,?)";
		  try (Connection con = DbUtil.getConnection();
			         PreparedStatement ps = con.prepareStatement(insertQuery)) {
			
			ps.setString(1, student.getName());
			ps.setString(2, student.getPassword());
			ps.setString(3, student.getEmail());
			ps.setString(4, student.getPhone());
			ps.setInt(5, student.getDepartment_id());
			
			rows=ps.executeUpdate();
			
			
	
		  }
		
			
			
	
	
		return rows;
		
		
	}

	@Override
	public StudentDto validateStudent(StudentDto student) throws IOException, SQLException {
		System.out.println("validating user creds");
		if(student==null) {
			throw new IllegalArgumentException("student cant be null");
		}
		String selectQuery="select * from student where name = ? and password = ?";
		 try (Connection con = DbUtil.getConnection();
		         PreparedStatement ps = con.prepareStatement(selectQuery)) {
		
		ps.setString(1, student.getName());
		ps.setString(2, student.getPassword());
		
		
		try(ResultSet rs=ps.executeQuery()){
		
		if(rs.next()) {
			 StudentDto dto = new StudentDto();
                
			 
			    dto.setName(rs.getString("name"));
			    dto.setEmail(rs.getString("email"));
			    dto.setId(rs.getInt("id"));

			    return dto;
		}
		}
	
		
		
		

	  }
	
		
		return null;
	}

}
