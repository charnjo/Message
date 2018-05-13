package com.messsage.api.message.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.messsage.api.message.Repository.MessageRepository;
import com.messsage.api.message.model.Message;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


@Service
public class MessageServiceImpl extends EntityJpaService implements MessageService{
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	MessageRepository messageRepository;


	@Override
	public String updateMessage(Message message) {
							
		 EntityManager em = emf.createEntityManager();
	     em.getTransaction().begin();
	     Message savedProduct = em.merge(message);	     
	     em.getTransaction().commit();
		 return savedProduct.getTitle() + " Modified";
	
	}

	@Override
	public Message getById(long id) {
		EntityManager em = emf.createEntityManager();
        return em.find(Message.class, id);
	
	}

	@Override
	public List<Message> listAll() {
		 EntityManager em = emf.createEntityManager();
	     return em.createQuery("from Message", Message.class).getResultList();
	}

	@Override
	public Message addMessage(Message message) {
		 EntityManager em = emf.createEntityManager();
	     em.getTransaction().begin();
	     Message savedMessage = em.merge(message);
	     em.getTransaction().commit();

	     return savedMessage;

	}
	
	public List<Message> listLastN(int n){
		
		EntityManager em = emf.createEntityManager();
	    
		return em.createQuery("from Message Order by localDateTime desc", Message.class)
				.setMaxResults(n)
				.getResultList();
		
	}

}
