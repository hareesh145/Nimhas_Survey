package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivityChildrenResultBinding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;

import java.util.Calendar;
import java.util.Locale;

public class ChildrenResult extends AppCompatActivity {
    private ActivityChildrenResultBinding binding;
    Activity activity;
    Long demoGraphicsID;
    private int surveyID;
    MyNimhans myGameApp;
    String selectedResultCode;
    private EligibleResponse eligibleResponse;
    private String ageValue;
    final Calendar myCalendar1 = Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();
    ServeySection3cRequest serveySection3cRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildrenResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        binding.interviewStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedResultCode = selectedValue;
                Log.d("resultCode", "Selected value: " + selectedValue);

                switch (checkedId) {
                    case R.id.completed:
                        Toast.makeText(getApplicationContext(), "Interview Completed", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.refused:
                        binding.specify1.setVisibility(View.VISIBLE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.date3.setText("");
                        binding.time.setText("");
                        Toast.makeText(getApplicationContext(), "Refused to take part", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.partiallyCompleted:
                        binding.nextVisitDateTime.setVisibility(View.VISIBLE);
                        binding.specify1.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        Toast.makeText(getApplicationContext(), " Interview Partially Completed", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.pending:
                        binding.specify1.setVisibility(View.GONE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        binding.date3.setText("");
                        binding.time.setText("");
                        Toast.makeText(getApplicationContext(), "Interview Pending", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        binding.specify1.setVisibility(View.GONE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        binding.date3.setText("");
                        binding.time.setText("");
                        break;


                }

            }

        });

    }
    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section13Activity.class));

    }
    public void onClickNextSection(View v) {
        Intent intent = new Intent(activity, Section6aActivity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        startActivity(intent);
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
    public void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ChildrenResult.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                binding.time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}