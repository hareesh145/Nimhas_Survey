package com.ganesh.nimhans.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityLoginBinding;
import com.ganesh.nimhans.model.LoginRequest;
import com.ganesh.nimhans.model.LoginResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Activity activity;
    private ActivityLoginBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String PHONENO_KEY = "phoneNo_key";

    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    MyNimhans myGameApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        phoneNo = myGameApp.getUserPhoneNo();
        binding.edPhoneNo.setText(phoneNo);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        phoneNo = sharedpreferences.getString(PHONENO_KEY, null);
        pswd = sharedpreferences.getString(PASSWORD_KEY, null);
        // getting data from shared prefs and
        // storing it in our string variable.
//        binding.edPhoneNo.setText("test");
//        binding.edPswd.setText("test123");

    }

    public void onClickLogin(View v) {
        if (binding.langRadioBtns.getCheckedRadioButtonId()==binding.hindiBtn.getId()) {
            Locale myLocale = new Locale("hi");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }else {
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        if (!Util.isNetworkConnected(this)) {
            Util.showLongToast(this, "Please Check Internet Connection", false);
            return;
        }
        phoneNo = binding.edPhoneNo.getText().toString();
        pswd = binding.edPswd.getText().toString();

        if (TextUtils.isEmpty(phoneNo) || TextUtils.isEmpty(pswd)) {
            Toast.makeText(activity, "Please Enter all field", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiService.postLogin(new LoginRequest(phoneNo, pswd));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()) {
                    PreferenceConnector.writeInteger(activity, PreferenceConnector.USERID, loginResponse.getUserId());
                    PreferenceConnector.writeString(activity, PreferenceConnector.USERCODE, loginResponse.getUsercode());
                    PreferenceConnector.writeString(activity, PreferenceConnector.SUPERVISOR, loginResponse.getRole());
                    PreferenceConnector.writeString(activity, PreferenceConnector.TOKEN, response.headers().get("authorization"));
                    PreferenceConnector.writeString(activity, PreferenceConnector.LOGIN_ID, phoneNo);

                    if (loginResponse.getRole().equals("SUPERVISOR")) {
                        startActivity(new Intent(activity, DashboardActivity.class));
                    }
                    else {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(PHONENO_KEY, phoneNo);
                        editor.putString(PASSWORD_KEY, pswd);
                        editor.apply();
                        startActivity(new Intent(activity, ActivitySurvey.class));
                        activity.finish();
                    }


                } else {
                    try {
                        JsonObject jsonObject = new Gson().fromJson(response.errorBody().string(), JsonObject.class);
                        Util.showToast(activity, jsonObject.get("error").getAsString());
                    } catch (Exception e) {
                        Util.showToast(activity, "Oops Something went Wrong Please try again");
                        throw new RuntimeException(e);

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (phoneNo != null && pswd != null) {
            Intent i = new Intent(LoginActivity.this, ActivitySurvey.class);
            startActivity(i);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //
    }
}
