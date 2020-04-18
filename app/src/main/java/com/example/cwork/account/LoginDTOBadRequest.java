package com.example.cwork.account;

public class LoginDTOBadRequest {
    private String invalid;
    private String email;
    private String password;
    public String getInvalid() {
        return invalid;
    }
    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
