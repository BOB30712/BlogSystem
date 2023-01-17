package com.example.service;

import java.util.List;

import com.example.model.Target;

public interface TargetService {
	
	//取得
	public Target getTarget(Integer tid);
	//新增
	public boolean addTarget(Target target);
	//取得全部
	public List<Target> getAll();

}
