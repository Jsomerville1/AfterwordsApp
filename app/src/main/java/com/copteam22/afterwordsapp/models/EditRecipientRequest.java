// src/main/java/com/yourpackage/models/EditRecipientRequest.java

package com.copteam22.afterwordsapp.models;

import com.google.gson.annotations.SerializedName;

public class EditRecipientRequest {

    @SerializedName("recipientId")
    private int recipientId;

    @SerializedName("messageId")
    private int messageId;

    @SerializedName("recipientName")
    private String recipientName;

    @SerializedName("recipientEmail")
    private String recipientEmail;

    // Default Constructor
    public EditRecipientRequest() {}

    // Parameterized Constructor
    public EditRecipientRequest(int recipientId, int messageId, String recipientName, String recipientEmail) {
        this.recipientId = recipientId;
        this.messageId = messageId;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
    }

    // Getters and Setters

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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
}
