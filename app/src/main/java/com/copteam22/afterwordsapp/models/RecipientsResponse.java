// app/src/main/java/com/copteam22/afterwordsapp/models/RecipientsResponse.java
package com.copteam22.afterwordsapp.models;

import java.util.List;

public class RecipientsResponse {
    private List<String> recipients;
    private String error;

    // Constructor
    public RecipientsResponse(List<String> recipients, String error) {
        this.recipients = recipients;
        this.error = error;
    }

    // Getter and Setter for recipients
    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    // Getter and Setter for error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
