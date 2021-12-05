package com.mapsa.company_management_system.service;

import com.mapsa.company_management_system.config.EmailConfig;
import com.mapsa.company_management_system.model.Email;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class EmailService {

    EmailConfig emailConfig;
    JavaMailSenderImpl mailSender;
    Email email;

    // A common path in windows
    private final String UPLOADED_FILE_DIRECTORY = "C:" + File.pathSeparator + "Program Files";

    public EmailService(EmailConfig emailConfig, JavaMailSenderImpl mailSender, Email email){
        this.emailConfig = emailConfig;
        this.mailSender = mailSender;
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());
        this.email = email;
    }


    public void sendEmail(Email email, MultipartFile attachedFile) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setCc(email.getCc().toArray(new String[0]));
            helper.setBcc(email.getBcc().toArray(new String[0]));
            helper.setSentDate(new Date(System.currentTimeMillis()));
            helper.setText(email.getBody());
            Path path = Paths.get(UPLOADED_FILE_DIRECTORY);
            attachedFile.transferTo(path);
            helper.addAttachment(attachedFile.getOriginalFilename(), path.toFile());
            helper.setSubject(email.getSubject());
            mailSender.send(helper.getMimeMessage());
    }
}
