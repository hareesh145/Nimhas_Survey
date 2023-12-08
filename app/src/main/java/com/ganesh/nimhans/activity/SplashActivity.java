package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.R;
import com.ganesh.nimhans.utils.PreferenceConnector;

public class SplashActivity extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        loadDash();
    }

    public void loadDash() {
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(activity, LoginActivity.class));
                /*if (PreferenceConnector.readInteger(activity, PreferenceConnector.USERID, 0) != 0) {
                    if (TextUtils.equals(PreferenceConnector.readString(activity, PreferenceConnector.SUPERVISOR, "SUPERVISOR"), "SUPERVISOR")) {
                        startActivity(new Intent(activity, DashboardActivity.class));
                    } else
                        startActivity(new Intent(activity, ActivitySurvey.class));
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                }
                activity.finish();*/
            }
        }, (long) 3000);
    }
}