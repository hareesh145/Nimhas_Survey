package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection13Binding;
import com.ganesh.nimhans.utils.Util;

import java.util.Calendar;
import java.util.Locale;

public class Section13Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection13Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    public Section3aActivity sec3 = new Section3aActivity();
    final Calendar dateOfVisitCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    private String selectedVisit;
    private String selectedResultCode;
    private String timepicker;
    private String interviewDate;
    private String time;
    private String datePickerfield;
    EditText specify, datePicker2, timePicker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection13Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();


        binding.resultCode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedResultCode = selectedValue;
                Log.d("resultCode", "Selected value: " + selectedValue);

                switch (checkedId) {
                    case R.id.c:
                    case R.id.h:
                        binding.commentResultCode.setVisibility(View.VISIBLE);
                        break;
                    case R.id.b:
                        binding.commentResultCode.setVisibility(View.GONE);
                        break;

                }

            }
        });
        binding.visit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedVisit = selectedValue;

                switch (radioButton.getId()) {
                    case R.id.first_visit:
                        binding.totalVisits.setText("1");
                        break;
                    case R.id.second_visit:
                        binding.totalVisits.setText("2");
                        break;
                    case R.id.thired_visit:
                        binding.totalVisits.setText("3");
                        break;
                }
            }
        });
    }

    public void onClickSubmit(View v) {
        Util.showToast(activity, "Successfully data saved");
        Log.d("sec3", "onClickSubmit: " + sec3.getSelectedCaste());
        finishAffinity();
        startActivity(new Intent(this, ActivitySurvey.class));
    }

    public void showDatePickerDialog1(View v) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateOfVisitCalendar.set(Calendar.YEAR, year);
                dateOfVisitCalendar.set(Calendar.MONTH, monthOfYear);
                dateOfVisitCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(this, dateSetListener, dateOfVisitCalendar.get(Calendar.YEAR), dateOfVisitCalendar.get(Calendar.MONTH), dateOfVisitCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateOfVisit.setText(sdf1.format(dateOfVisitCalendar.getTime()));
    }


    public void showTimePickerDialog(View v) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateTime(hourOfDay, minute, sec);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    private void updateTime(int hour, int minute, int sec) {
        String amPm;

        if (hour >= 12) {
            hour = hour - 12;
            sec = sec;
        }
        String time = String.format("%02d:%02d:%s", hour, minute, sec);
        timePicker.setText(time);
        timepicker = timePicker.getText().toString();
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
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
        datePicker2.setText(sdf1.format(myCalendar2.getTime()));
        datePickerfield = datePicker2.getText().toString();
    }


    public void showDatePickerDialog3(View v) {
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

}
