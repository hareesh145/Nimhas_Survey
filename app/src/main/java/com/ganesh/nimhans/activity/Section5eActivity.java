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
import com.ganesh.nimhans.databinding.ActivitySection5eBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5eActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5eBinding binding;
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
        binding = ActivitySection5eBinding.inflate(getLayoutInflater());
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
        binding.amphetamine.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedamphetamineProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            switch (checkedId) {
                case R.id.yes266e:
                    binding.amphetamineProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.amphetamineProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167e.clearCheck();
                    binding.tobaccoProduct168e.clearCheck();
                    binding.tobaccoProduct169e.clearCheck();
                    binding.tobaccoProduct170e.clearCheck();
                    binding.tobaccoProduct171e.clearCheck();
                    binding.tobaccoProduct172e.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167e.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedamphetamineProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            switch (checkedId) {
                case R.id.never67e:
                    binding.tobaccoQues68e.setVisibility(View.GONE);
                    binding.tobaccoQues68eRb.setVisibility(View.GONE);
                    binding.tobaccoQues69e.setVisibility(View.GONE);
                    binding.tobaccoQues69eRb.setVisibility(View.GONE);
                    binding.tobaccoQues70e.setVisibility(View.GONE);
                    binding.tobaccoQues70eRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68e.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68eRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69e.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69eRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70e.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70eRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        if (binding.amphetamine.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
            Util.showToast(activity, "Successfully data saved");

            serveySection5Request.setQno72e(getSelectedItem(binding.tobaccoProduct172e.getCheckedRadioButtonId(), binding.never72e.getId(), binding.onceTwice72e.getId(), binding.monthly72e.getId()));
            serveySection5Request.setQno71e(getSelectedItem(binding.tobaccoProduct171e.getCheckedRadioButtonId(), binding.never71e.getId(), binding.yes71e.getId(), binding.yesBut71e.getId()));
            serveySection5Request.setQno70e(getSelectedItem(binding.tobaccoProduct170e.getCheckedRadioButtonId(), binding.never70e.getId(), binding.onceTwice70e.getId(), binding.m70e.getId(), binding.w70e.getId(), binding.dOA70e.getId()));
            serveySection5Request.setQno69e(getSelectedItem69G(binding.tobaccoProduct169e.getCheckedRadioButtonId(), binding.never69e.getId(), binding.onceOrTwice69e.getId(), binding.m69e.getId(), binding.w69e.getId(), binding.dOA69e.getId()));
            serveySection5Request.setQno68e(getSelectedItem68G(binding.tobaccoProduct168e.getCheckedRadioButtonId(), binding.never68e.getId(), binding.onceTwice68e.getId(), binding.m68e.getId(), binding.w68e.getId(), binding.dOA68e.getId()));
            serveySection5Request.setQno67e(getSelectedItem67G(binding.alcoholProduct167e.getCheckedRadioButtonId(), binding.never67e.getId(), binding.onceOrTwice67e.getId(), binding.monthly67e.getId(), binding.weekly67e.getId(), binding.daily67e.getId()));
            serveySection5Request.setQno66e(getSelected66g(binding.amphetamine.getCheckedRadioButtonId(), binding.no366e.getId(), binding.yes266e.getId()));

            Intent intent = new Intent(activity, Section5fActivity.class);
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
            //  }
       /* Intent intent = new Intent(activity, ChildrenResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(RCADS4_RESULT, rCards4Result);
        startActivity(intent);
*/
        }
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
        Intent intent = new Intent(Section5eActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
