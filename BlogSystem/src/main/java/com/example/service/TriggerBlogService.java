package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.TriggerBlogRepository;
import com.example.model.TriggerBlog;

@Service
public class TriggerBlogService {
	
	@Autowired
	private TriggerBlogRepository triggerBlogRepository;
	
	public List<TriggerBlog> getall(){
		return triggerBlogRepository.findAll();
	}
}
