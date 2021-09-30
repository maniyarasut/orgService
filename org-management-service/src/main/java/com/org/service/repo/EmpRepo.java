package com.org.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.service.model.Emp;
@Repository
public interface EmpRepo extends JpaRepository<Emp, Long> {
	
	@Query("select emp from Emp emp left outer join fetch emp.org org")
	List<Emp> findAllEmp();

	@Modifying
	@Query("delete Emp emp where emp.org.id=:id")
	void deleteByOrgId(@Param("id")Long id);
	

}
