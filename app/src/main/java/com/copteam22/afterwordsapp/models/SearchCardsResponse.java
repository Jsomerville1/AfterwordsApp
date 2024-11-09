// app/src/main/java/com/copteam22/afterwordsapp/models/SearchCardsResponse.java
package com.copteam22.afterwordsapp.models;

import java.util.List;

public class SearchCardsResponse {
    private List<String> results;
    private String error;

    // Constructor
    public SearchCardsResponse(List<String> results, String error) {
        this.results = results;
        this.error = error;
    }

    // Getter and Setter for results
    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    // Getter and Setter for error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
