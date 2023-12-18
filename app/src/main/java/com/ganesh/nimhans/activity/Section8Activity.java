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
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection8Binding;
import com.ganesh.nimhans.model.ServeySection8Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section8Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection8Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private String ageValue;
    private String respondentTxt;
    private int surveyID;
    private long demoGraphicsID;
    private EligibleResponse eligibleResponse;
    RadioGroup respondent_grp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection8Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);

        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        binding.childAge.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);
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
                        binding.respondent.setVisibility(View.GONE);
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.respondent.setVisibility(View.GONE);
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.respondent.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection8Request serveySection8Request = new ServeySection8Request();
        if (respondentTxt == null) {
            respondentTxt = binding.respondent.getText().toString();
        }
        serveySection8Request.setSection8Respondent(respondentTxt);
        serveySection8Request.setQno108(getChecked108Value(binding.question108.getCheckedRadioButtonId()));
        serveySection8Request.setQno109(getChecked109Value(binding.question109.getCheckedRadioButtonId()));
        serveySection8Request.setQno110(getChecked110Value(binding.question110.getCheckedRadioButtonId()));
        serveySection8Request.setQno111(getChecked111Value(binding.question111.getCheckedRadioButtonId()));
        serveySection8Request.setQno112(getChecked112Value(binding.question112.getCheckedRadioButtonId()));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.progressBar.setVisibility(View.VISIBLE);
        apiService.putServeySection8AData(eligibleResponse.houseHoldId, serveySection8Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    JsonObject userResponse = response.body();
                    if (response.isSuccessful()) {
                        Log.d("response", "onResponse: " + userResponse);
                        try {
                            if (userResponse.get("sldResult").getAsInt()>=4) {
                                binding.sldResult.setText("1");
                            } else {
                                binding.sldResult.setText("0");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            binding.sldResult.setText("0");
                        }
                    }
                } catch (Exception e) {
                    binding.sldResult.setText("0");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.sldResult.setText("0");
            }
        });
    }

    private int getChecked108Value(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.question_108_a:
                return 1;
            case R.id.question_108_e:
                return 5;
            case R.id.question_108_b:
                return 2;
            case R.id.question_108_c:
                return 3;
            case R.id.question_108_d:
                return 4;
        }
        return 1;
    }


    private int getChecked109Value(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.question_109_a:
                return 1;
            case R.id.question_109_e:
                return 5;
            case R.id.question_109_b:
                return 2;
            case R.id.question_109_c:
                return 3;
            case R.id.question_109_d:
                return 4;
        }
        return 1;
    }

    private int getChecked110Value(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.question_110_a:
                return 1;
            case R.id.question_110_e:
                return 5;
            case R.id.question_110_b:
                return 2;
            case R.id.question_110_c:
                return 3;
            case R.id.question_110_d:
                return 4;
        }
        return 1;
    }

    private int getChecked111Value(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.question_111_a:
                return 1;
            case R.id.question_111_e:
                return 5;
            case R.id.question_111_b:
                return 2;
            case R.id.question_111_c:
                return 3;
            case R.id.question_111_d:
                return 4;
        }
        return 1;
    }

    private int getChecked112Value(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.question_112_a:
                return 1;
            case R.id.question_112_e:
                return 5;
            case R.id.question_112_b:
                return 2;
            case R.id.question_112_c:
                return 3;
            case R.id.question_112_d:
                return 4;
        }
        return 1;
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section9Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section7bActivity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section8Activity.this, ResultPage.class);
        startActivity(intent);
    }
}
