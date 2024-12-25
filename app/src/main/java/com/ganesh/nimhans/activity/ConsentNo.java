package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityConsentNoBinding;
import com.ganesh.nimhans.utils.Constants;

import java.util.Calendar;
import java.util.Locale;

public class ConsentNo extends AppCompatActivity {
    private ActivityConsentNoBinding binding;
    Activity activity;
    MyNimhans myGameApp;
    private String selectedResultCode;
    private String timepicker;
    private String interviewDate;
    private String time;
    private String datePickerfield;
    EditText specify, datePicker2, timePicker, date3;
    final Calendar dateOfVisitCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    Long demoGraphicsID;
    private int surveyID;
    boolean isFromSection1;
    String consentForStudy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsentNoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        isFromSection1 = getIntent().getBooleanExtra("isFromSection1", false);
        consentForStudy = getIntent().getStringExtra("consentForStudy");
        binding.resultCode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedResultCode = selectedValue;
                Log.d("resultCode", "Selected value: " + selectedValue);

                binding.commentResultCode.setVisibility(View.GONE);
                binding.nextVisitDateTime.setVisibility(View.GONE);
                binding.specify1.setText("");
                binding.date3.setText("");
                binding.time.setText("");

                if (checkedId == R.id.h || checkedId == R.id.c) {
                    binding.commentResultCode.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.b || checkedId == R.id.d) {
                    binding.nextVisitDateTime.setVisibility(View.VISIBLE);
                    binding.commentResultCode.setVisibility(View.GONE);
                } else {
                    // Default case
                    binding.commentResultCode.setVisibility(View.GONE);
                    binding.nextVisitDateTime.setVisibility(View.GONE);
                }


            }

        });

    }

    private final DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                /**
                 * @param view       the picker associated with the dialog
                 * @param year       the selected year
                 * @param month      the selected month (0-11 for compatibility with
                 *                   {@link Calendar#MONTH})
                 * @param dayOfMonth the selected day of the month (1-31, depending on
                 *                   month)
                 */
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateEditText();
                }
            };

    public void showDatePickerDialog2(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalender();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    private void updateEditText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        binding.date3.setText(sdf.format(calendar.getTime()));
        new DatePickerDialog(this, dateSetListener, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateCalender() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        binding.date3.setText(sdf.format(calendar.getTime()));
    }

    public void onClickSubmit(View v) {
        Intent intent = new Intent(ConsentNo.this, ActivitySurvey.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        startActivity(intent);
    }

    public void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ConsentNo.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                binding.time.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}