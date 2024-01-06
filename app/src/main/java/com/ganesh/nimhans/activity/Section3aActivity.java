package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.MARITAL_STATUS;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.PreferenceConnector.NAME_OF_RESPONDENT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection3aBinding;
import com.ganesh.nimhans.model.ServeySectionRequest;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section3aActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection3aBinding binding;
    String phoneNo, pswd;
    Section2Activity sec2;
    ProgressBar progressBar;
    RadioButton radioButton;
    MyNimhans myGameApp;
    EditText Specify, Specify0, Specify1, Specify2, NoOfSons, NoOfDaughters, IncomePerMonth;
    LinearLayout que_5_layout,que_4_layout;


    RadioGroup Caste, AnswerType, MaritalStatus, YesOrNo;
    String selectedCaste, selectedAnswerType, selectedMaritalStatus, selectedYesOrNo;
    Long demoGraphicsID;
    int noOfSons;
    int noOfDaughters;
    String repeatCount ;
    int incomePerMonth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection3aBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();
        Specify = findViewById(R.id.Specify);
        Specify0 = findViewById(R.id.SSDDDTTTTTVVVVVVHHH);
        Specify1 = findViewById(R.id.Specify1);
        Specify2 = findViewById(R.id.Specify2);
        NoOfSons = findViewById(R.id.Sons);
        NoOfDaughters = findViewById(R.id.daughters);
        IncomePerMonth = findViewById(R.id.Income);
        Caste = findViewById(R.id.Caste);
        AnswerType = findViewById(R.id.AnswerType);
        que_5_layout = findViewById(R.id.que_5_layout);
        que_4_layout = findViewById(R.id.que_4_layout);
        MaritalStatus = findViewById(R.id.maritalStatus);
        YesOrNo = findViewById(R.id.yesOrNo);
