package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("from Message where aid=:id")
	List<Message> findbyaid(@Param("id") Integer id);
}
