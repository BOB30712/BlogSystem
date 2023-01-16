package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Target;

public interface TargetRepository extends JpaRepository<Target, Integer>{

}
