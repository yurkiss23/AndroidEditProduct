package com.example.cwork.network.interceptors;

import com.example.cwork.LoginFragment;
import com.example.cwork.NavigationHost;
import com.example.cwork.application.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .build();
        Response response = chain.proceed(newRequest);
        if (response.code() == 401) {
            MyApplication context = (MyApplication) MyApplication.getAppContext();
            NavigationHost navigationHost = (NavigationHost) context.getCurrentActivity();
            navigationHost.navigateTo(new LoginFragment(), false);
            //  return response;
        }
        return response;
    }
}
