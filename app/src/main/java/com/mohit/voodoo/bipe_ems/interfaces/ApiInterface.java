package com.mohit.voodoo.bipe_ems.interfaces;

import com.mohit.voodoo.bipe_ems.activity.EditInviteeActivity;
import com.mohit.voodoo.bipe_ems.postmodel.EventListHelper;
import com.mohit.voodoo.bipe_ems.postmodel.InvitationListHelper;
import com.mohit.voodoo.bipe_ems.postmodel.InviteeHelper;
import com.mohit.voodoo.bipe_ems.postmodel.LoginHelper;
import com.mohit.voodoo.bipe_ems.postmodel.QRScanHelper;
import com.mohit.voodoo.bipe_ems.responsemodel.EventListResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.InvitationListResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.InviteeResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> login(@Body LoginHelper loginHelper);

    @Headers("Content-Type: application/json")
    @POST("update-invitation-status")
    Call<Object> qrCodeScan(@Body QRScanHelper qrScanHelper);

    @Headers("Content-Type: application/json")
    @POST("get-my-events")
    Call<EventListResponse> getMyEvents(@Body EventListHelper eventListHelper);

    @Headers("Content-Type: application/json")
    @POST("get-my-invitations")
    Call<InvitationListResponse> getMyInvitations(@Body InvitationListHelper invitationListHelper);

    @Headers("Content-Type: application/json")
    @POST("get-invitee")
    Call<InviteeResponse> getInvitee(@Body InviteeHelper inviteeHelper);

    @Headers("Content-Type: application/json")
    @POST("update-invitee")
    Call<EditInviteeActivity.UpdateInviteeResponse> updateInvitee(@Body EditInviteeActivity.UpdateInviteeHelper updateInviteeHelper);
}
