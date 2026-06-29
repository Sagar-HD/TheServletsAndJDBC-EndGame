package com.xworkz.service;


import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;

import com.xworkz.dto.StudentDto;
import com.xworkz.exceptions.ValidationException;

public interface AuthService {
	
	
	void register(StudentDto student) throws Exception;
	StudentDto login(StudentDto student)throws Exception;
	
	
	

	
}
