package com.danglich.jobxinseeker.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
	
	private final JavaMailSender mailSender;
	
	private final static Logger LOGGER = LoggerFactory
            .getLogger(MailServiceImpl.class);

	@Override
	@Async
	public void send(String to, String subject, String email){
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true , "UTF-8");
			helper.setTo(to);
			helper.setFrom("nguyendanglich30@gmail.com");
			helper.setSubject(subject);
			helper.setText(email, true);
			
			mailSender.send(message);
		} catch (MessagingException e) {
			LOGGER.error("failed to send email");
			e.printStackTrace();
		}
		
		
	}

}
