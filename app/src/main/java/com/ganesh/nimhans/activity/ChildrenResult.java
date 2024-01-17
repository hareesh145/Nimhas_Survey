package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS4_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS5_3_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

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
    String rCards4Result;
    private boolean section5_status;
    private int ASSIST_screener;
    String rCards5Result;
    String rCards5_2_Result;
    String rCards5_3_Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChildrenResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        rCards4Result = getIntent().getStringExtra(RCADS4_RESULT);
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        section5_status = getIntent().getBooleanExtra("section5_status", false);
        ASSIST_screener = getIntent().getIntExtra("ASSIST_screener", 0);
        rCards5Result = PreferenceConnector.readString(this, RCADS5_1_RESULT, "0");
        rCards5_2_Result = PreferenceConnector.readString(this, RCADS5_2_RESULT, "0");
        rCards5_3_Result  = PreferenceConnector.readString(this, RCADS5_3_RESULT, "0");
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

        String message = "You are found to be positive for the following screener\n";

        if (rCards4Result != null && rCards4Result.equals("1")) {
            message = message + "\n 4.  Anxiety and Depression : " + rCards4Result + "\n";
        }
        if (rCards5Result != null && rCards5Result.equals("1")) {
            message = message + "\n 5A.  Substance (Alcohol) use : " + rCards5Result + "\n";
        }
        if (rCards5_2_Result != null && rCards5_2_Result.equals("1")) {
            message = message + "\n 5B.  Substance (Other) use  : " + rCards5_2_Result + "\n";
        }
        if (rCards5_3_Result != null && rCards5_3_Result.equals("1")) {
            message = message + "\n 5C.  Substance (Injectable drug) use : " + rCards5_3_Result + "\n";
        }

//        String message = "You are found to be positive for the following screener\n" +
//                "\n 4.  Anxiety and Depression : " + PreferenceConnector.readString(this, RCADS4_RESULT, "") + "\n";
//        if (section5_status) {
//            if (!(rCards4Result != null && rCards4Result.equals("1"))) {
//                message = "You are found to be positive for the following screeners\n" +
//                        "\n 4.  Anxiety and Depression : " + PreferenceConnector.readString(this, RCADS4_RESULT, "") +
//                        "\n 5.  Smoking/ Harmful drinking/ Substance use : " + ASSIST_screener + "\n";
//            }
//        }

        if ((rCards4Result != null && rCards4Result.equals("1")) || (rCards5Result != null && rCards5Result.equals("1"))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChildrenResult.this);
            builder.setMessage(message);
            builder.setTitle("Alert !");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                Util.showToast(activity, "Successfully data saved");
                // checkStatusIsRefused();
                Intent intent = new Intent(ChildrenResult.this, Section6Activity.class);
                intent.putExtra(AGE_ID, ageValue);
                intent.putExtra(SURVEY_ID, surveyID);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
                intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
                intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
                startActivity(intent);
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return;
        }
        //checkStatusIsRefused();
        Intent intent = new Intent(ChildrenResult.this, Section6Activity.class);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

    private void checkStatusIsRefused() {
        if (selectedResultCode.contains("Refused")) {
            Intent intent = new Intent(ChildrenResult.this, Eligiblechildren.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            startActivity(intent);
            finish();
            return;
        }
        navigateToSection();
    }

    private void navigateToSection() {
        Intent intent = new Intent(activity, Section6Activity.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(RCADS4_RESULT, rCards4Result);
        startActivity(intent);
        finish();
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