package com.email.bootEmailService.service;

import com.email.bootEmailService.bo.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.id}")
    private String emailId;
    public boolean sendEmail(EmailDetails emailDetails) throws Exception {
        emailDetails.setFrom("abhishek.study1218@gmail.com");
        try{
            emailConfig(emailDetails);
            return true;
        }catch (Exception e ) {
            throw new Exception("Exception Occured in email service, Error: "+ e.getMessage());
        }
    }
    private void emailConfig(EmailDetails emailDetails) throws MessagingException {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();


        //setting properties
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Properties: " +properties);

        //1 to get a session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailId, emailPassword);
                //password generated by gmail twoFactor app password
                //link - https://myaccount.google.com/apppasswords?pli=1&
                // rapt=AEjHL4P9wTWY5XUKV2DoGQBz6EesWzuIPZZKKnR5-EbvAse4tp6kAwWd-VLG_ghL2OwRi4hZfynIje0GVmNDf8SCzmld0bZfdMiM0wfQ-i0IeJxyLqNNewI
            }
        });
        session.setDebug(true);

        //compose the message
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom("abhishek.study1218@gmail.com");
        mimeMessage.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailDetails.getTo()));
        if(emailDetails.getCc()!=null)
            mimeMessage.setRecipients(Message.RecipientType.CC,
                InternetAddress.parse(emailDetails.getCc()));
        if(emailDetails.getBcc()!=null)
            mimeMessage.setRecipients(Message.RecipientType.BCC,
                InternetAddress.parse(emailDetails.getBcc()));
        if(emailDetails.getMultipart()!=null)
            mimeMessage.setContent(emailDetails.getMultipart());
        mimeMessage.setSubject(emailDetails.getSubject());
        mimeMessage.setText(emailDetails.getBody());

        //send the message using Transport class
        Transport.send(mimeMessage);
        System.out.println("Mail.. Send");

    }
}