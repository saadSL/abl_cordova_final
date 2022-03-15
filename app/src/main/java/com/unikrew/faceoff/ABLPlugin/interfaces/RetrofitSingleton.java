package com.unikrew.faceoff.ABLPlugin.interfaces;

import com.unikrew.faceoff.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private RetrofitSingleton(){

    }
    private static Retrofit retrofitSingleton = null;

    public static Retrofit getInstance()
    {
        if (retrofitSingleton == null)
            retrofitSingleton = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofitSingleton;
    }
}
