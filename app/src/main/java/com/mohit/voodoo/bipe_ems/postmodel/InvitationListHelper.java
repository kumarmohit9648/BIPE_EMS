package com.mohit.voodoo.bipe_ems.postmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvitationListHelper {

    public InvitationListHelper(String token_id, String event_id, String is_received) {
        this.token_id = token_id;
        this.event_id = event_id;
        this.is_received = is_received;
    }

    @SerializedName("token_id")
    @Expose
    public String token_id;

    @SerializedName("event_id")
    @Expose
    public String event_id;

    @SerializedName("is_received")
    @Expose
    public String is_received;
}
