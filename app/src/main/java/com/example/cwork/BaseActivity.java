package com.example.cwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cwork.account.JwtServiceHolder;
import com.example.cwork.application.MyApplication;

public abstract class BaseActivity extends AppCompatActivity implements NavigationHost, JwtServiceHolder {

    public BaseActivity() {
        MyApplication myApp=(MyApplication)MyApplication.getAppContext();
        myApp.setCurrentActivity(this);
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void saveJWTToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.putString("token",token);
            Log.i("Login",token);
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public String getToken() {
        SharedPreferences prefs=this.getSharedPreferences("jwtStore",Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
    }
    @Override
    public void removeToken() {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.remove("token");
            edit.apply();
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
