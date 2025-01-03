package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS8_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

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
import com.ganesh.nimhans.model.ServeySection3cRequest;
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
    ServeySection3cRequest serveySection3cRequest;
    RadioGroup respondent_grp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection8Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);

        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        binding.childAge.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });

        binding.respondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.mother_btn) {
                    respondentTxt = "Mother";
                    binding.respondent.setVisibility(View.GONE);
                } else if (checkedId == R.id.father_btn) {
                    respondentTxt = "Father";
                    binding.respondent.setVisibility(View.GONE);
                } else if (checkedId == R.id.gaurdian_btn) {
                    respondentTxt = "Guardian";
                    binding.respondent.setVisibility(View.VISIBLE);
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
                            int screenPositiveNegative = 0;
                            if (binding.question108D.isChecked() || binding.question108E.isChecked() || binding.question109D.isChecked() || binding.question109E.isChecked() || binding.question110D.isChecked() || binding.question110E.isChecked() || binding.question111D.isChecked() || binding.question111E.isChecked() || binding.question112D.isChecked() || binding.question112E.isChecked()) {
                                screenPositiveNegative = 1;
                            }
                            Log.d("TAG", "onResponse: " + screenPositiveNegative);
                            binding.sldResult.setText("" + screenPositiveNegative);
                            PreferenceConnector.writeString(Section8Activity.this, RCADS8_RESULT, "" + screenPositiveNegative);

                            Log.d("TAG", "RCADS8_RESULT : " + PreferenceConnector.readString(Section8Activity.this, RCADS8_RESULT, ""));
                            Util.showToast(activity, "Successfully data saved");
                            Intent intent = new Intent(activity, Section9Activity.class);
                            intent.putExtra(AGE_ID, ageValue);
                            intent.putExtra(SURVEY_ID, surveyID);
                            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                            intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                            startActivity(intent);
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

    private int calculateSLDResult() {

        return getChecked108Value(binding.question108.getCheckedRadioButtonId()) +
                getChecked109Value(binding.question109.getCheckedRadioButtonId()) +
                getChecked110Value(binding.question110.getCheckedRadioButtonId()) +
                getChecked111Value(binding.question111.getCheckedRadioButtonId()) +
                getChecked112Value(binding.question112.getCheckedRadioButtonId());
    }

    private int getChecked108Value(int checkedRadioButtonId) {

        if (checkedRadioButtonId == R.id.question_108_a) {
            return 1;
        } else if (checkedRadioButtonId == R.id.question_108_e) {
            return 5;
        } else if (checkedRadioButtonId == R.id.question_108_b) {
            return 2;
        } else if (checkedRadioButtonId == R.id.question_108_c) {
            return 3;
        } else if (checkedRadioButtonId == R.id.question_108_d) {
            return 4;
        }
        return 0;
    }


    private int getChecked109Value(int checkedRadioButtonId) {
        if (checkedRadioButtonId == R.id.question_109_a) {
            return 1;
        } else if (checkedRadioButtonId == R.id.question_109_e) {
            return 5;
        } else if (checkedRadioButtonId == R.id.question_109_b) {
            return 2;
        } else if (checkedRadioButtonId == R.id.question_109_c) {
            return 3;
        } else if (checkedRadioButtonId == R.id.question_109_d) {
            return 4;
        } else {
            return 0;
        }

    }

    private int getChecked110Value(int checkedRadioButtonId) {
        if (checkedRadioButtonId == R.id.question_110_a) {
            return 1;
        } else if (checkedRadioButtonId == R.id.question_110_e) {
            return 5;
        } else if (checkedRadioButtonId == R.id.question_110_b) {
            return 2;
        } else if (checkedRadioButtonId == R.id.question_110_c) {
            return 3;
        } else if (checkedRadioButtonId == R.id.question_110_d) {
            return 4;
        } else {
            return 0;
        }
    }

    private int getChecked111Value(int checkedRadioButtonId) {

        if (checkedRadioButtonId == R.id.question_111_a) {
            return 1;
        } else if (checkedRadioButtonId == R.id.question_111_e) {
            return 5;
        } else if (checkedRadioButtonId == R.id.question_111_b) {
            return 2;
        } else if (checkedRadioButtonId == R.id.question_111_c) {
            return 3;
        } else if (checkedRadioButtonId == R.id.question_111_d) {
            return 4;
        } else {
            return 0;
        }
    }

    private int getChecked112Value(int checkedRadioButtonId) {

        if (checkedRadioButtonId == R.id.question_112_a) {
            return 1;
        } else if (checkedRadioButtonId == R.id.question_112_e) {
            return 5;
        } else if (checkedRadioButtonId == R.id.question_112_b) {
            return 2;
        } else if (checkedRadioButtonId == R.id.question_112_c) {
            return 3;
        } else if (checkedRadioButtonId == R.id.question_112_d) {
            return 4;
        } else {
            return 0;
        }
    }

    public void onClickNextSection(View v) {
        checkRCADSScore();
    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section7bActivity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section8Activity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }
}
