// src/main/java/com/yourpackage/models/User.java

package com.copteam22.afterwordsapp;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private int checkInFreq;
    private boolean verified;
    private boolean deceased;
    private String createdAt;
    private String lastLogin;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(int id, String firstName, String lastName, String username, String email,
                int checkInFreq, boolean verified, boolean deceased,
                String createdAt, String lastLogin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.checkInFreq = checkInFreq;
        this.verified = verified;
        this.deceased = deceased;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }


    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCheckInFreq() {
        return checkInFreq;
    }

    public void setCheckInFreq(int checkInFreq) {
        this.checkInFreq = checkInFreq;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}