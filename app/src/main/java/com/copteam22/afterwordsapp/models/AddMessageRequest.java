// src/main/java/com/copteam22/afterwordsapp/models/AddMessageRequest.java

package com.copteam22.afterwordsapp.models;

import com.google.gson.annotations.SerializedName;

public class AddMessageRequest {

    @SerializedName("userId")
    private int userId;

    @SerializedName("content")
    private String content;

    // Default Constructor
    public AddMessageRequest() {}

    // Parameterized Constructor
    public AddMessageRequest(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    // Getters and Setters

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
