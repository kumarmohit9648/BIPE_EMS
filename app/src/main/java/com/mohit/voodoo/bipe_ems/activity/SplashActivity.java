package com.mohit.voodoo.bipe_ems.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.appcommon.UserSessionManager;
import com.mohit.voodoo.bipe_ems.sharedpreference.SharedPreference;

public class SplashActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private UserSessionManager userSessionManager;
    private ViewGroup transitionsContainer;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSessionManager = UserSessionManager.getsharedprefInstance(this);


        sharedPreference = SharedPreference.getsharedprefInstance(this);
        transitionsContainer = findViewById(R.id.transitions_container);
        text = transitionsContainer.findViewById(R.id.text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                text.setVisibility(View.VISIBLE);
                redirect();
            }
        }, 1000);
    }

    private void redirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSessionManager.isLoggedIn()){
                    startActivity(new Intent(SplashActivity.this,DashBoard.class));
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }
            }
        },2000);
    }

  /*  private void redirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 2000);
    }*/
}
