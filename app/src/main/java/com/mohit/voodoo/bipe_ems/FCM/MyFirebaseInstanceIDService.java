package com.mohit.voodoo.bipe_ems.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mohit.voodoo.bipe_ems.appcommon.UserSessionManager;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIdService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        UserSessionManager shrPref = UserSessionManager.getsharedprefInstance(MyFirebaseInstanceIDService.this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        shrPref.setFCMToken(refreshedToken);
        Log.d("Refreshed token: ", "" + refreshedToken);
    }
}
