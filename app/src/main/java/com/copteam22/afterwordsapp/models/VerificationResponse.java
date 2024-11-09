// app/src/main/java/com/copteam22/afterwordsapp/models/VerificationResponse.java
package com.copteam22.afterwordsapp.models;

public class VerificationResponse {
    private String message;
    private String error;

    // Constructor
    public VerificationResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
