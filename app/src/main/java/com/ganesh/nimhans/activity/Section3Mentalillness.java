package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection3MentalillnessBinding;
import com.ganesh.nimhans.utils.Constants;

public class Section3Mentalillness extends AppCompatActivity {
    Activity activity;
    private ActivitySection3MentalillnessBinding binding;
    MyNimhans myGameApp;
    LinearLayout mental_layout, specify4layout;
    RadioGroup AnswerType1;
    String selectedIllness;
    String illness;
    String typeIllness;
    String tobacco;
    String nicotineUsed;
    Long demoGraphicsID;
    private int surveyID;
    String[] selectedTypeOfProblem;
    EditText Specify4;
    String selectedAnswerType2;
    String selectedTobacco;
    String selectedNicotineUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection3MentalillnessBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        specify4layout = binding.specify4layout;
        mental_layout = binding.mentalLayout;
        Specify4 = binding.Specify4;

        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);

        binding.answerType1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedTobacco = selectedValue;
            Log.d("selectedAnswerType1", "Selected value: " + selectedTobacco);
            switch (checkedId) {
                case R.id.yes:
                    binding.mentalLayout.setVisibility(View.VISIBLE);
                    binding.specify4layout.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.mentalLayout.setVisibility(View.GONE);
                    binding.specify4layout.setVisibility(View.GONE);
                    break;
            }
        });
        binding.answerType2.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAnswerType2 = selectedValue;
            Log.d("selectedAnswerType2", "Selected value: " + selectedAnswerType2);
            switch (checkedId) {
                case R.id.yes1:
                    binding.section17A.setVisibility(View.VISIBLE);
                    break;
                case R.id.no1:
                    binding.section17A.setVisibility(View.GONE);
                    break;
            }
        });
        binding.alcohol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton radioButton, boolean isChecked) {
                String selectedValue = radioButton.getText().toString();
                selectedNicotineUsed = selectedValue;
            }
        });
    }

    public void onClickPreviousSection(View v) {
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section3Mentalillness.this, ResultPage.class);
        startActivity(intent);
    }

    public void onClickNextSection(View v) {
        Intent intent = new Intent(Section3Mentalillness.this, Eligiblechildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        startActivity(intent);
    }

}