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

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5dBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5dActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5dBinding binding;
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
    private ServeySection5Request serveySection5Request;
    private String rCards4Result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5dBinding.inflate(getLayoutInflater());
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
        binding.cocaine.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcocaineProduct = selectedValue;
            Log.d("selectedcocaineProduct", "Selected value: " + selectedcocaineProduct);
            switch (checkedId) {
                case R.id.yes266d:
                    binding.cocaineProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.cocaineProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167d.clearCheck();
                    binding.tobaccoProduct168d.clearCheck();
                    binding.tobaccoProduct169d.clearCheck();
                    binding.tobaccoProduct170d.clearCheck();
                    binding.tobaccoProduct171d.clearCheck();
                    //binding.aTobaccoGrp.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167d.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcocaineProduct = selectedValue;
            Log.d("selectedcocaineProduct", "Selected value: " + selectedcocaineProduct);
            switch (checkedId) {
                case R.id.never67d:
                    binding.tobaccoQues68d.setVisibility(View.GONE);
                    binding.tobaccoQues68dRb.setVisibility(View.GONE);
                    binding.tobaccoQues69d.setVisibility(View.GONE);
                    binding.tobaccoQues69dRb.setVisibility(View.GONE);
                    binding.tobaccoQues70d.setVisibility(View.GONE);
                    binding.tobaccoQues70dRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68d.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68dRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69d.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69dRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70d.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70dRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        serveySection5Request.setQno72d(getSelectedItem(binding.tobaccoProduct172d.getCheckedRadioButtonId(), binding.never72b.getId(), binding.once72b.getId(), binding.monthly72b.getId()));
        serveySection5Request.setQno71d(getSelectedItem(binding.tobaccoProduct171d.getCheckedRadioButtonId(), binding.never71b.getId(), binding.yes71b.getId(), binding.yesno71b.getId()));
        serveySection5Request.setQno70d(getSelectedItem70a(binding.tobaccoProduct170d.getCheckedRadioButtonId(), binding.never70b.getId(), binding.once70b.getId(), binding.monthly70b.getId(), binding.weekly70b.getId(), binding.daily70b.getId()));
        serveySection5Request.setQno69d(getSelectedItem69J(binding.tobaccoProduct169d.getCheckedRadioButtonId(), binding.never69c.getId(), binding.once69c.getId(), binding.monthly69c.getId(), binding.weekly69c.getId(), binding.daily69c.getId()));
        serveySection5Request.setQno68d(getSelectedItem68J(binding.tobaccoProduct168d.getCheckedRadioButtonId(), binding.never68c.getId(), binding.once68c.getId(), binding.monthly68c.getId(), binding.weekly68c.getId(), binding.daily68c.getId()));
        serveySection5Request.setQno67d(getSelectedItem67J(binding.alcoholProduct167d.getCheckedRadioButtonId(), binding.never67d.getId(), binding.onceOrTwice67d.getId(), binding.monthly67d.getId(), binding.weekly67d.getId(), binding.daily67d.getId()));
        serveySection5Request.setQno66d(getSelected66J(binding.cocaine.getCheckedRadioButtonId(), binding.no366d.getId(), binding.yes266d.getId()));
        //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
        Intent intent = new Intent(activity, Section5eActivity.class);
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
    private int getSelected66J(int selectedGrp, int no, int yes) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 3;
        }
        return -1;
    }

    private int getSelectedItem(int selectedGrp, int no, int yes, int yesBut) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 6;
        } else if (selectedGrp == yesBut) {
            return 3;
        }
        return -1;
    }


    private int getSelectedItem70a(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        return -1;
    }


    private int getSelectedItem69J(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        return -1;
    }

    private int getSelectedItem67J(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        return -1;
    }
    private int getSelectedItem68J(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        return -1;
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
        Intent intent = new Intent(Section5dActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
