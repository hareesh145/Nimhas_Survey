package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_2_RESULT;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5iBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5iActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5iBinding binding;
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
    private ServeySection5Request serveySection5Request;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5iBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        serveySection5Request = (ServeySection5Request) getIntent().getSerializableExtra(SURVEY_SECTION5);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        phoneNo = myGameApp.getUserPhoneNo();
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);
        binding.opioids.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteopioidsProduct = selectedValue;
            Log.d("selecteopioidsProduct", "Selected value: " + selecteopioidsProduct);
            switch (checkedId) {
                case R.id.yes266i:
                    binding.opioidsProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.opioidsProductsQueAll.setVisibility(View.GONE);
                    binding.alcoholProduct167i.clearCheck();
                    binding.tobaccoProduct168i.clearCheck();
                    binding.tobaccoProduct169i.clearCheck();
                    binding.tobaccoProduct170i.clearCheck();
                    binding.tobaccoProduct171i.clearCheck();
                    binding.tobaccoProduct172i.clearCheck();
                    break;
            }
        });
        binding.alcoholProduct167i.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteopioidsProduct = selectedValue;
            Log.d("selecteopioidsProduct", "Selected value: " + selecteopioidsProduct);
            switch (checkedId) {
                case R.id.never67i:
                    binding.tobaccoQues68i.setVisibility(View.GONE);
                    binding.tobaccoQues68iRb.setVisibility(View.GONE);
                    binding.tobaccoQues69i.setVisibility(View.GONE);
                    binding.tobaccoQues69iRb.setVisibility(View.GONE);
                    binding.tobaccoQues70i.setVisibility(View.GONE);
                    binding.tobaccoQues70iRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68i.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68iRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69i.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69iRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70i.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70iRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        if (binding.opioids.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
            Util.showToast(activity, "Successfully data saved");

            serveySection5Request.setQno72i(getSelectedItem(binding.tobaccoProduct172i.getCheckedRadioButtonId(), binding.never72i.getId(), binding.oneTwice72i.getId(), binding.monthly72i.getId()));
            serveySection5Request.setQno71i(getSelectedItem(binding.tobaccoProduct171i.getCheckedRadioButtonId(), binding.never71i.getId(), binding.yes71i.getId(), binding.yesBut71i.getId()));
            serveySection5Request.setQno70i(getSelectedItem(binding.tobaccoProduct170i.getCheckedRadioButtonId(), binding.never70i.getId(), binding.onceTwice70i.getId(), binding.m70i.getId(), binding.w70i.getId(), binding.dOA70i.getId()));
            serveySection5Request.setQno69i(getSelectedItem69I(binding.tobaccoProduct169i.getCheckedRadioButtonId(), binding.never69i.getId(), binding.onceTwice69i.getId(), binding.m69i.getId(), binding.w69i.getId(), binding.dOA69i.getId()));
            serveySection5Request.setQno68i(getSelectedItem68J(binding.tobaccoProduct168i.getCheckedRadioButtonId(), binding.never68i.getId(), binding.onceT68i.getId(), binding.m68i.getId(), binding.w68i.getId(), binding.dOA68i.getId()));
            serveySection5Request.setQno67i(getSelectedItem67i(binding.alcoholProduct167i.getCheckedRadioButtonId(), binding.never67i.getId(), binding.onceOrTwice67i.getId(), binding.monthly67i.getId(), binding.weekly67i.getId(), binding.daily67i.getId()));
            serveySection5Request.setQno66i(getSelected66i(binding.opioids.getCheckedRadioButtonId(), binding.no366i.getId(), binding.yes266i.getId()));

            Intent intent = new Intent(activity, Section5jActivity.class);
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

    private int getSelected66i(int selectedGrp, int no, int yes) {
        if (selectedGrp == no) {
            return 0;
        } else if (selectedGrp == yes) {
            return 3;
        }
        return 0;
    }

    private int getSelectedItem67i(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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

    private int getSelectedItem69I(int selectedGrp, int never, int onceTwice, int monthly, int weekly, int daily) {
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
        Intent intent = new Intent(Section5iActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
