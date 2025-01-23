package uk.co.devinity.menumaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailService {

    private final SesClient sesClient;

    private final String senderEmail;

    public EmailService(SesClient sesClient, @Value("${aws.ses.sender.email}") String senderEmail) {
        this.sesClient = sesClient;
        this.senderEmail = senderEmail;
    }

    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(to).build())
                .message(
                        Message.builder()
                                .subject(Content.builder().data(subject).build())
                                .body(Body.builder().text(Content.builder().data(body).build())
                                        .build()).build()
                ).source(senderEmail).build();

        sesClient.sendEmail(request);
    }
}