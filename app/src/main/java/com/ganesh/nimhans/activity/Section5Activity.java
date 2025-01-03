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
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.HashMap;

public class Section5Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5Binding binding;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
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
            if (checkedId == R.id.yes2) {
                binding.tobaccoProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.tobaccoProductsQueAll.setVisibility(View.GONE);
                binding.tobaccoProduct167a.clearCheck();
                binding.tobaccoProduct168a.clearCheck();
                binding.tobaccoProduct169a.clearCheck();
                binding.tobaccoProduct171a.clearCheck();
                binding.tobaccoProduct172a.clearCheck();
            }
        });

        binding.tobaccoProduct167a.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1) return;
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedtobaccoProduct = selectedValue;
            Log.d("selectedtobaccoProduct", "Selected value: " + selectedtobaccoProduct);
            if (checkedId == R.id.never67a) {
                binding.tobaccoQues68a.setVisibility(View.GONE);
                binding.tobaccoQues68aRb.setVisibility(View.GONE);
                binding.tobaccoQues69a.setVisibility(View.GONE);
                binding.tobaccoQues69aRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68a.setVisibility(View.VISIBLE);
                binding.tobaccoQues68aRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69a.setVisibility(View.VISIBLE);
                binding.tobaccoQues69aRb.setVisibility(View.VISIBLE);
            }
            binding.tobaccoQues70a.setVisibility(View.GONE);
            binding.tobaccoQues70aRb.setVisibility(View.GONE);
        });
        binding.alcoholic.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedAlcoholicProduct);
            if (checkedId == R.id.yes266b) {
                binding.alcoholProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.alcoholProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167b.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedAlcoholicProduct);
            if (checkedId == R.id.never67b) {
                binding.tobaccoQues68b.setVisibility(View.GONE);
                binding.tobaccoQues68bRb.setVisibility(View.GONE);
                binding.tobaccoQues69b.setVisibility(View.GONE);
                binding.tobaccoQues69bRb.setVisibility(View.GONE);
                binding.tobaccoQues70b.setVisibility(View.GONE);
                binding.tobaccoQues70bRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68b.setVisibility(View.VISIBLE);
                binding.tobaccoQues68bRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69b.setVisibility(View.VISIBLE);
                binding.tobaccoQues69bRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70b.setVisibility(View.VISIBLE);
                binding.tobaccoQues70bRb.setVisibility(View.VISIBLE);
            }
        });
        binding.cannabis.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcannabisProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            if (checkedId == R.id.yes266c) {
                binding.cannabisProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.cannabisProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167c.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAlcoholicProduct = selectedValue;
            Log.d("selectedAlcoholicProduct", "Selected value: " + selectedcannabisProduct);
            if (checkedId == R.id.never67c) {
                binding.tobaccoQues68c.setVisibility(View.GONE);
                binding.tobaccoQues68cRb.setVisibility(View.GONE);
                binding.tobaccoQues69c.setVisibility(View.GONE);
                binding.tobaccoQues69cRb.setVisibility(View.GONE);
                binding.tobaccoQues70c.setVisibility(View.GONE);
                binding.tobaccoQues70cRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68c.setVisibility(View.VISIBLE);
                binding.tobaccoQues68cRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69c.setVisibility(View.VISIBLE);
                binding.tobaccoQues69cRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70c.setVisibility(View.VISIBLE);
                binding.tobaccoQues70cRb.setVisibility(View.VISIBLE);
            }
        });
        binding.cocaine.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcocaineProduct = selectedValue;
            Log.d("selectedcocaineProduct", "Selected value: " + selectedcocaineProduct);
            if (checkedId == R.id.yes266d) {
                binding.cocaineProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.cocaineProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167d.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedcocaineProduct = selectedValue;
            Log.d("selectedcocaineProduct", "Selected value: " + selectedcocaineProduct);
            if (checkedId == R.id.never67d) {
                binding.tobaccoQues68d.setVisibility(View.GONE);
                binding.tobaccoQues68dRb.setVisibility(View.GONE);
                binding.tobaccoQues69d.setVisibility(View.GONE);
                binding.tobaccoQues69dRb.setVisibility(View.GONE);
                binding.tobaccoQues70d.setVisibility(View.GONE);
                binding.tobaccoQues70dRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68d.setVisibility(View.VISIBLE);
                binding.tobaccoQues68dRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69d.setVisibility(View.VISIBLE);
                binding.tobaccoQues69dRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70d.setVisibility(View.VISIBLE);
                binding.tobaccoQues70dRb.setVisibility(View.VISIBLE);
            }
        });
        binding.amphetamine.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedamphetamineProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            if (checkedId == R.id.yes266e) {
                binding.amphetamineProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.amphetamineProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167e.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedamphetamineProduct = selectedValue;
            Log.d("selectedamphetamineProduct", "Selected value: " + selectedamphetamineProduct);
            if (checkedId == R.id.never67e) {
                binding.tobaccoQues68e.setVisibility(View.GONE);
                binding.tobaccoQues68eRb.setVisibility(View.GONE);
                binding.tobaccoQues69e.setVisibility(View.GONE);
                binding.tobaccoQues69eRb.setVisibility(View.GONE);
                binding.tobaccoQues70e.setVisibility(View.GONE);
                binding.tobaccoQues70eRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68e.setVisibility(View.VISIBLE);
                binding.tobaccoQues68eRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69e.setVisibility(View.VISIBLE);
                binding.tobaccoQues69eRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70e.setVisibility(View.VISIBLE);
                binding.tobaccoQues70eRb.setVisibility(View.VISIBLE);
            }
        });
        binding.inhalants.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedinhalantsProduct = selectedValue;
            Log.d("selectedinhalantsProduct", "Selected value: " + selectedinhalantsProduct);
            if (checkedId == R.id.yes266f) {
                binding.inhalantsProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.inhalantsProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167f.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedinhalantsProduct = selectedValue;
            Log.d("selectedinhalantsProduct", "Selected value: " + selectedinhalantsProduct);
            if (checkedId == R.id.never67f) {
                binding.tobaccoQues68f.setVisibility(View.GONE);
                binding.tobaccoQues68fRb.setVisibility(View.GONE);
                binding.tobaccoQues69f.setVisibility(View.GONE);
                binding.tobaccoQues69fRb.setVisibility(View.GONE);
                binding.tobaccoQues70f.setVisibility(View.GONE);
                binding.tobaccoQues70fRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68f.setVisibility(View.VISIBLE);
                binding.tobaccoQues68fRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69f.setVisibility(View.VISIBLE);
                binding.tobaccoQues69fRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70f.setVisibility(View.VISIBLE);
                binding.tobaccoQues70fRb.setVisibility(View.VISIBLE);
            }
        });
        binding.sedatives.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSedativesProduct = selectedValue;
            Log.d("selectedSedativesProduct", "Selected value: " + selectedSedativesProduct);
            if (checkedId == R.id.yes266g) {
                binding.sedativesProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.sedativesProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167g.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSedativesProduct = selectedValue;
            Log.d("selectedSedativesProduct", "Selected value: " + selectedSedativesProduct);
            if (checkedId == R.id.never67g) {
                binding.tobaccoQues68g.setVisibility(View.GONE);
                binding.tobaccoQues68gRb.setVisibility(View.GONE);
                binding.tobaccoQues69G.setVisibility(View.GONE);
                binding.tobaccoQues69gRb.setVisibility(View.GONE);
                binding.tobaccoQues70g.setVisibility(View.GONE);
                binding.tobaccoQues70gRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68g.setVisibility(View.VISIBLE);
                binding.tobaccoQues68gRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69G.setVisibility(View.VISIBLE);
                binding.tobaccoQues69gRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70g.setVisibility(View.VISIBLE);
                binding.tobaccoQues70gRb.setVisibility(View.VISIBLE);
            }
        });
        binding.hallucinogens.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedhallucinogensProduct = selectedValue;
            Log.d("selectedhallucinogensProduct", "Selected value: " + selectedhallucinogensProduct);
            if (checkedId == R.id.yes266h) {
                binding.hallucinogensProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.hallucinogensProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167h.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedhallucinogensProduct = selectedValue;
            Log.d("selectedhallucinogensProduct", "Selected value: " + selectedhallucinogensProduct);
            if (checkedId == R.id.never67h) {
                binding.tobaccoQues68h.setVisibility(View.GONE);
                binding.tobaccoQues68hRb.setVisibility(View.GONE);
                binding.tobaccoQues69h.setVisibility(View.GONE);
                binding.tobaccoQues69hRb.setVisibility(View.GONE);
                binding.tobaccoQues70h.setVisibility(View.GONE);
                binding.tobaccoQues70hRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68h.setVisibility(View.VISIBLE);
                binding.tobaccoQues68hRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69h.setVisibility(View.VISIBLE);
                binding.tobaccoQues69hRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70h.setVisibility(View.VISIBLE);
                binding.tobaccoQues70hRb.setVisibility(View.VISIBLE);
            }
        });
        binding.opioids.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteopioidsProduct = selectedValue;
            Log.d("selecteopioidsProduct", "Selected value: " + selecteopioidsProduct);
            if (checkedId == R.id.yes266i) {
                binding.opioidsProductsQueAll.setVisibility(View.VISIBLE);
            } else {
                binding.opioidsProductsQueAll.setVisibility(View.GONE);
            }
        });
        binding.alcoholProduct167i.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteopioidsProduct = selectedValue;
            Log.d("selecteopioidsProduct", "Selected value: " + selecteopioidsProduct);
            if (checkedId == R.id.never67i) {
                binding.tobaccoQues68i.setVisibility(View.GONE);
                binding.tobaccoQues68iRb.setVisibility(View.GONE);
                binding.tobaccoQues69i.setVisibility(View.GONE);
                binding.tobaccoQues69iRb.setVisibility(View.GONE);
                binding.tobaccoQues70i.setVisibility(View.GONE);
                binding.tobaccoQues70iRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68i.setVisibility(View.VISIBLE);
                binding.tobaccoQues68iRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69i.setVisibility(View.VISIBLE);
                binding.tobaccoQues69iRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70i.setVisibility(View.VISIBLE);
                binding.tobaccoQues70iRb.setVisibility(View.VISIBLE);
            }
        });
        binding.others.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteothersProduct = selectedValue;
            Log.d("selecteothersProduct", "Selected value: " + selecteothersProduct);
            if (checkedId == R.id.yes266j) {
                binding.othersProductsQueAll.setVisibility(View.VISIBLE);
                binding.Specify1.setVisibility(View.VISIBLE);
            } else {
                binding.othersProductsQueAll.setVisibility(View.GONE);
                binding.Specify1.setVisibility(View.GONE);
                binding.Specify1.setText("");
            }
        });
        binding.alcoholProduct167j.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selecteothersProduct = selectedValue;
            Log.d("selecteothersProduct", "Selected value: " + selecteothersProduct);
            if (checkedId == R.id.never67j) {
                binding.tobaccoQues68j.setVisibility(View.GONE);
                binding.tobaccoQues68jRb.setVisibility(View.GONE);
                binding.tobaccoQues69j.setVisibility(View.GONE);
                binding.tobaccoQues69jRb.setVisibility(View.GONE);
                binding.tobaccoQues70j.setVisibility(View.GONE);
                binding.tobaccoQues70jRb.setVisibility(View.GONE);
            } else {
                binding.tobaccoQues68j.setVisibility(View.VISIBLE);
                binding.tobaccoQues68jRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues69j.setVisibility(View.VISIBLE);
                binding.tobaccoQues69jRb.setVisibility(View.VISIBLE);
                binding.tobaccoQues70j.setVisibility(View.VISIBLE);
                binding.tobaccoQues70jRb.setVisibility(View.VISIBLE);
            }
        });

        binding.drugInjectionGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) return;
                RadioButton radioButton = findViewById(checkedId);


            }
        });
        /*ArrayList<Question> allQuestions = QuestionUtils.getAllQuestions();
        questionAdapter = new QuestionAdapter(this, allQuestions);
        binding.questionOptionsList.setAdapter(questionAdapter);
        for (Question question : allQuestions) {
            questionOptionsMap.put(question.getQuestionNo() + "a", 0);
            questionOptionsMap.put(question.getQuestionNo() + "b", 0);
            questionOptionsMap.put(question.getQuestionNo() + "c", 0);
            questionOptionsMap.put(question.getQuestionNo() + "d", 0);
            questionOptionsMap.put(question.getQuestionNo() + "e", 0);
            questionOptionsMap.put(question.getQuestionNo() + "f", 0);
            questionOptionsMap.put(question.getQuestionNo() + "g", 0);
            questionOptionsMap.put(question.getQuestionNo() + "h", 0);
            questionOptionsMap.put(question.getQuestionNo() + "i", 0);
        }


        binding.rcadsScore.setOnClickListener(v -> checkRCADSScore());

        binding.tobaccoProduct.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);

            switch (radioButton.getId()) {
                case R.id.yes2:
                    binding.question66B.setVisibility(View.GONE);
                    binding.question66BDesc.setVisibility(View.GONE);
                    binding.question66C.setVisibility(View.GONE);
                    binding.question66CDesc.setVisibility(View.GONE);
                    binding.alcoholProducts.setVisibility(View.GONE);
                    binding.question66D.setVisibility(View.GONE);
                    binding.question66DDesc.setVisibility(View.GONE);
                    binding.cannabis.setVisibility(View.GONE);
                    binding.question66E.setVisibility(View.GONE);
                    binding.question66EDesc.setVisibility(View.GONE);
                    binding.cocaine.setVisibility(View.GONE);
                    binding.question66F.setVisibility(View.GONE);
                    binding.question66FDesc.setVisibility(View.GONE);
                    binding.amphetamine.setVisibility(View.GONE);
                    binding.question66G.setVisibility(View.GONE);
                    binding.question66GDesc.setVisibility(View.GONE);
                    binding.inhalants.setVisibility(View.GONE);

                    binding.question66H.setVisibility(View.GONE);
                    binding.question66HDesc.setVisibility(View.GONE);

                    binding.question66I.setVisibility(View.GONE);
                    binding.question66IDesc.setVisibility(View.GONE);
                    binding.sedatives.setVisibility(View.GONE);
                    binding.question66J.setVisibility(View.GONE);
                    binding.question66JDesc.setVisibility(View.GONE);
                    binding.hallucinogens.setVisibility(View.GONE);
                    binding.opioids.setVisibility(View.GONE);
                    binding.Specify16.setVisibility(View.GONE);
                    binding.Specify9.setVisibility(View.GONE);
                    break;
                case R.id.no3:
                    binding.question66B.setVisibility(View.VISIBLE);
                    binding.question66BDesc.setVisibility(View.VISIBLE);
                    binding.question66C.setVisibility(View.VISIBLE);
                    binding.question66CDesc.setVisibility(View.VISIBLE);
                    binding.alcoholProducts.setVisibility(View.VISIBLE);
                    binding.question66D.setVisibility(View.VISIBLE);
                    binding.question66DDesc.setVisibility(View.VISIBLE);
                    binding.cannabis.setVisibility(View.VISIBLE);
                    binding.question66E.setVisibility(View.VISIBLE);
                    binding.question66EDesc.setVisibility(View.VISIBLE);
                    binding.cocaine.setVisibility(View.VISIBLE);
                    binding.question66F.setVisibility(View.VISIBLE);
                    binding.question66FDesc.setVisibility(View.VISIBLE);
                    binding.amphetamine.setVisibility(View.VISIBLE);
                    binding.question66G.setVisibility(View.VISIBLE);
                    binding.question66GDesc.setVisibility(View.VISIBLE);
                    binding.inhalants.setVisibility(View.VISIBLE);

                    binding.question66H.setVisibility(View.VISIBLE);
                    binding.question66HDesc.setVisibility(View.VISIBLE);
                    binding.hallucinogens.setVisibility(View.VISIBLE);

                    binding.question66I.setVisibility(View.VISIBLE);
                    binding.question66IDesc.setVisibility(View.VISIBLE);
                    binding.sedatives.setVisibility(View.VISIBLE);
                    binding.question66J.setVisibility(View.VISIBLE);
                    binding.question66JDesc.setVisibility(View.VISIBLE);
                    binding.hallucinogens.setVisibility(View.VISIBLE);
                    binding.opioids.setVisibility(View.VISIBLE);
                    binding.Specify16.setVisibility(View.VISIBLE);
                    binding.Specify9.setVisibility(View.VISIBLE);
                    break;
            }
        });*/
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
        Util.showToast(activity, "Successfully data saved");
        //   if (binding.onceOrTwice67a.isChecked() || binding.monthly67a.isChecked() || binding.weekly67a.isChecked() || binding.daily67a.isChecked()) {
        Intent intent = new Intent(activity, ChildrenResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(RCADS4_RESULT, rCards4Result);
        intent.putExtra("section5_status", true);
        intent.putExtra("ASSIST_screener", 1);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
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
        Intent intent = new Intent(Section5Activity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

}
