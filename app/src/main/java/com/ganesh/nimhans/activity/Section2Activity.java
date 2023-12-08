package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection2Binding;
import com.ganesh.nimhans.model.DemoGraphicsrequest;
import com.ganesh.nimhans.model.DemoGraphyResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section2Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection2Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    MyNimhans myGameApp;
    String demoGraphicsId;
    EditText supervisor, codeOfSupervisor, dateOfScrutinizingTheQuestionnaire, user, codeOfUser, dateOfDataEntry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();

        supervisor = binding.nameOfSupervisor;
        codeOfSupervisor = binding.codeOfSupervisor;
        user = binding.user;
        codeOfUser = binding.codeOfUser;
        dateOfDataEntry = binding.date4;
        dateOfScrutinizingTheQuestionnaire = findViewById(R.id.date);
        dateOfDataEntry = findViewById(R.id.date4);
        binding.user.setText(PreferenceConnector.readString(this,PreferenceConnector.LOGIN_ID,""));
        binding.codeOfUser.setText(PreferenceConnector.readString(this,PreferenceConnector.LOGIN_ID,""));
    }

    public void onClickNextSection(View v) {
        Intent intent = getIntent();
        String stateValue = intent.getStringExtra("state");
        String districtValue = intent.getStringExtra("district");
        String talukaValue = intent.getStringExtra("taluka");
        String cityValue = intent.getStringExtra("cityOrTownOrVillage");
        String houseHoldNumberValue = intent.getStringExtra("houseHoldNo");
        String localeValue = intent.getStringExtra("locale");
        String nameOfRespondentValue = intent.getStringExtra("respodentName");
        String addressValue = intent.getStringExtra("address");
        String mobileNumberValue = intent.getStringExtra("mobileno");
        String dateOfInterviewValue = intent.getStringExtra("interviewDate");
        String consentedForStudyValue = intent.getStringExtra("consentedForStudy");
        String visitValue = intent.getStringExtra("visit");
        String totleVisitValue = intent.getStringExtra("totaleVisits");
        String datePicker1Value = intent.getStringExtra("date");
        String resultCodeValue = intent.getStringExtra("resultCode");
        String specifyValue = intent.getStringExtra("specify");
        String datePicker2Value = intent.getStringExtra("nextAgainDate");
        String timePickerValue = intent.getStringExtra("nextAgainTime");
        String supervisorValue = supervisor.getText().toString();
        String codeOfSupervisorValue = codeOfSupervisor.getText().toString();
        String userValue = user.getText().toString();
        String codeOfUserValue = codeOfUser.getText().toString();
        String questionnarire = dateOfScrutinizingTheQuestionnaire.getText().toString();
        String dateOfDataEntryValue = dateOfDataEntry.getText().toString();
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DemoGraphyResponse> call = apiService.postDemography(new DemoGraphicsrequest(stateValue, districtValue, talukaValue, cityValue, houseHoldNumberValue, localeValue, nameOfRespondentValue, addressValue, mobileNumberValue, dateOfInterviewValue, consentedForStudyValue, visitValue, datePicker1Value, resultCodeValue, specifyValue, datePicker2Value, timePickerValue,
                supervisorValue, codeOfSupervisorValue, questionnarire, userValue, codeOfUserValue, dateOfDataEntryValue), PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        call.enqueue(new Callback<DemoGraphyResponse>() {
                      @Override
            public void onResponse(Call<DemoGraphyResponse> call, Response<DemoGraphyResponse> response) {
                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
                DemoGraphyResponse userResponse = response.body();
                if (response.isSuccessful()) {
                    demoGraphicsId = userResponse.getDemographicsId();
                    System.out.println("deeee" + userResponse.getDemographicsId());
                    activity.finish();
                    Util.showToast(activity, "Successfully data saved");
                    Intent i = new Intent(activity, Section3aActivity.class);
                    Bundle bundle = new Bundle();

//Add your data to bundle
                    bundle.putString("demoo", demoGraphicsId);

//Add the bundle to the intent
                    i.putExtras(bundle);

//Fire that second activity
                    startActivity(i);


                } else {
                    Util.showToast(activity, "Failed to saved the data");
                }
            }

            @Override
            public void onFailure(Call<DemoGraphyResponse> call, Throwable t) {

            }
        });

    }

    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section1Activity.class));
    }

    public void showDatePickerDialog1(View v) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(this, dateSetListener, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateOfScrutinizingTheQuestionnaire.setText(sdf.format(myCalendar1.getTime()));
    }

    public void showDatePickerDialog2(View v) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };
        new DatePickerDialog(this, dateSetListener, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateOfDataEntry.setText(sdf.format(myCalendar2.getTime()));
    }


}
