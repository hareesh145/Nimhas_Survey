package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityCreateUserBinding;
import com.ganesh.nimhans.model.LoginRequest;
import com.ganesh.nimhans.model.LoginResponse;
import com.ganesh.nimhans.model.UserRequest;
import com.ganesh.nimhans.model.UserResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {
    Activity activity;
    private ActivityCreateUserBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    EditText edFullname, edAge, edMobileNumber, edEmail, edUserName, edPassword;

    MyNimhans myGameApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();

        edFullname = binding.edFullname;
        edAge = binding.edAge;
        edMobileNumber = binding.edMobileNumber;
        edEmail = binding.edEmail;
        edUserName = binding.edUserName;
        edPassword = binding.edPassword;
    }

    public void onClickSubmit(View v) {

        if (edFullname.length() == 0 || edAge.length() == 0 || edMobileNumber.length() == 0 || edEmail.length() == 0 || edUserName.length() == 0 || edPassword.length() == 0) {
            Util.showToast(activity, "Please enter all details before submit");
            return;
        }
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.postAppUser(new UserRequest(edFullname.getText().toString(), edAge.getText().toString(), edMobileNumber.getText().toString(), edEmail.getText().toString(), "USER", edUserName.getText().toString(), edPassword.getText().toString()), PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                UserResponse userResponse = response.body();
                if (response.isSuccessful()) {
                    System.out.println(userResponse.getUserId());
                    activity.finish();
                    startActivity(new Intent(activity, ViewUserActivity.class));
                    return;
                } else {
                    Util.showToast(activity, "Failed to create user");
                    return;
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });

    }
}
