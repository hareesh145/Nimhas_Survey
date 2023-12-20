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
import com.ganesh.nimhans.databinding.ActivitySection12Binding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

public class Section12Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection12Binding binding;
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
        binding = ActivitySection12Binding.inflate(getLayoutInflater());
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

        binding.illnessCare207.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify207.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify207.setVisibility(View.GONE);
            }
        });

        binding.illnessCare208.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify208.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify208.setVisibility(View.GONE);
            }
        });

        binding.illnessCare211.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify211.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify211.setVisibility(View.GONE);
            }
        });


        binding.section12RespondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        binding.section12Respondent.setVisibility(View.GONE);
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.section12Respondent.setVisibility(View.GONE);
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.section12Respondent.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        binding.options209.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_209) {
                    binding.options210213.setVisibility(View.VISIBLE);
                } else {
                    binding.options210213.setVisibility(View.GONE);
                }
            }
        });

        binding.heardVisitedOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.nor_visited) {
                    binding.options216219.setVisibility(View.GONE);
                } else {
                    binding.options216219.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section13Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section11Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section12Activity.this, ResultPage.class);
        startActivity(intent);
    }
}
