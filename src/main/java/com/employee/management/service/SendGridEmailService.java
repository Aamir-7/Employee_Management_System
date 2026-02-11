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



@Service
public class SendGridEmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    @Value("${SENDGRID_FROM_EMAIL}")
    private String fromEmail;

    private static final Logger log =
            LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String to, String subject, String contentText) {

        log.info("ENTERED sendEmail()");
        log.info("API KEY PRESENT = {}", apiKey != null);
        log.info("FROM EMAIL = [{}]", fromEmail);
        log.info("TO EMAIL = [{}]", to);

        try {
            Email from = new Email(fromEmail);
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", contentText);
            Mail mail = new Mail(from, subject, toEmail, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();


            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("SENDGRID STATUS CODE = {}", response.getStatusCode());
            log.info("SENDGRID RESPONSE BODY = {}", response.getBody());
            log.info("SENDGRID RESPONSE HEADERS = {}", response.getHeaders());
        } catch (Exception e) {
            log.info("Email sending failed but continuing flow");
        }
    }


}


