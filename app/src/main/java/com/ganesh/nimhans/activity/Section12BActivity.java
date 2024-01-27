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

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection12bBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;

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
    private String ageValue;
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
        binding.PARENTSGUARDIAN.setOnCheckedChangeListener((group, checkedId) -> {
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
            String section6Result = PreferenceConnector.readString(this, RCADS6_RESULT, "");
            String section7aResult = PreferenceConnector.readString(this, RCADS7A_RESULT, "");
            String section7bResult = PreferenceConnector.readString(this, RCADS7B_RESULT, "");
            String section8Result = PreferenceConnector.readString(this, RCADS8_RESULT, "");
            String section9aResult = PreferenceConnector.readString(this, RCADS9_1_RESULT, "");
            String section9hResult = PreferenceConnector.readString(this, RCADS9_2_RESULT, "");
            String section9OResult = PreferenceConnector.readString(this, RCADS9_3_RESULT, "");
            String section10Result = PreferenceConnector.readString(this, RCADS10_RESULT, "");
            String section11Result = PreferenceConnector.readString(this, RCADS11_RESULT, "");

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
//        Intent intent = new Intent(activity, Section13Activity.class);
//        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//        intent.putExtra(SURVEY_ID, surveyID);
//        intent.putExtra(AGE_ID, ageValue);
//        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
//        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
//        startActivity(intent);
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