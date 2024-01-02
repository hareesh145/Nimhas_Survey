package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.FAMILY_COUNT;
import static com.ganesh.nimhans.utils.Constants.LINE_NO;
import static com.ganesh.nimhans.utils.Constants.MARITAL_STATUS;
import static com.ganesh.nimhans.utils.Constants.NO_OF_PEOPLE;
import static com.ganesh.nimhans.utils.Constants.REPEAT_COUNT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection3bBinding;
import com.ganesh.nimhans.model.HouseHoldModel;
import com.ganesh.nimhans.model.ServeySection3bRequest;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section3bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection3bBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    LinearLayout mental_layout, specify4layout;
    EditText Specify3, NoOfPersons, Name, Age,
            Specify4;
    RadioButton radioButton;
    RadioGroup Gender, MaritalStatus1, AnswerType1;
    String selectedGender;
    String selectedMaritalStatus1 = "";
    String selectedAnswerType2;
    String selectedTobacco;
    String selectedNicotineUsed;
    String selectedIllness;

    String noOfPeople;
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
    private String maritalState;

    String[] selectedTypeOfProblem;
    int repeatCount = 0;
    int ageCount =0;
    int getMaritalState = 0;


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
        Name = binding.Name;
        Age = binding.age;
        specify4layout = binding.specify4layout;
        mental_layout = binding.mentalLayout;
        Specify4 = binding.Specify4;
        Gender = binding.gender;
        MaritalStatus1 = binding.maritalStatus1;
        AnswerType1 = binding.answerType1;
        phoneNo = myGameApp.getUserPhoneNo();
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        maritalState = getIntent().getStringExtra(MARITAL_STATUS);
        if (getIntent().hasExtra(NO_OF_PEOPLE)) {
            binding.maritalStatus1.setEnabled(true);
            binding.totalNoOfPeople.setVisibility(View.GONE);
            binding.relation.setVisibility(View.VISIBLE);
            binding.selfEdittext.setVisibility(View.GONE);
            binding.NoOfPeople.setText(String.valueOf(getIntent().getIntExtra(NO_OF_PEOPLE, 0)));
            Log.d("TAG", "onCreate: " + binding.NoOfPeople.getText().toString());
            binding.lineNo.setText(String.valueOf(getIntent().getIntExtra(LINE_NO, 0)));
            repeatCount = getIntent().getIntExtra(REPEAT_COUNT, 0);

        } else {
            //First time gets invoked
            binding.totalNoOfPeople.setVisibility(View.VISIBLE);
            binding.relation.setVisibility(View.GONE);
            String nameOfRespondent = PreferenceConnector.readString(this, PreferenceConnector.NAME_OF_RESPONDENT, "");
            binding.Name.setText(nameOfRespondent);
            binding.whatIsRelationTxt.setText("What is the relationship of (" + nameOfRespondent + ") to you?");
            binding.isNameMaleOrFemale.setText("Is (" + nameOfRespondent + ") a male or female?");
            binding.howOldAge.setText("How old is (" + nameOfRespondent + ")?");
            binding.relation.selectItemByIndex(0);
            int checkedID = getCheckedID(maritalState);
            if (checkedID != -1) {
                binding.maritalStatus1.check(checkedID);
                if (checkedID == R.id.Married) {
                    binding.NotMarried.setEnabled(false);
                    binding.WorDorS.setEnabled(false);
                }
                else if (checkedID == R.id.NotMarried){
                    binding.Married.setEnabled(false);
                    binding.WorDorS.setEnabled(false);
                }else {
                    binding.Married.setEnabled(false);
                    binding.NotMarried.setEnabled(false);
                }
            }
        }

        Log.d("TAG", "onCreate: " + repeatCount);

        if (repeatCount > 1) {
            binding.addMember.setVisibility(View.VISIBLE);
            binding.nextbutton.setVisibility(View.GONE);
        } else {
            binding.addMember.setVisibility(View.GONE);
            binding.nextbutton.setVisibility(View.VISIBLE);
        }

        binding.NoOfPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!s.toString().isEmpty()) {
                        binding.lineNo.setText("1");
                        repeatCount = Integer.parseInt(binding.NoOfPeople.getText().toString());
                        if (Integer.parseInt(s.toString()) > 1) {
                            binding.addMember.setVisibility(View.VISIBLE);
                            binding.nextbutton.setVisibility(View.GONE);
                        } else {
                            binding.addMember.setVisibility(View.GONE);
                            binding.nextbutton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.lineNo.setText("");
                        binding.addMember.setVisibility(View.GONE);
                        binding.nextbutton.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              try {
                  if (!s.toString().isEmpty()) {
                      ageCount = Integer.parseInt(binding.age.getText().toString());
                      if (Integer.parseInt(s.toString()) >= 6) {
                          binding.qus131415.setVisibility(View.VISIBLE);
                      } else {
                          binding.qus131415.setVisibility(View.GONE);
                      }
                  } else {
                      binding.qus131415.setVisibility(View.VISIBLE);
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Gender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedGender = selectedValue;
            Log.d("selectedGender", "Selected value: " + selectedGender);
        });
        MaritalStatus1.setOnCheckedChangeListener((group, checkedId) -> {
            radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedMaritalStatus1 = selectedValue;
            Log.d("selectedMaritalStatus1", "Selected value: " + selectedMaritalStatus1);
        });
        AnswerType1.setOnCheckedChangeListener((group, checkedId) -> {
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


        noOfPeople = NoOfPersons.getText().toString();
        houseHoldMember = Name.getText().toString();
        houseHoldNumberValue = houseHoldMember;

        gender = selectedGender;

        illness = selectedIllness;
        typeIllness = Specify4.getText().toString();
        tobacco = selectedTobacco;
        nicotineUsed = selectedNicotineUsed;

        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!s.toString().isEmpty()) {
                        binding.whatIsRelationTxt.setText("What is the relationship of (" + s + ") to you?");
                        binding.isNameMaleOrFemale.setText("Is (" + s + ") a male or female?");
                        binding.howOldAge.setText("How old is (" + s + ")?");
                    } else {
                        binding.whatIsRelationTxt.setText("What is the relationship of (NAME) to you?");
                        binding.isNameMaleOrFemale.setText("Is (NAME) a male or female?");
                        binding.howOldAge.setText("How old is (NAME)?");
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

    private int getCheckedID(String maritalState) {
        if (maritalState.equals("Never Married")) {
            return R.id.NotMarried;
        } else if (maritalState.equals("Currently Married")) {
            return R.id.Married;
        } else if (maritalState.equals("Widowed/Divorced /Separated")) {
            return R.id.WorDorS;
        }
        return -1;
    }

    public void onClickNextSection(View v) {
        Log.d("noOfPeople", "onClickNextSection: " + NoOfPersons.getText().toString());
        Log.d("lineNo", "onClickNextSection: " + binding.lineNo.getText().toString());
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

        if (!binding.lineNo.getText().toString().isEmpty()) {
            serveySection5Request.setQno8(Integer.parseInt(binding.lineNo.getText().toString()));
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

        ArrayList<String> selectedTypeOfProblems = new ArrayList<>();

        String alchol = null;
        if (binding.alcohol.isChecked()) {
            alchol = binding.alcohol.getText().toString();
        }
        if (alchol != null) {
            selectedTypeOfProblems.add(alchol);
        }
        String tobacoo = null;
        if (binding.tobacco.isChecked()) {
            tobacoo = binding.tobacco.getText().toString();
        }
        if (tobacoo != null) {
            selectedTypeOfProblems.add(tobacoo);
        }

        String substanceUse = null;
        if (binding.substanceUse.isChecked()) {
            substanceUse = binding.substanceUse.getText().toString();
        }
        if (substanceUse != null) {
            selectedTypeOfProblems.add(substanceUse);
        }

        if (selectedTypeOfProblems.size() > 0) {
            selectedTypeOfProblem = new String[selectedTypeOfProblems.size()];
            selectedTypeOfProblem = selectedTypeOfProblems.toArray(selectedTypeOfProblem);
        }

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HouseHoldModel houseHoldModel = new HouseHoldModel();
        houseHoldModel.setQno7(Integer.parseInt(binding.NoOfPeople.getText().toString()));
        houseHoldModel.setQno8(Integer.parseInt(binding.lineNo.getText().toString()));
        houseHoldModel.setQno9(binding.Name.getText().toString());
        houseHoldModel.setQno10(binding.relation.getText().toString());
        houseHoldModel.setQno11(getGendarSelection());

        if (!binding.age.getText().toString().isEmpty()) {
            houseHoldModel.setQno12(Integer.parseInt(binding.age.getText().toString()));
        }
        houseHoldModel.setQno13(selectedMaritalStatus1);
        houseHoldModel.setQno14(binding.occupation.getText().toString());
        houseHoldModel.setQno15(binding.education.getSelectedItem().toString());
        houseHoldModel.setQno16(binding.answerType1.getCheckedRadioButtonId() == R.id.yes ? "Yes" : "No");
        houseHoldModel.setQno16A(binding.Specify4.getText().toString());
        houseHoldModel.setQno17(selectedAnswerType2);
        if (selectedTypeOfProblems.size() > 0)
            houseHoldModel.setQno17A(selectedTypeOfProblem);

        Call<JsonObject> apiCall = apiInterface.saveHouseHold(surveyID, houseHoldModel, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

        apiCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Intent intent = new Intent(activity, Section3Mentalillness.class);
                int familyCount = PreferenceConnector.readInteger(Section3bActivity.this, FAMILY_COUNT, 0);
                if (familyCount == 0) {
                    PreferenceConnector.writeInteger(Section3bActivity.this, FAMILY_COUNT, Integer.parseInt(binding.NoOfPeople.getText().toString()) - 1);
                } else {
                    familyCount = familyCount - 1;
                    PreferenceConnector.writeInteger(Section3bActivity.this, FAMILY_COUNT, familyCount);
                }
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(NO_OF_PEOPLE, Integer.parseInt(binding.NoOfPeople.getText().toString()));
                intent.putExtra(LINE_NO, Integer.parseInt(binding.lineNo.getText().toString()) + 1);
                intent.putExtra(REPEAT_COUNT, repeatCount - 1);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
//        Call<JsonObject> call = apiService.putServeySection3BData(surveyID, serveySection5Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (binding.progressBar.isShown())
//                    binding.progressBar.setVisibility(View.GONE);
//                JsonObject userResponse = response.body();
//                if (response.isSuccessful()) {
//                    Log.d("response", "onResponse: " + userResponse);
//                    Util.showToast(activity, "Successfully data saved");
//                    Intent intent = new Intent(Section3bActivity.this, Section3cActivity.class);
//                    if (!binding.age.getText().toString().isEmpty()) {
//                        int age = Integer.parseInt(binding.age.getText().toString());
//                        if (age > 17) {
//                            intent = new Intent(Section3bActivity.this, Section12Activity.class);
//                        } else if (age > 8 && age < 17) {
//                            intent = new Intent(Section3bActivity.this, Section3cActivity.class);
//                        } else if (age > 6 && age < 17) {
//                            intent = new Intent(Section3bActivity.this, Section4Activity.class);
//                        }
//                    }
//
//                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                    intent.putExtra(SURVEY_ID, surveyID);
//                    intent.putExtra(AGE_ID, binding.age.getText().toString());
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
//                Util.showToast(activity, "Successfully data saved");
//                Intent intent = new Intent(Section3bActivity.this, Section3cActivity.class);
//                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
//                intent.putExtra(SURVEY_ID, surveyID);
//                startActivity(intent);
//            }
//        });


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
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section3bActivity.this, ResultPage.class);
        startActivity(intent);
    }

    public void onClickAddMember(View v) {

        if (repeatCount > 0) {
            ArrayList<String> selectedTypeOfProblems = new ArrayList<>();

            String alchol = null;
            if (binding.alcohol.isChecked()) {
                alchol = binding.alcohol.getText().toString();
            }
            if (alchol != null) {
                selectedTypeOfProblems.add(alchol);
            }
            String tobacoo = null;
            if (binding.tobacco.isChecked()) {
                tobacoo = binding.tobacco.getText().toString();
            }
            if (tobacoo != null) {
                selectedTypeOfProblems.add(tobacoo);
            }

            String substanceUse = null;
            if (binding.substanceUse.isChecked()) {
                substanceUse = binding.substanceUse.getText().toString();
            }
            if (substanceUse != null) {
                selectedTypeOfProblems.add(substanceUse);
            }

            if (selectedTypeOfProblems.size() > 0) {
                selectedTypeOfProblem = new String[selectedTypeOfProblems.size()];
                selectedTypeOfProblem = selectedTypeOfProblems.toArray(selectedTypeOfProblem);
            }

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            HouseHoldModel houseHoldModel = new HouseHoldModel();
            houseHoldModel.setQno7(Integer.parseInt(binding.NoOfPeople.getText().toString()));
            houseHoldModel.setQno8(Integer.parseInt(binding.lineNo.getText().toString()));
            houseHoldModel.setQno9(binding.Name.getText().toString());
            houseHoldModel.setQno10(binding.relation.getText().toString());
            houseHoldModel.setQno11(getGendarSelection());

            if (!binding.age.getText().toString().isEmpty()) {
                houseHoldModel.setQno12(Integer.parseInt(binding.age.getText().toString()));
            }
            houseHoldModel.setQno13(selectedMaritalStatus1);
            houseHoldModel.setQno14(binding.occupation.getText().toString());
            houseHoldModel.setQno15(binding.education.getSelectedItem().toString());
            houseHoldModel.setQno16(binding.answerType1.getCheckedRadioButtonId() == R.id.yes ? "Yes" : "No");
            houseHoldModel.setQno16A(binding.Specify4.getText().toString());
            houseHoldModel.setQno17(selectedAnswerType2);
            if (selectedTypeOfProblems.size() > 0)
                houseHoldModel.setQno17A(selectedTypeOfProblem);

            Call<JsonObject> apiCall = apiInterface.saveHouseHold(surveyID, houseHoldModel, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));

            apiCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Intent intent = new Intent(activity, Section3bActivity.class);
                    int familyCount = PreferenceConnector.readInteger(Section3bActivity.this, FAMILY_COUNT, 0);
                    if (familyCount == 0) {
                        PreferenceConnector.writeInteger(Section3bActivity.this, FAMILY_COUNT, Integer.parseInt(binding.NoOfPeople.getText().toString()) - 1);
                    } else {
                        familyCount = familyCount - 1;
                        PreferenceConnector.writeInteger(Section3bActivity.this, FAMILY_COUNT, familyCount);
                    }
                    intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                    intent.putExtra(SURVEY_ID, surveyID);
                    intent.putExtra(NO_OF_PEOPLE, Integer.parseInt(binding.NoOfPeople.getText().toString()));
                    intent.putExtra(LINE_NO, Integer.parseInt(binding.lineNo.getText().toString()) + 1);
                    intent.putExtra(REPEAT_COUNT, repeatCount - 1);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }


    }
}
