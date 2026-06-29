package com.xworkz.dao;

import com.xworkz.dto.StudentDto;

public interface ProfileDao {
	StudentDto getProfile(String name) throws Exception;

}
