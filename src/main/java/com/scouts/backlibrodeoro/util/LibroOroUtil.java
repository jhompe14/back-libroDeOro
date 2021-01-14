package com.scouts.backlibrodeoro.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class LibroOroUtil {

    private static final String FORMAT_DATE = "dd/MM/yyyy";
    private static final String USER_EMAIL = "champascouts@gmail.com";
    private static final String PASSWORD_EMAIL = "champascouts123";

    public static String setFormatDate(Date date){
        return Optional.ofNullable(date).map(d -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
            return simpleDateFormat.format(d);
        }).orElse("");
    }

    public static String randomCode(final int length){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static void sendEmail(String recipient, String subject, String content) throws MessagingException {
        Properties props = getPropertiesEmail();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_EMAIL, PASSWORD_EMAIL);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(USER_EMAIL, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        msg.setSubject(subject);
        msg.setContent(content, "text/html");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }

    private static Properties getPropertiesEmail(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

}
