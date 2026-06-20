package com.example.notification.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class TemplateService {

    // Mock Database Templates
    public String getTemplateContent(String templateId) {
        if ("welcome_email".equals(templateId)) {
            return "Hello {{name}}, welcome to our platform!";
        } else if ("otp_sms".equals(templateId)) {
            return "Your security code is {{code}}. Do not share it.";
        }
        return "Notification update: {{message}}";
    }

    public String compileTemplate(String templateId, Map<String, String> data) {
        String content = getTemplateContent(templateId);
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
        }
        return content;
    }
}