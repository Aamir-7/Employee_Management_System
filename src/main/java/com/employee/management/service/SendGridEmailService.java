package com.employee.management.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SendGridEmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    @Value("${SENDGRID_FROM_EMAIL}")
    private String fromEmail;

    private static final Logger log =
            LoggerFactory.getLogger(SendGridEmailService.class);

    public void sendTemplateEmail(
            String to,
            String templateId,
            Map<String, Object> dynamicData
    ) {

        try {
            Email from = new Email(fromEmail);
            Email toEmail = new Email(to);

            Mail mail = new Mail();
            mail.setFrom(from);

            Personalization personalization = new Personalization();
            personalization.addTo(toEmail);

            dynamicData.forEach(personalization::addDynamicTemplateData);

            mail.addPersonalization(personalization);
            mail.setTemplateId(templateId);

            SendGrid sg = new SendGrid(apiKey);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("SendGrid Template Status: {}", response.getStatusCode());

        } catch (Exception e) {
            log.error("Template email failed", e);
        }
    }

    public void sendEmail(
            String to,
            String subject,
            String contentText
    ) {

        try {
            Email from = new Email(fromEmail);
            Email toEmail = new Email(to);

            Mail mail = new Mail(from, subject, toEmail,
                    new com.sendgrid.helpers.mail.objects.Content("text/plain", contentText));

            SendGrid sg = new SendGrid(apiKey);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            log.info("SendGrid Plain Email Status: {}", response.getStatusCode());

        } catch (Exception e) {
            log.error("Plain email failed", e);
        }
    }
}


