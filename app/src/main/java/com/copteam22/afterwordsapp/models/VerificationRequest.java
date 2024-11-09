// app/src/main/java/com/copteam22/afterwordsapp/models/VerificationRequest.java
package com.copteam22.afterwordsapp.models;

public class VerificationRequest {
    private String Username;
    private String code;

    // Constructor
    public VerificationRequest(String username, String code) {
        this.Username = username;
        this.code = code;
    }

    // Getter and Setter for Username
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    // Getter and Setter for code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
