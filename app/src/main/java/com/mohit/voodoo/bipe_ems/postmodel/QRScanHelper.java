package com.mohit.voodoo.bipe_ems.postmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QRScanHelper {

    @SerializedName("event_id")
    @Expose
    public String event_id;

    @SerializedName("invitation_id")
    @Expose
    public String invitation_id;

    @SerializedName("receptionist_id")
    @Expose
    public String receptionist_id;

    public QRScanHelper(String event_id, String invitation_id, String receptionist_id) {
        this.event_id = event_id;
        this.invitation_id = invitation_id;
        this.receptionist_id = receptionist_id;
    }
}
