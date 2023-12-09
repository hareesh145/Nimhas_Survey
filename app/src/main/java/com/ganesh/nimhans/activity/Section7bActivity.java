package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.databinding.ActivitySection7bBinding;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;

public class Section7bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7bBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private String ageValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection7bBinding.inflate(getLayoutInflater());
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
        binding.iasqResultTxt.setText("0");
    }

    public void onClickNextSection(View v) {
        Intent intent = new Intent(activity, Section8Activity.class);
        Util.showToast(activity, "Successfully data saved");
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID,surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID,demoGraphicsID);
        startActivity(intent);

    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section7aActivity.class));
        finish();
    }
    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section7bActivity.this,ResultPage.class);
        startActivity(intent);
    }
}
