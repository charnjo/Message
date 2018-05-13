package com.messsage.api.message.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.messsage.api.message.Repository.MessageRepository;
import com.messsage.api.message.model.Message;
import com.messsage.api.message.service.MessageService;

@RestController
public class MessageController {
	
  @Autowired	
  private MessageService messageService;
  
  @Autowired
  private MessageRepository messageRepository;

  
  @PostMapping("/post/message")
  public ResponseEntity<Message> postMessage(@RequestBody Message addedMessage) {

	  Message message = messageService.addMessage(addedMessage);

		if (message == null)
			return ResponseEntity.noContent().build();

		Message jsonMSG = new Message(message.getId());

		return new ResponseEntity<Message>(jsonMSG, HttpStatus.OK);
	}
  
  
  @PutMapping("put/{msgId}")
  public ResponseEntity<String> modifyMessage(@PathVariable Long msgId, @RequestBody Message modMessage){
    	
    	Optional<Message> msgOptional = messageRepository.findById(msgId);


		if (!msgOptional.isPresent()) {
			return new ResponseEntity<String>("Message not found", HttpStatus.OK);
		}
    	
		
		modMessage.setId(msgId);
		LocalDateTime createdDT = msgOptional.get().getLocalDateTime();	
		Long secondsDiff = LocalDateTime.from(createdDT).until(LocalDateTime.now(), ChronoUnit.SECONDS);
		
		System.out.println("Seconds Elapsed " + secondsDiff);
		
		if (secondsDiff > 10) {			
			return new ResponseEntity<String>("Time Exceeded, this record can no longer be changed", HttpStatus.OK);			
		}
	     
    	String message = messageService.updateMessage(modMessage);

		return new ResponseEntity<String>(message, HttpStatus.OK);  	
  }
  
    
  @GetMapping("/message/{msgId}")
  public ResponseEntity<Message> retrieveMessageById(@PathVariable Long msgId) {
    	Optional<Message> msgOptional = messageRepository.findById(msgId);


		if (!msgOptional.isPresent()) {
			return new ResponseEntity<Message>(HttpStatus.OK);
		}
		
		Message jsonMSG = new Message(msgOptional.get().getId(), 
				                      msgOptional.get().getTitle(), 
				                      msgOptional.get().getMsg());
		
		return new ResponseEntity<Message>(jsonMSG, HttpStatus.OK);  	
  }
    
  @GetMapping("/lastmessages/{msgNo}")
  public List<Message> retrieveNMessages(@PathVariable int msgNo){
	     return messageService.listLastN(msgNo); 
	     
  }

  @GetMapping("/messages")
  public List<Message> retrieveMessages() {
		return messageService.listAll();
  }
  


}
