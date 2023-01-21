package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.TargetRepository;
import com.example.model.Target;

@Service
public class TargetServiceImpl implements TargetService{
	
	@Autowired
	private TargetRepository targetRepository;

	@Override
	public Target getTarget(Integer tid) {
		//取得
		if(!targetRepository.findById(tid).isEmpty()) {
			return targetRepository.findById(tid).get();
		}
		return null;
	}

	@Override
	public boolean addTarget(Target target) {
		//新增
		if(targetRepository.findByname(target.getTname())==null) {
			targetRepository.save(target);
			return true;
		}
		return false;
	}

	@Override
	public List<Target> getAll() {
		//取得全部
		return targetRepository.findAll();
	}

}
