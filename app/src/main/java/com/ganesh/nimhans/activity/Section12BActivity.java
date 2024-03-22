package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS10_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS11_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS6_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7A_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7B_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS8_RESULT;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection12bBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.SurveySection12B;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section12BActivity extends AppCompatActivity {
    private static final String TAG = Section12BActivity.class.getSimpleName();
    private ActivitySection12bBinding binding;
    Activity activity;
    Long demoGraphicsID;
    private int surveyID;
    MyNimhans myGameApp;
    String selectedResultCode;
    String selectedCaste;
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    String stringParent_guardian;
    private String ageValue,respondentTxt;
    private String rCards4Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection12bBinding.inflate(getLayoutInflater());
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
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);
        binding.childAge.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        binding.funnyFeeling.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            Log.d("selectedCaste", "Selected value: " + selectedCaste);
            switch (checkedId) {
                case R.id.often2:
                    binding.Specify1.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.Specify1.setVisibility(View.GONE);
                    binding.Specify1.setText("");
                    break;
            }
        });
        binding.problemMother.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            switch (checkedId) {
                case R.id.yes_mother:
                    binding.checkboxProblem.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.checkboxProblem.setVisibility(View.GONE);
                    binding.HypertensionHighBP.setChecked(false);
                    binding.DiabetesMellitus.setChecked(false);
                    binding.Infections.setChecked(false);
                    binding.SeizuresConvulsions.setChecked(false);
                    break;
            }
        });
        binding.PARENTSGUARDIAN.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        respondentTxt = "Mother";
                        binding.specifyRespo.setVisibility(View.GONE);
                        binding.specifyRespo.setText("");
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.specifyRespo.setVisibility(View.GONE);
                        binding.specifyRespo.setText("");
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.specifyRespo.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        binding.noFunyes.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            Log.d("selectedCaste", "Selected value: " + selectedCaste);
            switch (checkedId) {
                case R.id.never3:
                    binding.Specify2.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.Specify2.setVisibility(View.GONE);
                    binding.Specify2.setText("");
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        if (binding.PARENTSGUARDIAN.getCheckedRadioButtonId() == -1 || binding.Anxiety.getCheckedRadioButtonId() == -1  || binding.problemMother.getCheckedRadioButtonId() == -1 || binding.funnyFeeling.getCheckedRadioButtonId() == -1 || binding.feelingWorry.getCheckedRadioButtonId() == -1|| binding.feelingAfraid.getCheckedRadioButtonId() == -1 || binding.noFunyes.getCheckedRadioButtonId() == -1|| binding.noFun.getCheckedRadioButtonId() == -1 || binding.Specify3.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            SurveySection12B surveySection12B = new SurveySection12B();
            int checkedRadioButtonId = binding.PARENTSGUARDIAN.getCheckedRadioButtonId();
            if (checkedRadioButtonId == -1) {
                if (respondentTxt.equalsIgnoreCase("Guardian")) {
                    surveySection12B.setSection6Arespondent(respondentTxt);
                    surveySection12B.setSection6AGuardian(binding.specifyRespo.getText().toString());
                } else {
                    surveySection12B.setSection6Arespondent(respondentTxt);
                    surveySection12B.setSection6AGuardian("NA");
                }
            } else {
                if (respondentTxt.equalsIgnoreCase("Guardian")) {
                    surveySection12B.setSection6Arespondent(respondentTxt);
                    surveySection12B.setSection6AGuardian(binding.specifyRespo.getText().toString());
                } else {
                    surveySection12B.setSection6Arespondent(respondentTxt);
                    surveySection12B.setSection6AGuardian("NA");
                }
            }
            if (binding.Anxiety.getCheckedRadioButtonId() != -1) {
                surveySection12B.setQno73a(Integer.toString(getSelected73A(binding.Anxiety.getCheckedRadioButtonId(), binding.never45.getId(), binding.sometime.getId(), binding.often.getId())));
            } else {
                surveySection12B.setQno73a("NA");
            }
            if (binding.problemMother.getCheckedRadioButtonId() != -1) {
                String mother73b = Integer.toString(getCheckedRadioGrpID(binding.problemMother.getCheckedRadioButtonId(), binding.yesMother.getId(), binding.noMother.getId()));
                surveySection12B.setQno73b(mother73b);
                if (mother73b.equals("1")) {
                    if (binding.HypertensionHighBP.isChecked()) {
                        surveySection12B.setQno73ba(binding.HypertensionHighBP.getText().toString());
                    } else {
                        surveySection12B.setQno73ba("NA");

                    }
                    if (binding.DiabetesMellitus.isChecked()) {
                        surveySection12B.setQno73bb(binding.DiabetesMellitus.getText().toString());
                    } else {
                        surveySection12B.setQno73bb("NA");

                    }
                    if (binding.Infections.isChecked()) {
                        surveySection12B.setQno73bc(binding.Infections.getText().toString());
                    } else {
                        surveySection12B.setQno73bc("NA");

                    }
                    if (binding.SeizuresConvulsions.isChecked()) {
                        surveySection12B.setQno73bd(binding.SeizuresConvulsions.getText().toString());
                    } else {
                        surveySection12B.setQno73bd("NA");

                    }
                } else {
                    surveySection12B.setQno73ba("NA NO");
                    surveySection12B.setQno73bb("NA NO");
                    surveySection12B.setQno73bc("NA NO");
                    surveySection12B.setQno73bd("NA NO");
                }
            } else {
                surveySection12B.setQno73ba("NA NO");
                surveySection12B.setQno73bb("NA NO");
                surveySection12B.setQno73bc("NA NO");
                surveySection12B.setQno73bd("NA NO");
            }


            if (binding.funnyFeeling.getCheckedRadioButtonId() != -1) {
                String typeOfDelivery = Integer.toString(getSelected73C(binding.funnyFeeling.getCheckedRadioButtonId(), binding.never47.getId(), binding.sometime2.getId(), binding.often2.getId()));
                if (typeOfDelivery.equals("96")) {
                    surveySection12B.setQno73c(typeOfDelivery);
                    surveySection12B.setQno73cSpecify(binding.Specify1.getText().toString());
                } else {
                    surveySection12B.setQno73c(typeOfDelivery);
                    surveySection12B.setQno73cSpecify("NA");
                }
            } else {
                surveySection12B.setQno73c("NA");
                surveySection12B.setQno73cSpecify("NA");
            }
            if (binding.feelingWorry.getCheckedRadioButtonId() != -1) {
                String typeOfDelivery = Integer.toString(getSelected73D(binding.feelingWorry.getCheckedRadioButtonId(), binding.never1.getId(), binding.sometime3.getId()));
                surveySection12B.setQno73d(typeOfDelivery);
            } else {
                surveySection12B.setQno73d("NA");
            }


            if (binding.feelingAfraid.getCheckedRadioButtonId() != -1) {
                String typeOfDelivery = Integer.toString(getSelected73D(binding.feelingAfraid.getCheckedRadioButtonId(), binding.never2.getId(), binding.sometime4.getId()));
                surveySection12B.setQno73e(typeOfDelivery);
            } else {
                surveySection12B.setQno73e("NA");
            }

            if (binding.noFunyes.getCheckedRadioButtonId() != -1) {
                String typeOfDelivery = Integer.toString(getSelected73D(binding.noFunyes.getCheckedRadioButtonId(), binding.never3.getId(), binding.sometime5.getId()));
                if (typeOfDelivery.equals("1")) {
                    surveySection12B.setQno73f(typeOfDelivery);
                    surveySection12B.setQno73fSpecify(binding.Specify2.getText().toString());
                } else {
                    surveySection12B.setQno73f(typeOfDelivery);
                    surveySection12B.setQno73fSpecify("NA");
                }
            } else {
                surveySection12B.setQno73f("NA");
                surveySection12B.setQno73fSpecify("NA");
            }

            if (!binding.Specify3.getText().toString().isEmpty()) {
                surveySection12B.setQno73g(Integer.parseInt(binding.Specify3.getText().toString()));
            } else {
                surveySection12B.setQno73g(-1);
            }


            if (binding.noFun.getCheckedRadioButtonId() != -1) {
                String typeOfDelivery = Integer.toString(getSelected73h(binding.noFun.getCheckedRadioButtonId(), binding.neverh.getId(), binding.sometime5h.getId(), binding.sometimeh.getId()));
                surveySection12B.setQno73h(typeOfDelivery);
            } else {
                surveySection12B.setQno73h("NA");
            }

            binding.progressBar.setVisibility(View.VISIBLE);
            apiService.putServeySection12BData(eligibleResponse.houseHoldId, surveySection12B, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);

                    if (Float.parseFloat(ageValue) < 2) {
                        Intent intent = new Intent(activity, ParentResult.class);
                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intent.putExtra(SURVEY_ID, surveyID);
                        intent.putExtra(AGE_ID, ageValue);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                        startActivity(intent);
                    } else {
                        String section6Result = PreferenceConnector.readString(Section12BActivity.this, RCADS6_RESULT, "");
                        String section7aResult = PreferenceConnector.readString(Section12BActivity.this, RCADS7A_RESULT, "");
                        String section7bResult = PreferenceConnector.readString(Section12BActivity.this, RCADS7B_RESULT, "");
                        String section8Result = PreferenceConnector.readString(Section12BActivity.this, RCADS8_RESULT, "");
                        String section9aResult = PreferenceConnector.readString(Section12BActivity.this, RCADS9_1_RESULT, "");
                        String section9hResult = PreferenceConnector.readString(Section12BActivity.this, RCADS9_2_RESULT, "");
                        String section9OResult = PreferenceConnector.readString(Section12BActivity.this, RCADS9_3_RESULT, "");
                        String section10Result = PreferenceConnector.readString(Section12BActivity.this, RCADS10_RESULT, "");
                        String section11Result = PreferenceConnector.readString(Section12BActivity.this, RCADS11_RESULT, "");

                        Log.d(TAG, "section6Result : " + section6Result);
                        Log.d(TAG, "section7aResult : " + section7aResult);
                        Log.d(TAG, "section7bResult : " + section7bResult);
                        Log.d(TAG, "section8Result : " + section8Result);
                        Log.d(TAG, "section9aResult : " + section9aResult);
                        Log.d(TAG, "section9hResult : " + section9hResult);
                        Log.d(TAG, "section9OResult : " + section9OResult);
                        Log.d(TAG, "section10Result : " + section10Result);
                        Log.d(TAG, "section11Result : " + section11Result);

                        Intent intent;
                        if (section6Result.equals("1") || section7aResult.equals("1") || section7bResult.equals("1")
                                || section8Result.equals("1") || section9aResult.equals("1") || section9hResult.equals("1") ||
                                section9OResult.equals("1") || section10Result.equals("1") || section11Result.equals("1")) {
                            intent = new Intent(activity, Section13Activity.class);
                        } else {
                            intent = new Intent(activity, ParentResult.class);
                        }
                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intent.putExtra(SURVEY_ID, surveyID);
                        intent.putExtra(AGE_ID, ageValue);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
//        if (Float.parseFloat(ageValue) <= 17.0f) {
//            if (Float.parseFloat(ageValue) >= 6.0f) {
//                Intent intent = new Intent(activity, Section6Activity.class);
//                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                intent.putExtra(SURVEY_ID, surveyID);
//                intent.putExtra(AGE_ID, ageValue);
//                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                startActivity(intent);
//            } else if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
//                //If the age is greater than 2 & less than 3
//                Intent intent = new Intent(activity, Section7aActivity.class);
//                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                intent.putExtra(SURVEY_ID, surveyID);
//                intent.putExtra(AGE_ID, ageValue);
//                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                startActivity(intent);
//            } else if (Float.parseFloat(ageValue) >= 4.0f) {
//                //If the age is greater than 3 Krishna
//                Intent intent = new Intent(activity, Section7bActivity.class);
//                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                intent.putExtra(SURVEY_ID, surveyID);
//                intent.putExtra(AGE_ID, ageValue);
//                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                startActivity(intent);
//            } else {
//                //IF the Age is 18
//                Intent intent = new Intent(activity, Section8Activity.class);
//                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                intent.putExtra(SURVEY_ID, surveyID);
//                intent.putExtra(AGE_ID, ageValue);
//                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//                startActivity(intent);
//            }
//        }


//        Intent intent = new Intent(activity, Section13Activity.class);
//        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//        intent.putExtra(SURVEY_ID, surveyID);
//        intent.putExtra(AGE_ID, ageValue);
//        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//        startActivity(intent);
        }
    }

    private int getSelected73h(int checkedRadioButtonId, int id, int id1,int id2) {
        if (checkedRadioButtonId == id) {
            return 1;
        } else if (checkedRadioButtonId == id1) {
            return 2;
        }else if (checkedRadioButtonId == id2) {
            return 3;
        }
        return -1;
    }

    private int getSelected73D(int checkedRadioButtonId, int id, int id1) {
        if (checkedRadioButtonId == id) {
            return 1;
        } else if (checkedRadioButtonId == id1) {
            return 0;
        }
        return -1;
    }

    private int getSelected73C(int checkedRadioButtonId, int id, int id1, int id2) {
        if (checkedRadioButtonId == id) {
            return 1;
        } else if (checkedRadioButtonId == id1) {
            return 2;
        } else if (checkedRadioButtonId == id2) {
            return 96;
        }
        return -1;
    }


    private int getCheckedRadioGrpID(int checkedRadioButtonId, int yesId, int noId) {
        if (checkedRadioButtonId == yesId) {
            return 1;
        } else if (checkedRadioButtonId == noId) {
            return 0;
        }
        return -1;
    }

    private int getSelected73A(int checkedRadioButtonId, int id, int id1, int id2) {
        if (checkedRadioButtonId == id) {
            return 1;
        } else if (checkedRadioButtonId == id1) {
            return 2;
        } else if (checkedRadioButtonId == id2) {
            return 3;
        }
        return -1;
    }

    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section13Activity.class));

    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section12BActivity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }
}