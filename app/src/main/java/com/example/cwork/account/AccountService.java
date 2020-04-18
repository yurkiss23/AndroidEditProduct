package com.example.cwork.account;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountService {
    private static AccountService mInstance;
    private static final String BASE_URL = "https://androidapi.azurewebsites.net/api/account/";//"https://masterlock20200324083512.azurewebsites.net/api/account/";
    private Retrofit mRetrofit;
    private AccountService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient
                .Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }
    public static AccountService getInstance() {
        if (mInstance == null) {
            mInstance = new AccountService();
        }
        return mInstance;
    }
    public AccountHolderApi getJSONApi() {
        return mRetrofit.create(AccountHolderApi.class);
    }
}
