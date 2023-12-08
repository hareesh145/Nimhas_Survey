package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.CallBackListner;
import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityCreateUserBinding;
import com.ganesh.nimhans.databinding.ActivityViewUserBinding;
import com.ganesh.nimhans.model.UserRequest;
import com.ganesh.nimhans.model.UserResponse;
import com.ganesh.nimhans.model.ViewUserResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserActivity extends AppCompatActivity implements CallBackListner {
    Activity activity;
    private ActivityViewUserBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    ListView lView;
    MyNimhans myGameApp;
    ArrayList<ViewUserResponse> userResponse;
    MyCustomAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();
        lView = (ListView) findViewById(R.id.my_listview);

        //handle listview and assign adapter
        getUsers();
    }

    public void getUsers() {
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ViewUserResponse>> call = apiService.getAppUser(PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

        call.enqueue(new Callback<ArrayList<ViewUserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ViewUserResponse>> call, Response<ArrayList<ViewUserResponse>> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                userResponse = response.body();
                if (response.isSuccessful()) {
                    adapter = new MyCustomAdapter(userResponse, activity);
                    lView.setAdapter(adapter);
                    adapter.setCallBackListner(ViewUserActivity.this);
                    System.out.println("user " + userResponse.get(0).getUserId());
                    return;
                } else {
//                    Util.showToast(activity, loginResponse.getMessage());
                    return;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ViewUserResponse>> call, Throwable t) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });

    }

    @Override
    public void onButtonClickListner(int position, int tag) {
        if (tag == 2) {
            startActivity(new Intent(activity, EditUserActivity.class).putExtra("userId", userResponse.get(position).getUserId()));
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the User?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteUser(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteUser(int position) {
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.deleteItem(userResponse.get(position).getUserId(), PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    getUsers();
                    System.out.println("deleted ");
                    return;
                } else {
//                    Util.showToast(activity, loginResponse.getMessage());
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }

}
