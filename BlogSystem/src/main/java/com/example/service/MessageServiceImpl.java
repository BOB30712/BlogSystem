package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MessageRepository;
import com.example.model.Message;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;

	@Override
	public boolean addmessage(Message msg) {
		// TODO Auto-generated method stub
		messageRepository.save(msg);
		return true;
	}

	@Override
	public boolean deletemessage(Integer mid) {
		// TODO Auto-generated method stub
		messageRepository.deleteById(mid);
		return true;
	}

	@Override
	public List<Message> getmessagebyaid(Integer aid) {
		// TODO Auto-generated method stub
		return messageRepository.findbyaid(aid);
	}

}
