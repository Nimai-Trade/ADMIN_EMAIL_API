package com.nimai.email.utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class emailtest {

 

       public static void main(String[] args) {

             // TODO Auto-generated method stub

             Properties props = new Properties();

           props.put("mail.smtp.auth", "true");

           props.put("mail.smtp.starttls.enable", "true");

           props.put("mail.smtp.host", "smtp.office365.com");

           props.put("mail.smtp.port", "587");

           Session session = Session.getDefaultInstance(props,
                   new javax.mail.Authenticator() {
                       //Authenticating the password

                       protected PasswordAuthentication getPasswordAuthentication() {

                           return new PasswordAuthentication("uat_notification@360tf.trade", "uat_360tf");

                       }

                   });

           try {

               //Creating MimeMessage object

               MimeMessage mm = new MimeMessage(session);
               //Setting sender addres
               mm.setFrom(new InternetAddress("uat_notification@360tf.trade"));
               //Adding receiver
               mm.addRecipient(Message.RecipientType.TO, new InternetAddress("dhiraj.jagtap@yopmail.com"));
               //Adding subject
               mm.setSubject("Test Java prg");
               //Adding message
               mm.setText("Test Java prg");          
               //Sending email

               Transport.send(mm);

 

           } catch (MessagingException e) {

               e.printStackTrace();

           }

 

       }

 

}