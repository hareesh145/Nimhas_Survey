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
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION5;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5Binding;
import com.ganesh.nimhans.databinding.ActivitySection5hBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5hActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5hBinding binding;
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
    ServeySection5Request serveySection5Request;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5hBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        serveySection5Request = (ServeySection5Request) getIntent().getSerializableExtra(SURVEY_SECTION5);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        phoneNo = myGameApp.getUserPhoneNo();
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);
        binding.hallucinogens.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedhallucinogensProduct = selectedValue;
            Log.d("selected hallucinogens Product", "Selected value: " + selectedhallucinogensProduct);
            switch (checkedId) {
                case R.id.yes266h:
                    binding.hallucinogensProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.hallucinogensProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167h.clearCheck();
                    binding.tobaccoProduct168h.clearCheck();
                    binding.tobaccoProduct169h.clearCheck();
                    binding.tobaccoProduct170h.clearCheck();
                    binding.tobaccoProduct171h.clearCheck();
                    binding.tobaccoProduct172h.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167h.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedhallucinogensProduct = selectedValue;
            Log.d("selectedhallucinogensProduct", "Selected value: " + selectedhallucinogensProduct);
            switch (checkedId) {
                case R.id.never67h:
                    binding.tobaccoQues68h.setVisibility(View.GONE);
                    binding.tobaccoQues68hRb.setVisibility(View.GONE);
                    binding.tobaccoQues69h.setVisibility(View.GONE);
                    binding.tobaccoQues69hRb.setVisibility(View.GONE);
                    binding.tobaccoQues70h.setVisibility(View.GONE);
                    binding.tobaccoQues70hRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68h.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68hRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69h.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69hRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70h.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70hRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        if (binding.hallucinogens.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
            Util.showToast(activity, "Successfully data saved");

            serveySection5Request.setQno72h(getSelectedItem(binding.tobaccoProduct172h.getCheckedRadioButtonId(), binding.never72h.getId(), binding.onceTwice72h.getId(), binding.monthly72h.getId()));
            serveySection5Request.setQno71h(getSelectedItem(binding.tobaccoProduct171h.getCheckedRadioButtonId(), binding.never71h.getId(), binding.yes71h.getId(), binding.yesBut71h.getId()));
            serveySection5Request.setQno70h(getSelectedItem(binding.tobaccoProduct170h.getCheckedRadioButtonId(), binding.never70h.getId(), binding.onceTwice70h.getId(), binding.m70h.getId(), binding.w70h.getId(), binding.dOA70h.getId()));
            serveySection5Request.setQno69h(getSelectedItem69H(binding.tobaccoProduct169h.getCheckedRadioButtonId(), binding.never69h.getId(), binding.onceTwice69h.getId(), binding.m69h.getId(), binding.w69h.getId(), binding.dOA69h.getId()));
            serveySection5Request.setQno68h(getSelectedItem68h(binding.tobaccoProduct168h.getCheckedRadioButtonId(), binding.never68h.getId(), binding.onceTwice68h.getId(), binding.m68h.getId(), binding.w68h.getId(), binding.dOA68h.getId()));
            serveySection5Request.setQno67h(getSelectedItem67h(binding.alcoholProduct167h.getCheckedRadioButtonId(), binding.never67h.getId(), binding.onceOrTwice67h.getId(), binding.monthly67h.getId(), binding.weekly67h.getId(), binding.daily67h.getId()));
            serveySection5Request.setQno66h(getSelected66h(binding.hallucinogens.getCheckedRadioButtonId(), binding.no366h.getId(), binding.yes266h.getId()));

            Intent intent = new Intent(activity, Section5iActivity.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            intent.putExtra(RCADS4_RESULT, rCards4Result);
            intent.putExtra("section5_status", true);
            intent.putExtra("ASSIST_screener", 1);
            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
            intent.putExtra(SURVEY_SECTION5, serveySection5Request);
            startActivity(intent);
        }
    }

    private int getSelected66h(int selectedGrp, int no, int yes) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 3;
        }
        return 0;
    }

    private int getSelectedItem67h(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
        if (selectedGrp == never) {
            return 0;
        } else if (selectedGrp == onceTwice) {
            return 2;
        } else if (selectedGrp == monthly) {
            return 3;
        } else if (selectedGrp == weekly) {
            return 4;
        } else if (selectedGrp == daily) {
            return 6;
        }
        return 0;
    }

    private int getSelectedItem68h(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
        if (selectedGrp == never) {
            return 0;
        } else if (selectedGrp == onceTwice) {
            return 3;
        } else if (selectedGrp == monthly) {
            return 4;
        } else if (selectedGrp == weekly) {
            return 5;
        } else if (selectedGrp == daily) {
            return 6;
        }
        return 0;
    }


    private int getSelectedItem69H(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
        if (selectedGrp == never) {
            return 0;
        } else if (selectedGrp == onceTwice) {
            return 4;
        } else if (selectedGrp == monthly) {
            return 5;
        } else if (selectedGrp == weekly) {
            return 6;
        } else if (selectedGrp == daily) {
            return 7;
        }
        return 0;
    }

    private int getSelectedItem(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
        if (selectedGrp == never) {
            return 0;
        } else if (selectedGrp == onceTwice) {
            return 5;
        } else if (selectedGrp == monthly) {
            return 6;
        } else if (selectedGrp == weekly) {
            return 7;
        } else if (selectedGrp == daily) {
            return 8;
        }
        return 0;
    }

    private int getSelectedItem(int selectedGrp, int no, int yes, int yesBut) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 6;
        } else if (selectedGrp == yesBut) {
            return 3;
        }
        return 0;
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
        Intent intent = new Intent(Section5hActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
