package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer>{

}
