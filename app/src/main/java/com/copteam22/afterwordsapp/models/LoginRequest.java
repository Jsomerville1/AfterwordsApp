// app/src/main/java/com/copteam22/afterwordsapp/models/LoginRequest.java
package com.copteam22.afterwordsapp.models;

public class LoginRequest {
    private String Username;
    private String Password;

    // Constructor
    public LoginRequest(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    // Getter and Setter for Username
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    // Getter and Setter for Password
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
