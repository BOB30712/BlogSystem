package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
