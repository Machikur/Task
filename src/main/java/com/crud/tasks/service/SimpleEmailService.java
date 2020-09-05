package com.crud.tasks.service;

import com.crud.tasks.domain.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SimpleEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    private final JavaMailSender javaMailSender;

    @Autowired
    public SimpleEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(Mail mail) {
        try {
            SimpleMailMessage emailMessage = createMessage(mail);
            javaMailSender.send(emailMessage);
            LOGGER.info("Email has been sent");
        } catch (MailException s) {
            LOGGER.error("Failed to process email sending", s.getMessage(), s);
        }
    }

    private SimpleMailMessage createMessage(Mail mail) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(mail.getMailTo());
        emailMessage.setSubject(mail.getSubject());
        emailMessage.setText(mail.getMessage());

        String cc = mail.getCc();
        if (!StringUtils.isEmpty(cc)) {
            emailMessage.setCc(cc);
        }
        return emailMessage;
    }
}
