package com.example.notification.dto;

import java.util.Map;

public class NotificationRequest {
    private String recipient;
    private String templateId;
    private Map<String, String> templateData;

    // Getters and Setters
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getTemplateId() { return templateId; }
    public void setTemplateId(String templateId) { this.templateId = templateId; }
    public Map<String, String> getTemplateData() { return templateData; }
    public void setTemplateData(Map<String, String> templateData) { this.templateData = templateData; }
}