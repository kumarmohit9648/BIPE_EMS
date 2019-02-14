package com.mohit.voodoo.bipe_ems.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventListResponse {

    public EventListResponse(String flag, List<Events> events) {
        this.flag = flag;
        this.events = events;
    }

    @SerializedName("flag")
    @Expose
    public String flag;

    @SerializedName("events")
    @Expose
    public List<Events> events;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public class Events {

        @SerializedName("event_name")
        @Expose
        public String event_name;

        @SerializedName("start_date")
        @Expose
        public String start_date;

        @SerializedName("end_date")
        @Expose
        public String end_date;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("event_id")
        @Expose
        public String event_id;

        @SerializedName("student_id")
        @Expose
        public String student_id;

        @SerializedName("employee_id")
        @Expose
        public String employee_id;

        @SerializedName("receptionist")
        @Expose
        public String receptionist;

        @SerializedName("is_active")
        @Expose
        public String is_active;

        @SerializedName("created_at")
        @Expose
        public String created_at;
    }
}
