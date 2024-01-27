package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.FAMILY_COUNT;
import static com.ganesh.nimhans.utils.Constants.House_Hold_Model;
import static com.ganesh.nimhans.utils.Constants.LINE_NO;
import static com.ganesh.nimhans.utils.Constants.NO_OF_PEOPLE;
import static com.ganesh.nimhans.utils.Constants.REPEAT_COUNT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.ganesh.nimhans.model.HouseHoldModel;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    String householdid;
    HouseHoldModel houseHoldModel;

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
        householdid =getIntent().getStringExtra(House_Hold_Model);
        Log.e("House_Hold_Model","House_Hold_Model 3M :"+householdid);
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
                    binding.Specify4.setText("");
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
                    binding.tobacco.setChecked(false);
                    binding.alcohol.setChecked(false);
                    binding.substanceUse.setChecked(false);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Section3Mentalillness.this);
        builder.setMessage("Are you sure you want to go to Result Section?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(Section3Mentalillness.this,ResultPage.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onClickNextSection(View v) {

        Intent intent = new Intent(Section3Mentalillness.this, ResultPage.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(House_Hold_Model,householdid);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}