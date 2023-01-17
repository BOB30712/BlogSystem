package com.example.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dao.TargetRepository;
import com.example.model.Article;
import com.example.model.Target;
import com.example.service.ArticleService;
import com.example.service.TargetService;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private TargetService targetService;
	
	@GetMapping("/Article/get/{id}")
	@ResponseBody
	public Article getArticle(@PathVariable Integer id) {
		System.out.println("搜尋Article: "+id);
		return articleService.getArticle(id);
	}
	
	@GetMapping("/Article/getlist/{id}")
	@ResponseBody
	public List<Article> getArticlebyTarget(@PathVariable Integer id) {
		System.out.println("搜尋ArticlebyTarget: "+id);
		return articleService.getALLbyTarget(targetService.getTarget(id));
	}
	
	//新增標籤
	@GetMapping("/Target/add/{name}")
	@ResponseBody
	public String addTarget(@PathVariable String name) {
		Target t=new Target();
		t.setTname(name);
		if(targetService.addTarget(t)) {
			return "新增標籤--->成功";
		}else {
			return "新增標籤--->失敗";
		}
	}
	
	//新增標籤到文章
	@GetMapping("/Article/addTaeget/{aid}/{tid}")
	@ResponseBody
	public String addTargetToArticle(@PathVariable Integer aid,@PathVariable Integer tid) {

		Article a= articleService.getArticle(aid);
		Set<Target> tags=a.getTargets();
		if(!tags.contains(targetService.getTarget(tid))&&targetService.getTarget(tid)!=null) {
			tags.add(targetService.getTarget(tid));
			//tags.remove(targetService.getTarget(tid));
		}
		a.setTargets(tags);
		articleService.updateArticle(a);
		
		
		return "addTargetToArticle";
	}
	
	//刪除文章裡面的標籤
	@GetMapping("/Article/removeTaeget/{aid}/{tid}")
	@ResponseBody
	public String removeTarget(@PathVariable Integer aid,@PathVariable Integer tid) {
		Article a= articleService.getArticle(aid);
		Set<Target> tags=a.getTargets();
		System.out.println("before "+tags.size());
		//
		if(targetService.getTarget(tid)!=null) {
			tags.remove(targetService.getTarget(tid));
		}
		System.out.println("after "+tags.size());
		a.setTargets(tags);
		articleService.updateArticle(a);
		return "removeTarget";
	}
	
}
