package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.ChatMessage;

public interface ChatDao {

	public void save(ChatMessage chat);
	public List<ChatMessage> findAllChats();
	public List<ChatMessage> findChats(Long autor,Long opponent, Long msgid,boolean top);
	public List<ChatMessage> findChats(Long autor);
	public List<ChatMessage> findChats(Long autor,Long opponent);

}
