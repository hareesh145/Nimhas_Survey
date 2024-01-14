package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS6_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.Section6Adapter;
import com.ganesh.nimhans.databinding.ActivitySection6Binding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
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
    String stringParent_guardian;
    RadioGroup PARENTS_GUARDIAN;
    HashMap<Integer, Integer> integerHashMap = new HashMap<>();
    private String ageValue;
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection6Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        PARENTS_GUARDIAN = findViewById(R.id.PARENTS_GUARDIAN);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);

        binding.section6List.setAdapter(new Section6Adapter(QuestionUtils.getSection6Questions(), this));
        integerHashMap.put(77, 0);
        integerHashMap.put(74, 0);
        integerHashMap.put(75, 0);
        integerHashMap.put(76, 0);

        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRCadsScore();
            }
        });
        PARENTS_GUARDIAN.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            stringParent_guardian = selectedValue;
            Log.d("selectedCaste", "Selected value: " + stringParent_guardian);
            switch (checkedId) {
                case R.id.guardian_rb:
                    binding.specifyRespo.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.specifyRespo.setVisibility(View.GONE);
                    binding.specifyRespo.setText("");
                    break;
            }
        });
    }

    private int calculateIDResult() {
        // (Q.74, Q.75, Q.76, Q.77)
        return integerHashMap.get(74) + integerHashMap.get(75) + integerHashMap.get(76) + integerHashMap.get(77);
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
                        int screenPositiveNegative = 0;
                        if (calculateIDResult() >= 4) {
                            screenPositiveNegative = 1;
                        }
                        binding.idScannerResult.setText("" + screenPositiveNegative);
                        PreferenceConnector.writeString(Section6Activity.this, RCADS6_RESULT, "" + screenPositiveNegative);

                        Util.showToast(activity, "Successfully data saved");
                        Log.d("TAG", "onResponse RCADS6_RESULT: " + PreferenceConnector.readString(Section6Activity.this, RCADS6_RESULT, ""));

//                        if (Float.parseFloat(ageValue) <= 17.0f) {
//                            if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
//                                Intent intent = new Intent(activity, Section7aActivity.class);
//                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                                intent.putExtra(SURVEY_ID, surveyID);
//                                intent.putExtra(AGE_ID, ageValue);
//                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                                startActivity(intent);
//                            } else if (Float.parseFloat(ageValue) >= 4.0f) {
//                                //If the age is greater than 2
//                                Intent intent = new Intent(activity, Section7bActivity.class);
//                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                                intent.putExtra(SURVEY_ID, surveyID);
//                                intent.putExtra(AGE_ID, ageValue);
//                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                                startActivity(intent);
//                            } else {
//                                //IF the Age is 18
//                                Intent intent = new Intent(activity, Section8Activity.class);
//                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                                intent.putExtra(SURVEY_ID, surveyID);
//                                intent.putExtra(AGE_ID, ageValue);
//                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                                startActivity(intent);
//                            }
//                        }
                        if (Float.parseFloat(ageValue) <= 17.0f) {
                            if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
                                //If the age is greater than 2 & less than 3
                                Intent intent = new Intent(activity, Section7aActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 4.0f) {
                                //If the age is greater than 3 Krishna
                                Intent intent = new Intent(activity, Section7bActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else {
                                //IF the Age is 18
                                Intent intent = new Intent(activity, Section8Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            }
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
        calculateRCadsScore();
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
        Intent intent = new Intent(Section6Activity.this, ParentResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

    public void setQuestionOption(int questionNo, int option) {
        integerHashMap.put(questionNo, option);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
