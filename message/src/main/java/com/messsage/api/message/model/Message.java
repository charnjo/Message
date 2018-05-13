package com.messsage.api.message.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Message {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String title;
    private String msg;
    
    @Column(updatable=false, insertable=true)
    private LocalDateTime localDateTime ;
    
    private LocalDateTime updatedAt;
    
    
    public Message() {
        super(); 	
    }
    
    public Message(Long id) {
    	this.id = id;	
    }
    
    public Message(String title, String msg) {
    
    	this.title = title;
    	this.msg = msg;
    }
    
    public Message(Long id, String title, String msg) {
    	this.id = id;	
    	this.title = title;
    	this.msg = msg;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	@PrePersist
	public void setLocalDateTime() {
		this.localDateTime = this.updatedAt = LocalDateTime.now();
	}

	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@PreUpdate
	public void setUpdatedAt() {
		this.updatedAt = LocalDateTime.now();
	}	

}
