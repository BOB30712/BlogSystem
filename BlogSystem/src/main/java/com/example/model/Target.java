package com.example.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="target")
public class Target {

	@Id
	@Column(name = "tid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tid;
	
	@Column(name = "tname")
	private String tname;
	
	/*
	@JsonIgnoreProperties("targets")
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="targets")
	private Set<Article> articles;
	*/

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	/*
	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	*/
	
}
