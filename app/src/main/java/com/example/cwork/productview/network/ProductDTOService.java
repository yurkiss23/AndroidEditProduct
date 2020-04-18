package com.example.cwork.productview.network;

import com.example.cwork.network.interceptors.AuthorizationInterceptor;
import com.example.cwork.network.interceptors.JWTInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDTOService {
    private static ProductDTOService mInstance;
    private static final String BASE_URL = "https://androidapi.azurewebsites.net/api/";
    private Retrofit mRetrofit;

    private ProductDTOService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new JWTInterceptor())
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ProductDTOService getInstance() {
        if (mInstance == null) {
            mInstance = new ProductDTOService();
        }
        return mInstance;
    }

    public ProductDTOHolderApi getJSONApi() {
        return mRetrofit.create(ProductDTOHolderApi.class);
    }
}
