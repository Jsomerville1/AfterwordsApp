// src/main/java/com/yourpackage/models/GenericResponse.java

package com.copteam22.afterwordsapp.network;

public class GenericResponse {
    private String message;
    private String error;

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
