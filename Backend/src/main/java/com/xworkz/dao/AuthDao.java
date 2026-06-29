package com.xworkz.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.xworkz.dto.StudentDto;

public interface AuthDao {
	int saveStudent(StudentDto student) throws IOException,SQLException;
	StudentDto validateStudent(StudentDto student) throws IOException,SQLException;
	

}
