package com.example.delivery.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class MailSendService {
    public void sendSMS(String text) {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Message.creator(new PhoneNumber("1111"),
                new PhoneNumber("1111"), text).create();
    }
    public void sendMail(String text) {
        SendGrid sendGrid = new SendGrid("sendGridAPIKey");
        Email from = new Email("sample@gmail.com");
        String subject = "Order";
        Email to = new Email("client@gmail.com");
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);
        mail.setReplyTo(new Email("sample@gmail.com"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response api = sendGrid.api(request);
            log.info(api.getStatusCode());
        } catch (IOException ex) {
            throw new EntityNotFoundException("cant get response please check mail or endpoint");
        }
    }
}
