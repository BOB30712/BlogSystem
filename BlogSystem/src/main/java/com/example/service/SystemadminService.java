package com.example.service;

import java.util.List;

import com.example.model.Systemadmin;

public interface SystemadminService {

	//取得(透過adminaccount)
	public Systemadmin getSystemadmin(String adminaccount);
	//登入
	public boolean checkadmin(String adminaccount,String adminpassword);
	//新增
	public boolean addSystemadmin(Systemadmin systemadmin);
	//修改
	public boolean updateSystemadmin(Systemadmin systemadmin);
	//刪除
	public boolean deleteSystemadmin(Integer aid);
	//取得全部
	public List<Systemadmin> getALL();
}
