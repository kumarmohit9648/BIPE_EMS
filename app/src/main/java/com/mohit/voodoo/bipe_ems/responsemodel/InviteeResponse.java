package com.mohit.voodoo.bipe_ems.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteeResponse {

    public InviteeResponse(String flag, InvitationListResponse.Invitation list) {
        this.flag = flag;
        this.list = list;
    }

    @SerializedName("flag")
    @Expose
    public String flag;

    @SerializedName("invitation")
    @Expose
    InvitationListResponse.Invitation list;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public InvitationListResponse.Invitation getList() {
        return list;
    }

    public void setList(InvitationListResponse.Invitation list) {
        this.list = list;
    }
}
