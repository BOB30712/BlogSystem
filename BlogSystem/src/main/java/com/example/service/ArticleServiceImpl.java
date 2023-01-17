package com.example.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ArticleRepository;
import com.example.model.Article;
import com.example.model.Target;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public Article getArticle(Integer aid) {
		//取得(id)
		if(!articleRepository.findById(aid).isEmpty()) {
			return articleRepository.findById(aid).get();
		}
		return null;
	}

	@Override
	public boolean addArticle(Article article) {
		//新增
		if(articleRepository.findById(article.getAid()).isEmpty()) {
			articleRepository.save(article);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateArticle(Article article) {
		//修改
		if(!articleRepository.findById(article.getAid()).isEmpty()) {
			articleRepository.save(article);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteArticle(Integer aid) {
		//刪除
		if(!articleRepository.findById(aid).isEmpty()) {
			articleRepository.deleteById(aid);
			return true;
		}
		return false;
	}

	@Override
	public List<Article> getALL() {
		//取得全部
		return articleRepository.findAll();
	}

	@Override
	public List<Article> getALLbyTarget(Target target) {
		//取得相同的標籤(單一)
		List<Article> Articles=articleRepository.findAll();
		
		return Articles.stream().filter(t->t.getTargets().contains(target)).collect(Collectors.toList());
	}

}
