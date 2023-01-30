package com.example.service;

import com.example.model.Message;

public interface MessageService {
	
	//新增
	public boolean addmessage(Message msg);
	
	//刪除
	public boolean deletemessage(Integer mid);
}
