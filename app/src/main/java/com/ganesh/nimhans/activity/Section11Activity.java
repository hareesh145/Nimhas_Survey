package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS11_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection11Binding;
import com.ganesh.nimhans.model.ServeySection11Request;
import com.ganesh.nimhans.model.ServeySection3cRequest;
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

public class Section11Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection11Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    EditText social_phobia_txt, panic_disorder_txt;

    MyNimhans myGameApp;
    private String ageValue;
    private int surveyID;
    private long demoGraphicsID;
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    private String respondentTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection11Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        phoneNo = myGameApp.getUserPhoneNo();
        social_phobia_txt = findViewById(R.id.social_phobia_txt);
        panic_disorder_txt = findViewById(R.id.panic_disorder_txt);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        binding.checkAge.setText(ageValue);
        binding.section11RespondentGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mother_btn:
                        binding.section11Respondent.setVisibility(View.GONE);
                        break;
                    case R.id.father_btn:
                        respondentTxt = "Father";
                        binding.section11Respondent.setVisibility(View.GONE);
                        break;
                    case R.id.gaurdian_btn:
                        respondentTxt = "Guardian";
                        binding.section11Respondent.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        binding.rcadsScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRCADSScore();
            }
        });
    }

    private void checkRCADSScore() {
        ServeySection11Request serveySection11Request = new ServeySection11Request();
        int checkedRadioButtonId = binding.section11RespondentGrp.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            serveySection11Request.setSection11Respondent(binding.section11Respondent.getText().toString());
        } else {
            serveySection11Request.setSection11Respondent(respondentTxt);
        }
        serveySection11Request.setQno160(getCheckedIDValue(binding.question160Grp.getCheckedRadioButtonId(), R.id.question_160_a, R.id.question_160_b, R.id.question_160_c, R.id.question_160_d));
        serveySection11Request.setQno161(getCheckedIDValue(binding.question161.getCheckedRadioButtonId(), R.id.question_161_a, R.id.question_161_b, R.id.question_161_c, R.id.question_161_d));
        serveySection11Request.setQno162(getCheckedIDValue(binding.question162.getCheckedRadioButtonId(), R.id.question_162_a, R.id.question_162_b, R.id.question_162_c, R.id.question_162_d));
        serveySection11Request.setQno163(getCheckedIDValue(binding.question163.getCheckedRadioButtonId(), R.id.question_163_a, R.id.question_163_b, R.id.question_163_c, R.id.question_163_d));
        serveySection11Request.setQno164(getCheckedIDValue(binding.question164.getCheckedRadioButtonId(), R.id.question_164_a, R.id.question_164_b, R.id.question_164_c, R.id.question_164_d));
        serveySection11Request.setQno165(getCheckedIDValue(binding.question165.getCheckedRadioButtonId(), R.id.question_165_a, R.id.question_165_b, R.id.question_165_c, R.id.question_165_d));
        serveySection11Request.setQno166(getCheckedIDValue(binding.question166.getCheckedRadioButtonId(), R.id.question_166_a, R.id.question_166_b, R.id.question_166_c, R.id.question_166_d));
        serveySection11Request.setQno167(getCheckedIDValue(binding.question167.getCheckedRadioButtonId(), R.id.question_167_a, R.id.question_167_b, R.id.question_167_c, R.id.question_167_d));
        serveySection11Request.setQno168(getCheckedIDValue(binding.question168.getCheckedRadioButtonId(), R.id.question_168_a, R.id.question_168_b, R.id.question_168_c, R.id.question_168_d));
        serveySection11Request.setQno169(getCheckedIDValue(binding.question169.getCheckedRadioButtonId(), R.id.question_169_a, R.id.question_169_b, R.id.question_169_c, R.id.question_169_d));
        serveySection11Request.setQno170(getCheckedIDValue(binding.question170.getCheckedRadioButtonId(), R.id.question_170_a, R.id.question_170_b, R.id.question_170_c, R.id.question_170_d));
        serveySection11Request.setQno171(getCheckedIDValue(binding.question171.getCheckedRadioButtonId(), R.id.question_171_a, R.id.question_171_b, R.id.question_171_c, R.id.question_171_d));
        serveySection11Request.setQno172(getCheckedIDValue(binding.question172.getCheckedRadioButtonId(), R.id.question_172_a, R.id.question_172_b, R.id.question_172_c, R.id.question_172_d));
        serveySection11Request.setQno173(getCheckedIDValue(binding.question173.getCheckedRadioButtonId(), R.id.question_173_a, R.id.question_173_b, R.id.question_173_c, R.id.question_173_d));
        serveySection11Request.setQno174(getCheckedIDValue(binding.question174.getCheckedRadioButtonId(), R.id.question_174_a, R.id.question_174_b, R.id.question_174_c, R.id.question_174_d));
        serveySection11Request.setQno175(getCheckedIDValue(binding.question175.getCheckedRadioButtonId(), R.id.question_175_a, R.id.question_175_b, R.id.question_175_c, R.id.question_175_d));
        serveySection11Request.setQno176(getCheckedIDValue(binding.question176.getCheckedRadioButtonId(), R.id.question_176_a, R.id.question_176_b, R.id.question_176_c, R.id.question_176_d));
        serveySection11Request.setQno177(getCheckedIDValue(binding.question177.getCheckedRadioButtonId(), R.id.question_177_a, R.id.question_177_b, R.id.question_177_c, R.id.question_177_d));
        serveySection11Request.setQno178(getCheckedIDValue(binding.question178.getCheckedRadioButtonId(), R.id.question_178_a, R.id.question_178_b, R.id.question_178_c, R.id.question_178_d));
        serveySection11Request.setQno179(getCheckedIDValue(binding.question179.getCheckedRadioButtonId(), R.id.question_179_a, R.id.question_179_b, R.id.question_179_c, R.id.question_179_d));
        serveySection11Request.setQno180(getCheckedIDValue(binding.question180.getCheckedRadioButtonId(), R.id.question_180_a, R.id.question_180_b, R.id.question_180_c, R.id.question_180_d));
        serveySection11Request.setQno181(getCheckedIDValue(binding.question181.getCheckedRadioButtonId(), R.id.question_181_a, R.id.question_181_b, R.id.question_181_c, R.id.question_181_d));
        serveySection11Request.setQno182(getCheckedIDValue(binding.question182.getCheckedRadioButtonId(), R.id.question_182_a, R.id.question_182_b, R.id.question_182_c, R.id.question_182_d));
        serveySection11Request.setQno183(getCheckedIDValue(binding.question183.getCheckedRadioButtonId(), R.id.question_183_a, R.id.question_183_b, R.id.question_183_c, R.id.question_183_d));
        serveySection11Request.setQno184(getCheckedIDValue(binding.question184.getCheckedRadioButtonId(), R.id.question_184_a, R.id.question_184_b, R.id.question_184_c, R.id.question_184_d));
        serveySection11Request.setQno185(getCheckedIDValue(binding.question185.getCheckedRadioButtonId(), R.id.question_185_a, R.id.question_185_b, R.id.question_185_c, R.id.question_185_d));
        serveySection11Request.setQno186(getCheckedIDValue(binding.question186.getCheckedRadioButtonId(), R.id.question_186_a, R.id.question_186_b, R.id.question_186_c, R.id.question_186_d));
        serveySection11Request.setQno187(getCheckedIDValue(binding.question187.getCheckedRadioButtonId(), R.id.question_187_a, R.id.question_187_b, R.id.question_187_c, R.id.question_187_d));
        serveySection11Request.setQno188(getCheckedIDValue(binding.question188.getCheckedRadioButtonId(), R.id.question_188_a, R.id.question_188_b, R.id.question_188_c, R.id.question_188_d));
        serveySection11Request.setQno189(getCheckedIDValue(binding.question189.getCheckedRadioButtonId(), R.id.question_189_a, R.id.question_189_b, R.id.question_189_c, R.id.question_189_d));
        serveySection11Request.setQno190(getCheckedIDValue(binding.question190.getCheckedRadioButtonId(), R.id.question_190_a, R.id.question_190_b, R.id.question_190_c, R.id.question_190_d));
        serveySection11Request.setQno191(getCheckedIDValue(binding.question191.getCheckedRadioButtonId(), R.id.question_191_a, R.id.question_191_b, R.id.question_191_c, R.id.question_191_d));
        serveySection11Request.setQno192(getCheckedIDValue(binding.question192.getCheckedRadioButtonId(), R.id.question_192_a, R.id.question_192_b, R.id.question_192_c, R.id.question_192_d));
        serveySection11Request.setQno193(getCheckedIDValue(binding.question193.getCheckedRadioButtonId(), R.id.question_193_a, R.id.question_193_b, R.id.question_193_c, R.id.question_193_d));
        serveySection11Request.setQno194(getCheckedIDValue(binding.question194.getCheckedRadioButtonId(), R.id.question_194_a, R.id.question_194_b, R.id.question_194_c, R.id.question_194_d));
        serveySection11Request.setQno195(getCheckedIDValue(binding.question195.getCheckedRadioButtonId(), R.id.question_195_a, R.id.question_195_b, R.id.question_195_c, R.id.question_195_d));
        serveySection11Request.setQno196(getCheckedIDValue(binding.question196.getCheckedRadioButtonId(), R.id.question_196_a, R.id.question_196_b, R.id.question_196_c, R.id.question_196_d));
        serveySection11Request.setQno197(getCheckedIDValue(binding.question197.getCheckedRadioButtonId(), R.id.question_197_a, R.id.question_197_b, R.id.question_197_c, R.id.question_197_d));
        serveySection11Request.setQno198(getCheckedIDValue(binding.question198.getCheckedRadioButtonId(), R.id.question_198_a, R.id.question_198_b, R.id.question_198_c, R.id.question_198_d));
        serveySection11Request.setQno199(getCheckedIDValue(binding.question199.getCheckedRadioButtonId(), R.id.question_199_a, R.id.question_199_b, R.id.question_199_c, R.id.question_199_d));
        serveySection11Request.setQno200(getCheckedIDValue(binding.question200.getCheckedRadioButtonId(), R.id.question_200_a, R.id.question_200_b, R.id.question_200_c, R.id.question_200_d));
        serveySection11Request.setQno201(getCheckedIDValue(binding.question201.getCheckedRadioButtonId(), R.id.question_201_a, R.id.question_201_b, R.id.question_201_c, R.id.question_201_d));
        serveySection11Request.setQno202(getCheckedIDValue(binding.question202.getCheckedRadioButtonId(), R.id.question_202_a, R.id.question_202_b, R.id.question_202_c, R.id.question_202_d));
        serveySection11Request.setQno203(getCheckedIDValue(binding.question203.getCheckedRadioButtonId(), R.id.question_203_a, R.id.question_203_b, R.id.question_203_c, R.id.question_203_d));
        serveySection11Request.setQno204(getCheckedIDValue(binding.question204.getCheckedRadioButtonId(), R.id.question_204_a, R.id.question_204_b, R.id.question_204_c, R.id.question_204_d));
        serveySection11Request.setQno205(getCheckedIDValue(binding.question205.getCheckedRadioButtonId(), R.id.question_205_a, R.id.question_205_b, R.id.question_205_c, R.id.question_205_d));
        serveySection11Request.setQno206(getCheckedIDValue(binding.question206.getCheckedRadioButtonId(), R.id.question_206_a, R.id.question_206_b, R.id.question_206_c, R.id.question_206_d));
        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        apiClient.putServeySection11AData(eligibleResponse.houseHoldId, serveySection11Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();
                if (response.isSuccessful()) {
                    // binding.cdResult.setText(response.body().get("cdResult").getAsString());
                    try {
                        binding.socialPhobiaTxt.setText(calculateSocialPhobia() + " - " + userResponse.get("parentSocialPhobia"));
                       /* if (userResponse.get("socialPhobia").getAsInt() >= 65) {
                            binding.socialPhobiaTxt.setText("1");
                        } else {
                            binding.socialPhobiaTxt.setText("0");
                        }*/
                        binding.panicDisorderTxt.setText(calculatePanicDisorder() + " - " + userResponse.get("parentPanicDisorder"));
                       /* if (userResponse.get("panicDisorder").getAsInt() >= 65) {
                            binding.panicDisorderTxt.setText("1");
                        } else {
                            binding.panicDisorderTxt.setText("0");
                        }*/
                        binding.majorDepressionTxt.setText(calculateMajorDepression() + " - " + userResponse.get("parentMajorDepression"));

                       /* if (userResponse.get("majorDepression").getAsInt() >= 65) {
                            binding.majorDepressionTxt.setText("1");
                        } else {
                            binding.majorDepressionTxt.setText("0");
                        }*/
                        binding.separationAnxietyTxt.setText(calculateSA() + " - " + userResponse.get("parentSeparationAnxiety"));
                       /* if (userResponse.get("separationAnxiety").getAsInt() >= 65) {
                            binding.separationAnxietyTxt.setText("1");
                        } else {
                            binding.separationAnxietyTxt.setText("0");
                        }
*/
                        binding.generalizedAnxietyTxt.setText(calculateGA() + " - " + userResponse.get("parentGeneralizedAnxiety"));
                       /* if (userResponse.get("generalizedAnxiety").getAsInt() >= 65) {
                            binding.generalizedAnxietyTxt.setText("1");
                        } else {
                            binding.generalizedAnxietyTxt.setText("0");
                        }*/
                        binding.obsessiveCompulsiveTxt.setText(calculateOC() + " - " + userResponse.get("parentObsessiveCompulsive"));

                        /*if (userResponse.get("obsessiveCompulsive").getAsInt() >= 65) {
                            binding.obsessiveCompulsiveTxt.setText("1");
                        } else {
                            binding.obsessiveCompulsiveTxt.setText("0");
                        }*/
                        int screenPositive = 0;
                        if (userResponse.get("parentSocialPhobia").getAsInt() >= 65
                                || userResponse.get("parentPanicDisorder").getAsInt() >= 65
                                || userResponse.get("parentMajorDepression").getAsInt() >= 65
                                || userResponse.get("parentSeparationAnxiety").getAsInt() >= 65
                                || userResponse.get("parentGeneralizedAnxiety").getAsInt() >= 65
                                || userResponse.get("parentObsessiveCompulsive").getAsInt() >= 65) {
                            screenPositive = 1;
                            binding.rcadsResult.setText("RCADS Self Screener : " + screenPositive);
                        } else {
                            binding.rcadsResult.setText("RCADS Self Screener : " + screenPositive);
                        }
                        PreferenceConnector.writeString(Section11Activity.this, RCADS11_RESULT, "" + screenPositive);
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


    private int calculateMajorDepression() {
//        (Q.161+Q.165+Q.170+Q.174+Q.179+Q.180+Q.184+Q.188+Q.199+Q.206)
        return getCheckedIDValue(binding.question161.getCheckedRadioButtonId(), R.id.question_161_a, R.id.question_161_b,
                R.id.question_161_c, R.id.question_161_d) +
                getCheckedIDValue(binding.question165.getCheckedRadioButtonId(), R.id.question_165_a, R.id.question_165_b,
                        R.id.question_165_c, R.id.question_165_d) +
                getCheckedIDValue(binding.question170.getCheckedRadioButtonId(), R.id.question_170_a, R.id.question_170_b,
                        R.id.question_170_c, R.id.question_170_d) +
                getCheckedIDValue(binding.question174.getCheckedRadioButtonId(), R.id.question_174_a, R.id.question_174_b,
                        R.id.question_174_c, R.id.question_174_d) +
                getCheckedIDValue(binding.question179.getCheckedRadioButtonId(), R.id.question_179_a, R.id.question_179_b,
                        R.id.question_179_c, R.id.question_179_d) +
                getCheckedIDValue(binding.question180.getCheckedRadioButtonId(), R.id.question_180_a, R.id.question_180_b,
                        R.id.question_180_c, R.id.question_180_d) +
                getCheckedIDValue(binding.question184.getCheckedRadioButtonId(), R.id.question_184_a, R.id.question_184_b,
                        R.id.question_184_c, R.id.question_184_d) +
                getCheckedIDValue(binding.question188.getCheckedRadioButtonId(), R.id.question_188_a, R.id.question_188_b,
                        R.id.question_188_c, R.id.question_188_d) +
                getCheckedIDValue(binding.question199.getCheckedRadioButtonId(), R.id.question_199_a, R.id.question_199_b,
                        R.id.question_199_c, R.id.question_199_d) +
                getCheckedIDValue(binding.question206.getCheckedRadioButtonId(), R.id.question_206_a, R.id.question_206_b,
                        R.id.question_206_c, R.id.question_206_d);
    }

    private int calculateSocialPhobia() {
        // (Q.163+Q.166+Q.167+Q.171+Q.179+Q.189+Q.191+Q.197+Q. 202)
        return getCheckedIDValue(binding.question163.getCheckedRadioButtonId(), R.id.question_163_a, R.id.question_163_b,
                R.id.question_163_c, R.id.question_163_d) +
                getCheckedIDValue(binding.question166.getCheckedRadioButtonId(), R.id.question_166_a, R.id.question_166_b,
                        R.id.question_166_c, R.id.question_166_d) +
                getCheckedIDValue(binding.question167.getCheckedRadioButtonId(), R.id.question_167_a, R.id.question_167_b,
                        R.id.question_167_c, R.id.question_167_d) +
                getCheckedIDValue(binding.question171.getCheckedRadioButtonId(), R.id.question_171_a, R.id.question_171_b,
                        R.id.question_171_c, R.id.question_171_d) +
                getCheckedIDValue(binding.question179.getCheckedRadioButtonId(), R.id.question_179_a, R.id.question_179_b,
                        R.id.question_179_c, R.id.question_179_d) +
                getCheckedIDValue(binding.question189.getCheckedRadioButtonId(), R.id.question_189_a, R.id.question_189_b,
                        R.id.question_189_c, R.id.question_189_d) +
                getCheckedIDValue(binding.question191.getCheckedRadioButtonId(), R.id.question_191_a, R.id.question_191_b,
                        R.id.question_191_c, R.id.question_191_d) +
                getCheckedIDValue(binding.question197.getCheckedRadioButtonId(), R.id.question_197_a, R.id.question_197_b,
                        R.id.question_197_c, R.id.question_197_d) +
                getCheckedIDValue(binding.question202.getCheckedRadioButtonId(), R.id.question_202_a, R.id.question_202_b,
                        R.id.question_202_c, R.id.question_202_d);
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Intent intent = new Intent(activity, Section12Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);

    }

    private int calculatePanicDisorder() {
//        (Q.162+Q.173+Q.183+Q.185+Q.187+Q.193+Q.195+Q.198+Q. 200)
        return getCheckedIDValue(binding.question162.getCheckedRadioButtonId(), R.id.question_162_a, R.id.question_162_b, R.id.question_162_c, R.id.question_162_d) +
                getCheckedIDValue(binding.question173.getCheckedRadioButtonId(), R.id.question_173_a, R.id.question_173_b, R.id.question_173_c, R.id.question_173_d) +
                getCheckedIDValue(binding.question183.getCheckedRadioButtonId(), R.id.question_183_a, R.id.question_183_b, R.id.question_183_c, R.id.question_183_d) +
                getCheckedIDValue(binding.question185.getCheckedRadioButtonId(), R.id.question_185_a, R.id.question_185_b, R.id.question_185_c, R.id.question_185_d) +
                getCheckedIDValue(binding.question187.getCheckedRadioButtonId(), R.id.question_187_a, R.id.question_187_b, R.id.question_187_c, R.id.question_187_d) +
                getCheckedIDValue(binding.question193.getCheckedRadioButtonId(), R.id.question_193_a, R.id.question_193_b, R.id.question_193_c, R.id.question_193_d) +
                getCheckedIDValue(binding.question195.getCheckedRadioButtonId(), R.id.question_195_a, R.id.question_195_b, R.id.question_195_c, R.id.question_195_d) +
                getCheckedIDValue(binding.question198.getCheckedRadioButtonId(), R.id.question_198_a, R.id.question_198_b, R.id.question_198_c, R.id.question_198_d) +
                getCheckedIDValue(binding.question200.getCheckedRadioButtonId(), R.id.question_200_a, R.id.question_200_b, R.id.question_200_c, R.id.question_200_d);
    }

    private int calculateSA() {
//        (Q.164+Q.168+Q.176+Q.177+Q.192+Q.204+Q.205)
        return getCheckedIDValue(binding.question164.getCheckedRadioButtonId(), R.id.question_164_a, R.id.question_164_b, R.id.question_164_c, R.id.question_164_d) +
                getCheckedIDValue(binding.question168.getCheckedRadioButtonId(), R.id.question_168_a, R.id.question_168_b, R.id.question_168_c, R.id.question_168_d) +
                getCheckedIDValue(binding.question176.getCheckedRadioButtonId(), R.id.question_176_a, R.id.question_176_b, R.id.question_176_c, R.id.question_176_d) +
                getCheckedIDValue(binding.question177.getCheckedRadioButtonId(), R.id.question_177_a, R.id.question_177_b, R.id.question_177_c, R.id.question_177_d) +
                getCheckedIDValue(binding.question192.getCheckedRadioButtonId(), R.id.question_192_a, R.id.question_192_b, R.id.question_192_c, R.id.question_192_d) +
                getCheckedIDValue(binding.question204.getCheckedRadioButtonId(), R.id.question_204_a, R.id.question_204_b, R.id.question_204_c, R.id.question_204_d) +
                getCheckedIDValue(binding.question205.getCheckedRadioButtonId(), R.id.question_205_a, R.id.question_205_b, R.id.question_205_c, R.id.question_205_d);
    }

    private int calculateOC() {
//        (Q.169+Q.175+Q.182+Q.190+Q.201+Q.203)
        return getCheckedIDValue(binding.question169.getCheckedRadioButtonId(), R.id.question_169_a, R.id.question_169_b, R.id.question_169_c, R.id.question_169_d) +
                getCheckedIDValue(binding.question175.getCheckedRadioButtonId(), R.id.question_175_a, R.id.question_175_b, R.id.question_175_c, R.id.question_175_d) +
                getCheckedIDValue(binding.question182.getCheckedRadioButtonId(), R.id.question_182_a, R.id.question_182_b, R.id.question_182_c, R.id.question_182_d) +
                getCheckedIDValue(binding.question190.getCheckedRadioButtonId(), R.id.question_190_a, R.id.question_190_b, R.id.question_190_c, R.id.question_190_d) +
                getCheckedIDValue(binding.question201.getCheckedRadioButtonId(), R.id.question_201_a, R.id.question_201_b, R.id.question_201_c, R.id.question_201_d) +
                getCheckedIDValue(binding.question203.getCheckedRadioButtonId(), R.id.question_203_a, R.id.question_203_b, R.id.question_203_c, R.id.question_203_d);
    }

    public int calculateGA() {
//        (Q.160+Q.172+Q.181+Q.186+Q.195+Q.196)
        return getCheckedIDValue(binding.question160Grp.getCheckedRadioButtonId(), R.id.question_160_a, R.id.question_160_b, R.id.question_160_c, R.id.question_160_d) +
                getCheckedIDValue(binding.question172.getCheckedRadioButtonId(), R.id.question_172_a, R.id.question_172_b, R.id.question_172_c, R.id.question_172_d) +
                getCheckedIDValue(binding.question181.getCheckedRadioButtonId(), R.id.question_181_a, R.id.question_181_b, R.id.question_181_c, R.id.question_181_d) +
                getCheckedIDValue(binding.question186.getCheckedRadioButtonId(), R.id.question_186_a, R.id.question_186_b, R.id.question_186_c, R.id.question_186_d) +
                getCheckedIDValue(binding.question195.getCheckedRadioButtonId(), R.id.question_195_a, R.id.question_195_b, R.id.question_195_c, R.id.question_195_d) +
                getCheckedIDValue(binding.question196.getCheckedRadioButtonId(), R.id.question_196_a, R.id.question_196_b, R.id.question_196_c, R.id.question_196_d);
    }

    public void onClickPreviousSection(View v) {
        finish();
    }

    private int getCheckedIDValue(int checkedRadioButtonId, int question_160_a, int question_160_b, int question_160_c, int question_160_d) {

        if (checkedRadioButtonId == question_160_d) {
            return 3;
        } else if (checkedRadioButtonId == question_160_b) {
            return 1;
        } else if (checkedRadioButtonId == question_160_c) {
            return 2;
        } else {
            return 0;
        }
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section11Activity.this, ParentResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

    public void onClickChildrenResult(View v) {
        Intent intent = new Intent(Section11Activity.this, ChildrenResult.class);
        startActivity(intent);
    }
}
