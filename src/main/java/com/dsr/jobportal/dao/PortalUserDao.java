package com.dsr.jobportal.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dsr.jobportal.dto.PortalUser;
import com.dsr.jobportal.repository.PortalUserRepository;

import jakarta.validation.Valid;

@Component
public class PortalUserDao 
{

	
	@Autowired
	PortalUserRepository userRepository;
	
	public boolean existsByEmail(String email) 
	{
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}
	
	public void saveUser( PortalUser portalUser)
	{
		userRepository.save(portalUser);
	}

	public PortalUser findUserById(int id) 
	{
		return userRepository.findById(id).orElse(null);
		
	}

	public boolean existsByMobile(Long mobile) 
	{
		
		return userRepository.existsByMobile(mobile);
	}
}
