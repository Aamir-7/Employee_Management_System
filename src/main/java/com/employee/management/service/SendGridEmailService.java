package com.employee.management.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SendGridEmailService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    @Value("${SENDGRID_FROM_EMAIL")
    private String fromEmail;

    public void sendEmail(String to, String subject, String contentText){

        Email from=new Email(fromEmail);
        Email toEmail =new Email(to);
        Content content=new Content("text/plain",contentText);
        Mail mail=new Mail(from,subject,toEmail,content);

        SendGrid sg=new SendGrid(apiKey);
        Request request=new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        }catch (IOException e){
            throw new RuntimeException("SendGrid mail failed to send ");
        }

    }

}


