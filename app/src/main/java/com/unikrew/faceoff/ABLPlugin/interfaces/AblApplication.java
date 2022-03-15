package com.unikrew.faceoff.ABLPlugin.interfaces;

import android.app.Application;

public class AblApplication extends Application {

    static public RetrofitApi apiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        apiInterface = RetrofitSingleton.getInstance().create(RetrofitApi.class);
//        apiInterface =
//                RetrofitObject.INSTANCE.getInstence().create(RetrofitApi.class);
    }
}
