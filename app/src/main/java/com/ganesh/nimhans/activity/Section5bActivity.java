package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5bBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5bBinding binding;
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
        binding = ActivitySection5bBinding.inflate(getLayoutInflater());
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
        binding.alcoholic.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedAlcoholicProduct);
            switch (checkedId) {
                case R.id.yes266b:
                    binding.alcoholProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.alcoholProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167b.clearCheck();
                    binding.tobaccoProduct168b.clearCheck();
                    binding.tobaccoProduct169b.clearCheck();
                    binding.tobaccoProduct170b.clearCheck();
                    binding.tobaccoProduct171b.clearCheck();
                    binding.tobaccoProduct172b.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167b.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedAlcoholicProduct);
            switch (checkedId) {
                case R.id.never67b:
                    binding.tobaccoQues68b.setVisibility(View.GONE);
                    binding.tobaccoQues68bRb.setVisibility(View.GONE);
                    binding.tobaccoQues69b.setVisibility(View.GONE);
                    binding.tobaccoQues69bRb.setVisibility(View.GONE);
                    binding.tobaccoQues70b.setVisibility(View.GONE);
                    binding.tobaccoQues70bRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68b.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68bRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69b.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69bRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70b.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70bRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        serveySection5Request.setQno72b(getSelectedItem(binding.tobaccoProduct172b.getCheckedRadioButtonId(), binding.never72b.getId(), binding.once72b.getId(), binding.monthly72b.getId()));
        serveySection5Request.setQno71b(getSelectedItem(binding.tobaccoProduct171b.getCheckedRadioButtonId(), binding.never71b.getId(), binding.yes71b.getId(), binding.yesno71b.getId()));
        serveySection5Request.setQno70b(getSelectedItem70a(binding.tobaccoProduct170b.getCheckedRadioButtonId(), binding.never70b.getId(), binding.once70b.getId(), binding.monthly70b.getId(), binding.weekly70b.getId(), binding.daily70b.getId()));
        serveySection5Request.setQno69b(getSelectedItem69J(binding.tobaccoProduct169b.getCheckedRadioButtonId(), binding.never69b.getId(), binding.once69b.getId(), binding.monthly69b.getId(), binding.weekly69b.getId(), binding.daily69b.getId()));
        serveySection5Request.setQno68b(getSelectedItem68J(binding.tobaccoProduct168b.getCheckedRadioButtonId(), binding.never68b.getId(), binding.once68b.getId(), binding.monthly68b.getId(), binding.weekly68b.getId(), binding.daily68b.getId()));
        serveySection5Request.setQno67b(getSelectedItem67J(binding.alcoholProduct167b.getCheckedRadioButtonId(), binding.never67b.getId(), binding.onceOrTwice67b.getId(), binding.monthly67b.getId(), binding.weekly67b.getId(), binding.daily67b.getId()));
        serveySection5Request.setQno66b(getSelected66J(binding.alcoholic.getCheckedRadioButtonId(), binding.no366b.getId(), binding.yes266b.getId()));
        //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
        Intent intent = new Intent(activity, Section5cActivity.class);
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
        Intent intent = new Intent(Section5bActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
