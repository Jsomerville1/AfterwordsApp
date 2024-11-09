// app/src/main/java/com/copteam22/afterwordsapp/models/RegisterRequest.java
package com.copteam22.afterwordsapp.models;

public class RegisterRequest {
    private String FirstName;
    private String LastName;
    private String Username;
    private String Email;
    private String Password;
    private int CheckInFreq;

    // Constructor
    public RegisterRequest(String firstName, String lastName, String username, String email, String password, int checkInFreq) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Username = username;
        this.Email = email;
        this.Password = password;
        this.CheckInFreq = checkInFreq;
    }

    // Getter and Setter for FirstName
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    // Getter and Setter for LastName
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    // Getter and Setter for Username
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    // Getter and Setter for Email
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    // Getter and Setter for Password
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    // Getter and Setter for CheckInFreq
    public int getCheckInFreq() {
        return CheckInFreq;
    }

    public void setCheckInFreq(int checkInFreq) {
        this.CheckInFreq = checkInFreq;
    }
}
