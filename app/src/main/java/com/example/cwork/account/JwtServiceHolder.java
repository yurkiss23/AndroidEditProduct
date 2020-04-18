package com.example.cwork.account;

public interface JwtServiceHolder {
    void saveJWTToken(String token);
    String getToken();
    void removeToken();
}
