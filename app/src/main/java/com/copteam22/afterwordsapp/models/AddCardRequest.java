// app/src/main/java/com/copteam22/afterwordsapp/models/AddCardRequest.java
package com.copteam22.afterwordsapp.models;

public class AddCardRequest {
    private String userId;
    private String card;

    // Constructor
    public AddCardRequest(String userId, String card) {
        this.userId = userId;
        this.card = card;
    }

    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter for card
    public String getCard() {
        return card;
    }

    // Setter for card
    public void setCard(String card) {
        this.card = card;
    }
}
