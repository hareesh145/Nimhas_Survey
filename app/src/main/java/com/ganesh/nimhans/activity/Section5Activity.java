package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.QuestionAdapter;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection5Binding;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Question;
import com.ganesh.nimhans.utils.QuestionUtils;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section5Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection5Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;

    private HashMap<String, Integer> questionOptionsMap = new HashMap<>();
    private String ageValue;
    QuestionAdapter questionAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.ageAndMark.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();
        ArrayList<Question> allQuestions = QuestionUtils.getAllQuestions();
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
                    binding.alcoholProducts.setVisibility(View.GONE);
                    binding.cannabis.setVisibility(View.GONE);
                    binding.cocaine.setVisibility(View.GONE);
                    binding.amphetamine.setVisibility(View.GONE);
                    binding.inhalants.setVisibility(View.GONE);
                    binding.sedatives.setVisibility(View.GONE);
                    binding.hallucinogens.setVisibility(View.GONE);
                    binding.opioids.setVisibility(View.GONE);
                    break;
                case R.id.no3:
                    break;
            }
        });
    }


    private void checkRCADSScore() {
        ServeySection5Request serveySection5Request = new ServeySection5Request();
        serveySection5Request.setQno66a(binding.tobaccoProduct.getCheckedRadioButtonId() == R.id.no3 ? 0 : 3);
        serveySection5Request.setQno66b(binding.alcoholProducts.getCheckedRadioButtonId() == R.id.no4 ? 0 : 3);
        serveySection5Request.setQno66c(binding.cannabis.getCheckedRadioButtonId() == R.id.no5 ? 0 : 3);
        serveySection5Request.setQno66d(binding.cocaine.getCheckedRadioButtonId() == R.id.no6 ? 0 : 3);
        serveySection5Request.setQno66e(binding.amphetamine.getCheckedRadioButtonId() == R.id.no7 ? 0 : 3);
        serveySection5Request.setQno66f(binding.inhalants.getCheckedRadioButtonId() == R.id.no8 ? 0 : 3);
        serveySection5Request.setQno66g(binding.sedatives.getCheckedRadioButtonId() == R.id.no9 ? 0 : 3);
        serveySection5Request.setQno66h(binding.hallucinogens.getCheckedRadioButtonId() == R.id.no10 ? 0 : 3);
        serveySection5Request.setQno66i(binding.opioids.getCheckedRadioButtonId() == R.id.no11 ? 0 : 3);
        serveySection5Request.setQno66j(binding.Specify16.getCheckedRadioButtonId() == R.id.no12 ? 0 : 3);
        if (questionOptionsMap.get("67a") != null) {
            serveySection5Request.setQno67a(questionOptionsMap.get("67a"));
        } else {
            serveySection5Request.setQno67a(1);
        }
        if (questionOptionsMap.get("67b") != null) {
            serveySection5Request.setQno67b(questionOptionsMap.get("67b"));
        } else {
            serveySection5Request.setQno67b(1);
        }

        if (questionOptionsMap.get("67c") != null) {
            serveySection5Request.setQno67c(questionOptionsMap.get("67c"));
        } else {
            serveySection5Request.setQno67c(1);
        }
        serveySection5Request.setQno67d(questionOptionsMap.get("67d"));
        serveySection5Request.setQno67e(questionOptionsMap.get("67e"));
        serveySection5Request.setQno67f(questionOptionsMap.get("67f"));
        serveySection5Request.setQno67g(questionOptionsMap.get("67g"));
        serveySection5Request.setQno67h(questionOptionsMap.get("67h"));
        serveySection5Request.setQno67i(questionOptionsMap.get("67i"));

        serveySection5Request.setQno68a(questionOptionsMap.get("68a"));
        serveySection5Request.setQno68b(questionOptionsMap.get("68b"));
        serveySection5Request.setQno68c(questionOptionsMap.get("68c"));
        serveySection5Request.setQno68d(questionOptionsMap.get("68d"));
        serveySection5Request.setQno68e(questionOptionsMap.get("68e"));
        serveySection5Request.setQno68f(questionOptionsMap.get("68f"));
        serveySection5Request.setQno68g(questionOptionsMap.get("68g"));
        serveySection5Request.setQno68h(questionOptionsMap.get("68h"));
        serveySection5Request.setQno68i(questionOptionsMap.get("68i"));

        serveySection5Request.setQno69a(questionOptionsMap.get("69a"));
        serveySection5Request.setQno69b(questionOptionsMap.get("69b"));
        serveySection5Request.setQno69c(questionOptionsMap.get("69c"));
        serveySection5Request.setQno69d(questionOptionsMap.get("69d"));
        serveySection5Request.setQno69e(questionOptionsMap.get("69e"));
        serveySection5Request.setQno69f(questionOptionsMap.get("69f"));
        serveySection5Request.setQno69g(questionOptionsMap.get("69g"));
        serveySection5Request.setQno69h(questionOptionsMap.get("69h"));
        serveySection5Request.setQno69i(questionOptionsMap.get("69i"));

        serveySection5Request.setQno70a(questionOptionsMap.get("70a"));
        serveySection5Request.setQno70b(questionOptionsMap.get("70b"));
        serveySection5Request.setQno70c(questionOptionsMap.get("70c"));
        serveySection5Request.setQno70d(questionOptionsMap.get("70d"));
        serveySection5Request.setQno70e(questionOptionsMap.get("70e"));
        serveySection5Request.setQno70f(questionOptionsMap.get("70f"));
        serveySection5Request.setQno70g(questionOptionsMap.get("70g"));
        serveySection5Request.setQno70h(questionOptionsMap.get("70h"));
        serveySection5Request.setQno70i(questionOptionsMap.get("70i"));

        serveySection5Request.setQno71a(questionOptionsMap.get("71a"));
        serveySection5Request.setQno71b(questionOptionsMap.get("71b"));
        serveySection5Request.setQno71c(questionOptionsMap.get("71c"));
        serveySection5Request.setQno71d(questionOptionsMap.get("71d"));
        serveySection5Request.setQno71e(questionOptionsMap.get("71e"));
        serveySection5Request.setQno71f(questionOptionsMap.get("71f"));
        serveySection5Request.setQno71g(questionOptionsMap.get("71g"));
        serveySection5Request.setQno71h(questionOptionsMap.get("71h"));
        serveySection5Request.setQno71i(questionOptionsMap.get("71i"));

        serveySection5Request.setQno72a(questionOptionsMap.get("72a"));
        serveySection5Request.setQno72b(questionOptionsMap.get("72b"));
        serveySection5Request.setQno72c(questionOptionsMap.get("72c"));
        serveySection5Request.setQno72d(questionOptionsMap.get("72d"));
        serveySection5Request.setQno72e(questionOptionsMap.get("72e"));
        serveySection5Request.setQno72f(questionOptionsMap.get("72f"));
        serveySection5Request.setQno72g(questionOptionsMap.get("72g"));
        serveySection5Request.setQno72h(questionOptionsMap.get("72h"));
        serveySection5Request.setQno72i(questionOptionsMap.get("72i"));

        switch (binding.injectableDrugSubstance1.getCheckedRadioButtonId()) {
            case R.id.no18:
                serveySection5Request.setQno73(0);
                break;
            case R.id.yes32:
                serveySection5Request.setQno73(6);
                break;
            case R.id.yes33:
                serveySection5Request.setQno73(3);
                break;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.putServeySection5Data(surveyID, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();
                if (response.isSuccessful()) {
                    Log.d("response", "onResponse: " + userResponse);
                    try {
                        binding.alcoholSubstance.setText(userResponse.get("alcoholScore").getAsString());
                        binding.otherSubstance.setText(userResponse.get("otherScore").getAsString());
                        binding.injectableDrugSubstance.setText(userResponse.get("injectableScore").getAsString());
                        binding.assistResult.setText(userResponse.get("assistResult").getAsString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section6Activity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        startActivity(intent);
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


}
