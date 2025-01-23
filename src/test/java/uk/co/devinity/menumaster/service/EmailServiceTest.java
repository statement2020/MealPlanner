package uk.co.devinity.menumaster.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private SesClient sesClient;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "test@example.com";
        String subject = "Test Invitation";
        String body = "You are invited to join a meal plan.";

        emailService.sendEmail(to, subject, body);

        verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }
}

