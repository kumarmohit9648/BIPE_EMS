package com.mohit.voodoo.bipe_ems.postmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginHelper {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("fire_base_id")
    @Expose
    public String fireBaseId;

    @SerializedName("imei_no")
    @Expose
    public String imeiNo;

    public LoginHelper(String username, String password, String fireBaseId, String imeiNo) {
        this.username = username;
        this.password = password;
        this.fireBaseId = fireBaseId;
        this.imeiNo = imeiNo;
    }
}
