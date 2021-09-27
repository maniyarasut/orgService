package com.org.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.service.model.Org;

public interface OrgRepo extends JpaRepository<Org, Long> {
	
	

}
