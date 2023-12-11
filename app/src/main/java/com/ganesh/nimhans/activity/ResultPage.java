package com.ganesh.nimhans.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityResultPageBinding;
import com.ganesh.nimhans.utils.Util;

import java.util.Calendar;
import java.util.Locale;

public class ResultPage extends AppCompatActivity {
    private ActivityResultPageBinding binding;
    Activity activity;
    MyNimhans myGameApp;
    private String selectedResultCode;
    private String timepicker;
    private String interviewDate;
    private String time;
    private String datePickerfield;
    EditText specify, datePicker2, timePicker;
    final Calendar dateOfVisitCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
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
    public void onClickSubmit(View v) {
        Intent intent = new Intent(ResultPage.this,ActivitySurvey.class);
        startActivity(intent);
    }
}