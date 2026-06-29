package com.xworkz.service;

import com.xworkz.dto.StudentDto;

public interface ProfileService {
	
	StudentDto getProfile(String name) throws Exception;

}
