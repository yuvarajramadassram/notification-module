package com.example.notification.service;

import com.example.notification.entity.NotificationLog;
import com.example.notification.repository.NotificationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationProcessor {

    private final JavaMailSender mailSender;
    private final NotificationRepository repository;
    private final TemplateService templateService;

    @Value("${twilio.account.sid}") private String twilioSid;
    @Value("${twilio.auth.token}") private String twilioToken;
    @Value("${twilio.phone.number}") private String twilioPhone;

    public NotificationProcessor(org.springframework.mail.javamail.JavaMailSender mailSender, NotificationRepository repository, TemplateService templateService) {
        this.mailSender = mailSender;
        this.repository = repository;
        this.templateService = templateService;
    }

    @Async("notificationExecutor")
    public void processEmail(NotificationLog log, Map<String, String> templateData) {
        String body = templateService.compileTemplate(log.getTemplateId(), templateData);
        executeWithRetry(log, () -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(log.getRecipient());
            message.setSubject("Notification Alert");
            message.setText(body);
            mailSender.send(message);
        });
    }

    @Async("notificationExecutor")
    public void processSms(NotificationLog log, Map<String, String> templateData) {
        String body = templateService.compileTemplate(log.getTemplateId(), templateData);
        executeWithRetry(log, () -> {
            Twilio.init(twilioSid, twilioToken);
            Message.creator(
                new PhoneNumber(log.getRecipient()),
                new PhoneNumber(twilioPhone),
                body
            ).create();
        });
    }

    // Common Retry Loop Mechanism
    private void executeWithRetry(NotificationLog log, Runnable sendAction) {
        int maxRetries = 3;
        while (log.getRetryCount() < maxRetries) {
            try {
                sendAction.run();
                log.setStatus("SENT");
                repository.save(log);
                return; // Success, break out
            } catch (Exception e) {
                log.setRetryCount(log.getRetryCount() + 1);
                log.setErrorMessage(e.getMessage());
                if (log.getRetryCount() >= maxRetries) {
                    log.setStatus("FAILED");
                } else {
                    log.setStatus("RETRYING");
                    try { Thread.sleep(2000 * log.getRetryCount()); } catch (InterruptedException ignored) {} // Linear backoff
                }
                repository.save(log);
            }
        }
    }
}