package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Target;


public interface TargetRepository extends JpaRepository<Target, Integer>{

	@Query("from Target where tname=:tname")
	Target findByname(@Param("tname") String name);
}
