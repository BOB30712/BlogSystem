package com.example.service;

import com.example.model.Photo;

public interface PhotoService {

	//上傳圖片
	public Photo onloadPhoto(Photo p);
	
	//取得圖片
	public Photo getPhoto(Integer pid);
	
}
