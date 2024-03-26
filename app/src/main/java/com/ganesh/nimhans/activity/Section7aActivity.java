package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS7A_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection7aBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection7aRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section7aActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7aBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    RadioGroup section7_respondent;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private String ageValue;
    private EligibleResponse eligibleResponse;
    private String respondentTxt;
    ServeySection3cRequest serveySection3cRequest;
    String stringParent_guardian;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection7aBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        phoneNo = myGameApp.getUserPhoneNo();
        section7_respondent = findViewById(R.id.section7_respondent);
        binding.childAge.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });
        section7_respondent.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            stringParent_guardian = selectedValue;
            Log.d("selectedCaste", "Selected value: " + stringParent_guardian);
            switch (checkedId) {

                case R.id.mother_btn:
                    respondentTxt = "Mother";
                    binding.otherTxt.setVisibility(View.GONE);
                    binding.otherTxt.setText("");
                    break;
                case R.id.father_btn:
                    respondentTxt = "Father";
                    binding.otherTxt.setVisibility(View.GONE);
                    binding.otherTxt.setText("");
                    break;
                case R.id.gaurdian_btn:
                    respondentTxt = "Guardian";
                    binding.otherTxt.setVisibility(View.VISIBLE);
                    break;
            }
        });

    }

    private void checkRCADSScore() {
        ServeySection7aRequest serveySection7aRequest = new ServeySection7aRequest();
        int checkedRadioButtonId = binding.section7Respondent.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            if (respondentTxt.equalsIgnoreCase("Guardian")){
                serveySection7aRequest.setMchatRespondent(respondentTxt);
                serveySection7aRequest.setMchatGr(binding.otherTxt.getText().toString());
            }
            else {
                serveySection7aRequest.setMchatRespondent(respondentTxt);
                serveySection7aRequest.setMchatGr("NA");
            }
        } else {
            if (respondentTxt.equalsIgnoreCase("Guardian")){
                serveySection7aRequest.setMchatRespondent(respondentTxt);
                serveySection7aRequest.setMchatGr(binding.otherTxt.getText().toString());
            }
            else {
                serveySection7aRequest.setMchatRespondent(respondentTxt);
                serveySection7aRequest.setMchatGr("NA");
            }
        }
        serveySection7aRequest.setQno78(getCheckedIDYes1(binding.question78Options.getCheckedRadioButtonId(), R.id.question_78_yes, R.id.question_78_no));
        serveySection7aRequest.setQno79(getCheckedIDYes0(binding.question79Options.getCheckedRadioButtonId(), R.id.question_79_yes, R.id.question_79_no));
        serveySection7aRequest.setQno80(getCheckedIDYes1(binding.question80Options.getCheckedRadioButtonId(), R.id.question_80_yes, R.id.question_80_no));
        serveySection7aRequest.setQno81(getCheckedIDYes1(binding.question81Options.getCheckedRadioButtonId(), R.id.question_81_yes, R.id.question_81_no));
        serveySection7aRequest.setQno82(getCheckedIDYes0(binding.question82Options.getCheckedRadioButtonId(), R.id.question_82_yes, R.id.question_82_no));
        serveySection7aRequest.setQno83(getCheckedIDYes1(binding.question83Options.getCheckedRadioButtonId(), R.id.question_83_yes, R.id.question_83_no));
        serveySection7aRequest.setQno84(getCheckedIDYes1(binding.question84Options.getCheckedRadioButtonId(), R.id.question_84_yes, R.id.question_84_no));
        serveySection7aRequest.setQno85(getCheckedIDYes1(binding.question85Options.getCheckedRadioButtonId(), R.id.question_85_yes, R.id.question_85_no));
        serveySection7aRequest.setQno86(getCheckedIDYes1(binding.question86Options.getCheckedRadioButtonId(), R.id.question_86_yes, R.id.question_86_no));
        serveySection7aRequest.setQno87(getCheckedIDYes1(binding.question87Options.getCheckedRadioButtonId(), R.id.question_87_yes, R.id.question_87_no));
        serveySection7aRequest.setQno88(getCheckedIDYes1(binding.question88Options.getCheckedRadioButtonId(), R.id.question_88_yes, R.id.question_88_no));
        serveySection7aRequest.setQno89(getCheckedIDYes0(binding.question89Options.getCheckedRadioButtonId(), R.id.question_89_yes, R.id.question_89_no));
        serveySection7aRequest.setQno90(getCheckedIDYes1(binding.question90Options.getCheckedRadioButtonId(), R.id.question_90_yes, R.id.question_90_no));
        serveySection7aRequest.setQno91(getCheckedIDYes1(binding.question91Options.getCheckedRadioButtonId(), R.id.question_91_yes, R.id.question_91_no));
        serveySection7aRequest.setQno92(getCheckedIDYes1(binding.question92Options.getCheckedRadioButtonId(), R.id.question_92_yes, R.id.question_92_no));
        serveySection7aRequest.setQno93(getCheckedIDYes1(binding.question93Options.getCheckedRadioButtonId(), R.id.question_93_yes, R.id.question_93_no));
        serveySection7aRequest.setQno94(getCheckedIDYes1(binding.question94Options.getCheckedRadioButtonId(), R.id.question_94_yes, R.id.question_94_no));
        serveySection7aRequest.setQno95(getCheckedIDYes1(binding.question95Options.getCheckedRadioButtonId(), R.id.question_95_yes, R.id.question_95_no));
        serveySection7aRequest.setQno96(getCheckedIDYes1(binding.question96Options.getCheckedRadioButtonId(), R.id.question_96_yes, R.id.question_96_no));
        serveySection7aRequest.setQno97(getCheckedIDYes1(binding.question97Options.getCheckedRadioButtonId(), R.id.question_97_yes, R.id.question_97_no));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.putServeySection7AData(eligibleResponse.houseHoldId, serveySection7aRequest, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.e("eligibleResponse.houseHoldId Section 7", "eligibleResponse.houseHoldId Section 7" + eligibleResponse.houseHoldId);
                    try {
                        int screenPositiveNegative = 0;
                        int merchantRawResult = calculateMerchantResult();
                        if (merchantRawResult >= 3) {
                            screenPositiveNegative = 1;
                        }
                        binding.merchantResult.setText(merchantRawResult + " - " + screenPositiveNegative);
                        PreferenceConnector.writeString(Section7aActivity.this, RCADS7A_RESULT, "" + screenPositiveNegative);
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.merchantResult.setText("0");
                        PreferenceConnector.writeString(Section7aActivity.this, RCADS7A_RESULT, "0");
                    }
                    if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
                        Intent intent = new Intent(Section7aActivity.this, Section12Activity.class);
                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intent.putExtra(SURVEY_ID, surveyID);
                        intent.putExtra(AGE_ID, ageValue);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                        startActivity(intent);
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private int calculateMerchantResult() {
        return getSelectedYesAs1(binding.question78Options.getCheckedRadioButtonId(), binding.question78Yes.getId(), binding.question78No.getId()) +
                getSelectedYesAs1(binding.question80Options.getCheckedRadioButtonId(), binding.question80Yes.getId(), binding.question80No.getId()) +
                getSelectedYesAs1(binding.question81Options.getCheckedRadioButtonId(), binding.question81Yes.getId(), binding.question81No.getId()) +
                getSelectedYesAs1(binding.question83Options.getCheckedRadioButtonId(), binding.question83Yes.getId(), binding.question83No.getId()) +
                getSelectedYesAs1(binding.question84Options.getCheckedRadioButtonId(), binding.question84Yes.getId(), binding.question84No.getId()) +
                getSelectedYesAs1(binding.question85Options.getCheckedRadioButtonId(), binding.question85Yes.getId(), binding.question85No.getId()) +
                getSelectedYesAs1(binding.question86Options.getCheckedRadioButtonId(), binding.question86Yes.getId(), binding.question86No.getId()) +
                getSelectedYesAs1(binding.question87Options.getCheckedRadioButtonId(), binding.question87Yes.getId(), binding.question87No.getId()) +
                getSelectedYesAs1(binding.question88Options.getCheckedRadioButtonId(), binding.question88Yes.getId(), binding.question88No.getId()) +
                getSelectedYesAs1(binding.question90Options.getCheckedRadioButtonId(), binding.question90Yes.getId(), binding.question90No.getId()) +
                getSelectedYesAs1(binding.question91Options.getCheckedRadioButtonId(), binding.question91Yes.getId(), binding.question91No.getId()) +
                getSelectedYesAs1(binding.question92Options.getCheckedRadioButtonId(), binding.question92Yes.getId(), binding.question92No.getId()) +
                getSelectedYesAs1(binding.question93Options.getCheckedRadioButtonId(), binding.question93Yes.getId(), binding.question93No.getId()) +
                getSelectedYesAs1(binding.question94Options.getCheckedRadioButtonId(), binding.question94Yes.getId(), binding.question94No.getId()) +
                getSelectedYesAs1(binding.question95Options.getCheckedRadioButtonId(), binding.question95Yes.getId(), binding.question95No.getId()) +
                getSelectedYesAs1(binding.question96Options.getCheckedRadioButtonId(), binding.question96Yes.getId(), binding.question96No.getId()) +
                getSelectedYesAs1(binding.question97Options.getCheckedRadioButtonId(), binding.question97Yes.getId(), binding.question97No.getId()) +
                getSelectedYesAs0(binding.question79Options.getCheckedRadioButtonId(), binding.question79Yes.getId(), binding.question79No.getId()) +
                getSelectedYesAs0(binding.question82Options.getCheckedRadioButtonId(), binding.question82Yes.getId(), binding.question82No.getId()) +
                getSelectedYesAs0(binding.question89Options.getCheckedRadioButtonId(), binding.question89Yes.getId(), binding.question89No.getId());

    }

    private int getSelectedYesAs1(int checkedID, int yes_id, int noId) {
        if (checkedID == yes_id) {
            return 1;
        } else if (checkedID == noId) {
            return 0;
        } else {
            return -1;
        }
    }

    private int getSelectedYesAs0(int checkedID, int yes_id, int noId) {
        if (checkedID == yes_id) {
            return 0;
        } else if (checkedID == noId) {
            return 1;
        } else {
            return -1;
        }
    }


    private int getCheckedIDYes1(int checkedID, int yes_id, int no_id) {
        if (checkedID == yes_id) {
            return 1;
        } else if (checkedID == no_id) {
            return 0;
        } else {
            return -1;
        }
    }
        private int getCheckedIDYes0(int checkedIDYes0, int yes_idYes0, int no_idYes0) {
            if (checkedIDYes0 == yes_idYes0) {
                return 0;
            } else if (checkedIDYes0 == no_idYes0) {
                return 1;
            } else {
                return -1;
            }
    }

    public void onClickNextSection(View v) {
        if (binding.section7Respondent.getCheckedRadioButtonId() == -1
                || binding.question78Options.getCheckedRadioButtonId() == -1
                || binding.question79Options.getCheckedRadioButtonId() == -1
                || binding.question80Options.getCheckedRadioButtonId() == -1
                || binding.question81Options.getCheckedRadioButtonId() == -1
                || binding.question82Options.getCheckedRadioButtonId() == -1
                || binding.question83Options.getCheckedRadioButtonId() == -1
                || binding.question84Options.getCheckedRadioButtonId() == -1
                || binding.question85Options.getCheckedRadioButtonId() == -1
                || binding.question86Options.getCheckedRadioButtonId() == -1
                || binding.question87Options.getCheckedRadioButtonId() == -1
                || binding.question88Options.getCheckedRadioButtonId() == -1
                || binding.question89Options.getCheckedRadioButtonId() == -1
                || binding.question90Options.getCheckedRadioButtonId() == -1
                || binding.question91Options.getCheckedRadioButtonId() == -1
                || binding.question92Options.getCheckedRadioButtonId() == -1
                || binding.question93Options.getCheckedRadioButtonId() == -1
                || binding.question94Options.getCheckedRadioButtonId() == -1
                || binding.question95Options.getCheckedRadioButtonId() == -1
                || binding.question96Options.getCheckedRadioButtonId() == -1
                || binding.question97Options.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
       /* binding.progressBar.setVisibility(View.VISIBLE);
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section7bActivity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);*/
//        startActivity(new Intent(activity, Section7bActivity.class));
            checkRCADSScore();
        }

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section6Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section7aActivity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
