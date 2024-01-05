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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection7bBinding;
import com.ganesh.nimhans.model.ServeySection7bRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section7bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7bBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    ImageView eye_contact_image, social_smile_image, Remain_aloof_image, reach_out_image, engage_solitary_image, not_maintain_image, sustain_conversation_image, engage_stereotyped_image, inanimate_objects_image, respond_objects_image;
    TextView eye_contact, social_smile, Remain_aloof, reach_out, engage_solitary, not_maintain, sustain_conversation, engage_stereotyped, inanimate_objects, respond_objects;
    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private EligibleResponse eligibleResponse;
    private String ageValue;
    private String respondentTxt;
    EditText iasq_respondendt_txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection7bBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);

        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);

        myGameApp = (MyNimhans) activity.getApplicationContext();
        eye_contact_image = findViewById(R.id.eye_contact_image);
        eye_contact = findViewById(R.id.eye_contact);
        social_smile = findViewById(R.id.social_smile);
        social_smile_image = findViewById(R.id.social_smile_image);
        Remain_aloof_image = findViewById(R.id.Remain_aloof_image);
        Remain_aloof = findViewById(R.id.Remain_aloof);
        reach_out_image = findViewById(R.id.reach_out_image);
        reach_out = findViewById(R.id.reach_out);
        engage_solitary_image = findViewById(R.id.engage_solitary_image);
        engage_solitary = findViewById(R.id.engage_solitary);
        not_maintain_image = findViewById(R.id.not_maintain_image);
        not_maintain = findViewById(R.id.not_maintain);
        sustain_conversation_image = findViewById(R.id.sustain_conversation_image);
        sustain_conversation = findViewById(R.id.sustain_conversation);
        engage_stereotyped_image = findViewById(R.id.engage_stereotyped_image);
        inanimate_objects_image = findViewById(R.id.inanimate_objects_image);
        inanimate_objects = findViewById(R.id.inanimate_objects);
        respond_objects_image = findViewById(R.id.respond_objects_image);
        respond_objects = findViewById(R.id.respond_objects);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        iasq_respondendt_txt = findViewById(R.id.iasq_respondendt_txt);

        phoneNo = myGameApp.getUserPhoneNo();
        binding.childAge.setText(ageValue);


        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });

        binding.iasqRespondendtGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        respondentTxt = "Mother";
                        binding.iasqRespondendtTxt.setVisibility(View.GONE);
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.iasqRespondendtTxt.setVisibility(View.GONE);
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.iasqRespondendtTxt.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection7bRequest serveySection7bRequest = new ServeySection7bRequest();
        if (respondentTxt == null) {
            respondentTxt = binding.iasqRespondendtTxt.getText().toString();
        }
        serveySection7bRequest.iasqRespondent = respondentTxt;
        serveySection7bRequest.qno98 = getCheckedID(binding.q98.getCheckedRadioButtonId(), R.id.q98a, R.id.q98b);
        serveySection7bRequest.qno99 = getCheckedID(binding.q99.getCheckedRadioButtonId(), R.id.q99a, R.id.q99b);
        serveySection7bRequest.qno100 = getCheckedID(binding.q100.getCheckedRadioButtonId(), R.id.q100a, R.id.q100b);

        serveySection7bRequest.qno101 = getCheckedID(binding.q101.getCheckedRadioButtonId(), R.id.q101a, R.id.q101b);
        serveySection7bRequest.qno102 = getCheckedID(binding.q102.getCheckedRadioButtonId(), R.id.q102a, R.id.q102b);
        serveySection7bRequest.qno103 = getCheckedID(binding.q103.getCheckedRadioButtonId(), R.id.q103a, R.id.q103b);
        serveySection7bRequest.qno104 = getCheckedID(binding.q104.getCheckedRadioButtonId(), R.id.q104a, R.id.q104b);
        serveySection7bRequest.qno105 = getCheckedID(binding.q105.getCheckedRadioButtonId(), R.id.q105a, R.id.q105b);
        serveySection7bRequest.qno106 = getCheckedID(binding.q106.getCheckedRadioButtonId(), R.id.q106a, R.id.q106b);
        serveySection7bRequest.qno107 = getCheckedID(binding.q107.getCheckedRadioButtonId(), R.id.q107a, R.id.q107b);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.putServeySection7bData(eligibleResponse.houseHoldId, serveySection7bRequest, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        int screenPositiveNegative = 0;
                        if (calculateIASQResult() >= 1) {
                            screenPositiveNegative = 1;
                        }
                        binding.iasqResultTxt.setText(calculateIASQResult() + " - "  + screenPositiveNegative);
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.iasqResultTxt.setText("0");
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private int calculateIASQResult() {
        return getSelectedYesAs1(binding.q98.getCheckedRadioButtonId(), binding.q98a.getId()) +
                getSelectedYesAs1(binding.q99.getCheckedRadioButtonId(), binding.q99a.getId()) +
                getSelectedYesAs1(binding.q100.getCheckedRadioButtonId(), binding.q100a.getId()) +
                getSelectedYesAs1(binding.q101.getCheckedRadioButtonId(), binding.q101a.getId()) +
                getSelectedYesAs1(binding.q102.getCheckedRadioButtonId(), binding.q102a.getId()) +
                getSelectedYesAs1(binding.q103.getCheckedRadioButtonId(), binding.q103a.getId()) +
                getSelectedYesAs1(binding.q104.getCheckedRadioButtonId(), binding.q104a.getId()) +
                getSelectedYesAs1(binding.q105.getCheckedRadioButtonId(), binding.q105a.getId()) +
                getSelectedYesAs1(binding.q106.getCheckedRadioButtonId(), binding.q106a.getId()) +
                getSelectedYesAs1(binding.q107.getCheckedRadioButtonId(), binding.q107a.getId());
    }

    private int getSelectedYesAs1(int checkedID, int yes_id) {
        if (checkedID == yes_id) {
            return 1;
        } else {
            return 0;
        }
    }


    private int getCheckedID(int checkedID, int yes_id, int no_id) {
        if (checkedID == yes_id) {
            return 1;
        } else {
            return 2;
        }
    }

    public void onClickNextSection(View v) {
        /*Intent intent = new Intent(activity, Section8Activity.class);
        Util.showToast(activity, "Successfully data saved");
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);*/
        if (Float.parseFloat(ageValue) >= 4.0f && Float.parseFloat(ageValue) <= 7.0f) {
            Intent intent = new Intent(Section7bActivity.this, Section9Activity.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
            startActivity(intent);
        } else if (Float.parseFloat(ageValue) >= 8.0f) {
            Intent intent = new Intent(Section7bActivity.this, Section8Activity.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            intent.putExtra(AGE_ID, ageValue);
            intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
            intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
            startActivity(intent);
        }

    }

    public void onClickPreviousSection(View v) {
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section7bActivity.this, ChildrenResult.class);
        startActivity(intent);
    }

    public void onClickEyeContact(View v) {
        if (binding.qus98Info.getVisibility() == View.GONE) {
            binding.qus98Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus98Info.setVisibility(View.GONE);
        }

    }

    public void onClickSocialSmile(View v) {
        if (binding.qus99Info.getVisibility() == View.GONE) {
            binding.qus99Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus99Info.setVisibility(View.GONE);
        }
    }

    public void onClickRemainAloof(View v) {
        if (binding.qus100Info.getVisibility() == View.GONE) {
            binding.qus100Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus100Info.setVisibility(View.GONE);
        }
    }

    public void onClickReachOut(View v) {
        if (binding.qus101Info.getVisibility() == View.GONE) {
            binding.qus101Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus101Info.setVisibility(View.GONE);
        }
    }

    public void onClickEngageSolitary(View v) {
        if (binding.qus102Info.getVisibility() == View.GONE) {
            binding.qus102Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus102Info.setVisibility(View.GONE);
        }
    }

    public void onClickNotMaintain(View v) {
        if (binding.qus103Info.getVisibility() == View.GONE) {
            binding.qus103Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus103Info.setVisibility(View.GONE);
        }
    }

    public void onClickSustainConversation(View v) {
        if (binding.qus104Info.getVisibility() == View.GONE) {
            binding.qus104Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus104Info.setVisibility(View.GONE);
        }
    }

    public void onClickEngageStereotyped(View v) {
        if (binding.qus105Info.getVisibility() == View.GONE) {
            binding.qus105Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus105Info.setVisibility(View.GONE);
        }
    }

    public void onClickInanimateObjects(View v) {
        if (binding.qus106Info.getVisibility() == View.GONE) {
            binding.qus106Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus106Info.setVisibility(View.GONE);
        }
    }

    public void onClickRespondObjects(View v) {
        if (binding.qus107Info.getVisibility() == View.GONE) {
            binding.qus107Info.setVisibility(View.VISIBLE);
        } else {
            binding.qus107Info.setVisibility(View.GONE);
        }
    }
}
