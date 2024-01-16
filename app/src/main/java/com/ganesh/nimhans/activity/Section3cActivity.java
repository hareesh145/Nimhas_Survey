package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection3cBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section3cActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection3cBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    String Specify5, Taluk, Village, Address, HeadOfFamily, Name1;
    RadioGroup districtUK, YesOrNo, InterviewStatus;
    MyNimhans myGameApp;
    String selecteddistrictUK;
    String selectedYesOrNo;
    String selectedInterviewStatus;
    int NoOfChildren;
    String Specify6;
    private Long demoGraphicsID;
    private int surveyID;
    String ageValue;

    EligibleResponse eligibleResponse;
    private List<StateModel> stateModels;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection3cBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);

        demoGraphicsID = Long.parseLong(String.valueOf(eligibleResponse.surveySection.demographics.demographicsId));
        ageValue = String.valueOf(eligibleResponse.qno12);


        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);

        YesOrNo = findViewById(R.id.yesOrNo1);
        InterviewStatus = findViewById(R.id.interviewStatus);
        surveyID = getIntent().getIntExtra(Constants.SURVEY_ID, -1);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.districtUK.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteddistrictUK = selectedValue;

        });
       /* YesOrNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedYesOrNo = selectedValue;

            }
        });*/
        InterviewStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedInterviewStatus = selectedValue;
            }
        });

        binding.districtUK.check(getCheckID(PreferenceConnector.readString(this, PreferenceConnector.VILLAGE, "")));
        binding.village.setText(PreferenceConnector.readString(this, PreferenceConnector.VILLAGE, ""));
        binding.taluk.setText(PreferenceConnector.readString(this, PreferenceConnector.TALUKA, ""));
        binding.Specify6.setText(eligibleResponse.surveySection.demographics.randamId + "" + eligibleResponse.qno8);
    }

    private int getCheckID(String selectID) {
        if (selectID.contains("Dehradun")) {
            return R.id.dehradun;
        } else if (selectID.contains("Pauri")) {
            return R.id.pauri;
        } else if (selectID.contains("Almora")) {
            return R.id.almora;
        } else if (selectID.contains("Nainital")) {
            return R.id.nainital;
        }
        return R.id.dehradun;
    }

    public void onClickNextSection(View v) {

        Specify6 = binding.Specify6.getText().toString();
        Specify5 = binding.Specify5.getText().toString();
        Taluk = binding.taluk.getText().toString();
        Village = binding.village.getText().toString();
        Address = binding.address.getText().toString();
        HeadOfFamily = binding.headOfFamily.getText().toString();
        Name1 = binding.Name1.getText().toString();
        ServeySection3cRequest serveySection5Request = new ServeySection3cRequest();
        serveySection5Request.setQno18A(binding.NoofChildren.getText().toString());
        serveySection5Request.setQno18B(Specify6);
        serveySection5Request.setQno18C(selecteddistrictUK);
        serveySection5Request.setQno18D(Taluk);
        serveySection5Request.setQno18E(Village);
        serveySection5Request.setQno18F(Address);
        serveySection5Request.setQno18G(HeadOfFamily);
        serveySection5Request.setQno18H(Name1);
        serveySection5Request.setQno18I(selectedYesOrNo);
        serveySection5Request.setQno18J(selectedInterviewStatus);

        binding.progressBar.setVisibility(View.VISIBLE);

        ApiClient.getClient().create(ApiInterface.class).consentSave(eligibleResponse.houseHoldId, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (YesOrNo.getCheckedRadioButtonId() == R.id.no2) {
                        Intent intentno = new Intent(Section3cActivity.this, ChildrenResult.class);
                        intentno.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intentno.putExtra(SURVEY_ID, surveyID);
                        intentno.putExtra(AGE_ID, ageValue);
                        intentno.putExtra(SURVEY_SECTION3C, serveySection5Request);
                        intentno.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        startActivity(intentno);
                    } else {
                        if (Float.parseFloat(ageValue) <= 17.0f) {
                            if (Float.parseFloat(ageValue) >= 8.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section4Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 6.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section6Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
                                //If the age is greater than 1 & less than 2
                                Intent intent = new Intent(Section3cActivity.this, Section7aActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) > 3.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section7bActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 8.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section8Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 10.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section5Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 6.0f && Float.parseFloat(ageValue) <= 12.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section10Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) <= 1.0f) {
                                Intent intent = new Intent(Section3cActivity.this, Section12Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection5Request);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });



/*//        binding.progressBar.setVisibility(View.VISIBLE);
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<JsonObject> call = apiService.putServeySection3CData(surveyID, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (binding.progressBar.isShown())
//                    binding.progressBar.setVisibility(View.GONE);
//                JsonObject userResponse = response.body();
//                if (response.isSuccessful()) {
//                    Log.d("response", "onResponse: " + userResponse);
//                    Util.showToast(activity, "Successfully data saved");
//                    Intent intent = new Intent(Section3cActivity.this, Section4Activity.class);
//                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                    intent.putExtra(SURVEY_ID, surveyID);
//                    intent.putExtra(AGE_ID, ageValue);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
//            }
//        });*/


    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section3bActivity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section3cActivity.this, ChildrenResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);
        finish();
    }


    private String getSelectedVillageCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.villageName.equals(selectedVillage)) {
                return stateModel.villageCode;
            }
        }
        return "";
    }

    private String getSelectedTalukaCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.subDistrictName.equals(selectedVillage)) {
                return stateModel.subDistrictCode;
            }
        }
        return "";
    }

    private String getSelectedDistrictCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.districtName.equals(selectedVillage)) {
                return stateModel.districtCode;
            }
        }
        return "";
    }

    private String getSelectedStateCode(String selectedState) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.stateName.equals(selectedState)) {
                return stateModel.stateCode;
            }
        }
        return "";
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}
