package com.ecommerce.main.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecommerce.main.exceptionhandler.EmailSendingException;
import com.ecommerce.main.servicei.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	 private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	 
	@Override
	public void sendEmail(String to, String subject, String body) {
		try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
        } catch (MailException e) {
        	 // here Logging the error
            LOGGER.error("Failed to send email", e);
            // here Throwing a custom exception
            throw new EmailSendingException("Failed to send email", e);
        }
    
	}

	
}
