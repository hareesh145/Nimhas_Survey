package com.ganesh.nimhans.activity;


import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection4Binding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection4Request;
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

public class Section4Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    Activity activity;
    private ActivitySection4Binding binding;
    String phoneNo, pswd;
    EditText Specify7, ChildAge, SocialPhobia, PanicDisorder, MajorDepression, SeparationAnxiety,
            GeneralizedAnxiety, ObsessiveCompulsive;
    RadioGroup Anxiety, FeelingSad, FunnyFeeling, FeelingWorry, FeelingAfraid, NoFun,
            FeelingScared, FeelingAngry, WorryAboutParents, SillyThoughts, SleepingProblem,
            BadAtSchool, FeelingAwful, BreatheLessnessSuddenly, AppetiteProblem, CrossCheckOCD,
            ScaryWhileSleep, NoEnergy, FeelingFoolish, FeelingTired, NegativeThoughts,
            RemoveBadThoughtFromHead, HeartBeatHigh, CantThink, Shevirinhg, FeelingWorthless,
            AfraidOfMistakes, Superstition, SocialAnxietyDisorder, Agoraphobia, Apprehension,
            AnticipatoryAnxiety, SuddenSyncope, MorbidContemplation, Glossophobia, Palpitations,
            Immobility, AnxietyAboutAnxiety, OCD, SocialAnxiety, RitualisticBehavior, NighttimeAnxiety,
            SeparationAnxiety1, Restlessness, afraidGoingSchool;
    MyNimhans myGameApp;
    int selectedAnxiety;
    int selectedFeelingSad;
    int selectedFunnyFeeling;
    int selectedFeelingWorry;
    int selectedFeelingAfraid;
    int selectedNoFun;
    int selectedFeelingScared;
    int selectedFeelingAngry;
    int selectedWorryAboutParents;
    int selectedSillyThoughts;
    int selectedSleepingProblem;
    private Long demoGraphicsID;
    private int selectedBadAtSchool;
    private int selectedFeelingAwful, selectedBreatheLessnessSuddenly, selectedAppetiteProblem;
    private int selectedCrossCheckOCD;
    private int selectedScaryWhileSleep;
    private int selectedAfraidGoingSchool;
    private int selectedNoEnergy;
    private int selectedFeelingFoolish;
    private int selectedFeelingTired;
    private int selectedNegativeThoughts;
    private int selectedRemoveBadThoughtfromHead;
    private int selectedHeartBeatHigh;
    private int selectedCantThink;
    private int selectedShevirinhg;
    private RadioGroup badHallucination, feelingShaky;
    private int selectedBadHallucination;
    private int selectedFeelingShaky;
    private int selectedFeelingWorthless;
    private int selectedAfraidOfMistakes;
    private int selectedSuperstition;
    private int selectedSocialAnxietyDisorder;
    private int selectedAgoraphobia;
    private int selectedApprehension;
    private int selectedAnticipatoryAnxiety;
    private int selectedSuddenSyncope;
    private int selectedMorbidContemplation;
    private int selectedGlossophobia;
    private int selectedPalpitations;
    private int selectedImmobility;
    private int selectedAnxietyAboutAnxiety;
    private int selectedOCD;
    private int selectedSocialAnxiety;
    private int selectedRitualisticBehavior;
    private int selectedNighttimeAnxiety;
    private int selectedSeparationAnxiety1;
    private int selectedRestlessness;
    private int surveyID;
    private String ageValue;
    private String rcadsResult;
    EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    LocationManager locationManager;
    String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        Specify7 = findViewById(R.id.Specify7);
        ChildAge = findViewById(R.id.childAge);
        SocialPhobia = findViewById(R.id.SP15);
        PanicDisorder = findViewById(R.id.PD25);
        MajorDepression = findViewById(R.id.MD20);
        SeparationAnxiety = findViewById(R.id.SA35);
        GeneralizedAnxiety = findViewById(R.id.GA45);
        ObsessiveCompulsive = findViewById(R.id.OC34);
        Anxiety = findViewById(R.id.Anxiety);
        FeelingSad = findViewById(R.id.feelingSad);
        FunnyFeeling = findViewById(R.id.funnyFeeling);
        FeelingWorry = findViewById(R.id.feelingWorry);
        FeelingAfraid = findViewById(R.id.feelingAfraid);
        NoFun = findViewById(R.id.noFun);
        FeelingScared = findViewById(R.id.feelingScared);
        FeelingAngry = findViewById(R.id.feelingAngryWithMe);
        WorryAboutParents = findViewById(R.id.worryAboutParents);
        SillyThoughts = findViewById(R.id.sillyThoughts);
        SleepingProblem = findViewById(R.id.sleepingProblem);
        BadAtSchool = findViewById(R.id.badAtSchool);
        FeelingAwful = findViewById(R.id.feelingAwful);
        BreatheLessnessSuddenly = findViewById(R.id.CantBreatheSuddenly);
        AppetiteProblem = findViewById(R.id.appetiteProblem);
        CrossCheckOCD = findViewById(R.id.crossCheckOCD);
        ScaryWhileSleep = findViewById(R.id.scaryWhileSleep);
        NoEnergy = findViewById(R.id.noEnergy);
        FeelingFoolish = findViewById(R.id.feelingFoolish);
        FeelingTired = findViewById(R.id.feelingTired);
        NegativeThoughts = findViewById(R.id.negativeThoughts);
        RemoveBadThoughtFromHead = findViewById(R.id.removeBadThoughtfromHead);
        HeartBeatHigh = findViewById(R.id.heartBeatHigh);
        CantThink = findViewById(R.id.cantThink);
        Shevirinhg = findViewById(R.id.shevirinhg);
        FeelingWorthless = findViewById(R.id.feelingWorthless);
        AfraidOfMistakes = findViewById(R.id.afraidOfMistakes);
        Superstition = findViewById(R.id.superstition);
        SocialAnxietyDisorder = findViewById(R.id.socialAnxietyDisorder);
        Agoraphobia = findViewById(R.id.agoraphobia);
        Apprehension = findViewById(R.id.apprehension);
        AnticipatoryAnxiety = findViewById(R.id.anticipatoryAnxiety);
        SuddenSyncope = findViewById(R.id.suddenSyncope);
        MorbidContemplation = findViewById(R.id.morbidContemplation);
        Glossophobia = findViewById(R.id.glossophobia);
        Palpitations = findViewById(R.id.palpitations);
        Immobility = findViewById(R.id.immobility);
        AnxietyAboutAnxiety = findViewById(R.id.anxietyAboutAnxiety);
        OCD = findViewById(R.id.OCD);
        SocialAnxiety = findViewById(R.id.socialAnxiety);
        RitualisticBehavior = findViewById(R.id.ritualisticBehavior);
        NighttimeAnxiety = findViewById(R.id.nighttimeAnxiety);
        SeparationAnxiety1 = findViewById(R.id.separationAnxiety1);
        Restlessness = findViewById(R.id.restlessness);

        afraidGoingSchool = findViewById(R.id.afraidGoingSchool);
        badHallucination = findViewById(R.id.badHallucination);
        feelingShaky = findViewById(R.id.feelingShaky);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.childAge.setText(ageValue);
        phoneNo = myGameApp.getUserPhoneNo();
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        binding.childNameAge.setText(eligibleResponse.qno9 + " Age");
       /* ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }*/
        Anxiety.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAnxiety = getSelectedValuePosition(selectedValue);
            Log.d("selectedAnxiety", "Selected value: " + selectedAnxiety);
        });
        FeelingSad.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingSad = getSelectedValuePosition(selectedValue);
            Log.d("selectedFeelingSad", "Selected value: " + selectedFeelingSad);
        });
        FunnyFeeling.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFunnyFeeling = getSelectedValuePosition(selectedValue);
            Log.d("selectedFunnyFeeling", "Selected value: " + selectedFunnyFeeling);
        });
        FeelingWorry.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingWorry = getSelectedValuePosition(selectedValue);
            Log.d("selectedFeelingWorry", "Selected value: " + selectedFeelingWorry);
        });
        FeelingAfraid.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingAfraid = getSelectedValuePosition(selectedValue);
            Log.d("selectedFeelingAfraid", "Selected value: " + selectedFeelingAfraid);
        });
        NoFun.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedNoFun = getSelectedValuePosition(selectedValue);
            Log.d("selectedNoFun", "Selected value: " + selectedNoFun);
        });
        FeelingScared.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingScared = getSelectedValuePosition(selectedValue);
            Log.d("selectedFeelingScared", "Selected value: " + selectedFeelingScared);
        });
        FeelingAngry.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingAngry = getSelectedValuePosition(selectedValue);
            Log.d("selectedFeelingAngry", "Selected value: " + selectedFeelingAngry);
        });
        WorryAboutParents.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedWorryAboutParents = getSelectedValuePosition(selectedValue);
            Log.d("selectedWorryAboutParents", "Selected value: " + selectedWorryAboutParents);
        });
        SillyThoughts.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSillyThoughts = getSelectedValuePosition(selectedValue);
            Log.d("selectedSillyThoughts", "Selected value: " + selectedSillyThoughts);
        });
        SleepingProblem.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedSleepingProblem = getSelectedValuePosition(selectedValue);
            Log.d("selectedSleepingProblem", "Selected value: " + selectedSleepingProblem);
        });
        BadAtSchool.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedBadAtSchool = getSelectedValuePosition(selectedValue);
            Log.d("selectedSleepingProblem", "Selected value: " + selectedSleepingProblem);
        });
        FeelingAwful.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedFeelingAwful = getSelectedValuePosition(selectedValue);
            Log.d("selectedSleepingProblem", "Selected value: " + selectedSleepingProblem);
        });

        BreatheLessnessSuddenly.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedBreatheLessnessSuddenly = getSelectedValuePosition(selectedValue);
            Log.d("selectedSleepingProblem", "Selected value: " + selectedSleepingProblem);
        });

        AppetiteProblem.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedAppetiteProblem = getSelectedValuePosition(selectedValue);
            Log.d("selectedSleepingProblem", "Selected value: " + selectedSleepingProblem);
        });

        binding.rcadsScore.setOnClickListener(v -> checkRCADSValue());

        CrossCheckOCD.setOnCheckedChangeListener(this);
        ScaryWhileSleep.setOnCheckedChangeListener(this);
        NoEnergy.setOnCheckedChangeListener(this);
        FeelingFoolish.setOnCheckedChangeListener(this);
        afraidGoingSchool.setOnCheckedChangeListener(this);
        FeelingTired.setOnCheckedChangeListener(this);
        NegativeThoughts.setOnCheckedChangeListener(this);
        RemoveBadThoughtFromHead.setOnCheckedChangeListener(this);
        HeartBeatHigh.setOnCheckedChangeListener(this);
        CantThink.setOnCheckedChangeListener(this);
        Shevirinhg.setOnCheckedChangeListener(this);
        badHallucination.setOnCheckedChangeListener(this);
        feelingShaky.setOnCheckedChangeListener(this);
        FeelingWorthless.setOnCheckedChangeListener(this);
        AfraidOfMistakes.setOnCheckedChangeListener(this);
        Superstition.setOnCheckedChangeListener(this);
        SocialAnxietyDisorder.setOnCheckedChangeListener(this);
        Agoraphobia.setOnCheckedChangeListener(this);
        Apprehension.setOnCheckedChangeListener(this);
        AnticipatoryAnxiety.setOnCheckedChangeListener(this);
        SuddenSyncope.setOnCheckedChangeListener(this);
        MorbidContemplation.setOnCheckedChangeListener(this);
        Glossophobia.setOnCheckedChangeListener(this);
        Palpitations.setOnCheckedChangeListener(this);
        NighttimeAnxiety.setOnCheckedChangeListener(this);
        binding.immobility.setOnCheckedChangeListener(this);
        binding.anxietyAboutAnxiety.setOnCheckedChangeListener(this);
        binding.OCD.setOnCheckedChangeListener(this);
        binding.socialAnxiety.setOnCheckedChangeListener(this);
        binding.ritualisticBehavior.setOnCheckedChangeListener(this);
        binding.separationAnxiety1.setOnCheckedChangeListener(this);
        binding.restlessness.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = findViewById(checkedId);
        String selectedValue = radioButton.getText().toString();

        switch (group.getId()) {
            case R.id.crossCheckOCD:
                selectedCrossCheckOCD = getSelectedValuePosition(selectedValue);
                break;
            case R.id.scaryWhileSleep: //35
                selectedScaryWhileSleep = getSelectedValuePosition(selectedValue);
                break;
            case R.id.afraidGoingSchool: // 36
                selectedAfraidGoingSchool = getSelectedValuePosition(selectedValue);
                break;
            case R.id.noEnergy: //37
                selectedNoEnergy = getSelectedValuePosition(selectedValue);
                break;
            case R.id.feelingFoolish: //38
                selectedFeelingFoolish = getSelectedValuePosition(selectedValue);
                break;
            case R.id.feelingTired: //39
                selectedFeelingTired = getSelectedValuePosition(selectedValue);
                break;
            case R.id.negativeThoughts: //40
                selectedNegativeThoughts = getSelectedValuePosition(selectedValue);
                break;
            case R.id.removeBadThoughtfromHead:  //41
                selectedRemoveBadThoughtfromHead = getSelectedValuePosition(selectedValue);
                break;
            case R.id.heartBeatHigh:  //42
                selectedHeartBeatHigh = getSelectedValuePosition(selectedValue);
                break;
            case R.id.cantThink:  //43
                selectedCantThink = getSelectedValuePosition(selectedValue);
                break;
            case R.id.shevirinhg:  //44
                selectedShevirinhg = getSelectedValuePosition(selectedValue);
                break;
            case R.id.badHallucination:  //45
                selectedBadHallucination = getSelectedValuePosition(selectedValue);
                break;
            case R.id.feelingShaky:  //46
                selectedFeelingShaky = getSelectedValuePosition(selectedValue);
                break;
            case R.id.feelingWorthless:  //47
                selectedFeelingWorthless = getSelectedValuePosition(selectedValue);
                break;
            case R.id.afraidOfMistakes:  //48
                selectedAfraidOfMistakes = getSelectedValuePosition(selectedValue);
                break;
            case R.id.superstition:  //49
                selectedSuperstition = getSelectedValuePosition(selectedValue);
                break;
            case R.id.socialAnxietyDisorder:  //50
                selectedSocialAnxietyDisorder = getSelectedValuePosition(selectedValue);
                break;
            case R.id.agoraphobia:  //51
                selectedAgoraphobia = getSelectedValuePosition(selectedValue);
                break;
            case R.id.apprehension:  //52
                selectedApprehension = getSelectedValuePosition(selectedValue);
                break;
            case R.id.anticipatoryAnxiety:  //53
                selectedAnticipatoryAnxiety = getSelectedValuePosition(selectedValue);
                break;
            case R.id.suddenSyncope:  //54
                selectedSuddenSyncope = getSelectedValuePosition(selectedValue);
                break;
            case R.id.morbidContemplation:  //55
                selectedMorbidContemplation = getSelectedValuePosition(selectedValue);
                break;
            case R.id.glossophobia:  //56
                selectedGlossophobia = getSelectedValuePosition(selectedValue);
                break;
            case R.id.palpitations:  //57
                selectedPalpitations = getSelectedValuePosition(selectedValue);
                break;
            case R.id.immobility:  //58
                selectedImmobility = getSelectedValuePosition(selectedValue);
                break;
            case R.id.anxietyAboutAnxiety:  //59
                selectedAnxietyAboutAnxiety = getSelectedValuePosition(selectedValue);
                break;
            case R.id.OCD:  //60
                selectedOCD = getSelectedValuePosition(selectedValue);
                break;
            case R.id.socialAnxiety:  //61
                selectedSocialAnxiety = getSelectedValuePosition(selectedValue);
                break;
            case R.id.ritualisticBehavior:  //62
                selectedRitualisticBehavior = getSelectedValuePosition(selectedValue);
                break;
            case R.id.nighttimeAnxiety:  //63
                selectedNighttimeAnxiety = getSelectedValuePosition(selectedValue);
                break;
            case R.id.separationAnxiety1:  //64
                selectedSeparationAnxiety1 = getSelectedValuePosition(selectedValue);
                break;

            case R.id.restlessness:  //65
                selectedRestlessness = getSelectedValuePosition(selectedValue);
                break;
        }
    }

    private int getSelectedValuePosition(String selectedValue) {
        if (selectedValue.contains("Never")) {
            return 0;
        } else if (selectedValue.contains("Sometimes")) {
            return 1;
        } else if (selectedValue.contains("Often")) {
            return 2;
        } else if (selectedValue.contains("Always")) {
            return 3;
        }
        return -1;
    }


    public void checkRCADSValue() {
        ServeySection4Request serveySection4Request = new ServeySection4Request();

        serveySection4Request.setQno18A("" + getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        serveySection4Request.setQno18B(eligibleResponse.surveySection.demographics.randamId + "" + eligibleResponse.qno8);
        serveySection4Request.setQno18C(serveySection3cRequest.getQno18C());
        serveySection4Request.setQno18D(serveySection3cRequest.getQno18D());
        serveySection4Request.setQno18E(serveySection3cRequest.getQno18E());
        serveySection4Request.setQno18F(serveySection3cRequest.getQno18F());
        serveySection4Request.setQno18G(serveySection3cRequest.getQno18G());
        serveySection4Request.setQno18H(serveySection3cRequest.getQno18H());
        serveySection4Request.setQno18I(serveySection3cRequest.getQno18I());
        serveySection4Request.setQno18J(serveySection3cRequest.getQno18J());
        serveySection4Request.setLatitude(serveySection3cRequest.getLatitude());
        serveySection4Request.setLongitude(serveySection3cRequest.getLatitude());

        serveySection4Request.setQno19(selectedAnxiety);
        serveySection4Request.setQno20(selectedFeelingSad);
        serveySection4Request.setQno21(selectedFunnyFeeling);
        serveySection4Request.setQno22(selectedFeelingWorry);
        serveySection4Request.setQno23(selectedFeelingAfraid);
        serveySection4Request.setQno24(selectedNoFun);
        serveySection4Request.setQno25(selectedFeelingScared);
        serveySection4Request.setQno26(selectedFeelingAngry);
        serveySection4Request.setQno27(selectedWorryAboutParents);
        serveySection4Request.setQno28(selectedSillyThoughts);
        serveySection4Request.setQno29(selectedSleepingProblem);
        serveySection4Request.setQno30(selectedBadAtSchool);
        serveySection4Request.setQno31(selectedFeelingAwful);
        serveySection4Request.setQno32(selectedBreatheLessnessSuddenly);
        serveySection4Request.setQno33(selectedAppetiteProblem);
        serveySection4Request.setQno34(selectedCrossCheckOCD);
        serveySection4Request.setQno35(selectedScaryWhileSleep);
        serveySection4Request.setQno36(selectedAfraidGoingSchool);
        serveySection4Request.setQno37(selectedNoEnergy);
        serveySection4Request.setQno38(selectedFeelingFoolish);
        serveySection4Request.setQno39(selectedFeelingTired);
        serveySection4Request.setQno40(selectedNegativeThoughts);
        serveySection4Request.setQno41(selectedRemoveBadThoughtfromHead);
        serveySection4Request.setQno42(selectedHeartBeatHigh);
        serveySection4Request.setQno43(selectedCantThink);
        serveySection4Request.setQno44(selectedShevirinhg);
        serveySection4Request.setQno45(selectedBadHallucination);
        serveySection4Request.setQno46(selectedFeelingShaky);
        serveySection4Request.setQno47(selectedFeelingWorthless);
        serveySection4Request.setQno48(selectedAfraidOfMistakes);
        serveySection4Request.setQno49(selectedSuperstition);
        serveySection4Request.setQno50(selectedSocialAnxietyDisorder);
        serveySection4Request.setQno51(selectedAgoraphobia);
        serveySection4Request.setQno52(selectedApprehension);
        serveySection4Request.setQno53(selectedAnticipatoryAnxiety);
        serveySection4Request.setQno54(selectedSuddenSyncope);
        serveySection4Request.setQno55(selectedMorbidContemplation);
        serveySection4Request.setQno56(selectedGlossophobia);
        serveySection4Request.setQno57(selectedPalpitations);
        serveySection4Request.setQno58(selectedImmobility);
        serveySection4Request.setQno59(selectedAnxietyAboutAnxiety);
        serveySection4Request.setQno60(selectedOCD);
        serveySection4Request.setQno61(selectedSocialAnxiety);
        serveySection4Request.setQno62(selectedRitualisticBehavior);
        serveySection4Request.setQno63(selectedNighttimeAnxiety);
        serveySection4Request.setQno64(selectedSeparationAnxiety1);
        serveySection4Request.setQno65(selectedRestlessness);

        Log.d("TAG", "serveySection4Request " + serveySection4Request);


        binding.progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.putServeySection4Data(eligibleResponse.houseHoldId, serveySection4Request, PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject userResponse = response.body();

                if (response.isSuccessful()) {
                    Log.d("response", "onResponse: " + userResponse);
                    try {
                        binding.SP15.setText(calculateSocialPhobia() + " - " + userResponse.get("socialPhobia"));
//                        if (userResponse.get("socialPhobia").getAsInt() >= 65) {
//
//                        } else {
//                            binding.SP15.setText("0");
//                        }
                        binding.PD25.setText(calculatePanicDisorder() + " - " + userResponse.get("panicDisorder"));
//                        if (userResponse.get("panicDisorder").getAsInt() >= 65) {
//                            binding.PD25.setText("1");
//                        } else {
//                            binding.PD25.setText("0");
//                        }
                        binding.MD20.setText(calculateMajorDepression() + " - " + userResponse.get("majorDepression"));
//                        if (userResponse.get("majorDepression").getAsInt() >= 65) {
//                            binding.MD20.setText("1");
//                        } else {
//                            binding.MD20.setText("0");
//                        }
                        int calculateSA = calculateSA();
                        Log.d("TAG", "onResponse: " + calculateSA);
                        binding.SA35.setText(calculateSA + " - " + userResponse.get("separationAnxiety"));
//                        if (userResponse.get("separationAnxiety").getAsInt() >= 65) {
//                            binding.SA35.setText("1");
//                        } else {
//                            binding.SA35.setText("0");
//                        }
                        binding.GA45.setText(calculateGA() + " - " + userResponse.get("generalizedAnxiety"));
//                        if (userResponse.get("generalizedAnxiety").getAsInt() >= 65) {
//                            binding.GA45.setText("1");
//                        } else {
//                            binding.GA45.setText("0");
//                        }
                        binding.OC34.setText(calculateOC() + " - " + userResponse.get("obsessiveCompulsive"));
//                        if (userResponse.get("obsessiveCompulsive").getAsInt() >= 65) {
//                            binding.OC34.setText("1");
//                        } else {
//                            binding.OC34.setText("0");
//                        }

                        if (userResponse.get("socialPhobia").getAsInt() >= 65
                                || userResponse.get("panicDisorder").getAsInt() >= 65
                                || userResponse.get("separationAnxiety").getAsInt() >= 65
                                || userResponse.get("majorDepression").getAsInt() >= 65
                                || userResponse.get("generalizedAnxiety").getAsInt() >= 65
                                || userResponse.get("obsessiveCompulsive").getAsInt() >= 65) {
                            binding.rcadsResult.setText("1");
                        } else {
                            binding.rcadsResult.setText("0");
                        }
                        rcadsResult = binding.rcadsResult.getText().toString();
                        PreferenceConnector.writeString(Section4Activity.this, RCADS4_RESULT, rcadsResult);

                        if (Float.parseFloat(ageValue) <= 17.0f) {
                            //If the age is less than 17
                            if (Float.parseFloat(ageValue) >= 10.0f) {
                                //If the age is greater than 10
                                Intent intent = new Intent(Section4Activity.this, Section5aActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                Log.e("RCADS_RESULT", "RCADS_RESULT " + rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) < 10.0f) {
                                Intent intent = new Intent(Section4Activity.this, ChildrenResult.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 6.0f) {
                                //If the age is greater than 6
                                Intent intent = new Intent(Section4Activity.this, Section6Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 4.0f) {
                                //If the age is greater than 3 Krishna
                                Intent intent = new Intent(Section4Activity.this, Section7bActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 2.0f && Float.parseFloat(ageValue) <= 3.0f) {
                                //If the age is greater than 1 & less than 2
                                Intent intent = new Intent(Section4Activity.this, Section7aActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 8.0f) {
                                Intent intent = new Intent(Section4Activity.this, Section8Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 10.0f) {
                                Intent intent = new Intent(Section4Activity.this, Section5aActivity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            } else if (Float.parseFloat(ageValue) >= 6.0f || Float.parseFloat(ageValue) <= 12.0f) {
                                Intent intent = new Intent(Section4Activity.this, Section10Activity.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                intent.putExtra(AGE_ID, ageValue);
                                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                                intent.putExtra(RCADS4_RESULT, rcadsResult);
                                startActivity(intent);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private int calculateOC() {
        //(Q.28+Q.34+Q.41+Q.49+Q.60+Q.62)
        return selectedSillyThoughts + selectedCrossCheckOCD + selectedRemoveBadThoughtfromHead
                + selectedSuperstition + selectedOCD + selectedRitualisticBehavior;
    }

    private int calculateGA() {
//        (Q.19+Q.31+Q.40+Q.45+Q.53+Q.55)
        return selectedAnxiety + selectedFeelingAwful + selectedNegativeThoughts + selectedBadHallucination + selectedAnticipatoryAnxiety
                + selectedMorbidContemplation;
    }

    private int calculateSA() {
//        (Q.23+Q.27+Q.35+Q.36+Q.51+Q.63+Q.64)
        return selectedFeelingAfraid + selectedWorryAboutParents + selectedScaryWhileSleep + selectedAfraidGoingSchool
                + selectedAgoraphobia + selectedNighttimeAnxiety + selectedSeparationAnxiety1;
    }

    private int calculateMajorDepression() {
//        (Q.20+Q.24+Q.29+Q.33+Q.37+Q.39+Q.43+Q.47+Q.58+Q.65)
        return selectedFeelingSad + selectedNoFun + selectedSleepingProblem + selectedAppetiteProblem
                + selectedNoEnergy + selectedFeelingTired + selectedCantThink
                + selectedFeelingWorthless + selectedImmobility + selectedRestlessness;
    }

    private int calculateSocialPhobia() {
        //Q.22+Q.25+Q.26+Q.30+Q.38+Q.48+Q.50+Q.56+Q. 61
        return selectedFeelingWorry + selectedFeelingScared + selectedFeelingAngry +
                selectedBadAtSchool + selectedFeelingFoolish + selectedAfraidOfMistakes
                + selectedSocialAnxietyDisorder + selectedGlossophobia + selectedSocialAnxiety;
    }

    private int calculatePanicDisorder() {
        //(Q.21+Q.32+Q.42+Q.44+Q.46+Q.52+Q.54+Q.57+Q. 59)
        return selectedFunnyFeeling + selectedBreatheLessnessSuddenly +
                selectedHeartBeatHigh + selectedShevirinhg + selectedFeelingShaky
                + selectedApprehension + selectedSuddenSyncope + selectedPalpitations + selectedAnxietyAboutAnxiety;
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        checkRCADSValue();

    }

    public void onClickPreviousSection(View v) {
        finish();
    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section4Activity.this, ConsentNoChildren.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        intent.putExtra(RCADS4_RESULT, rcadsResult);
        startActivity(intent);
    }
   /* private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Log.e("latitude Section 4 :","latitude Section 4 : "+latitude);
                Log.e("longitude Section 4 : ","longitude Section 4 : "+longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
