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
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection7aBinding;
import com.ganesh.nimhans.model.ServeySection7aRequest;
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

public class Section7aActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7aBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private String ageValue;
    private EligibleResponse eligibleResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection7aBinding.inflate(getLayoutInflater());
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
        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection7aRequest serveySection7aRequest = new ServeySection7aRequest();
        int checkedRadioButtonId = binding.section7Respondent.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton checkedID = findViewById(checkedRadioButtonId);
            serveySection7aRequest.setMchatRespondent(checkedID.getText().toString());
        } else {
            serveySection7aRequest.setMchatRespondent(binding.otherTxt.getText().toString());
        }


        serveySection7aRequest.setQno79(getCheckedID(binding.question79Options.getCheckedRadioButtonId(), R.id.question_79_yes,R.id.question_79_no));
        serveySection7aRequest.setQno80(getCheckedID(binding.question80Options.getCheckedRadioButtonId(), R.id.question_80_yes,R.id.question_80_no));
        serveySection7aRequest.setQno81(getCheckedID(binding.question81Options.getCheckedRadioButtonId(), R.id.question_81_yes,R.id.question_81_no));
        serveySection7aRequest.setQno82(getCheckedID(binding.question82Options.getCheckedRadioButtonId(), R.id.question_82_yes,R.id.question_82_no));
        serveySection7aRequest.setQno83(getCheckedID(binding.question83Options.getCheckedRadioButtonId(), R.id.question_83_yes,R.id.question_83_no));
        serveySection7aRequest.setQno84(getCheckedID(binding.question84Options.getCheckedRadioButtonId(), R.id.question_84_yes,R.id.question_84_no));
        serveySection7aRequest.setQno85(getCheckedID(binding.question85Options.getCheckedRadioButtonId(), R.id.question_85_yes,R.id.question_85_no));
        serveySection7aRequest.setQno86(getCheckedID(binding.question86Options.getCheckedRadioButtonId(), R.id.question_86_yes,R.id.question_86_no));
        serveySection7aRequest.setQno87(getCheckedID(binding.question87Options.getCheckedRadioButtonId(), R.id.question_87_yes,R.id.question_87_no));
        serveySection7aRequest.setQno88(getCheckedID(binding.question88Options.getCheckedRadioButtonId(), R.id.question_88_yes,R.id.question_88_no));
        serveySection7aRequest.setQno89(getCheckedID(binding.question89Options.getCheckedRadioButtonId(), R.id.question_89_yes,R.id.question_89_no));
        serveySection7aRequest.setQno90(getCheckedID(binding.question90Options.getCheckedRadioButtonId(), R.id.question_90_yes,R.id.question_90_no));
        serveySection7aRequest.setQno91(getCheckedID(binding.question91Options.getCheckedRadioButtonId(), R.id.question_91_yes,R.id.question_91_no));
        serveySection7aRequest.setQno92(getCheckedID(binding.question92Options.getCheckedRadioButtonId(), R.id.question_92_yes,R.id.question_92_no));
        serveySection7aRequest.setQno93(getCheckedID(binding.question93Options.getCheckedRadioButtonId(), R.id.question_93_yes,R.id.question_93_no));
        serveySection7aRequest.setQno94(getCheckedID(binding.question94Options.getCheckedRadioButtonId(), R.id.question_94_yes,R.id.question_94_no));
        serveySection7aRequest.setQno95(getCheckedID(binding.question95Options.getCheckedRadioButtonId(), R.id.question_95_yes,R.id.question_95_no));
        serveySection7aRequest.setQno96(getCheckedID(binding.question96Options.getCheckedRadioButtonId(), R.id.question_96_yes,R.id.question_96_no));
        serveySection7aRequest.setQno97(getCheckedID(binding.question97Options.getCheckedRadioButtonId(), R.id.question_97_yes,R.id.question_97_no));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.putServeySection7AData(eligibleResponse.houseHoldId, serveySection7aRequest, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    try {
                        if (response.body().get("mchatResult").getAsInt()>=3) {
                            binding.merchantResult.setText("1");
                        }else{
                            binding.merchantResult.setText("0");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        binding.merchantResult.setText("0");
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


    private int getCheckedID(int checkedID, int yes_id, int no_id) {
        if (checkedID == yes_id) {
            return 1;
        } else {
            return 2;
        }
    }

    public void onClickNextSection(View v) {
        binding.progressBar.setVisibility(View.VISIBLE);
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section7bActivity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);
//        startActivity(new Intent(activity, Section7bActivity.class));

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section6Activity.class));
        finish();
    }
    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section7aActivity.this,ResultPage.class);
        startActivity(intent);
    }
}
