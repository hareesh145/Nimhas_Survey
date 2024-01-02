package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
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
        binding.question212.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.other) {
                    binding.othersSpecify212.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify212.setVisibility(View.GONE);
                }
            }
        });
        binding.question217.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dont_know) {
                    binding.km.setVisibility(View.VISIBLE);
                } else {
                    binding.km.setVisibility(View.VISIBLE);
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
        binding.question210.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.did_not_seek_care) {
                    binding.othersSpecify210.setVisibility(View.GONE);
                } else {
                    binding.othersSpecify210.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.modesOfTravel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.others_travel) {
                    binding.othersSpecify218.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify218.setVisibility(View.GONE);
                }
            }
        });

        binding.othersSpecify210.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.didNotSeekCare.setVisibility(View.VISIBLE);
                } else {
                    binding.didNotSeekCare.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.othersSpecify212.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.mentalHealthCare.setVisibility(View.VISIBLE);
                } else {
                    binding.mentalHealthCare.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.others_218:
                if (checked){
                    binding.othersSpecify218.setVisibility(View.VISIBLE);
                }
                else {
                    binding.othersSpecify218.setVisibility(View.GONE);
                }
                // Do your coding

            // Perform your logic
        }

    }
    public void onClickNextSection(View v) {

        if (Float.parseFloat(ageValue) < 2.0f) {
            Intent intent = new Intent(Section12Activity.this, ParentResult.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
            startActivity(intent);
        }else {
            //if the result is +ve we need to navigat
            Util.showToast(activity, "Successfully data saved");
            Intent intent = new Intent(activity, Section13Activity.class);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            startActivity(intent);
        }

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
