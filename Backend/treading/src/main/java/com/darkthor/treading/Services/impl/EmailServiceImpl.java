package com.darkthor.treading.Services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    private JavaMailSender javaMailSender;
    public void setVerificationOtpEmail(String email,String otp) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"utf-8");
        String subject= "verify Otp";
        String text ="Your OTP is :"+otp;
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);

        try {
            javaMailSender.send(mimeMessage);
        }catch (MailException me){
            throw new MailSendException(me.getMessage());
        }



    }
}