//        Specify =binding.Specify;
//        Specify1 = binding.Specify1;
//        Specify2 = binding.Specify2;
//        NoOfSons = binding.Sons;
//        NoOfDaughters = binding.daughters;
//        IncomePerMonth = binding.Income;
//        Caste = binding.Caste;
//        AnswerType = binding.AnswerType;
//        MaritalStatus = binding.maritalStatus;
//        YesOrNo = binding.yesOrNo;
        binding.Specify1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                    if (!s.toString().isEmpty()) {
                        repeatCount = Specify1.getText().toString();
                        binding.AnswerType.setVisibility(View.VISIBLE);
                        if (s.toString().length() > 0) {
                            binding.AnswerType.setVisibility(View.GONE);
                        } else {
                            binding.AnswerType.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.AnswerType.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Caste.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            Log.d("selectedCaste", "Selected value: " + selectedCaste);
            switch (checkedId){
                case R.id.other_religion:
                    binding.Specify.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.Specify.setVisibility(View.GONE);
                    binding.Specify.setText("");
                    break;
            }
        });
        AnswerType.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAnswerType = selectedValue;
            Log.d("selectedAnswerType", "Selected value: " + selectedAnswerType);
           /* switch (checkedId){
                case R.id.notWilling:
                    binding.castTribe.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.castTribe.setVisibility(View.GONE);
                    break;
            }*/
        });
        MaritalStatus.setOnCheckedChangeListener((group, checkedId) -> {
           radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedMaritalStatus = selectedValue;
            Log.d("selectedMaritalStatus", "Selected value: " + selectedMaritalStatus);
            switch (checkedId){
                case R.id.other:
                    binding.Specify2.setVisibility(View.VISIBLE);
                    break;
                case R.id.NotMarried:
                    binding.que5Layout.setVisibility(View.GONE);
                    binding.que4Layout.setVisibility(View.GONE);
                    binding.Specify2.setText("");
                    break;
                default:
                    binding.Specify2.setVisibility(View.GONE);
                    binding.que5Layout.setVisibility(View.VISIBLE);
                    binding.que4Layout.setVisibility(View.VISIBLE);
                    binding.Specify2.setText("");
                    break;
            }
        });
        YesOrNo.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedYesOrNo = selectedValue;
            Log.d("selectedYesOrNo", "Selected value: " + selectedYesOrNo);

            if (selectedYesOrNo.equals("No")) {
                binding.question5Section.setEnabled(false);
                binding.question5aSection.setEnabled(false);
                binding.question5bSection.setEnabled(false);
                binding.noOfDaughtsField.setEnabled(false);
                binding.noOfSonsField.setEnabled(false);
                binding.daughters.setEnabled(false);
                binding.que5Layout.setVisibility(View.GONE);
                binding.Sons.setEnabled(false);
            } else {
                binding.question5Section.setEnabled(true);
                binding.question5aSection.setEnabled(true);
                binding.question5bSection.setEnabled(true);
                binding.daughters.setEnabled(true);
                binding.Sons.setEnabled(true);
                binding.noOfDaughtsField.setEnabled(true);
                binding.noOfSonsField.setEnabled(true);
                binding.que5Layout.setVisibility(View.VISIBLE);
            }
        });
        String demoGraphicsId = getIntent().getStringExtra("demoGraphicsId");
        Log.d("demoGraphicsId", "onCreate: " + demoGraphicsId);


    }

    public void onClickNextSection(View v) {
        Bundle bundle = getIntent().getExtras();
        demoGraphicsID = Long.valueOf(bundle.getString("demoo"));
//        Log.d("demoID", "onCreate: " + demoGraphicsID);
//        Log.d("selectedCaste", "onCreate: " + selectedCaste);
//        Log.d("selectedAnswerType", "onCreate: " + selectedAnswerType);
//        Log.d("selectedMaritalStatus", "onCreate: " + selectedMaritalStatus);
//        Log.d("selectedYesOrNo", "onCreate: " + selectedYesOrNo);
////        String noOfSon = null;
////        Log.d("noOfSon", "onCreate: " + noOfSon);
//        Log.d("noOfDaughters", "onCreate: " + noOfDaughters);
//        Log.d("income", "onCreate: " + incomePerMonth);
        if (!NoOfSons.getText().toString().isEmpty()) {
            noOfSons = Integer.parseInt(NoOfSons.getText().toString());
        } else {
            noOfSons = 0;
        }

        String noOfSon = String.valueOf(noOfSons);
        String noOfDougter = String.valueOf(noOfDaughters);
        String income = String.valueOf(incomePerMonth);
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ServeySectionRequest servey = new ServeySectionRequest();
        servey.setQno1(selectedCaste);
        servey.setQno2(selectedAnswerType);
        servey.setQno3(selectedMaritalStatus);
        servey.setQno4(selectedYesOrNo);
        servey.setQno5A(String.valueOf(Integer.parseInt(noOfSon)));
        servey.setQno5B(String.valueOf(Integer.parseInt(noOfDougter)));
        servey.setQno6(String.valueOf(Integer.parseInt(income)));
        Call<JsonObject> call = apiService.postServeySection5Data(demoGraphicsID, servey, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();
                if (response.isSuccessful()) {
                    Log.d("response", "onResponse: " + userResponse);
                    Util.showToast(activity, "Successfully data saved");
                    Intent intent = new Intent(Section3aActivity.this, Section3bActivity.class);
                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                    if (selectedMaritalStatus != null && selectedMaritalStatus.equalsIgnoreCase("Others") && !binding.Specify2.getText().toString().isEmpty()){
                        selectedMaritalStatus=binding.Specify2.getText().toString();
                        intent.putExtra("other",true);
                    }
                    intent.putExtra(MARITAL_STATUS,  selectedMaritalStatus);
                    Log.e("MARITAL_STATUS","MARITAL_STATUS :"+ radioButton.getText());
                    intent.putExtra(SURVEY_ID, userResponse.get(SURVEY_ID).getAsInt());

                    PreferenceConnector.writeInteger(Section3aActivity.this, SURVEY_ID, userResponse.get(SURVEY_ID).getAsInt());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

    public void onClickPreviousSection(View v) {
       //startActivity(new Intent(activity, Section1Activity.class));
        finish();

    }
    public void onClickGoToResult(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Section3aActivity.this);
        builder.setMessage("Are you sure you want to go to Result Section?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(Section3aActivity.this,ResultPage.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public String getPhoneNo() {
        return phoneNo;
    }

    public String getSelectedCaste() {

        return selectedCaste;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //
    }
}
