package com.haohua.erp.serviceImp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class ActiveMQTest {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Test
	public void sendMessage() {

			jmsTemplate.send("point-point", new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage("hello");
				}
			});


	}

}
