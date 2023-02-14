package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SystemadminRepository;
import com.example.model.Systemadmin;

@Service
public class SystemadminServiceImpl implements SystemadminService{

	@Autowired
	private SystemadminRepository systemadminRepository;

	@Override
	public Systemadmin getSystemadmin(String adminaccount) {
		//取得(透過adminaccount)
		return systemadminRepository.findBySystemadmin(adminaccount);
	}

	@Override
	public boolean addSystemadmin(Systemadmin systemadmin) {
		//新增
		if(systemadminRepository.save(systemadmin)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateSystemadmin(Systemadmin systemadmin) {
		//修改
		if(systemadminRepository.save(systemadmin)!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSystemadmin(Integer aid) {
		//刪除
		if(!systemadminRepository.findById(aid).isEmpty()) {
			systemadminRepository.deleteById(aid);
		}
		return false;
	}

	@Override
	public List<Systemadmin> getALL() {
		//取得全部
		return systemadminRepository.findAll();
	}

	@Override
	public boolean checkadmin(String adminaccount, String adminpassword) {
		Systemadmin admin = this.getSystemadmin(adminaccount);
		if(admin!=null) {
			if(admin.getAdminpassword().equals(adminpassword)) {
				return true;
			}
		}
		return false;
	}
	
	
}
