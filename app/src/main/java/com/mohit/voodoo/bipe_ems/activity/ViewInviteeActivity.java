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
import com.mohit.voodoo.bipe_ems.adaptor.InviteeListAdaptor;
import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;
import com.mohit.voodoo.bipe_ems.interfaces.OnItemClickGetPosition;
import com.mohit.voodoo.bipe_ems.network.APITransaction;
import com.mohit.voodoo.bipe_ems.postmodel.InvitationListHelper;
import com.mohit.voodoo.bipe_ems.response.APIResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.InvitationListResponse;
import com.mohit.voodoo.bipe_ems.sharedpreference.SharedPreference;
import com.mohit.voodoo.bipe_ems.singleton.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewInviteeActivity extends AppCompatActivity implements OnItemClickGetPosition, View.OnClickListener {

    private static final String TAG = "ViewInviteeActivity";
    private InvitationListResponse invitationResponse;
    private SharedPreference sharedPreference;
    private InviteeListAdaptor adaptor;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private ImageView iv_backarrow;
    private ProgressDialog dialog;
    private String event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitee);
        init();
        getInviteeList();
    }

    private void getInviteeList() {
        try {
            event_id = getIntent().getStringExtra("event_id");

            apiInterface = ApiClient.getInstance(this).create(ApiInterface.class);

            JSONObject jsonObject = new JSONObject(sharedPreference.get_User_Data());
            retrofit2.Call request = apiInterface.getMyInvitations(
                    new InvitationListHelper(
                            jsonObject.getJSONObject("user_data").getString("token_id")
                            , event_id, "1"));

            dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait.....");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Log.d(TAG, "getInviteeList: " + request.request());
            APITransaction.startNetworkService(request, invitationListResponse2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    APIResponse invitationListResponse2 = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                dialog.cancel();
                JSONObject jsonObject = new JSONObject(new Gson().toJson(object));
                if (jsonObject.getString("flag").equalsIgnoreCase("true")) {
                    invitationResponse = (InvitationListResponse) object;
                    setAdaptor(invitationResponse.getList());
                } else {
                    Toast.makeText(ViewInviteeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            dialog.cancel();
            Toast.makeText(ViewInviteeActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        getInviteeList();
    }

    private void setAdaptor(List<InvitationListResponse.Invitation> events) {
        adaptor = new InviteeListAdaptor(this, events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);
        adaptor.getValuePosition(ViewInviteeActivity.this);
    }

    private void init() {
        sharedPreference = SharedPreference.getsharedprefInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        iv_backarrow = findViewById(R.id.iv_backarrow);
        iv_backarrow.setOnClickListener(this);
    }

    @Override
    public void getPosition(int position) {
        Intent intent = new Intent(ViewInviteeActivity.this, EditInviteeActivity.class);
        intent.putExtra("id", invitationResponse.getList().get(position).id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backarrow:
                onBackPressed();
                break;
        }
    }
}
