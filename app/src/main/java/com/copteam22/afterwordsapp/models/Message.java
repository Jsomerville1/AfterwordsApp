// src/main/java/com/yourpackage/models/Message.java

package com.copteam22.afterwordsapp.models;

import java.util.List;

public class Message {
    private int messageId;
    private int userId;
    private String content;
    private boolean isSent;
    private String createdAt;
    private String sendAt;
    private List<Recipient> recipients;

    // Default Constructor
    public Message() {}

    // Parameterized Constructor
    public Message(int messageId, int userId, String content, boolean isSent, String createdAt, String sendAt, List<Recipient> recipients) {
        this.messageId = messageId;
        this.userId = userId;
        this.content = content;
        this.isSent = isSent;
        this.createdAt = createdAt;
        this.sendAt = sendAt;
        this.recipients = recipients;
    }

    // Getters and Setters

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSendAt() {
        return sendAt;
    }

    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}
