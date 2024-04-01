package com.dsr.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsr.jobportal.dto.PortalUser;

public interface PortalUserRepository extends JpaRepository<PortalUser,Integer>
{
	boolean existsByEmail(String email);

	boolean existsByMobile(Long mobile);

	
}
