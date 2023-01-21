package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhotoRepository;
import com.example.model.Photo;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	private PhotoRepository photoRepository;

	@Override
	public Photo onloadPhoto(Photo p) {
		//上傳圖片
		return photoRepository.save(p);
	}

	@Override
	public Photo getPhoto(Integer pid) {
		//取得圖片
		return photoRepository.getById(pid);
	}

}
