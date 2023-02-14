package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Systemadmin;

public interface SystemadminRepository extends JpaRepository<Systemadmin, Integer>{
	
	@Query("SELECT t FROM Systemadmin t WHERE t.adminaccount = ?1")
	Systemadmin findBySystemadmin(String adminaccount);
}
