package com.mohit.voodoo.bipe_ems.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.appcommon.UserSessionManager;
import com.mohit.voodoo.bipe_ems.constant.URLConstant;
import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;
import com.mohit.voodoo.bipe_ems.internet.InternetConnection;
import com.mohit.voodoo.bipe_ems.network.APITransaction;
import com.mohit.voodoo.bipe_ems.postmodel.LoginHelper;
import com.mohit.voodoo.bipe_ems.response.APIResponse;
import com.mohit.voodoo.bipe_ems.sharedpreference.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private SharedPreference sharedPreference;
    UserSessionManager sessionManager;
    private EditText login_id, password;
    private ApiInterface apiInterface;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiInterface = URLConstant.getSOService();

        sessionManager = UserSessionManager.getsharedprefInstance(this);

        init();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    login();
                }
            }
        });
    }

    private boolean validation() {
        if (login_id.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Login ID", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login() {
        if (InternetConnection.isConnected(this)) {
            String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            LoginHelper loginHelper = new LoginHelper(login_id.getText().toString().trim(), password.getText().toString().trim(),
                    /*"1234"*/sessionManager.getFCMToken(), android_id);

            retrofit2.Call request = apiInterface.login(loginHelper);
            Log.d(TAG, "login: " + request.request());

            APITransaction.startNetworkService(request, login_response);
        } else {
            Toast.makeText(this, "Please Check Your Internet Connection !!", Toast.LENGTH_SHORT).show();
        }

    }

    APIResponse login_response = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                String json = new Gson().toJson(object);
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getString("error_code").equalsIgnoreCase("200")) {
                    sessionManager.setLoggedIn(true);
                    sharedPreference.set_User_Data(json);
                    Log.d(TAG, "OnResponseAPI: " + json);
                    startActivity(new Intent(LoginActivity.this, DashBoard.class));
                } else {
                    Toast.makeText(LoginActivity.this, jsonObject.getString("response_string"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            Toast.makeText(LoginActivity.this, new Gson().toJson(error), Toast.LENGTH_SHORT).show();
        }
    };

    private void init() {
        login_id = findViewById(R.id.login_id);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        sharedPreference = SharedPreference.getsharedprefInstance(this);
        // apiInterface = ApiClient.getInstance(this).create(ApiInterface.class);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
