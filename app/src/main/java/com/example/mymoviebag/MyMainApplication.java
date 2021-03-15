package com.example.mymoviebag;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyMainApplication extends Application {
    Context context;
    public MyMainApplication() {
        //empty constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
