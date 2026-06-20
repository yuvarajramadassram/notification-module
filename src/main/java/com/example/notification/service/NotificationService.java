package com.example.notification.service;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.entity.NotificationLog;
import com.example.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationProcessor processor;

    public NotificationService(NotificationRepository repository, NotificationProcessor processor) {
        this.repository = repository;
        this.processor = processor;
    }

    public String queueEmail(NotificationRequest request) {
        NotificationLog log = createLog(request, "EMAIL");
        processor.processEmail(log, request.getTemplateData());
        return "Email request accepted with processing ID: " + log.getId();
    }

    public String queueSms(NotificationRequest request) {
        NotificationLog log = createLog(request, "SMS");
        processor.processSms(log, request.getTemplateData());
        return "SMS request accepted with processing ID: " + log.getId();
    }

    private NotificationLog createLog(NotificationRequest request, String type) {
        NotificationLog log = new NotificationLog();
        log.setRecipient(request.getRecipient());
        log.setTemplateId(request.getTemplateId());
        log.setType(type);
        log.setStatus("PENDING");
        return repository.save(log);
    }
}
