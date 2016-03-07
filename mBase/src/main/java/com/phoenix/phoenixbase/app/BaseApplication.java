package com.phoenix.phoenixbase.app;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        //TODO:检查为什赋值两次
        instance = this;
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
