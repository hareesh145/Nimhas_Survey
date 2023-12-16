package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.Section6Adapter;
import com.ganesh.nimhans.databinding.ActivitySection6Binding;
import com.ganesh.nimhans.model.ServeySection6Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.QuestionUtils;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section6Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection6Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;

    HashMap<Integer, Integer> integerHashMap = new HashMap<>();
    private String ageValue;
    private EligibleResponse eligibleResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection6Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);

        binding.section6List.setAdapter(new Section6Adapter(QuestionUtils.getSection6Questions(), this));
        integerHashMap.put(77, 1);
        integerHashMap.put(74, 1);
        integerHashMap.put(75, 1);
        integerHashMap.put(76, 1);

        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRCadsScore();
            }
        });
    }

    private void calculateRCadsScore() {
        ServeySection6Request serveySection6Request = new ServeySection6Request();
        serveySection6Request.setSection6respondent(binding.specifyRespo.getText().toString());
        serveySection6Request.setQno77(integerHashMap.get(77));
        serveySection6Request.setQno74(integerHashMap.get(74));
        serveySection6Request.setQno75(integerHashMap.get(75));
        serveySection6Request.setQno76(integerHashMap.get(76));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.progressBar.setVisibility(View.VISIBLE);
        Call<JsonObject> call = apiService.putServeySection6Data(eligibleResponse.houseHoldId, serveySection6Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();
                if (response.isSuccessful()) {
                    Log.d("response", "onResponse: " + userResponse);
                    try {
                        if (userResponse.get("idResult").getAsInt()>=4) {
                            binding.idScannerResult.setText("1");
                        } else {
                            binding.idScannerResult.setText("0");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.idScannerResult.setText("0");
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");

        if (Integer.parseInt(ageValue) <= 17) {
            if (Integer.parseInt(ageValue) >= 1 && Integer.parseInt(ageValue) <= 17) {
                Intent intent = new Intent(activity, Section7aActivity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            } else if (Integer.parseInt(ageValue) >= 3 && Integer.parseInt(ageValue) <= 17) {
                //If the age is greater than 2
                Intent intent = new Intent(activity, Section7bActivity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            }
        } else {
            //IF the Age is 18
            Intent intent = new Intent(activity, Section7bActivity.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            startActivity(intent);
        }
//        Intent intent = new Intent(activity, Section7aActivity.class);
//        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//        intent.putExtra(SURVEY_ID, surveyID);
//        intent.putExtra(AGE_ID, ageValue);
//        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//        startActivity(intent);

//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<JsonObject> call = apiService.putServeySection5Data(surveyID, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (binding.progressBar.isShown())
//                    binding.progressBar.setVisibility(View.GONE);
//                JsonObject userResponse = response.body();
//                if (response.isSuccessful()) {
//                    Log.d("response", "onResponse: " + userResponse);
//                    Util.showToast(activity, "Successfully data saved");
//                    Intent intent = new Intent(activity, Section7aActivity.class);
//                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });


    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section5Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section6Activity.this, ResultPage.class);
        startActivity(intent);
    }

    public void setQuestionOption(int questionNo, int option) {
        integerHashMap.put(questionNo, option);
    }
}
