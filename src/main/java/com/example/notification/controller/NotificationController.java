package com.example.notification.controller;

import com.example.notification.dto.NotificationRequest;
import com.example.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody NotificationRequest request) {
        String response = notificationService.queueEmail(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendSms(@RequestBody NotificationRequest request) {
        String response = notificationService.queueSms(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}