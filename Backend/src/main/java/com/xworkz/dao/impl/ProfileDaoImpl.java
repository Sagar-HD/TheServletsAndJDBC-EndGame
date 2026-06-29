package com.xworkz.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.xworkz.dao.ProfileDao;
import com.xworkz.dbutil.DbUtil;
import com.xworkz.dto.StudentDto;

public class ProfileDaoImpl implements ProfileDao{

	@Override
	public StudentDto getProfile(String name) throws Exception {
		System.out.println("getting user profile");
		if(name==null) {
			throw new IllegalArgumentException("name is null");
		}
		String query="select * from student where name = ?";
		try(Connection con=DbUtil.getConnection();PreparedStatement ps=con.prepareStatement(query)){
			ps.setString(1, name);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					StudentDto dto=new StudentDto();
					dto.setName(rs.getString("name"));
					dto.setEmail(rs.getString("email"));
					dto.setPhone(rs.getString("password"));
					dto.setDepartment_id(rs.getInt("department_id"));
					return dto;
				}
				
				
				
			}
			
		}
		return null;
	}

}
