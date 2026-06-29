package com.xworkz.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.xworkz.dao.CourseDao;
import com.xworkz.dbutil.DbUtil;
import com.xworkz.dto.CourseDto;

public class CourseDaoImpl implements CourseDao{

	@Override
	public int addCourses(int student_id, CourseDto[] courses)throws Exception {
		//we making transaction and having save point after each update
		//even if i keep auto commit false it behaves same just demonstrated transaction and save point
		String query="insert into course_enrollment (student_id,course_id) values(?,?)";
		Savepoint savepoint = null;
		int courses_addded=0;
		try(Connection con=DbUtil.getConnection();PreparedStatement ps=con.prepareStatement(query)){
			//transaction start
			con.setAutoCommit(false);
		  ps.setInt(1, student_id);
		  for(CourseDto course:courses) {
			  if(course!=null) {
			  ps.setInt(2,course.getId());
			  try {
		       ps.executeUpdate();
		 	  //creating save point after each updates
			  savepoint=con.setSavepoint();
		       courses_addded++;
			  }catch (SQLException e) {
				  if (savepoint != null) {
				        con.rollback(savepoint);
				    } else {
				        con.rollback();   // rollback entire transaction
				    }
				}
		       
		
			  
			  }
		  }
		  con.commit();
			
		} 
		
		return courses_addded;
	}

	@Override
	public List<CourseDto> getCourses(int student_id) throws Exception{
		String query="SELECT c.course_id, c.course_name FROM course c INNER JOIN course_enrollment ce  ON c.course_id = ce.course_id WHERE ce.student_id = ?";
		 List<CourseDto> list = new ArrayList<>();
		try(Connection con=DbUtil.getConnection();PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1, student_id);
			try(ResultSet rs=ps.executeQuery()){
				while(rs.next()) {
					 CourseDto dto = new CourseDto();
		               dto.setId(rs.getInt("course_id"));
		               dto.setCourse_name(rs.getString("course_name"));

		                list.add(dto);
					
				}
				
			}
		}
		
		return list;
	}

}
