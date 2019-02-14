package com.mohit.voodoo.bipe_ems.constant;

import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;

public class URLConstant {

    private static String domain = "http://visitor.cropnet.co.in/";

    public static String api = "new-api/";
    public static String url = domain + api;



    public static ApiInterface getSOService() {
        return RetrofitClient.getClient(url).create(ApiInterface.class);
    }

}
