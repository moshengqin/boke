package com.example.demo.service.impl;



import com.example.demo.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailServiceImpl implements IMailService {
    @Value("${spring.mail.username}")
    private String mailUsername;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Override
    public void sendMailForActivationAccount(String Url, String email) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMailMessage,true);

            message.setSubject("账号激活");
            message.setFrom(mailUsername);
            message.setTo(email);
            message.setSentDate(new Date());

            Context context = new Context();
            context.setVariable("Url",Url);
            String text = templateEngine.process("activation-account.html",context);
            message.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMailMessage);
    }

    @Override
    public void sendMailForUpdatePassword(String Url, String email) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMailMessage,true);

            message.setSubject("密码修改");
            message.setFrom(mailUsername);
            message.setTo(email);
            message.setSentDate(new Date());

            Context context = new Context();
            context.setVariable("Url",Url);
            String text = templateEngine.process("update-password.html",context);
            message.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMailMessage);
    }
}
