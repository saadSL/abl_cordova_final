package com.ofss.digx.mobile.android.allied;

import android.app.Application;

import com.unikrew.faceoff.ABLPlugin.utils.RetrofitSingleton;
import com.unikrew.faceoff.ABLPlugin.utils.RetrofitApi;

public class AblApplication extends Application {
    static public RetrofitApi apiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        apiInterface = RetrofitSingleton.getInstance().create(RetrofitApi.class);
    }
}
