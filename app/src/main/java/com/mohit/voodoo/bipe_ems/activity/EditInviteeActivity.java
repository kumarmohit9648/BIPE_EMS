package com.mohit.voodoo.bipe_ems.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.interfaces.ApiInterface;
import com.mohit.voodoo.bipe_ems.network.APITransaction;
import com.mohit.voodoo.bipe_ems.postmodel.InviteeHelper;
import com.mohit.voodoo.bipe_ems.response.APIResponse;
import com.mohit.voodoo.bipe_ems.responsemodel.InviteeResponse;
import com.mohit.voodoo.bipe_ems.sharedpreference.SharedPreference;
import com.mohit.voodoo.bipe_ems.singleton.ApiClient;

import org.json.JSONObject;

public class EditInviteeActivity extends AppCompatActivity {

    private EditText name, mobile_no, email_id, description, note;
    private SharedPreference sharedPreference;
    private InviteeResponse inviteeResponse;
    private String invitation_id, token_id;
    private ApiInterface apiInterface;
    private ProgressDialog dialog;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_invitee);
        init();
        setDataOnEditText();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        findViewById(R.id.iv_backarrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void submit() {

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait.....");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        retrofit2.Call call = apiInterface.updateInvitee(new UpdateInviteeHelper(
                token_id,
                invitation_id,
                name.getText().toString().trim(),
                mobile_no.getText().toString().trim(),
                email_id.getText().toString().trim(),
                description.getText().toString().trim(),
                note.getText().toString().trim()
        ));

        APITransaction.startNetworkService(call, updateResponse);
    }

    APIResponse updateResponse = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                dialog.cancel();
                UpdateInviteeResponse response = (UpdateInviteeResponse) object;
                if (response.flag.equalsIgnoreCase("true")) {
                    Toast.makeText(EditInviteeActivity.this, response.message, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(EditInviteeActivity.this, response.message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            dialog.cancel();
            Toast.makeText(EditInviteeActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private void setDataOnEditText() {
        try {
            invitation_id = getIntent().getStringExtra("id");
            JSONObject jsonObject = new JSONObject(sharedPreference.get_User_Data());
            token_id = jsonObject.getJSONObject("user_data").getString("token_id");
            retrofit2.Call request = apiInterface.getInvitee(new InviteeHelper(token_id, invitation_id));

            dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait.....");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            APITransaction.startNetworkService(request, editInviteeResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    APIResponse editInviteeResponse = new APIResponse() {
        @Override
        public void OnResponseAPI(Object object) {
            try {
                dialog.cancel();
                JSONObject jsonObject = new JSONObject(new Gson().toJson(object));
                inviteeResponse = (InviteeResponse) object;
                if (inviteeResponse.flag.equalsIgnoreCase("true")) {
                    name.setText(inviteeResponse.getList().name);
                    mobile_no.setText(inviteeResponse.getList().mobile_no);
                    email_id.setText(inviteeResponse.getList().email_id);
                    description.setText(inviteeResponse.getList().description);
                    note.setText(inviteeResponse.getList().note);
                } else {
                    Toast.makeText(EditInviteeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnErrorAPI(String error) {
            dialog.cancel();
            Toast.makeText(EditInviteeActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private void init() {
        name = findViewById(R.id.name);
        mobile_no = findViewById(R.id.mobile_no);
        email_id = findViewById(R.id.email_id);
        description = findViewById(R.id.description);
        note = findViewById(R.id.note);
        submit = findViewById(R.id.submit);

        sharedPreference = SharedPreference.getsharedprefInstance(this);
        apiInterface = ApiClient.getInstance(this).create(ApiInterface.class);
    }

    public class UpdateInviteeHelper {

        public UpdateInviteeHelper(String token_id, String invitation_id, String name, String mobile_no, String email_id, String description, String note) {
            this.token_id = token_id;
            this.invitation_id = invitation_id;
            this.name = name;
            this.mobile_no = mobile_no;
            this.email_id = email_id;
            this.description = description;
            this.note = note;
        }

        @SerializedName("token_id")
        @Expose
        public String token_id;

        @SerializedName("invitation_id")
        @Expose
        public String invitation_id;

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
    }

    public class UpdateInviteeResponse {

        @SerializedName("flag")
        @Expose
        public String flag;

        @SerializedName("message")
        @Expose
        public String message;
    }
}
