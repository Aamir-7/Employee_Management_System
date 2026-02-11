package com.employee.management.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SendGridEmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    @Value("${SENDGRID_FROM_EMAIL}")
    private String fromEmail;

    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String to, String subject, String contentText) {

        log.error("ENTERED sendEmail()");
        log.error("API KEY PRESENT = {}", apiKey != null);
        log.error("FROM EMAIL = [{}]", fromEmail);
        log.error("TO EMAIL = [{}]", to);

        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", contentText);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.error("SENDGRID STATUS CODE = {}", response.getStatusCode());
            log.error("SENDGRID RESPONSE BODY = {}", response.getBody());
            log.error("SENDGRID RESPONSE HEADERS = {}", response.getHeaders());

            if (response.getStatusCode() != 202) {
                // DO NOT throw yet, just log
                return;
            }

        } catch (Exception e) {
            log.error("SendGrid call failed", e);
        }
    }


}


