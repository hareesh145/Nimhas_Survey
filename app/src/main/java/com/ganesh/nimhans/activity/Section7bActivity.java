package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection7bBinding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.Util;

public class Section7bActivity extends AppCompatActivity {
    Activity activity;
    private ActivitySection7bBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    ImageView eye_contact_image,social_smile_image,Remain_aloof_image,reach_out_image,engage_solitary_image,not_maintain_image,sustain_conversation_image,engage_stereotyped_image,inanimate_objects_image,respond_objects_image;
    TextView eye_contact,social_smile,Remain_aloof,reach_out,engage_solitary,not_maintain,sustain_conversation,engage_stereotyped,inanimate_objects,respond_objects;
    MyNimhans myGameApp;
    private long demoGraphicsID;
    private int surveyID;
    private EligibleResponse eligibleResponse;
    private String ageValue;

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
        engage_stereotyped = findViewById(R.id.engage_stereotyped);
        inanimate_objects_image = findViewById(R.id.inanimate_objects_image);
        inanimate_objects = findViewById(R.id.inanimate_objects);
        respond_objects_image = findViewById(R.id.respond_objects_image);
        respond_objects = findViewById(R.id.respond_objects);
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
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
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
    public void onClickEyeContact(View v){
        if (eye_contact.getVisibility() == View.GONE){
            eye_contact.setVisibility(View.VISIBLE);
        }else {
            eye_contact.setVisibility(View.GONE);
        }

    }
    public void onClickSocialSmile(View v){
        if (social_smile.getVisibility() == View.GONE){
            social_smile.setVisibility(View.VISIBLE);
        }else {
            social_smile.setVisibility(View.GONE);
        }
    }
    public void onClickRemainAloof(View v){
        if (Remain_aloof.getVisibility() == View.GONE){
            Remain_aloof.setVisibility(View.VISIBLE);
        }else {
            Remain_aloof.setVisibility(View.GONE);
        }
    }
    public void onClickReachOut(View v){
        if (reach_out.getVisibility() == View.GONE){
            reach_out.setVisibility(View.VISIBLE);
        }else {
            reach_out.setVisibility(View.GONE);
        }
    }
    public void onClickEngageSolitary(View v){
        if (engage_solitary.getVisibility() == View.GONE){
            engage_solitary.setVisibility(View.VISIBLE);
        }else {
            engage_solitary.setVisibility(View.GONE);
        }
    }
    public void onClickNotMaintain(View v){
        if (not_maintain.getVisibility() == View.GONE){
            not_maintain.setVisibility(View.VISIBLE);
        }else {
            not_maintain.setVisibility(View.GONE);
        }
    }
    public void onClickSustainConversation(View v){
        if (sustain_conversation.getVisibility() == View.GONE){
            sustain_conversation.setVisibility(View.VISIBLE);
        }else {
            sustain_conversation.setVisibility(View.GONE);
        }
    }
    public void onClickEngageStereotyped(View v){
        if (engage_stereotyped.getVisibility() == View.GONE){
            engage_stereotyped.setVisibility(View.VISIBLE);
        }else {
            engage_stereotyped.setVisibility(View.GONE);
        }
    }
    public void onClickInanimateObjects(View v){
        if (inanimate_objects.getVisibility() == View.GONE){
            inanimate_objects.setVisibility(View.VISIBLE);
        }else {
            inanimate_objects.setVisibility(View.GONE);
        }
    }
    public void onClickRespondObjects(View v){
        if (respond_objects.getVisibility() == View.GONE){
            respond_objects.setVisibility(View.VISIBLE);
        }else {
            respond_objects.setVisibility(View.GONE);
        }
    }
}
