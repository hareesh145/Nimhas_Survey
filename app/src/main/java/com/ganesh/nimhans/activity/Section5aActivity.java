package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
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
import com.ganesh.nimhans.databinding.ActivitySection5aBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5aActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5aBinding binding;
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
        binding = ActivitySection5aBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(Section5aActivity.this);
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
        //66A
        binding.tobaccoProduct.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedtobaccoProduct = selectedValue;
            Log.d("selectedCaste", "Selected value: " + selectedtobaccoProduct);
            switch (checkedId) {
                case R.id.yes2:

                    binding.tobaccoProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.tobaccoProductsQueAll.setVisibility(View.GONE);
                    binding.tobaccoProduct167a.clearCheck();
                    binding.tobaccoProduct168a.clearCheck();
                    binding.tobaccoProduct169a.clearCheck();
                    binding.tobaccoProduct170a.clearCheck();
                    binding.tobaccoProduct171a.clearCheck();
                    binding.tobaccoProduct172a.clearCheck();
                    break;
            }
        });

        binding.tobaccoProduct167a.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedtobaccoProduct = selectedValue;
            Log.d("selectedtobaccoProduct", "Selected value: " + selectedtobaccoProduct);
            switch (checkedId) {
                case R.id.never67a:
                    binding.tobaccoQues68a.setVisibility(View.GONE);
                    binding.tobaccoQues68aRb.setVisibility(View.GONE);
                    binding.tobaccoQues69a.setVisibility(View.GONE);
                    binding.tobaccoQues69aRb.setVisibility(View.GONE);
                    binding.tobaccoQues70a.setVisibility(View.GONE);
                    binding.tobaccoQues70aRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68a.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68aRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69a.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69aRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70a.setVisibility(View.GONE);
                    binding.tobaccoQues70aRb.setVisibility(View.GONE);
                    break;
            }
        });

    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        serveySection5Request= new ServeySection5Request();
        serveySection5Request.setQno72a(getSelectedItem(binding.tobaccoProduct172a.getCheckedRadioButtonId(), binding.never72a.getId(), binding.once72a.getId(), binding.monthly72a.getId()));
        serveySection5Request.setQno71a(getSelectedItem(binding.tobaccoProduct171a.getCheckedRadioButtonId(), binding.never71a.getId(), binding.yes71a.getId(), binding.yesNo71a.getId()));
        serveySection5Request.setQno70a(getSelectedItem70a(binding.tobaccoProduct170a.getCheckedRadioButtonId(), binding.never70a.getId(), binding.once70a.getId(), binding.monthly70a.getId(), binding.weekly70a.getId(), binding.daily70a.getId()));
        serveySection5Request.setQno69a(getSelectedItem69J(binding.tobaccoProduct169a.getCheckedRadioButtonId(), binding.never69a.getId(), binding.once69a.getId(), binding.monthly69a.getId(), binding.weekly69a.getId(), binding.daily69a.getId()));
        serveySection5Request.setQno68a(getSelectedItem68J(binding.tobaccoProduct168a.getCheckedRadioButtonId(), binding.never68a.getId(), binding.onceTwice68a.getId(), binding.monthly68a.getId(), binding.weekly68a.getId(), binding.daily68a.getId()));
        serveySection5Request.setQno67a(getSelectedItem67J(binding.tobaccoProduct167a.getCheckedRadioButtonId(), binding.never67a.getId(), binding.onceOrTwice67a.getId(), binding.monthly67a.getId(), binding.weekly67a.getId(), binding.daily67a.getId()));
        serveySection5Request.setQno66a(getSelected66J(binding.tobaccoProduct.getCheckedRadioButtonId(), binding.no3.getId(), binding.yes2.getId()));
        //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
        Intent intent = new Intent(activity, Section5bActivity.class);
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
            return 0;
        } else if (selectedGrp == monthly) {
            return 0;
        } else if (selectedGrp == weekly) {
            return 0;
        } else if (selectedGrp == daily) {
            return 0;
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
        Intent intent = new Intent(Section5aActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
