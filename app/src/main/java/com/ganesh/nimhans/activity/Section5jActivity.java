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
import com.ganesh.nimhans.databinding.ActivitySection5jBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5jActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5jBinding binding;
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
        binding = ActivitySection5jBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection5Request = (ServeySection5Request) getIntent().getSerializableExtra(SURVEY_SECTION5);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
        phoneNo = myGameApp.getUserPhoneNo();
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);
        binding.others.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteothersProduct = selectedValue;
            Log.d("selecteothersProduct", "Selected value: " + selecteothersProduct);
            switch (checkedId) {
                case R.id.yes266j:
                    binding.othersProductsQueAll.setVisibility(View.VISIBLE);
                    binding.Specify1.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.othersProductsQueAll.setVisibility(View.GONE);
                    binding.Specify1.setVisibility(View.GONE);
                    binding.alcoholProduct167j.clearCheck();
                    binding.tobaccoProduct168j.clearCheck();
                    binding.tobaccoProduct169j.clearCheck();
                    binding.tobaccoProduct170j.clearCheck();
                    binding.tobaccoProduct171j.clearCheck();
                    binding.tobaccoProduct172j.clearCheck();
                    binding.Specify1.setText("");
                    break;
            }
        });
        binding.alcoholProduct167j.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteothersProduct = selectedValue;
            Log.d("selecteothersProduct", "Selected value: " + selecteothersProduct);
            switch (checkedId) {
                case R.id.never67j:
                    binding.tobaccoQues68j.setVisibility(View.GONE);
                    binding.tobaccoQues68jRb.setVisibility(View.GONE);
                    binding.tobaccoQues69j.setVisibility(View.GONE);
                    binding.tobaccoQues69jRb.setVisibility(View.GONE);
                    binding.tobaccoQues70j.setVisibility(View.GONE);
                    binding.tobaccoQues70jRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68j.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68jRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69j.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69jRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70j.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70jRb.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.drugInjectionGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) return;
                RadioButton radioButton = findViewById(checkedId);
                switch (radioButton.getId()) {
                    case R.id.no18:
                        break;
                    case R.id.yes32:
                        break;
                    case R.id.yes33:
                        break;
                }

            }
        });
        binding.Specify1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!s.toString().isEmpty()) {
                        binding.pastThreeMonths.setText("In the past three months, how often have you used " + s + "");
                        binding.pastThree68j.setText("During the past three months, how often have you had a strong desire or urge to use " + s + "");
                        binding.financialProblems68j.setText("During the past three months, how often has your use of " + s + " led to health, social, legal or financial problems?");
                        binding.pastThree70j.setText("During the past three months, how often have you failed to do what was normally expected of you because of your use of " + s + "");
                        binding.pastThree71j.setText("Has a friend or relative or anyone else ever expressed concern about your use of " + s + "");
                        binding.pastThree72j.setText("Have you ever tried and failed to control, cut down or stop using " + s + "?");

                    } else {
                        binding.pastThreeMonths.setText("In the past three months, how often have you used");
                        binding.pastThree68j.setText("During the past three months, how often have you had a strong desire or urge to use ");
                        binding.financialProblems68j.setText("During the past three months, how often has your use of led to health, social, legal or financial problems?");
                        binding.pastThree70j.setText("During the past three months, how often have you failed to do what was normally expected of you because of your use of");
                        binding.pastThree71j.setText("Has a friend or relative or anyone else ever expressed concern about your use of");
                        binding.pastThree72j.setText("Have you ever tried and failed to control, cut down or stop using ?");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClickNextSection(View v) {
        if (binding.others.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        }else {
            Util.showToast(activity, "Successfully data saved");
            serveySection5Request.setQno72j(getSelectedItem(binding.tobaccoProduct172j.getCheckedRadioButtonId(), binding.never72j.getId(), binding.oneTwice72j.getId(), binding.monthly72j.getId()));
            serveySection5Request.setQno71j(getSelectedItem(binding.tobaccoProduct171j.getCheckedRadioButtonId(), binding.noNever71j.getId(), binding.yesIn71j.getId(), binding.yesBut71j.getId()));
            serveySection5Request.setQno70j(getSelectedItem(binding.tobaccoProduct170j.getCheckedRadioButtonId(), binding.never70j.getId(), binding.onceT70j.getId(), binding.monthly70j.getId(), binding.weekly70j.getId(), binding.dailyAlmost70j.getId()));
            serveySection5Request.setQno69j(getSelectedItem69J(binding.tobaccoProduct169j.getCheckedRadioButtonId(), binding.never69j.getId(), binding.onceT69j.getId(), binding.m69j.getId(), binding.w69j.getId(), binding.dOA69j.getId()));
            serveySection5Request.setQno68j(getSelectedItem68J(binding.tobaccoProduct168j.getCheckedRadioButtonId(), binding.n68j.getId(), binding.onceT68j.getId(), binding.m68j.getId(), binding.w68j.getId(), binding.doa68j.getId()));

            serveySection5Request.setQno67j(getSelectedItem67J(binding.alcoholProduct167j.getCheckedRadioButtonId(), binding.never67j.getId(), binding.onceOrTwice67j.getId(), binding.monthly67j.getId(), binding.weekly67j.getId(), binding.daily67j.getId()));
            serveySection5Request.setQno66j(getSelected66J(binding.others.getCheckedRadioButtonId(), binding.no366j.getId(), binding.yes266j.getId()));
            if (binding.others.getCheckedRadioButtonId() == binding.yes266j.getId()) {
                serveySection5Request.qno66jSpecify = binding.Specify1.getText().toString();
            } else {
                serveySection5Request.qno66jSpecify = "";
            }
            //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
            Intent intent = new Intent(activity, Section5FinalActivity.class);
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
        Intent intent = new Intent(Section5jActivity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
