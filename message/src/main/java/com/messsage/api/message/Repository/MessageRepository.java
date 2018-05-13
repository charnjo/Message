package com.messsage.api.message.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messsage.api.message.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{


}
