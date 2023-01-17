package com.example.service;

import java.util.List;

import com.example.model.Article;
import com.example.model.Target;

public interface ArticleService {
	
	//取得(id)
	public Article getArticle(Integer aid);
	//新增
	public boolean addArticle(Article article);
	//修改
	public boolean updateArticle(Article article);
	//刪除
	public boolean deleteArticle(Integer aid);
	//取得全部
	public List<Article> getALL();
	//取得相同的標籤(單一)
	public List<Article> getALLbyTarget(Target target);
	
}
