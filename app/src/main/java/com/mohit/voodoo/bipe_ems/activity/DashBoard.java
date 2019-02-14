package com.mohit.voodoo.bipe_ems.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.appcommon.UserSessionManager;
import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;
import com.mohit.voodoo.bipe_ems.internet.InternetConnection;
import com.mohit.voodoo.bipe_ems.network.APITransaction;
import com.mohit.voodoo.bipe_ems.postmodel.QRScanHelper;
import com.mohit.voodoo.bipe_ems.response.APIResponse;
import com.mohit.voodoo.bipe_ems.singleton.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DashBoard extends Activity implements View.OnClickListener {

    LinearLayout Scan_Code, Event, Invitee, Language;
    TextView tv_logout;
    String type, invitation_id, event_id, receptionist_id;
    final Activity activity = this;
    private ApiInterface apiInterface;
    private static final String TAG = "DashBoard";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Scan_Code = findViewById(R.id.ll_Scan_Code);
        Event = findViewById(R.id.ll_Event);
        Invitee = findViewById(R.id.ll_Invitee);
        Language = findViewById(R.id.ll_language);
        tv_logout = findViewById(R.id.tv_logout);

        Scan_Code.setOnClickListener(this);
        Event.setOnClickListener(this);
        Invitee.setOnClickListener(this);
        Language.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String response = result.getContents();
            List<String> lastvalue = Arrays.asList(response.split(","));
            invitation_id = lastvalue.get(0);
            event_id = lastvalue.get(1);
            receptionist_id = lastvalue.get(2);
            type = lastvalue.get(3);

            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Scan cancel", Toast.LENGTH_SHORT).show();
                } else if (type.equalsIgnoreCase("event") && type != null) {


                    QRScanHelper qrScanHelper = new QRScanHelper(event_id, invitation_id, receptionist_id);

                    apiInterface = ApiClient.getInstance(this).create(ApiInterface.class);
                    retrofit2.Call request = apiInterface.qrCodeScan(qrScanHelper);
                    Log.d(TAG, "QR Code: " + request.request());

                    APITransaction.startNetworkService(request, qrScan_response);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            Toast.makeText(this, "You are scanning wrong QRCode ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    APIResponse qrScan_response = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                String json = new Gson().toJson(object);
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getString("flag").equalsIgnoreCase("true")) {
                    Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashBoard.this, jsonObject.getString("response_string"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            Toast.makeText(DashBoard.this, new Gson().toJson(error), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_Scan_Code:
                if (InternetConnection.isConnected(this)) {
                    startScanner();
                } else {
                    Toast.makeText(activity, "Please Check Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_Event:
                startActivity(new Intent(DashBoard.this, EventActivity.class));
                break;
            case R.id.ll_Invitee:
                startActivity(new Intent(DashBoard.this, ViewEventActivity.class));
                break;
            case R.id.ll_language:
                showLangSelection();
                break;
            case R.id.tv_logout:
                logout();
                break;
        }
    }

    private void logout() {
        UserSessionManager sessionManager = UserSessionManager.getsharedprefInstance(getApplicationContext());
        sessionManager.setLoggedIn(false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startScanner() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES/*ALL_CODE_TYPES*/);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();
    }

    private void showLangSelection() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_lang);
        Button btnEng = dialog.findViewById(R.id.btnEng);
        Button btnHindi = dialog.findViewById(R.id.btnHindi);
        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                dialog.dismiss();
            }
        });

        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("hi");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, DashBoard.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
