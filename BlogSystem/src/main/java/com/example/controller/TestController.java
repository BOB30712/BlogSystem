package com.example.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dao.ArticleRepository;
import com.example.dao.TargetRepository;
import com.example.model.Article;
import com.example.model.Target;


@Controller
public class TestController {
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private TargetRepository targetRepository;

	@GetMapping("/test")
	@ResponseBody
	public String Hello() {
		return "Hello World";
	}
	
	@GetMapping("/getArticle/{id}")
	@ResponseBody
	public ResponseEntity getArticle(@PathVariable Integer id) {
		System.out.println("getArticle");
		
		return ResponseEntity.status(HttpStatus.OK).body(articleRepository.findById(id));
	}
	
	@GetMapping("/getTarget/{id}")
	@ResponseBody
	public ResponseEntity getTarget(@PathVariable Integer id) {
		System.out.println("getTarget");
		
		return ResponseEntity.status(HttpStatus.OK).body(targetRepository.findById(id));
	}
	
	@GetMapping("/addArticle")
	@ResponseBody
	public ResponseEntity addArticle() {
		System.out.println("addArticle");
		
		Set<Target> tags=new LinkedHashSet<Target>();
		/*
		tags.add(targetRepository.findById(1).get());
		tags.add(targetRepository.findById(2).get());
		*/
		
		Target tag=new Target();
		tag.setTname("怎麼會這樣");
		targetRepository.save(tag);
		tags.add(tag);
		
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());
		Article article=new Article();
		article.setTitle("測試2");
		article.setCreatetime(ts);
		article.setContent("uhbijnokm");
		article.setTargets(tags);
		articleRepository.save(article);
		
		return ResponseEntity.status(HttpStatus.OK).body(articleRepository.findById(4));
	}
}
