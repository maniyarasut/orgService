package com.org.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.service.model.LoginModel;

@Repository
public interface LoginRepo extends JpaRepository<LoginModel, String> {

}
