// app/src/main/java/com/copteam22/afterwordsapp/models/AddCardResponse.java
package com.copteam22.afterwordsapp.models;

public class AddCardResponse {
    private String error;

    // Constructor
    public AddCardResponse(String error) {
        this.error = error;
    }

    // Getter for error
    public String getError() {
        return error;
    }

    // Setter for error
    public void setError(String error) {
        this.error = error;
    }
}
