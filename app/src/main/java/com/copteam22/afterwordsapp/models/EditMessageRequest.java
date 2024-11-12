// src/main/java/com/yourpackage/models/EditMessageRequest.java

package com.copteam22.afterwordsapp.models;

import com.google.gson.annotations.SerializedName;

public class EditMessageRequest {

    @SerializedName("messageId")
    private int messageId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("content")
    private String content;

    // Default Constructor
    public EditMessageRequest() {}

    // Parameterized Constructor
    public EditMessageRequest(int messageId, int userId, String content) {
        this.messageId = messageId;
        this.userId = userId;
        this.content = content;
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
}
