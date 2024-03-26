package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection12Binding;
import com.ganesh.nimhans.model.ServeySection12Request;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section12Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection12Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    private String ageValue;
    private int surveyID;
    private long demoGraphicsID;
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    private String respondentTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection12Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        phoneNo = myGameApp.getUserPhoneNo();
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);

        binding.illnessCare207.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify207.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify207.setVisibility(View.GONE);
                binding.othersSpecify207.setText("");
            }
        });

        binding.illnessCare208.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify208.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify208.setVisibility(View.GONE);
                binding.othersSpecify208.setText("");
            }
        });

        binding.illnessCare211.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            if (newItem.equals("Others")) {
                binding.othersSpecify211.setVisibility(View.VISIBLE);
            } else {
                binding.othersSpecify211.setVisibility(View.GONE);
                binding.othersSpecify211.setText("");
            }
        });


        binding.section12RespondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        respondentTxt = "Mother";
                        binding.section12Respondent.setVisibility(View.GONE);
                        binding.section12Respondent.setText("");
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.section12Respondent.setVisibility(View.GONE);
                        binding.section12Respondent.setText("");
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.section12Respondent.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        binding.options209.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_209) {
                    binding.options210213.setVisibility(View.VISIBLE);
                } else {
                    binding.options210213.setVisibility(View.GONE);
                }
            }
        });
        binding.question212.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.other) {
                    binding.othersSpecify212.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify212.setVisibility(View.GONE);
                    binding.othersSpecify212.setText("");
                }
            }
        });
        binding.question217.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dont_know) {
                    binding.km.setVisibility(View.VISIBLE);
                } else {
                    binding.km.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.heardVisitedOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.nor_visited) {
                    binding.options216219.setVisibility(View.GONE);
                    binding.a216Edittext.setText("");
                    binding.b216Edittext.setText("");
                    binding.c216Edittext.setText("");
                    binding.a219Radiogroup.clearCheck();
                    binding.b219Radiogroup.clearCheck();
                    binding.c219Radiogroup.clearCheck();
                } else {
                    binding.options216219.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.question210.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.did_not_seek_care) {
                    binding.othersSpecify210.setVisibility(View.GONE);
                    binding.othersSpecify210.setText("");
                } else {
                    binding.othersSpecify210.setVisibility(View.VISIBLE);
                }
            }
        });

       /* binding.modesOfTravel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.others_travel) {
                    binding.othersSpecify218.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify218.setVisibility(View.GONE);
                    binding.othersSpecify218.setText("");
                }
            }
        });
*/
        binding.othersSpecify210.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.didNotSeekCare.setVisibility(View.VISIBLE);
                } else {
                    binding.didNotSeekCare.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.othersSpecify212.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.mentalHealthCare.setVisibility(View.VISIBLE);
                } else {
                    binding.mentalHealthCare.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.others_218:
                if (checked) {
                    binding.othersSpecify218.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify218.setVisibility(View.GONE);
                    binding.othersSpecify218.setText("");
                }
                // Do your coding

                // Perform your logic
        }

    }

    public void onClickNextSection(View v) {
        if (binding.section12RespondentGrp.getCheckedRadioButtonId() == -1
                || binding.illnessCare207.getText().toString().equalsIgnoreCase("Select illness Care")
                || binding.illnessCare208.getText().toString().equalsIgnoreCase("Select illness Care")
                || binding.options209.getCheckedRadioButtonId() == -1
                || binding.heardVisitedOptions.getCheckedRadioButtonId() == -1 //215
                || !isCheckBxGrp214Checked()) {
            Toast.makeText(getApplicationContext(), "Please fill the data", Toast.LENGTH_LONG).show();
        } else {
            ServeySection12Request serveySection12Request = new ServeySection12Request();
            int checkedRadioButtonId = binding.section12RespondentGrp.getCheckedRadioButtonId();
            if (checkedRadioButtonId == -1) {
                if (respondentTxt.equalsIgnoreCase("Guardian")) {
                    serveySection12Request.setSection12Respondent(respondentTxt);
                    serveySection12Request.setSection12Guardian(binding.section12Respondent.getText().toString());
                } else {
                    serveySection12Request.setSection12Respondent(respondentTxt);
                    serveySection12Request.setSection12Guardian("NA");
                }
            } else {
                if (respondentTxt.equalsIgnoreCase("Guardian")) {
                    serveySection12Request.setSection12Respondent(respondentTxt);
                    serveySection12Request.setSection12Guardian(binding.section12Respondent.getText().toString());
                } else {
                    serveySection12Request.setSection12Respondent(respondentTxt);
                    serveySection12Request.setSection12Guardian("NA");
                }
            }
       /* serveySection12Request.setQno207(binding.illnessCare207.getText().toString());
        serveySection12Request.setQno207Specify("NA");*/
            if (binding.illnessCare207.getText().toString().equals("Others")) {
                serveySection12Request.setQno207(binding.illnessCare207.getText().toString());
                serveySection12Request.setQno207Specify(binding.othersSpecify207.getText().toString());
            } else {
                serveySection12Request.setQno207(binding.illnessCare207.getText().toString());
                serveySection12Request.setQno207Specify("NA");
            }

            if (binding.illnessCare208.getText().toString().equals("Others")) {
                serveySection12Request.setQno208(binding.illnessCare208.getText().toString());
                serveySection12Request.setQno208Specify(binding.othersSpecify208.getText().toString());
            } else {
                serveySection12Request.setQno208(binding.illnessCare208.getText().toString());
                serveySection12Request.setQno208Specify("NA");
            }

            if (binding.options209.getCheckedRadioButtonId() != -1) {
                String qus209 = Integer.toString(getCheckedRadioGrpID(binding.options209.getCheckedRadioButtonId(), binding.yes209.getId(), binding.no209.getId()));
                serveySection12Request.setQno209(qus209);
            } else {
                serveySection12Request.setQno209("NA");
            }
            if (!binding.othersSpecify210.getText().toString().isEmpty()) {
                serveySection12Request.setQno210(Integer.parseInt(binding.othersSpecify210.getText().toString()));
            } else {
                serveySection12Request.setQno210(-1);
            }

            if (binding.illnessCare211.getText().toString().equals("Others")) {
                serveySection12Request.setQno211(binding.illnessCare211.getText().toString());
                serveySection12Request.setQno211Specify(binding.othersSpecify211.getText().toString());
            } else {
                serveySection12Request.setQno211(binding.illnessCare211.getText().toString());
                serveySection12Request.setQno211Specify("NA");
            }

            if (binding.question212.getCheckedRadioButtonId() != -1) {
                String checkIDText = getCheckedRadioGrpID212(binding.question212.getCheckedRadioButtonId(), binding.mentalHealthCare.getId(), binding.other.getId());
                if (checkIDText.equals("96")) {
                    serveySection12Request.setQno212(checkIDText);
                    serveySection12Request.setQno212Specify(binding.othersSpecify212.getText().toString());
                } else {
                    serveySection12Request.setQno212("1");
                    serveySection12Request.setQno212Specify("NA");
                }
            } else {
                serveySection12Request.setQno212("NA");
                serveySection12Request.setQno212Specify("NA");
            }

            if (binding.options213.getCheckedRadioButtonId() != -1) {
                String qusno213 = Integer.toString(getCheckedRadioGrpID(binding.options213.getCheckedRadioButtonId(), binding.yes213.getId(), binding.no213.getId()));
                serveySection12Request.setQno213(qusno213);
            } else {
                serveySection12Request.setQno213("NA");
            }
            if (binding.checkBx214Autism.isChecked()) {
                String value = binding.checkBx214Autism.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214A(value);
            } else {
                serveySection12Request.setQno214A("NA");
            }

            if (binding.checkBx214Id.isChecked()) {
                String value = binding.checkBx214Id.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214B(value);
            } else {
                serveySection12Request.setQno214B("NA");
            }
            if (binding.checkBx214Adhd.isChecked()) {
                String value = binding.checkBx214Adhd.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214C(value);
            } else {
                serveySection12Request.setQno214C("NA");
            }

            if (binding.checkBx214Odd.isChecked()) {
                String value = binding.checkBx214Odd.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214D(value);
            } else {
                serveySection12Request.setQno214D("NA");
            }

            if (binding.checkBx214Cd.isChecked()) {
                String value = binding.checkBx214Cd.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214E(value);
            } else {
                serveySection12Request.setQno214E("NA");
            }

            if (binding.checkBx214Ad.isChecked()) {
                String value = binding.checkBx214Ad.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214F(value);
            } else {
                serveySection12Request.setQno214F("NA");
            }
            if (binding.checkBx214Depression.isChecked()) {
                String value = binding.checkBx214Depression.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214G(value);
            } else {
                serveySection12Request.setQno214G("NA");
            }
            if (binding.checkBx214Taada.isChecked()) {
                String value = binding.checkBx214Taada.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214H(value);
            } else {
                serveySection12Request.setQno214H("NA");
            }
            if (binding.checkBx214Omilp.isChecked()) {
                String value = binding.checkBx214Omilp.getText().toString();
                value = value.substring(2);
                serveySection12Request.setQno214I(value);
            } else {
                serveySection12Request.setQno214I("NA");
            }
            if (binding.heardVisitedOptions.getCheckedRadioButtonId() != -1) {
                serveySection12Request.setQno215(Integer.toString(getCheckedRadioGrpID215(binding.heardVisitedOptions.getCheckedRadioButtonId(), binding.haveNotVisited.getId(), binding.visited.getId(), binding.norVisited.getId())));
            } else {
                serveySection12Request.setQno215("NA");
            }
            if (!binding.a216Edittext.getText().toString().isEmpty()) {
                serveySection12Request.setQno216A(binding.a216Edittext.getText().toString());
            } else {
                serveySection12Request.setQno216A("NA");
            }
            if (!binding.b216Edittext.getText().toString().isEmpty()) {
                serveySection12Request.setQno216B(binding.b216Edittext.getText().toString());
            } else {
                serveySection12Request.setQno216B("NA");
            }
            if (!binding.c216Edittext.getText().toString().isEmpty()) {
                serveySection12Request.setQno216C(binding.c216Edittext.getText().toString());
            } else {
                serveySection12Request.setQno216C("NA");
            }
            if (!binding.km.getText().toString().isEmpty()) {
                serveySection12Request.setQno217(Integer.parseInt(binding.km.getText().toString()));
            } else {
                serveySection12Request.setQno217(-1);
            }
            if (binding.publicBuses.isChecked()) {
                serveySection12Request.setQno218A(binding.publicBuses.getText().toString());
                serveySection12Request.setQno218Specify("NA");
            } else {
                serveySection12Request.setQno218A("NA");
                serveySection12Request.setQno218Specify("NA");

            }

            if (binding.autoRickshaw.isChecked()) {
                serveySection12Request.setQno218B(binding.autoRickshaw.getText().toString());
                serveySection12Request.setQno218Specify("NA");
            } else {
                serveySection12Request.setQno218B("NA");
                serveySection12Request.setQno218Specify("NA");

            }
            if (binding.train.isChecked()) {
                serveySection12Request.setQno218C(binding.train.getText().toString());
                serveySection12Request.setQno218Specify("NA");
            } else {
                serveySection12Request.setQno218C("NA");
                serveySection12Request.setQno218Specify("NA");

            }

            if (binding.privateBuses.isChecked()) {
                serveySection12Request.setQno218D(binding.privateBuses.getText().toString());
                serveySection12Request.setQno218Specify("NA");
            } else {
                serveySection12Request.setQno218D("NA");
                serveySection12Request.setQno218Specify("NA");

            }

            if (binding.taxis.isChecked()) {
                serveySection12Request.setQno218E(binding.taxis.getText().toString());
                serveySection12Request.setQno218Specify("NA");
            } else {
                serveySection12Request.setQno218E("NA");
                serveySection12Request.setQno218Specify("NA");

            }

            if (binding.others218.isChecked()) {
                serveySection12Request.setQno218F(binding.others218.getText().toString());
                serveySection12Request.setQno218Specify(binding.othersSpecify218.getText().toString());
            } else {
                serveySection12Request.setQno218F("NA");
                serveySection12Request.setQno218Specify("NA");

            }
            if (binding.a219Radiogroup.getCheckedRadioButtonId() != -1) {
                serveySection12Request.setQno219A(getCheckedRadioGrpID219A(binding.a219Radiogroup.getCheckedRadioButtonId(), binding.Easy.getId(), binding.someWhatPossible.getId(), binding.veryDifficult.getId(), binding.notPossible.getId()));
            } else {
                serveySection12Request.setQno219A("NA");
            }
            if (binding.b219Radiogroup.getCheckedRadioButtonId() != -1) {
                serveySection12Request.setQno219B(getCheckedRadioGrpID219B(binding.b219Radiogroup.getCheckedRadioButtonId(), binding.beasy.getId(), binding.bsomeWhatPossible.getId(), binding.bveryDifficult.getId(), binding.bnotPossible.getId()));
            } else {
                serveySection12Request.setQno219B("NA");
            }
            if (binding.c219Radiogroup.getCheckedRadioButtonId() != -1) {
                serveySection12Request.setQno219C(getCheckedRadioGrpID219C(binding.c219Radiogroup.getCheckedRadioButtonId(), binding.ceasy.getId(), binding.csomeWhatPossible.getId(), binding.cveryDifficult.getId(), binding.cnotPossible.getId()));
            } else {
                serveySection12Request.setQno219C("NA");
            }


            binding.progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            apiService.putServeySection12AData(eligibleResponse.houseHoldId, serveySection12Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    if (Float.parseFloat(ageValue) < 2.0f) {
                        Intent intent = new Intent(Section12Activity.this, Section12BActivity.class);
                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intent.putExtra(SURVEY_ID, surveyID);
                        intent.putExtra(AGE_ID, ageValue);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                        startActivity(intent);
                    } else {
                        //if the result is +ve we need to navigate
                        Util.showToast(activity, "Successfully data saved");
                        Intent intent = new Intent(activity, Section12BActivity.class);
                        intent.putExtra(AGE_ID, ageValue);
                        intent.putExtra(SURVEY_ID, surveyID);
                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    private boolean isCheckBxGrp214Checked() {
        if (binding.checkBx214Autism.isChecked()) {
            return true;
        }
        if (binding.checkBx214Ad.isChecked()) {
            return true;
        }
        if (binding.checkBx214Cd.isChecked()) {
            return true;
        }
        if (binding.checkBx214Depression.isChecked()) {
            return true;
        }
        if (binding.checkBx214Id.isChecked()) {
            return true;
        }
        if (binding.checkBx214Adhd.isChecked()) {
            return true;
        }
        if (binding.checkBx214Omilp.isChecked()) {
            return true;
        }
        if (binding.checkBx214Odd.isChecked()) {
            return true;
        }
        if (binding.checkBx214Taada.isChecked()) {
            return true;
        }
        return false;
    }

    private String getCheckedRadioGrpID212(int checkedRadioButtonId, int yesId, int noId) {
        if (checkedRadioButtonId == yesId) {
            return "Mental Health";
        } else if (checkedRadioButtonId == noId) {
            return "96";
        }
        return "NA";
    }

    private int getCheckedRadioGrpID215(int checkedRadioButtonId, int yesId, int noId, int norVisited) {
        if (checkedRadioButtonId == yesId) {
            return 1;
        } else if (checkedRadioButtonId == noId) {
            return 2;
        } else if (checkedRadioButtonId == norVisited) {
            return 3;
        }
        return -1;
    }

    private String getCheckedRadioGrpID219A(int checkedRadioButtonId, int easy, int some_what_possible, int very_difficult, int not_possible) {
        if (checkedRadioButtonId == easy) {
            return "1";
        } else if (checkedRadioButtonId == some_what_possible) {
            return "2";
        } else if (checkedRadioButtonId == very_difficult) {
            return "3";
        } else if (checkedRadioButtonId == not_possible) {
            return "4";
        }
        return "NA";
    }

    private String getCheckedRadioGrpID219B(int checkedRadioButtonId, int easy, int some_what_possible, int very_difficult, int not_possible) {
        if (checkedRadioButtonId == easy) {
            return "1";
        } else if (checkedRadioButtonId == some_what_possible) {
            return "2";
        } else if (checkedRadioButtonId == very_difficult) {
            return "3";
        } else if (checkedRadioButtonId == not_possible) {
            return "4";
        }
        return "NA";
    }

    private String getCheckedRadioGrpID219C(int checkedRadioButtonId, int easy, int some_what_possible, int very_difficult, int not_possible) {
        if (checkedRadioButtonId == easy) {
            return "1";
        } else if (checkedRadioButtonId == some_what_possible) {
            return "2";
        } else if (checkedRadioButtonId == very_difficult) {
            return "3";
        } else if (checkedRadioButtonId == not_possible) {
            return "4";
        }
        return "NA";
    }

    private int getCheckedRadioGrpID(int checkedRadioButtonId, int yesId, int noId) {
        if (checkedRadioButtonId == yesId) {
            return 1;
        } else if (checkedRadioButtonId == noId) {
            return 0;
        }
        return -1;
    }

    public void onClickPreviousSection(View v) {
//        startActivity(new Intent(activity, Section11Activity.class));
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section12Activity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }
}
