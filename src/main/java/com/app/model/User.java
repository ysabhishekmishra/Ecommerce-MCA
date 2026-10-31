package com.app.model;

public class User {
    private String username;
    private String email;
    private String password; // learning project: plain text. Real app me hash karo (BCrypt).

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
