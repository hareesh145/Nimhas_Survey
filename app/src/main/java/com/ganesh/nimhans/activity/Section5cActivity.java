package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_3_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5cBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5cActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5cBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    String selectedtobaccoProduct, selectedAlcoholicProduct, selectedcannabisProduct, selectedcocaineProduct, selectedamphetamineProduct, selectedinhalantsProduct, selectedSedativesProduct, selectedhallucinogensProduct, selecteopioidsProduct, selecteothersProduct;
    private HashMap<String, Integer> questionOptionsMap = new HashMap<>();
    private String ageValue;

    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    private String rCards4Result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5cBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        phoneNo = myGameApp.getUserPhoneNo();
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);

        binding.cannabis.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcannabisProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            if (checkedId == R.id.yes266c) {
                binding.cannabisProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.cannabisProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167c.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedcannabisProduct);
            if (checkedId == R.id.never67c) {
                binding.tobaccoQues68c.setVisibility(View.GONE);
                binding.tobaccoQues68cRb.setVisibility(View.GONE);
                binding.tobaccoQues69c.setVisibility(View.GONE);
                binding.tobaccoQues69cRb.setVisibility(View.GONE);
                binding.tobaccoQues70c.setVisibility(View.GONE);
                binding.tobaccoQues70cRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68c.setVisibility(View.VISIBLE);
                binding.tobaccoQues68cRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69c.setVisibility(View.VISIBLE);
                binding.tobaccoQues69cRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70c.setVisibility(View.VISIBLE);
                binding.tobaccoQues70cRb.setVisibility(View.VISIBLE);
            }
        });

    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
        Intent intent = new Intent(activity, Section5dActivity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(RCADS4_RESULT, rCards4Result);
        intent.putExtra("section5_status", true);
        intent.putExtra("ASSIST_screener", 1);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

    public void updateQuestionOption(String question, int option) {
        Integer integer = questionOptionsMap.get(question);
        if (integer != null) {
            questionOptionsMap.replace(question, option);
        } else {
            questionOptionsMap.put(question, option);
        }
    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, DashboardActivity.class));
        finish();

    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section5cActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}