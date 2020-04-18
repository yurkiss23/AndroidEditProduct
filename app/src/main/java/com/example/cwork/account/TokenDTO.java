package com.example.cwork.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenDTO {
    @SerializedName("token")
    @Expose
    private String token;
    public TokenDTO(String token, String invalid) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    @Override
    public String toString() {
        return "TokenDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}
