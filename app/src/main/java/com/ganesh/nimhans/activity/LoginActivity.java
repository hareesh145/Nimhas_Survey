package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Activity activity;
    private ActivityLoginBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

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

//        binding.edPhoneNo.setText("test");
//        binding.edPswd.setText("test123");

    }

    public void onClickLogin(View v) {
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

                    if (loginResponse.getRole().equals("SUPERVISOR"))
                        startActivity(new Intent(activity, DashboardActivity.class));
                    else
                        startActivity(new Intent(activity, Section1Activity.class));
                    activity.finish();
                    return;
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
}
