package com.xworkz.service.impl;

import com.xworkz.dao.ProfileDao;
import com.xworkz.dto.StudentDto;
import com.xworkz.exceptions.ValidationException;
import com.xworkz.service.ProfileService;

public class ProfileServiceImpl implements ProfileService{
	ProfileDao dao;
	

	public ProfileServiceImpl(ProfileDao dao) {
		super();
		this.dao = dao;
	}


	@Override
	public StudentDto getProfile(String name) throws Exception {
		if(name!=null) {
			StudentDto dto=dao.getProfile(name);
			return dto;
			
		}
		else {
			throw new ValidationException("name is null");
		}
		
	}

}
