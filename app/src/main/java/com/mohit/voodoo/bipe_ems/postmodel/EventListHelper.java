package com.mohit.voodoo.bipe_ems.postmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventListHelper {

    public EventListHelper(String token_id) {
        this.token_id = token_id;
    }

    @SerializedName("token_id")
    @Expose
    public String token_id;
}
