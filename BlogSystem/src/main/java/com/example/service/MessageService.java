package com.example.service;

import java.util.List;

import com.example.model.Message;

public interface MessageService {
	
	//新增
	public boolean addmessage(Message msg);
	
	//刪除
	public boolean deletemessage(Integer mid);
	
	//取得相同id的留言
	public List<Message> getmessagebyaid(Integer aid);
}
