package com.mohit.voodoo.bipe_ems.network;

import android.util.Log;

import com.google.gson.Gson;
import com.mohit.voodoo.bipe_ems.response.APIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APITransaction {

    private static final String TAG = "APITransaction";

    public static void startNetworkService(Call request, final APIResponse apiResponse) {
        request.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                apiResponse.OnResponseAPI(response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                apiResponse.OnErrorAPI(t.getMessage());
                Log.d("onResponse", "error :" + t.getMessage());
            }
        });
    }
}
