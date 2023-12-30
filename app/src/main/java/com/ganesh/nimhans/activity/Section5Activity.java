package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

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
import com.ganesh.nimhans.databinding.ActivitySection5Binding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();


        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();
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
                    break;
            }
        });

        binding.tobaccoProduct167a.setOnCheckedChangeListener((group, checkedId) -> {
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
                    binding.tobaccoQues70a.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70aRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
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
                    break;
            }
        });
        binding.alcoholProduct167b.setOnCheckedChangeListener((group, checkedId) -> {
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
                    break;
            }
        });
        binding.alcoholProduct167c.setOnCheckedChangeListener((group, checkedId) -> {
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
                    break;
            }
        });
        binding.alcoholProduct167d.setOnCheckedChangeListener((group, checkedId) -> {
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
                    break;
            }
        });
        binding.alcoholProduct167e.setOnCheckedChangeListener((group, checkedId) -> {
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
        binding.inhalants.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedinhalantsProduct = selectedValue;
            Log.d("selectedinhalantsProduct", "Selected value: " + selectedinhalantsProduct);
            switch (checkedId) {
                case R.id.yes266f:
                    binding.inhalantsProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.inhalantsProductsQueAll.setVisibility(View.GONE);
                    break;
            }
        });
        binding.alcoholProduct167f.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedinhalantsProduct = selectedValue;
            Log.d("selectedinhalantsProduct", "Selected value: " + selectedinhalantsProduct);
            switch (checkedId) {
                case R.id.never67f:
                    binding.tobaccoQues68f.setVisibility(View.GONE);
                    binding.tobaccoQues68fRb.setVisibility(View.GONE);
                    binding.tobaccoQues69f.setVisibility(View.GONE);
                    binding.tobaccoQues69fRb.setVisibility(View.GONE);
                    binding.tobaccoQues70f.setVisibility(View.GONE);
                    binding.tobaccoQues70fRb.setVisibility(View.GONE);
                    break;
                default:
                    binding.tobaccoQues68f.setVisibility(View.VISIBLE);
                    binding.tobaccoQues68fRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69f.setVisibility(View.VISIBLE);
                    binding.tobaccoQues69fRb.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70f.setVisibility(View.VISIBLE);
                    binding.tobaccoQues70fRb.setVisibility(View.VISIBLE);
                    break;
            }
        });
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
                    break;
            }
        });
        binding.alcoholProduct167g.setOnCheckedChangeListener((group, checkedId) -> {
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
        binding.hallucinogens.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedhallucinogensProduct = selectedValue;
            Log.d("selectedhallucinogensProduct", "Selected value: " + selectedhallucinogensProduct);
            switch (checkedId) {
                case R.id.yes266h:
                    binding.hallucinogensProductsQueAll.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.hallucinogensProductsQueAll.setVisibility(View.GONE);
                    break;
            }
        });
        binding.alcoholProduct167h.setOnCheckedChangeListener((group, checkedId) -> {
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
                    break;
            }
        });
        binding.alcoholProduct167i.setOnCheckedChangeListener((group, checkedId) -> {
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
                    break;
            }
        });
        binding.alcoholProduct167j.setOnCheckedChangeListener((group, checkedId) -> {
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
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
//        Intent intent = new Intent(activity, Section6Activity.class);
//        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//        intent.putExtra(SURVEY_ID, surveyID);
//        intent.putExtra(AGE_ID, ageValue);
//        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
//        startActivity(intent);
        if (Float.parseFloat(ageValue) <= 17.0f) {
            if (Float.parseFloat(ageValue) >= 6.0f) {
                Intent intent = new Intent(activity, Section6Activity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            } else if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
                //If the age is greater than 2 & less than 3
                Intent intent = new Intent(activity, Section7aActivity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            } else if (Float.parseFloat(ageValue) >= 4.0f) {
                //If the age is greater than 3 Krishna
                Intent intent = new Intent(activity, Section7bActivity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            } else {
                //IF the Age is 18
                Intent intent = new Intent(activity, Section8Activity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                startActivity(intent);
            }
        }
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
        Intent intent = new Intent(Section5Activity.this, ResultPage.class);
        startActivity(intent);
    }

}
