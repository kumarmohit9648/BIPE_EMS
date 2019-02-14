package com.mohit.voodoo.bipe_ems.singleton;

import android.content.Context;

import com.mohit.voodoo.bipe_ems.constant.URLConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getInstance(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLConstant.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
