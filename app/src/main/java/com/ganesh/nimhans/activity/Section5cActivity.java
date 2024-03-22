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
import com.ganesh.nimhans.databinding.ActivitySection5cBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
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
    private ServeySection5Request serveySection5Request;
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
        serveySection5Request = (ServeySection5Request) getIntent().getSerializableExtra(SURVEY_SECTION5);
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
            switch (checkedId) {
                case R.id.yes266c:
                    binding.cannabisProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.cannabisProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167c.clearCheck();
                    binding.tobaccoProduct168c.clearCheck();
                    binding.tobaccoProduct169c.clearCheck();
                    binding.tobaccoProduct170c.clearCheck();
                    binding.tobaccoProduct171c.clearCheck();
                    binding.tobaccoProduct172c.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167c.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedcannabisProduct);
            switch (checkedId) {
                case R.id.never67c:
                    binding.tobaccoQues68c.setVisibility(View.GONE);
                    binding.tobaccoQues68cRb.setVisibility(View.GONE);
                    binding.tobaccoQues69c.setVisibility(View.GONE);
                    binding.tobaccoQues69cRb.setVisibility(View.GONE);
                    binding.tobaccoQues70c.setVisibility(View.GONE);
                    binding.tobaccoQues70cRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68c.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68cRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69c.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69cRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70c.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70cRb.setVisibility(View.VISIBLE);
                    break;
            }
        });

    }

    public void onClickNextSection(View v) {
        if (binding.cannabis.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
            Util.showToast(activity, "Successfully data saved");
            serveySection5Request.setQno72c(getSelectedItem(binding.tobaccoProduct172c.getCheckedRadioButtonId(), binding.never72b.getId(), binding.once72b.getId(), binding.monthly72b.getId()));
            serveySection5Request.setQno71c(getSelectedItem(binding.tobaccoProduct171c.getCheckedRadioButtonId(), binding.never71b.getId(), binding.yes71b.getId(), binding.yesno71b.getId()));
            serveySection5Request.setQno70c(getSelectedItem70a(binding.tobaccoProduct170c.getCheckedRadioButtonId(), binding.never70b.getId(), binding.once70b.getId(), binding.monthly70b.getId(), binding.weekly70b.getId(), binding.daily70b.getId()));
            serveySection5Request.setQno69c(getSelectedItem69J(binding.tobaccoProduct169c.getCheckedRadioButtonId(), binding.never69c.getId(), binding.once69c.getId(), binding.monthly69c.getId(), binding.weekly69c.getId(), binding.daily69c.getId()));
            serveySection5Request.setQno68c(getSelectedItem68J(binding.tobaccoProduct168c.getCheckedRadioButtonId(), binding.never68c.getId(), binding.once68c.getId(), binding.monthly68c.getId(), binding.weekly68c.getId(), binding.daily68c.getId()));
            serveySection5Request.setQno67c(getSelectedItem67J(binding.alcoholProduct167c.getCheckedRadioButtonId(), binding.never67c.getId(), binding.onceOrTwice67c.getId(), binding.monthly67c.getId(), binding.weekly67c.getId(), binding.daily67c.getId()));
            serveySection5Request.setQno66c(getSelected66J(binding.cannabis.getCheckedRadioButtonId(), binding.no366c.getId(), binding.yes266c.getId()));
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
            intent.putExtra(SURVEY_SECTION5, serveySection5Request);
            startActivity(intent);
        }
    }
    private int getSelected66J(int selectedGrp, int no, int yes) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 3;
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
        return 0;
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
        return 0;
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
        return 0;
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
