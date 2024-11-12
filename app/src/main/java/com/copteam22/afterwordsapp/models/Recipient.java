// src/main/java/com/copteam22/afterwordsapp/models/Recipient.java

package com.copteam22.afterwordsapp.models;

public class Recipient {
    private int recipientId;
    private int userId;
    private String recipientName;
    private String recipientEmail;
    private int messageId; // Changed from List<Integer> messageIds
    private String createdAt;

    // Default Constructor
    public Recipient() {}

    // Parameterized Constructor
    public Recipient(int recipientId, int userId, String recipientName, String recipientEmail, int messageId, String createdAt) {
        this.recipientId = recipientId;
        this.userId = userId;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.messageId = messageId;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
