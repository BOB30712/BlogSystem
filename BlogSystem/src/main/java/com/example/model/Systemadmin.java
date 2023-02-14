package com.example.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "systemadmin")
public class Systemadmin {
	
	@Id
	@Column(name = "aid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer aid;
	
	@Column(name = "adminaccount")
	private String adminaccount;
	
	@Column(name = "adminpassword")
	private String adminpassword;
	
	@Column(name = "adminname")
	private String adminname;
	
	@Column(name = "level")
	private String level;
	
	@Column(name = "regdate")
	private Date regdate;
	
	@Column(name = "lastlogin")
	private Date lastlogin;

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getAdminaccount() {
		return adminaccount;
	}

	public void setAdminaccount(String adminaccoint) {
		this.adminaccount = adminaccoint;
	}

	public String getAdminpassword() {
		return adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	
	

}
