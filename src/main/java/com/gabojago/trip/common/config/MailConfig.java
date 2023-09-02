package com.gabojago.trip.common.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Value("${YNWOO_EMAIL_HOST}")
    private String EmailHost;

    @Value("${YNWOO_EMAIL_ADDRESS}")
    private String EmailAddress;
    @Value("${YNWOO_EMAIL_PASSWORD}")
    private String EmailPassword;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(EmailHost);
        javaMailSender.setUsername(EmailAddress);
        javaMailSender.setPassword(EmailPassword);

        javaMailSender.setPort(465);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", EmailHost);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.debug", "true");
        return properties;
    }
}
