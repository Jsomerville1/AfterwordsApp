// app/src/main/java/com/copteam22/afterwordsapp/models/LoginResponse.java
package com.copteam22.afterwordsapp.models;

public class LoginResponse {
    private int id;
    private String firstName;
    private String lastName;
    private boolean Verified;
    private String error;

    // Constructor
    public LoginResponse(int id, String firstName, String lastName, boolean verified, String error) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Verified = verified;
        this.error = error;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for Verified
    public boolean isVerified() {
        return Verified;
    }

    public void setVerified(boolean verified) {
        this.Verified = verified;
    }

    // Getter and Setter for error
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
