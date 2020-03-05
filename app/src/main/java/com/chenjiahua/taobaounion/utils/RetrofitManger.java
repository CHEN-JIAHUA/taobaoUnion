package com.chenjiahua.taobaounion.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManger {
    private static final RetrofitManger ourInstance = new RetrofitManger();
    private final Retrofit retrofit;

    public static RetrofitManger getInstance() {
        return ourInstance;
    }

    private RetrofitManger() {
        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
