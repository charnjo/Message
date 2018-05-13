package com.messsage.api.message.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityJpaService {
	
	protected EntityManagerFactory emf;
	
	@PersistenceUnit
	public void setEMF(EntityManagerFactory emf) {
		this.emf = emf;
		
	}

}
