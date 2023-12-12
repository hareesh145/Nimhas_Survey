package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.adapter.EligibleChildAdapter;
import com.ganesh.nimhans.databinding.ActivityEligibleChildrenListBinding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Eligiblechildren extends AppCompatActivity {
    ActivityEligibleChildrenListBinding binding;
    private long demoGraphicsID;
    private int surveyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEligibleChildrenListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface.getHouseHoldChilderns(surveyID, PreferenceConnector.readString(this, PreferenceConnector.TOKEN, ""))
                .enqueue(new Callback<List<EligibleResponse>>() {
                    @Override
                    public void onResponse(Call<List<EligibleResponse>> call, Response<List<EligibleResponse>> response) {
                        binding.progressBar.setVisibility(View.GONE);
                        try {
                            if (response.isSuccessful()) {
                                binding.EligibleList.setAdapter(new EligibleChildAdapter(Eligiblechildren.this, response.body()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EligibleResponse>> call, Throwable t) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }
}