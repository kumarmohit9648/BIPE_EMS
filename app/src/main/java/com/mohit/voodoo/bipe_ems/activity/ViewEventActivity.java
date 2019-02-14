package com.mohit.voodoo.bipe_ems.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.adaptor.EventListAdaptor;
import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;
import com.mohit.voodoo.bipe_ems.interfaces.OnItemClickGetPosition;
import com.mohit.voodoo.bipe_ems.network.APITransaction;
import com.mohit.voodoo.bipe_ems.postmodel.EventListHelper;
import com.mohit.voodoo.bipe_ems.response.APIResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.EventListResponse;
import com.mohit.voodoo.bipe_ems.sharedpreference.SharedPreference;
import com.mohit.voodoo.bipe_ems.singleton.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickGetPosition {

    private static final String TAG = "ViewEventActivity";
    private SharedPreference sharedPreference;
    private EventListResponse eventResponse;
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private EventListAdaptor adaptor;
    private ImageView iv_backarrow;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        init();
        getEventList();
    }

    private void getEventList() {
        try {
            apiInterface = ApiClient.getInstance(this).create(ApiInterface.class);

            JSONObject jsonObject = new JSONObject(sharedPreference.get_User_Data());
            EventListHelper eventListHelper = new EventListHelper(jsonObject.getJSONObject("user_data").getString("token_id"));

            Log.d(TAG, "getEventList: " + eventListHelper.token_id);
            retrofit2.Call request = apiInterface.getMyEvents(eventListHelper);

            dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait.....");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Log.d(TAG, "getEventList: " + request.request());
            APITransaction.startNetworkService(request, eventListResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    APIResponse eventListResponse = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                dialog.cancel();
                JSONObject jsonObject = new JSONObject(new Gson().toJson(object));
                if (jsonObject.getString("flag").equalsIgnoreCase("true")) {
                    eventResponse = (EventListResponse) object;
                    setAdaptor(eventResponse.getEvents());
                } else {
                    Toast.makeText(ViewEventActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            dialog.cancel();
            Toast.makeText(ViewEventActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private void setAdaptor(List<EventListResponse.Events> events) {
        adaptor = new EventListAdaptor(this, events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);
        adaptor.getValuePosition(ViewEventActivity.this);
    }

    private void init() {
        sharedPreference = SharedPreference.getsharedprefInstance(this);

        iv_backarrow = findViewById(R.id.iv_backarrow);
        recyclerView = findViewById(R.id.recyclerView);

        iv_backarrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backarrow:
                onBackPressed();
                break;
        }
    }

    @Override
    public void getPosition(int position) {
        Intent intent = new Intent(ViewEventActivity.this, ViewInviteeActivity.class);
        intent.putExtra("event_id", eventResponse.getEvents().get(position).event_id);
        startActivity(intent);
    }
}
