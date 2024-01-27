package com.ganesh.nimhans.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.adapter.PendingListAdapter;
import com.ganesh.nimhans.databinding.ActivityPendingListBinding;
import com.ganesh.nimhans.model.child.PendingListModel;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingListScreen extends AppCompatActivity {
    private String selectedDistrict;
    private String selectedlocale;
    String selectedStateCode = "";
    String districtCode = "";
    String talukaCode = "";
    String selectedVillageCode = "";
    String selectedcode = "";
    private List<StateModel> stateModels;
    ActivityPendingListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedStateCode = getIntent().getStringExtra("selectedStateCode");
        districtCode = getIntent().getStringExtra("districtCode");
        talukaCode = getIntent().getStringExtra("talukaCode");
        selectedVillageCode = getIntent().getStringExtra("selectedVillageCode");


        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        binding.progressBar.setVisibility(View.VISIBLE);
        apiService.getInprogressTasks(selectedStateCode,districtCode,talukaCode,selectedVillageCode, PreferenceConnector.readString(this, PreferenceConnector.TOKEN, ""))
                .enqueue(new Callback<List<PendingListModel>>() {
                    @Override
                    public void onResponse(Call<List<PendingListModel>> call, Response<List<PendingListModel>> response) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().size() > 0) {
                                PendingListAdapter pendingListAdapter = new PendingListAdapter(PendingListScreen.this, response.body(), stateModels);
                                binding.pendingList.setAdapter(pendingListAdapter);
                                binding.pendingList.setVisibility(View.VISIBLE);
                                binding.noPendingData.setVisibility(View.GONE);
                                binding.progressBar.setVisibility(View.GONE);
                            } else {
                                binding.pendingList.setVisibility(View.GONE);
                                binding.noPendingData.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PendingListModel>> call, Throwable t) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.pendingList.setVisibility(View.GONE);
                        binding.noPendingData.setVisibility(View.VISIBLE);
                    }
                });
    }
}