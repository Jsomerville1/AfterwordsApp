// src/main/java/com/yourpackage/models/AddRecipientRequest.java

package com.copteam22.afterwordsapp.models;

import com.google.gson.annotations.SerializedName;

public class AddRecipientRequest {

    @SerializedName("username")
    private String username;

    @SerializedName("recipientName")
    private String recipientName;

    @SerializedName("recipientEmail")
    private String recipientEmail;

    @SerializedName("messageId")
    private int messageId;

    // Default Constructor
    public AddRecipientRequest() {}

    // Parameterized Constructor
    public AddRecipientRequest(String username, String recipientName, String recipientEmail, int messageId) {
        this.username = username;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.messageId = messageId;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
