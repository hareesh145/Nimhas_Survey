package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
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
import com.ganesh.nimhans.databinding.ActivitySection6aBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;

public class Section6aActivity extends AppCompatActivity {
    private ActivitySection6aBinding binding;
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
        binding = ActivitySection6aBinding.inflate(getLayoutInflater());
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
        startActivity(new Intent(activity, Section13Activity.class));
    }

    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section13Activity.class));

    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section6aActivity.this, ParentResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }
}