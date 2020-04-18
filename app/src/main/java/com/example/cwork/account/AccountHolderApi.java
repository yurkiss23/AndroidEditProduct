package com.example.cwork.account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountHolderApi {
    @POST("login")
    public Call<TokenDTO> loginRequest(@Body LoginDTO login);
}
