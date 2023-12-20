package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityChildrenResultBinding;
import com.ganesh.nimhans.databinding.ActivityResultPageBinding;
import com.ganesh.nimhans.model.ServeySectionRequest;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildrenResult extends AppCompatActivity {
    private ActivityChildrenResultBinding binding;
    Activity activity;
    MyNimhans myGameApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildrenResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
    }
    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section11Activity.class));

    }
    public void onClickNextSection(View v) {
        startActivity(new Intent(activity, ResultPage.class));
    }
}