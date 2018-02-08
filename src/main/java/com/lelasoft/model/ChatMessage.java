package com.lelasoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name= "chat_messages")
public class ChatMessage {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long autor;
	private Long opponent;
	private String text;
	private String createdDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAutor() {
		return autor;
	}
	public void setAutor(Long autor) {
		this.autor = autor;
	}

	public Long getOpponent() {
		return opponent;
	}
	public void setOpponent(Long opponent) {
		this.opponent = opponent;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
