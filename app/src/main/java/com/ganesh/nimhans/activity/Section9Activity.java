package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection9Binding;
import com.ganesh.nimhans.model.ServeySection9Request;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section9Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection9Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private String ageValue;
    private String respondentTxt;
    private int surveyID;
    private long demoGraphicsID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection9Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        binding.checkAge.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();

        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);

        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });

        binding.respondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        respondentTxt = "Mother";
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        break;
                }
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection9Request serveySection9Request = new ServeySection9Request();
        if (respondentTxt == null) {
            respondentTxt = binding.respondent.getText().toString();
        }
        serveySection9Request.setSection9Respondent(respondentTxt);
        serveySection9Request.setQno113(getCheckedIDValue(binding.question113.getCheckedRadioButtonId(), R.id.question_113_a, R.id.question_113_b,
                R.id.question_113_c, R.id.question_113_d));
        serveySection9Request.setQno114(getCheckedIDValue(binding.question114.getCheckedRadioButtonId(), R.id.question_114_a, R.id.question_114_b,
                R.id.question_114_c, R.id.question_114_d));
        serveySection9Request.setQno115(getCheckedIDValue(binding.question115.getCheckedRadioButtonId(), R.id.question_115_a, R.id.question_115_b,
                R.id.question_115_c, R.id.question_115_d));
        serveySection9Request.setQno116(getCheckedIDValue(binding.question116.getCheckedRadioButtonId(), R.id.question_116_a, R.id.question_116_b,
                R.id.question_116_c, R.id.question_116_d));
        serveySection9Request.setQno117(getCheckedIDValue(binding.question117.getCheckedRadioButtonId(), R.id.question_117_a, R.id.question_117_b,
                R.id.question_117_c, R.id.question_117_d));
        serveySection9Request.setQno119(getCheckedIDValue(binding.question119.getCheckedRadioButtonId(), R.id.question_119_a, R.id.question_119_b,
                R.id.question_119_c, R.id.question_119_d));
        serveySection9Request.setQno121(getCheckedIDValue(binding.question121.getCheckedRadioButtonId(), R.id.question_121_a, R.id.question_121_b,
                R.id.question_121_c, R.id.question_121_d));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.progressBar.setVisibility(View.VISIBLE);

        apiService.putServeySection9AData(surveyID, serveySection9Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    JsonObject userResponse = response.body();
                    if (response.isSuccessful()) {
                        Log.d("response", "onResponse: " + userResponse);
                        try {
                            binding.inAttentionSubsetScore.setText(userResponse.get("inattentionSubsetScore").getAsString());
                            binding.hyperactivitySubsetScore.setText(userResponse.get("hyperAcctivitySubsetScore").getAsString());
                            binding.oddSubsetScore.setText(userResponse.get("oddSubsetScore").getAsString());
                            binding.adhdResult.setText(userResponse.get("adhdScore").getAsString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    binding.inAttentionSubsetScore.setText("0");
                    binding.hyperactivitySubsetScore.setText("0");
                    binding.oddSubsetScore.setText("0");
                    binding.adhdResult.setText("0");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.inAttentionSubsetScore.setText("0");
                binding.hyperactivitySubsetScore.setText("0");
                binding.oddSubsetScore.setText("0");
                binding.adhdResult.setText("0");
            }
        });
    }


    private int getCheckedIDValue(int checkedRadioButtonId, int question_113_a, int question_113_b, int question_113_c, int question_113_d) {

        if (checkedRadioButtonId == question_113_d) {
            return 3;
        } else if (checkedRadioButtonId == question_113_b) {
            return 1;
        } else if (checkedRadioButtonId == question_113_c) {
            return 2;
        } else {
            return 0;
        }
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section10Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        startActivity(intent);

    }

    public void onClickPreviousSection(View v) {
        //  startActivity(new Intent(activity, Section8Activity.class));
        finish();
    }
}
