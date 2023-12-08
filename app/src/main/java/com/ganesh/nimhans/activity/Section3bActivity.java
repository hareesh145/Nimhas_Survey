package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection3bBinding;
import com.ganesh.nimhans.model.ServeySection3bRequest;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section3bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection3bBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    EditText Specify3, NoOfPersons, NoOfPersons1, Name, Age,
            Specify4;
    RadioGroup Gender, MaritalStatus1, AnswerType1;
    String selectedGender;
    String selectedMaritalStatus1 = "";
    String selectedAnswerType2;
    String selectedTobacco;
    String selectedNicotineUsed;
    String selectedIllness;

    String noOfPeople;
    String lineNo;
    String houseHoldMember;
    String houseHoldNumberValue;
    String relationShip;
    String gender;
    String occupation;
    String education;
    String illness;
    String typeIllness;
    String tobacco;
    String nicotineUsed;
    MyNimhans myGameApp;
    Long demoGraphicsID;
    private int surveyID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection3bBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        Specify3 = binding.Specify3;
        NoOfPersons = binding.NoOfPeople;
        NoOfPersons1 = binding.NoOfPeople1;
        Name = binding.Name;
        Age = binding.age;

        Specify4 = binding.Specify4;
        Gender = binding.gender;
        MaritalStatus1 = binding.maritalStatus1;
        AnswerType1 = binding.answerType1;
        phoneNo = myGameApp.getUserPhoneNo();
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);

        Gender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedGender = selectedValue;
            Log.d("selectedGender", "Selected value: " + selectedGender);
        });
        MaritalStatus1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedMaritalStatus1 = selectedValue;
            Log.d("selectedMaritalStatus1", "Selected value: " + selectedMaritalStatus1);
        });
        AnswerType1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedTobacco = selectedValue;
            Log.d("selectedAnswerType1", "Selected value: " + selectedTobacco);
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
        noOfPeople = NoOfPersons.getText().toString();
        lineNo = NoOfPersons1.getText().toString();
        houseHoldMember = Name.getText().toString();
        houseHoldNumberValue = houseHoldMember;

        gender = selectedGender;

        illness = selectedIllness;
        typeIllness = Specify4.getText().toString();
        tobacco = selectedTobacco;
        nicotineUsed = selectedNicotineUsed;

    }

    public void onClickNextSection(View v) {
        Log.d("noOfPeople", "onClickNextSection: " + NoOfPersons.getText().toString());
        Log.d("lineNo", "onClickNextSection: " + lineNo);
//        Log.d("", "onClickNextSection: "+);
        Log.d("houseHoldMember", "onClickNextSection: " + houseHoldMember);
        Log.d("houseHoldNumberValue", "onClickNextSection: " + houseHoldNumberValue);
        Log.d("relationShip", "onClickNextSection: " + relationShip);
        Log.d("gender", "onClickNextSection: " + gender);
        Log.d("age", "onClickNextSection: " + binding.age.getText().toString());

        Log.d("occupation", "onClickNextSection: " + occupation);
        Log.d("education", "onClickNextSection: " + education);
        Log.d("illness", "onClickNextSection: " + illness);
        Log.d("typeIllness", "onClickNextSection: " + typeIllness);
        Log.d("tobacco", "onClickNextSection: " + tobacco);
        Log.d("nicotineUsed", "onClickNextSection: " + nicotineUsed);

        relationShip = binding.relation.getText().toString();
        occupation = binding.occupation.getText().toString();
        education = binding.education.getSelectedItem().toString();

        ServeySection3bRequest serveySection5Request = new ServeySection3bRequest();
        if (!NoOfPersons.getText().toString().isEmpty()) {
            serveySection5Request.setQno7(Integer.parseInt(NoOfPersons.getText().toString()));
        } else {
            serveySection5Request.setQno7(0);
        }

        if (!lineNo.isEmpty()) {
            serveySection5Request.setQno8(Integer.parseInt(lineNo));
        } else {
            serveySection5Request.setQno8(0);
        }
        serveySection5Request.setQno9(Name.getText().toString());
        serveySection5Request.setQno10(binding.relation.getText().toString());
        serveySection5Request.setQno11(getGendarSelection());
        if (!binding.age.getText().toString().isEmpty()) {
            serveySection5Request.setQno12(Integer.parseInt(binding.age.getText().toString()));
        } else {
            serveySection5Request.setQno12(0);
        }

        serveySection5Request.setQno13(selectedMaritalStatus1);
        serveySection5Request.setQno14(binding.occupation.getText().toString());
        serveySection5Request.setQno15(binding.education.getSelectedItem().toString());
        serveySection5Request.setQno16(binding.answerType1.getCheckedRadioButtonId() == R.id.yes ? "Yes" : "No");
        serveySection5Request.setQno16A(binding.Specify4.getText().toString());
        serveySection5Request.setQno17(selectedAnswerType2);
        serveySection5Request.setQno17A(selectedNicotineUsed);

        binding.progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.putServeySection3BData(surveyID, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();
                if (response.isSuccessful()) {
                    Log.d("response", "onResponse: " + userResponse);
                    Util.showToast(activity, "Successfully data saved");
                    Intent intent = new Intent(Section3bActivity.this, Section3cActivity.class);
                    if (!binding.age.getText().toString().isEmpty()) {
                        int age = Integer.parseInt(binding.age.getText().toString());
                        if (age > 17) {
                            intent = new Intent(Section3bActivity.this, Section12Activity.class);
                        } else if (age > 8 && age < 17) {
                            intent = new Intent(Section3bActivity.this, Section3cActivity.class);
                        } else if (age > 6 && age < 17) {
                            intent = new Intent(Section3bActivity.this, Section4Activity.class);
                        }
                    }

                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                    intent.putExtra(SURVEY_ID, surveyID);
                    intent.putExtra(AGE_ID, binding.age.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, "Successfully data saved");
                Intent intent = new Intent(Section3bActivity.this, Section3cActivity.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                startActivity(intent);
            }
        });


    }

    private int getGendarSelection() {
        int checkedRadioButtonId = binding.gender.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.Male) {
            return 1;
        } else if (checkedRadioButtonId == R.id.Female) {
            return 2;
        }
        return 0;
    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, DashboardActivity.class));
//        startActivity(new Intent(activity, Section3aActivity.class));
        finish();
    }
}
