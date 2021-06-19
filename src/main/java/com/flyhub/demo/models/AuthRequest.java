package com.flyhub.demo.models;

/**
 * this holds the authentication object submitted from the user form
 *
 * @author Benjammin E Ndugga
 */
public class AuthRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthRequest{" + "username=" + username + ", password=" + password + '}';
    }
}
