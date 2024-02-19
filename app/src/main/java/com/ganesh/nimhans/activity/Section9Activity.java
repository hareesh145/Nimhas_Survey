package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS9_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_3_RESULT;
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
import com.ganesh.nimhans.databinding.ActivitySection9Binding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection9Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
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
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection9Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        phoneNo = myGameApp.getUserPhoneNo();
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        binding.checkAge.setText(ageValue);
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
                switch (checkedId) {
                    case R.id.mother_btn:
                        respondentTxt = "Mother";
                        binding.respondent.setVisibility(View.GONE);
                        binding.respondent.setText("");
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.respondent.setVisibility(View.GONE);
                        binding.respondent.setText("");
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.respondent.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private int calculateISSResult() {
        //113 - 121
        return getCheckedIDValue(binding.question113.getCheckedRadioButtonId(), R.id.question_113_a, R.id.question_113_b,
                R.id.question_113_c, R.id.question_113_d) +
                getCheckedIDValue(binding.question114.getCheckedRadioButtonId(), R.id.question_114_a, R.id.question_114_b,
                        R.id.question_114_c, R.id.question_114_d) +
                getCheckedIDValue(binding.question115.getCheckedRadioButtonId(), R.id.question_115_a, R.id.question_115_b,
                        R.id.question_115_c, R.id.question_115_d) +
                getCheckedIDValue(binding.question116.getCheckedRadioButtonId(), R.id.question_116_a, R.id.question_116_b,
                        R.id.question_116_c, R.id.question_116_d) +
                getCheckedIDValue(binding.question117.getCheckedRadioButtonId(), R.id.question_117_a, R.id.question_117_b,
                        R.id.question_117_c, R.id.question_117_d) +
                getCheckedIDValue(binding.question118.getCheckedRadioButtonId(), R.id.question_118_a, R.id.question_118_b,
                        R.id.question_118_c, R.id.question_118_d) +
                getCheckedIDValue(binding.question119.getCheckedRadioButtonId(), R.id.question_119_a, R.id.question_119_b,
                        R.id.question_119_c, R.id.question_119_d) +
                getCheckedIDValue(binding.question120.getCheckedRadioButtonId(), R.id.question_120_a, R.id.question_120_b,
                        R.id.question_120_c, R.id.question_120_d) +
                getCheckedIDValue(binding.question121.getCheckedRadioButtonId(), R.id.question_121_a, R.id.question_121_b,
                        R.id.question_121_c, R.id.question_121_d);
    }

    private int calculateHssResult() {
        return getCheckedIDValue(binding.question122.getCheckedRadioButtonId(), R.id.question_122_a, R.id.question_122_b,
                R.id.question_122_c, R.id.question_122_d) +
                getCheckedIDValue(binding.question123.getCheckedRadioButtonId(), R.id.question_123_a, R.id.question_123_b,
                        R.id.question_123_c, R.id.question_123_d) +
                getCheckedIDValue(binding.question124.getCheckedRadioButtonId(), R.id.question_124_a, R.id.question_124_b,
                        R.id.question_124_c, R.id.question_124_d) +
                getCheckedIDValue(binding.question125.getCheckedRadioButtonId(), R.id.question_125_a, R.id.question_125_b,
                        R.id.q125c, R.id.q125d) +
                getCheckedIDValue(binding.question126.getCheckedRadioButtonId(), R.id.question_126a, R.id.question_126b,
                        R.id.question_126c, R.id.question_126d) +
                getCheckedIDValue(binding.question127.getCheckedRadioButtonId(), R.id.question127a, R.id.question127b,
                        R.id.question127c, R.id.question127d) +
                getCheckedIDValue(binding.question128.getCheckedRadioButtonId(), R.id.question128a, R.id.question128b,
                        R.id.question128c, R.id.question128d) +
                getCheckedIDValue(binding.question129.getCheckedRadioButtonId(), R.id.question129a, R.id.question129b,
                        R.id.question129c, R.id.question129d) +
                getCheckedIDValue(binding.question130.getCheckedRadioButtonId(), R.id.question130a, R.id.question130b,
                        R.id.question130c, R.id.question130d);
    }

    private int calculateOSSResult() {
        return getCheckedIDValue(binding.question131.getCheckedRadioButtonId(), R.id.question131a, R.id.question131b,
                R.id.question131c, R.id.question131d) +
                getCheckedIDValue(binding.question132.getCheckedRadioButtonId(), R.id.question132a, R.id.question132b,
                        R.id.question132c, R.id.question132d) +
                getCheckedIDValue(binding.question133.getCheckedRadioButtonId(), R.id.question133a, R.id.question133b,
                        R.id.question133c, R.id.question133d)
                + getCheckedIDValue(binding.question134.getCheckedRadioButtonId(), R.id.question134a, R.id.question134b,
                R.id.question134c, R.id.question134d)
                + getCheckedIDValue(binding.question135.getCheckedRadioButtonId(), R.id.q135a, R.id.q135b,
                R.id.q135c, R.id.q135d)
                + getCheckedIDValue(binding.q136.getCheckedRadioButtonId(), R.id.q136a, R.id.q136b,
                R.id.q136c, R.id.q136d) +
                +getCheckedIDValue(binding.q137.getCheckedRadioButtonId(), R.id.q137a, R.id.q137b,
                        R.id.q137c, R.id.q137d)
                + getCheckedIDValue(binding.q138.getCheckedRadioButtonId(), R.id.q138a, R.id.q138b,
                R.id.q138c, R.id.q138d);
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

        serveySection9Request.setQno122(getCheckedIDValue(binding.question122.getCheckedRadioButtonId(), R.id.question_122_a, R.id.question_122_b,
                R.id.question_122_c, R.id.question_122_d));
        serveySection9Request.setQno123(getCheckedIDValue(binding.question123.getCheckedRadioButtonId(), R.id.question_123_a, R.id.question_123_b,
                R.id.question_123_c, R.id.question_123_d));
        serveySection9Request.setQno124(getCheckedIDValue(binding.question124.getCheckedRadioButtonId(), R.id.question_124_a, R.id.question_124_b,
                R.id.question_124_c, R.id.question_124_d));
        serveySection9Request.setQno125(getCheckedIDValue(binding.question125.getCheckedRadioButtonId(), R.id.question_125_a, R.id.question_125_b,
                R.id.q125c, R.id.q125d));
        serveySection9Request.setQno126(getCheckedIDValue(binding.question126.getCheckedRadioButtonId(), R.id.question_126a, R.id.question_126b,
                R.id.question_126c, R.id.question_126d));
        serveySection9Request.setQno127(getCheckedIDValue(binding.question127.getCheckedRadioButtonId(), R.id.question127a, R.id.question127b,
                R.id.question127c, R.id.question127d));
        serveySection9Request.setQno128(getCheckedIDValue(binding.question128.getCheckedRadioButtonId(), R.id.question128a, R.id.question128b,
                R.id.question128c, R.id.question128d));
        serveySection9Request.setQno129(getCheckedIDValue(binding.question129.getCheckedRadioButtonId(), R.id.question129a, R.id.question129b,
                R.id.question129c, R.id.question129d));

        serveySection9Request.setQno130(getCheckedIDValue(binding.question130.getCheckedRadioButtonId(), R.id.question130a, R.id.question130b,
                R.id.question130c, R.id.question130d));

        serveySection9Request.setQno131(getCheckedIDValue(binding.question131.getCheckedRadioButtonId(), R.id.question131a, R.id.question131b,
                R.id.question131c, R.id.question131d));
        serveySection9Request.setQno132(getCheckedIDValue(binding.question132.getCheckedRadioButtonId(), R.id.question132a, R.id.question132b,
                R.id.question132c, R.id.question132d));
        serveySection9Request.setQno133(getCheckedIDValue(binding.question133.getCheckedRadioButtonId(), R.id.question133a, R.id.question133b,
                R.id.question133c, R.id.question133d));
        serveySection9Request.setQno134(getCheckedIDValue(binding.question134.getCheckedRadioButtonId(), R.id.question134a, R.id.question134b,
                R.id.question134c, R.id.question134d));
        serveySection9Request.setQno135(getCheckedIDValue(binding.question135.getCheckedRadioButtonId(), R.id.q135a, R.id.q135b,
                R.id.q135c, R.id.q135d));


        serveySection9Request.setQno136(getCheckedIDValue(binding.q136.getCheckedRadioButtonId(), R.id.q136a, R.id.q136b,
                R.id.q136c, R.id.q136d));
        serveySection9Request.setQno137(getCheckedIDValue(binding.q137.getCheckedRadioButtonId(), R.id.q137a, R.id.q137b,
                R.id.q137c, R.id.q137d));
        serveySection9Request.setQno138(getCheckedIDValue(binding.q138.getCheckedRadioButtonId(), R.id.q138a, R.id.q138b,
                R.id.q138c, R.id.q138d));


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.progressBar.setVisibility(View.VISIBLE);

        apiService.putServeySection9AData(eligibleResponse.houseHoldId, serveySection9Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    JsonObject userResponse = response.body();
                    if (response.isSuccessful()) {
                        Log.d("response", "onResponse: " + userResponse);
                        try {
                            int calculateISSResult = calculateISSResult();
                            int calculateHssResult = calculateHssResult();
                            int calculateOSSResult = calculateOSSResult();
                            int screenPositiveNegativeISS = 0;
                            if (calculateISSResult >= 13) {
                                screenPositiveNegativeISS = 1;
                            }
                            int screenPositiveNegativeHss = 0;
                            if (calculateHssResult >= 13) {
                                screenPositiveNegativeHss = 1;
                            }
                            int screenPositiveNegativeOss = 0;
                            if (calculateOSSResult >= 8) {
                                screenPositiveNegativeOss = 1;
                            }
                            binding.inAttentionSubsetScore.setText(calculateISSResult + " - " + screenPositiveNegativeISS);
                            binding.hyperactivitySubsetScore.setText(calculateHssResult + " - " + screenPositiveNegativeHss);
                            binding.oddSubsetScore.setText(calculateOSSResult + " - " + screenPositiveNegativeOss);

                            PreferenceConnector.writeString(Section9Activity.this, RCADS9_1_RESULT, "" + screenPositiveNegativeISS);
                            PreferenceConnector.writeString(Section9Activity.this, RCADS9_2_RESULT, "" + screenPositiveNegativeHss);
                            PreferenceConnector.writeString(Section9Activity.this, RCADS9_3_RESULT, "" + screenPositiveNegativeOss);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (Float.parseFloat(ageValue) >= 6.0f && Float.parseFloat(ageValue) <= 12.0f) {
                            Intent intent = new Intent(Section9Activity.this, Section10Activity.class);
                            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                            intent.putExtra(SURVEY_ID, surveyID);
                            intent.putExtra(AGE_ID, ageValue);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                            intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                            startActivity(intent);
                        } else if (Float.parseFloat(ageValue) < 6.0f) {
                            Intent intent = new Intent(Section9Activity.this, Section12Activity.class);
                            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                            intent.putExtra(SURVEY_ID, surveyID);
                            intent.putExtra(AGE_ID, ageValue);
                            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                            intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                            startActivity(intent);
                        } else if (Float.parseFloat(ageValue) >= 8.0f && Float.parseFloat(ageValue) <= 17.0f) {
                            Intent intent = new Intent(Section9Activity.this, Section11Activity.class);
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
                    binding.inAttentionSubsetScore.setText("0");
                    binding.hyperactivitySubsetScore.setText("0");
                    binding.oddSubsetScore.setText("0");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.inAttentionSubsetScore.setText("0");
                binding.hyperactivitySubsetScore.setText("0");
                binding.oddSubsetScore.setText("0");
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
       /* Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section10Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);*/
        checkRCADSScore();
    }

    public void onClickPreviousSection(View v) {
        //  startActivity(new Intent(activity, Section8Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section9Activity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }
}
