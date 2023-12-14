package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection10Binding;
import com.ganesh.nimhans.model.ServeySection10Request;
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

public class Section10Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection10Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private String ageValue;
    private int surveyID;
    private long demoGraphicsID;
    private EligibleResponse eligibleResponse;
    private String respondentTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection10Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        phoneNo = myGameApp.getUserPhoneNo();

        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        binding.checkAgeChild.setText(ageValue);

        binding.section10RespondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });

    }

    private void checkRCADSScore() {
        ServeySection10Request serveySection10Request = new ServeySection10Request();
        int checkedRadioButtonId = binding.section10RespondentGrp.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            serveySection10Request.setSection10Respondent(binding.section10Respondent.getText().toString());
        } else {
            serveySection10Request.setSection10Respondent(respondentTxt);
        }
        serveySection10Request.setQno139(getCheckedIDValue(binding.question139Grp.getCheckedRadioButtonId(), R.id.question_139_a, R.id.question_139_b, R.id.question_139_c, R.id.question_139_d));
        serveySection10Request.setQno140(getCheckedIDValue(binding.question140.getCheckedRadioButtonId(), R.id.question_140_a, R.id.question_140_b, R.id.question_140_c, R.id.question_140_d));
        serveySection10Request.setQno141(getCheckedIDValue(binding.question141.getCheckedRadioButtonId(), R.id.question_141_a, R.id.question_141_b, R.id.question_141_c, R.id.question_141_d));
        serveySection10Request.setQno142(getCheckedIDValue(binding.question142.getCheckedRadioButtonId(), R.id.question_142_a, R.id.question_142_b, R.id.question_142_c, R.id.question_142_d));
        serveySection10Request.setQno143(getCheckedIDValue(binding.question143.getCheckedRadioButtonId(), R.id.question_143_a, R.id.question_143_b, R.id.question_143_c, R.id.question_143_d));
        serveySection10Request.setQno144(getCheckedIDValue(binding.question144.getCheckedRadioButtonId(), R.id.question_144_a, R.id.question_144_b, R.id.question_144_c, R.id.question_144_d));
        serveySection10Request.setQno145(getCheckedIDValue(binding.question145.getCheckedRadioButtonId(), R.id.question_145_a, R.id.question_145_b, R.id.question_145_c, R.id.question_145_d));
        serveySection10Request.setQno146(getCheckedIDValue(binding.question146.getCheckedRadioButtonId(), R.id.question_146_a, R.id.question_146_b, R.id.question_146_c, R.id.question_146_d));
        serveySection10Request.setQno147(getCheckedIDValue(binding.question147.getCheckedRadioButtonId(), R.id.question_147_a, R.id.question_147_b, R.id.question_147_c, R.id.question_147_d));
        serveySection10Request.setQno148(getCheckedIDValue(binding.question148.getCheckedRadioButtonId(), R.id.question_148_a, R.id.question_148_b, R.id.question_148_c, R.id.question_148_d));
        serveySection10Request.setQno149(getCheckedIDValue(binding.question149.getCheckedRadioButtonId(), R.id.question_149_a, R.id.question_149_b, R.id.question_149_c, R.id.question_149_d));
        serveySection10Request.setQno150(getCheckedIDValue(binding.question150.getCheckedRadioButtonId(), R.id.question_150_a, R.id.question_150_b, R.id.question_150_c, R.id.question_150_d));
        serveySection10Request.setQno151(getCheckedIDValue(binding.question151.getCheckedRadioButtonId(), R.id.question_151_a, R.id.question_151_b, R.id.question_151_c, R.id.question_151_d));
        serveySection10Request.setQno152(getCheckedID150Value(binding.question152.getCheckedRadioButtonId(), R.id.question_152a, R.id.question_152b, R.id.question_152c, R.id.question_152d, R.id.question_152e));
        serveySection10Request.setQno153(getCheckedID150Value(binding.question153.getCheckedRadioButtonId(), R.id.question_153a, R.id.question_153b, R.id.question_153c, R.id.question_153d, R.id.question_153e));
        serveySection10Request.setQno154(getCheckedID150Value(binding.question154.getCheckedRadioButtonId(), R.id.question_154a, R.id.question_154b, R.id.question_154c, R.id.question_154d, R.id.question_154e));
        serveySection10Request.setQno155(getCheckedID150Value(binding.question155.getCheckedRadioButtonId(), R.id.question_155a, R.id.question_155b, R.id.question_155c, R.id.question_155d, R.id.question_155e));
        serveySection10Request.setQno156(getCheckedID150Value(binding.question156.getCheckedRadioButtonId(), R.id.question_156a, R.id.question_156b, R.id.question_156c, R.id.question_156d, R.id.question_156e));
        serveySection10Request.setQno157(getCheckedID150Value(binding.question157.getCheckedRadioButtonId(), R.id.question_157a, R.id.question_157b, R.id.question_157c, R.id.question_157d, R.id.question_157e));
        serveySection10Request.setQno158(getCheckedID150Value(binding.question158.getCheckedRadioButtonId(), R.id.question_158a, R.id.question_158b, R.id.question_158c, R.id.question_158d, R.id.question_158e));
        serveySection10Request.setQno159(getCheckedID150Value(binding.question159.getCheckedRadioButtonId(), R.id.question_159a, R.id.question_159b, R.id.question_159c, R.id.question_159d, R.id.question_159e));
        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        apiClient.putServeySection10AData(eligibleResponse.houseHoldId, serveySection10Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    binding.cdResult.setText(response.body().get("cdResult").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section11Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section9Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section10Activity.this, ResultPage.class);
        startActivity(intent);
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

    private int getCheckedID150Value(int checkedRadioButtonId, int question_113_a, int question_113_b, int question_113_c, int question_113_d, int question_113_e) {
        if (checkedRadioButtonId == question_113_e) {
            return 5;
        } else if (checkedRadioButtonId == question_113_d) {
            return 4;
        } else if (checkedRadioButtonId == question_113_b) {
            return 2;
        } else if (checkedRadioButtonId == question_113_c) {
            return 3;
        } else {
            return 1;
        }
    }
}
