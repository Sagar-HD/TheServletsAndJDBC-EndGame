package com.xworkz.service.impl;

import java.io.IOException;
import java.sql.SQLException;

import com.xworkz.dao.AuthDao;

import com.xworkz.dto.StudentDto;
import com.xworkz.exceptions.InvalidCredentialsException;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.AuthService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	private AuthDao dao;
	@Override
	public void register(StudentDto student) throws IOException,SQLException,ValidationException {
		System.out.println("user save service called");
		if(dao!=null) {
			System.out.println("data :"+student.getName()+student.getPassword());
			if(student!=null && student.getName()!=null && student.getDepartment_id()>0 && student.getPassword()!=null) {
				
			try {
				int rows=dao.saveStudent(student);
				if(rows == 0){
				    throw new SQLException("Student not saved");
				}
				
				
			} catch (IOException | SQLException e) {
				throw e;
				
			}
		
			
		
		}
		else {
			throw new ValidationException("name ,password or dept id missing");
		}
	}
	
	}
	public AuthServiceImpl(AuthDao dao) {
		super();
		this.dao = dao;
	}
	@Override
	public StudentDto login(StudentDto student) throws Exception {
		if(student!=null && student.getName()!=null && student.getPassword()!=null) {
			if(dao!=null) {
				try {
				StudentDto resStudent=dao.validateStudent(student);
				if(resStudent!=null) {
					return  resStudent;
					
				}
				else {
					throw new InvalidCredentialsException();
				}
			
			}
		  catch(IOException | SQLException e) {
			  throw e;
		  }
			  
		  }
		}
		else {
			throw new ValidationException("invalid input");
		}
		
		return null;
	}

}
