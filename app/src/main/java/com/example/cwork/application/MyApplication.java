package com.example.cwork.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class MyApplication extends Application {
    private static MyApplication instance;
    private static Context appContext;

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){ return mCurrentActivity; }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    public static MyApplication getInstance() { return instance; }

    public static Context getAppContext() { return appContext; }

    public void setAppContext(Context mAppContext) { this.appContext = mAppContext; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
