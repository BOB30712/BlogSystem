package com.example.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	
	//原先作法:mappedBy依據Article.java的targets屬性，無法設定CascadeType.PERSIST單獨刪除單一target資料
	//只能設定CascadeType.ALL同時刪除對應的article資料
	//@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy="targets")
	//後續作法:比照Article.java的連接方式就可以設定CascadeType.PERSIST單一刪除target資料
	//@ManyToMany(cascade = CascadeType.PERSIST)
	//@JoinTable(name="arttar", joinColumns = {@JoinColumn(name="tid")}, inverseJoinColumns = {@JoinColumn(name="aid")})
	/*
	@JsonIgnoreProperties("targets")
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="arttar", joinColumns = {@JoinColumn(name="tid")}, inverseJoinColumns = {@JoinColumn(name="aid")})
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
