package com.ecommerce.main.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ecommerce.main.exceptionhandler.EmailSendingException;
import com.ecommerce.main.model.User;
import com.ecommerce.main.servicei.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	 private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	 
	@Override
	public void sendEmail(String to, String subject, String body) {
		try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
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

	public void sendOrderConfirmationEmail(User user,double totalAmount, double deliveryCharge) {
	    String subject = "🛍️ Your Order Confirmation - " + user.getName();
	    
	    String message = "<div style='font-family: Arial, sans-serif; padding: 20px;'>" +
	                     "<h2 style='color: #008CBA;'>Your Order is Confirmed! 🎉</h2>" +
	                     "<p>Dear Customer,</p>" +
	                     "<p>Thank you for shopping with <b>YourStore</b>. Your order <b>" + "</b> has been successfully placed.</p>" +
	                     "<table style='width: 100%; border-collapse: collapse;'>" +
	                     "<tr><td><b>Total Amount:</b></td><td>$" + totalAmount + "</td></tr>" +
	                     "<tr><td><b>Delivery Charges:</b></td><td>$" + deliveryCharge + "</td></tr>" +
	                     "<tr><td><b>Grand Total:</b></td><td><b>$" + (totalAmount + deliveryCharge) + "</b></td></tr>" +
	                     "</table>" +
	                     "<p>We will notify you once your order is shipped.</p>" +
	                     "<hr>" +
	                     "<p>📞 Need help? Contact us at <a href='mailto:support@yourstore.com'>support@yourstore.com</a></p>" +
	                     "</div>";

	    try {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setFrom(mailFrom);
	        helper.setTo(user.getEmail());
	        helper.setSubject(subject);
	        helper.setText(message, true); // ✅ Set `true` to enable HTML content

	        javaMailSender.send(mimeMessage);
	    } catch (Exception e) {
	        LOGGER.error("Failed to send email", e);
	        throw new EmailSendingException("Failed to send email", e);
	    }
	}
}

