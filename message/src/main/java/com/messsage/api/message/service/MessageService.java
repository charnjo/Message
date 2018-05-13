package com.messsage.api.message.service;

import java.util.List;

import com.messsage.api.message.model.Message;

public interface MessageService {
	
	public Message addMessage(Message message);
	public String updateMessage(Message message);
	public Message getById(long id);
	List<Message> listAll();
	List<Message> listLastN(int n);

}
