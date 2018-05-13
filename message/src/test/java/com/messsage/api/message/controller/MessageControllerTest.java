package com.messsage.api.message.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messsage.api.message.Repository.MessageRepository;
import com.messsage.api.message.model.Message;
import com.messsage.api.message.service.MessageService;

public class MessageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock //Mockito Mock object
    private MessageService messageService;
	
	@InjectMocks
	private MessageController messageController;
	
	@Autowired
    private WebApplicationContext wac;

	
	@Before
    public void setup(){
        MockitoAnnotations.initMocks(this); //initilizes controller and mocks
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }
	
	
	@Test
	public void testPostMessage() throws Exception {
	     Message message = new Message();
	     message.setTitle("the tile");
	     message.setMsg("the message");
	     when(messageService.addMessage(message)).thenReturn(message);

	     mockMvc.perform(
	             post("/post/message")
	                     .contentType(MediaType.APPLICATION_JSON)
	                     .content(asJsonString(message)))
	                     .andExpect(jsonPath("$.id").doesNotExist())
	                     .andExpect(jsonPath("$.title").doesNotExist());

	}
	 
	
	 
	@Test
	public void testGetById() throws Exception {
	    
	     Message message = new Message();
	     message.setId((long) 1);
	     message.setTitle("title");
	     message.setMsg("The message");
	     List<Message> messages = new ArrayList<>();
	     messages.add(message);
	     when(messageService.getById(1)).thenReturn(new Message());
	     
	     mockMvc.perform(get("/message/1"))
	             .andExpect(status().isOk())
	             .andExpect(jsonPath("$.title", is("title")));
	  
	}
	 
	
	@Test
    public void testList() throws Exception{

        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        messages.add(new Message());

        //specific Mockito interaction, tell stub to return list of messages
        when(messageService.listAll()).thenReturn((List) messages); 

        mockMvc.perform(get("/messages")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", Matchers.hasSize(2)));

    }
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	}


}
