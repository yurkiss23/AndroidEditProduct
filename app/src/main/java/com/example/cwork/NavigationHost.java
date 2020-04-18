package com.example.cwork;

import androidx.fragment.app.Fragment;

import retrofit2.Response;

//import com.android.volley.Response;

public interface NavigationHost {
    void navigateTo(Fragment fragment, boolean addToBackstack);
    boolean isConnect(Response response);
}
