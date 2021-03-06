package com.malevtool.Application.MailService;

import Utils.Logging.Logger;
import com.malevtool.Application.Configurations.MailSenderConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailSender {
    private Session session;
    private InternetAddress addressFrom;
    private final List<InternetAddress> recipients = new ArrayList<>();
    private static AnnotationConfigApplicationContext mailServiceContext;

    @Autowired
    public MailSender(Properties properties) {
        try {
            session = Session.getInstance(properties, new Authenticator(properties));
            addressFrom = new InternetAddress(properties.getProperty("mail.smtp.from"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MailSender getInstance() {
        if(mailServiceContext == null)
            mailServiceContext = new AnnotationConfigApplicationContext(MailSenderConfiguration.class);
        return mailServiceContext.getBean(MailSender.class);
    }

    public void update(Properties properties) {
        try {
            session = Session.getInstance(properties, new Authenticator(properties));
            addressFrom = new InternetAddress(properties.getProperty("mail.smtp.from"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String content, String subject, String... recipients) throws MessagingException, NullPointerException {
        addRecipients(recipients);
        MimeMessage message = prepareMessage(content, subject);
        sendMessage(message);
    }

    public void sendMessage(com.malevtool.Entities.Message message) throws MessagingException {
        sendMessage(message.getContent(), message.getTitle(), message.getRecipients());
    }

    public void sendMessage(MimeMessage message) throws MessagingException {
        Logger.log(MailSender.class, "Sending message...", 4);
        send(message);

        StringBuilder recipientsString = new StringBuilder();
        for(InternetAddress recipient : recipients)
            recipientsString.append(recipient.getAddress());
        Logger.log(MailSender.class, "Message sent to: " + recipientsString.toString(), 1);
    }

    public void addRecipients(String[] recipients) throws NullPointerException {
        try {
            for (String recipient : recipients)
                addRecipient(recipient);
        } catch (NullPointerException e) {
            throw new NullPointerException("No recipients provided");
        }
    }

    public void addRecipient(String address) {
        try {
            recipients.add(new InternetAddress(address));
        } catch (AddressException e) {
            Logger.log(MailSender.class, "Cannot add recipient " + address + ": " + e.getMessage(), 2);
        }
    }

    public MimeMessage prepareMessage(String content, String subject) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(addressFrom);
            message.addRecipients(Message.RecipientType.TO, recipients.toArray(new InternetAddress[0]));
            message.setSubject(subject);
            message.setText(content);
            message.setSender(addressFrom);
            return message;
        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public List<InternetAddress> getRecipients() {
        return recipients;
    }

    private void send(MimeMessage message) throws MessagingException {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            Logger.log(MailSender.class, "Cannot send message: " + e.getMessage(), 2);
            throw e;
        }
    }
}

