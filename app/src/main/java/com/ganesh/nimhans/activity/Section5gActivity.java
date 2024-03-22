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
import com.ganesh.nimhans.databinding.ActivitySection5Binding;
import com.ganesh.nimhans.databinding.ActivitySection5gBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5gActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5gBinding binding;
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
        binding = ActivitySection5gBinding.inflate(getLayoutInflater());
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
        binding.sedatives.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSedativesProduct = selectedValue;
            Log.d("selectedSedativesProduct", "Selected value: " + selectedSedativesProduct);
            switch (checkedId) {
                case R.id.yes266g:
                    binding.sedativesProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.sedativesProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167g.clearCheck();
                    binding.tobaccoProduct168g.clearCheck();
                    binding.tobaccoProduct169g.clearCheck();
                    binding.tobaccoProduct170g.clearCheck();
                    binding.tobaccoProduct171g.clearCheck();
                    binding.tobaccoProduct172g.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167g.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSedativesProduct = selectedValue;
            Log.d("selectedSedativesProduct", "Selected value: " + selectedSedativesProduct);
            switch (checkedId) {
                case R.id.never67g:
                    binding.tobaccoQues68g.setVisibility(View.GONE);
                    binding.tobaccoQues68gRb.setVisibility(View.GONE);
                    binding.tobaccoQues69G.setVisibility(View.GONE);
                    binding.tobaccoQues69gRb.setVisibility(View.GONE);
                    binding.tobaccoQues70g.setVisibility(View.GONE);
                    binding.tobaccoQues70gRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68g.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68gRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69G.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69gRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70g.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70gRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");

        serveySection5Request.setQno72g(getSelectedItem(binding.tobaccoProduct172g.getCheckedRadioButtonId(), binding.never72g.getId(), binding.onceTwice72g.getId(), binding.monthly72g.getId()));
        serveySection5Request.setQno71g(getSelectedItem(binding.tobaccoProduct171g.getCheckedRadioButtonId(), binding.never71g.getId(), binding.yes71g.getId(), binding.yesBut71g.getId()));
        serveySection5Request.setQno70g(getSelectedItem(binding.tobaccoProduct170g.getCheckedRadioButtonId(), binding.never70g.getId(), binding.onceTwice70g.getId(), binding.m70g.getId(), binding.w70g.getId(), binding.dOA70g.getId()));
        serveySection5Request.setQno69g(getSelectedItem69G(binding.tobaccoProduct169g.getCheckedRadioButtonId(), binding.never69g.getId(), binding.onceTwice69g.getId(), binding.m69g.getId(), binding.w69g.getId(), binding.dOA69g.getId()));
        serveySection5Request.setQno68g(getSelectedItem68G(binding.tobaccoProduct168g.getCheckedRadioButtonId(), binding.never68g.getId(), binding.onceTwice68g.getId(), binding.m68g.getId(), binding.w68g.getId(), binding.dOA68g.getId()));
        serveySection5Request.setQno67g(getSelectedItem67G(binding.alcoholProduct167g.getCheckedRadioButtonId(), binding.never67g.getId(), binding.onceOrTwice67g.getId(), binding.monthly67g.getId(), binding.weekly67g.getId(), binding.daily67g.getId()));
        serveySection5Request.setQno66g(getSelected66g(binding.sedatives.getCheckedRadioButtonId(), binding.no366g.getId(), binding.yes266g.getId()));

        Intent intent = new Intent(activity, Section5hActivity.class);
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

    private int getSelected66g(int selectedGrp, int no, int yes) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 3;
        }
        return 0;
    }

    private int getSelectedItem67G(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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

    private int getSelectedItem68G(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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


    private int getSelectedItem69G(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        Intent intent = new Intent(Section5gActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
