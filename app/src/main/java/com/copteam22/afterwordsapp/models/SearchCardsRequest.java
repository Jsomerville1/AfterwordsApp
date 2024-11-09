// app/src/main/java/com/copteam22/afterwordsapp/models/SearchCardsRequest.java
package com.copteam22.afterwordsapp.models;

public class SearchCardsRequest {
    private String userId;
    private String search;

    // Constructor
    public SearchCardsRequest(String userId, String search) {
        this.userId = userId;
        this.search = search;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and Setter for search
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
