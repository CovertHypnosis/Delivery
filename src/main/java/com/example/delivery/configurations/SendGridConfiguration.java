package com.example.delivery.configurations;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Without proper key it will fail, commenting for now
//@Configuration
//public class SendGridConfiguration {
//    @Value("${spring.sendgrid.api-key}")
//    private String sendGridAPIKey;
//
//    @Bean
//    public SendGrid getSendGrid() {
//        return new SendGrid(sendGridAPIKey);
//    }
//}