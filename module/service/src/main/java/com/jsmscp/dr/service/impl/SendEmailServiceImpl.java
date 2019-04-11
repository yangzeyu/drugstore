package com.jsmscp.dr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.service.SendEmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    private JavaMailSender javaMailSender;


    @Autowired
    public SendEmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送邮件
     * @param contractEmail
     * @param content
     */
    @Override
    public void sendEmail(String contractEmail, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(Constant.FROM_USER);
            message.setTo(contractEmail);
            message.setText(content);
            message.setSubject(Constant.DRUGSTORE_EMAIL_SUBJECT);
            javaMailSender.send(message);
            logger.info("文本邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送文本邮件时发生异常！", e);
        }
    }


    /**
     * 发送html格式的邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String subject, String content, String userName) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(userName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
            logger.info("html邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }
}
