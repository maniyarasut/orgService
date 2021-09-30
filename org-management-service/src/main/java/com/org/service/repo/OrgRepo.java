package com.org.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.service.model.Org;
@Repository
public interface OrgRepo extends JpaRepository<Org, Long> {
	
	

}
