package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.adapter.EligibleChildAdapter;
import com.ganesh.nimhans.databinding.ActivityEligibleChildrenListBinding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Eligiblechildren extends AppCompatActivity {
    ActivityEligibleChildrenListBinding binding;
    private long demoGraphicsID;
    private int surveyID;
    private List<StateModel> stateModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEligibleChildrenListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);

        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getHouseHoldChilderns(surveyID, PreferenceConnector.readString(this, PreferenceConnector.TOKEN, ""))
                .enqueue(new Callback<List<EligibleResponse>>() {
                    @Override
                    public void onResponse(Call<List<EligibleResponse>> call, Response<List<EligibleResponse>> response) {
                        try {
                            Log.d("TAG", "onResponse: " + response.body());

                            if (response.isSuccessful()) {

                                if (response.body().isEmpty()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Eligiblechildren.this);
                                    builder.setMessage("There are no eligible participants. Thank the respondent and move on to the next household");

                                    builder.setTitle("Alert !");

                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                                        Intent intent =new Intent(Eligiblechildren.this,ResultPage.class);
                                        startActivity(intent);
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                                binding.EligibleList.setAdapter(new EligibleChildAdapter(Eligiblechildren.this, response.body(), demoGraphicsID, surveyID,stateModels));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EligibleResponse>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }
}