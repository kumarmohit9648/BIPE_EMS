package com.mohit.voodoo.bipe_ems.postmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteeHelper {

    public InviteeHelper(String token_id, String invitation_id) {
        this.token_id = token_id;
        this.invitation_id = invitation_id;
    }

    @SerializedName("token_id")
    @Expose
    public String token_id;

    @SerializedName("invitation_id")
    @Expose
    public String invitation_id;
}
