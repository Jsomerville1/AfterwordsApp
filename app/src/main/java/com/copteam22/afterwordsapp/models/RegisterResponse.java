// app/src/main/java/com/copteam22/afterwordsapp/models/RegisterResponse.java
package com.copteam22.afterwordsapp.models;

public class RegisterResponse {
    private String message;
    private int userId;
    private String error;

    // Constructor
    public RegisterResponse(String message, int userId, String error) {
        this.message = message;
        this.userId = userId;
        this.error = error;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
