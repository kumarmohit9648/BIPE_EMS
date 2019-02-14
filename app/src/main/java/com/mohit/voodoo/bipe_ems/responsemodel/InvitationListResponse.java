package com.mohit.voodoo.bipe_ems.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvitationListResponse {

    @SerializedName("flag")
    @Expose
    public String flag;

    @SerializedName("invitation")
    @Expose
    List<Invitation> list;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Invitation> getList() {
        return list;
    }

    public void setList(List<Invitation> list) {
        this.list = list;
    }

    public class Invitation {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("event_id")
        @Expose
        public String event_id;

        @SerializedName("invitation_id")
        @Expose
        public String invitation_id;

        @SerializedName("receptionist_id")
        @Expose
        public String receptionist_id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("mobile_no")
        @Expose
        public String mobile_no;

        @SerializedName("email_id")
        @Expose
        public String email_id;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("note")
        @Expose
        public String note;

        @SerializedName("is_active")
        @Expose
        public String is_active;

        @SerializedName("is_received")
        @Expose
        public String is_received;

        @SerializedName("created_at")
        @Expose
        public String created_at;

        @SerializedName("qr")
        @Expose
        public String qr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEvent_id() {
            return event_id;
        }

        public void setEvent_id(String event_id) {
            this.event_id = event_id;
        }

        public String getInvitation_id() {
            return invitation_id;
        }

        public void setInvitation_id(String invitation_id) {
            this.invitation_id = invitation_id;
        }

        public String getReceptionist_id() {
            return receptionist_id;
        }

        public void setReceptionist_id(String receptionist_id) {
            this.receptionist_id = receptionist_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getEmail_id() {
            return email_id;
        }

        public void setEmail_id(String email_id) {
            this.email_id = email_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getIs_received() {
            return is_received;
        }

        public void setIs_received(String is_received) {
            this.is_received = is_received;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }
    }
}
