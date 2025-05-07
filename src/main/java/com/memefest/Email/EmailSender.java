package com.memefest.Email;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class EmailSender {
  private static final Properties PROPERTIES = new Properties();
  
  private static final String USERNAME = "hyperforbics@gmail.com";
  
  private static final String PASSWORD = "pzgb lfmr awax mkcn";
  
  private static final String HOST = "smtp.gmail.com";
  
  static {
    PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
    PROPERTIES.put("mail.smtp.auth", "true");
    PROPERTIES.put("mail.smtp.starttls.enable", "true");
    PROPERTIES.put("mail.smtp.ssl.trust", "*");
    PROPERTIES.put("mail.smtp.ssl.protocols", "TLSv1.3");
    PROPERTIES.put("mail.smtp.port", "465");
    PROPERTIES.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
  }
  
  public static void sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {
    Authenticator authenticator = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(USERNAME, PASSWORD);
        }
      };
    Session session = Session.getInstance(PROPERTIES, (Authenticator)authenticator);
    session.setDebug(debug);
    try {
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom((Address)new InternetAddress(from));
      InternetAddress[] address = { new InternetAddress(to) };
      msg.setRecipients(Message.RecipientType.TO, (Address[])address);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      MimeBodyPart htmlPart = new MimeBodyPart();
      htmlPart.setContent(message, "text/html");
      MimeMultipart mimeMultipart = new MimeMultipart();
      mimeMultipart.addBodyPart((BodyPart)htmlPart);
      msg.setContent(mimeMultipart, "text/html");
      Transport.send((Message)msg);
    } catch (MessagingException mex) {
      mex.printStackTrace();
      Exception ex = null;
      if ((ex = mex.getNextException()) != null)
        ex.printStackTrace(); 
    } 
  }
}