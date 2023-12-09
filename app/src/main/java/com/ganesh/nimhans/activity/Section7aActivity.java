package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.databinding.ActivitySection7aBinding;
import com.ganesh.nimhans.model.ServeySection7aRequest;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;

public class Section7aActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7aBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private String ageValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection7aBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);
        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection7aRequest serveySection7aRequest = new ServeySection7aRequest();
        int checkedRadioButtonId = binding.section7Respondent.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            RadioButton checkedID = findViewById(checkedRadioButtonId);
            serveySection7aRequest.setMchatRespondent(checkedID.getText().toString());
        } else {
            serveySection7aRequest.setMchatRespondent(binding.otherTxt.getText().toString());
        }
//        serveySection7aRequest.setQno78();
        binding.merchantResult.setText("0");
    }

    public void onClickNextSection(View v) {
        binding.progressBar.setVisibility(View.VISIBLE);
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section7bActivity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        startActivity(intent);
//        startActivity(new Intent(activity, Section7bActivity.class));

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section6Activity.class));
        finish();
    }
    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section7aActivity.this,ResultPage.class);
        startActivity(intent);
    }
}
