package com.dealalert.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendMail {
    private static String mailPassword;
    private static String mailEmail;

    // setter to access value from app properties in static context
    @Value("${dealalert.app.mailPassword}")
    public void setMailPassword(String password) {
        mailPassword = password;
    }

    @Value("${dealalert.app.mailEmail}")
    public void setMailEmail(String email) { mailEmail = email;}

    public static void sendMail(String username, String email, String name, double price) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(mailEmail, mailPassword);
                    }
                });
        session.setDebug(true);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailEmail));

            Address[] toUser = InternetAddress
                    .parse(email);
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("New price of tracked item!");
            message.setText("Hello " + username + "!\nNew price of " + name + " is: $"+price);

            Transport.send(message);
            System.out.println("Sending mail completed!!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


