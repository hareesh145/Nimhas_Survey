package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.House_Hold_Model;
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
import com.ganesh.nimhans.databinding.ActivityResultPageBinding;
import com.ganesh.nimhans.model.HouseHoldModel;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPage extends AppCompatActivity {
    private ActivityResultPageBinding binding;
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
    boolean isInterviewcompleted;
    String consentForStudy;
    String householdid;
    private int householdidint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        isFromSection1 = getIntent().getBooleanExtra("isFromSection1", false);
        consentForStudy = getIntent().getStringExtra("consentForStudy");
        householdid =getIntent().getStringExtra(House_Hold_Model);
        householdidint =Integer.parseInt(householdid);
        Log.e("House_Hold_Model","House_Hold_Model RP :"+householdidint);
        binding.resultCode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedResultCode = selectedValue;
                Log.d("resultCode", "Selected value: " + selectedValue);

                switch (checkedId) {
                    case R.id.h:
                        binding.commentResultCode.setVisibility(View.VISIBLE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.date3.setText("");
                        binding.time.setText("");
                        isInterviewcompleted = false;
                        break;
                    case R.id.c:
                        binding.commentResultCode.setVisibility(View.VISIBLE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.date3.setText("");
                        binding.time.setText("");
                        isInterviewcompleted = false;
                        JsonObject jsonObject =new JsonObject();
                        jsonObject.addProperty("houseHoldStatus","Residents of Household Refused to take part");
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        apiInterface.putHouseholdStatus(surveyID,jsonObject, PreferenceConnector.readString(ResultPage.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                binding.progressBar.setVisibility(View.GONE);
                                try {
                                    JsonObject userResponse = response.body();
                                    if (response.isSuccessful()) {
                                        Log.d("response", "onResponse: " + userResponse);
                                        Toast.makeText(activity, "Household Refused to take part", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });
                        break;
                    case R.id.b:
                        binding.nextVisitDateTime.setVisibility(View.VISIBLE);
                        binding.commentResultCode.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        isInterviewcompleted = false;
                        Toast.makeText(activity, "Interview Partially Completed/Postponed", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.d:
                        binding.nextVisitDateTime.setVisibility(View.VISIBLE);
                        binding.commentResultCode.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        isInterviewcompleted = false;
                        break;
                    case R.id.a:
                        if (isFromSection1) {
                            finish();
                        } else {
                            binding.commentResultCode.setVisibility(View.GONE);
                            binding.nextVisitDateTime.setVisibility(View.GONE);
                            binding.specify1.setText("");
                            binding.date3.setText("");
                            binding.time.setText("");
                            isInterviewcompleted = true;
                        }
                        break;
                    default:
                        isInterviewcompleted = false;
                        binding.commentResultCode.setVisibility(View.GONE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        binding.date3.setText("");
                        binding.time.setText("");
                        break;

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
      /*  if (consentForStudy != null && consentForStudy.equals("no")) {
            Toast.makeText(activity, "Thanks for participating in the survey", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Intent intent = new Intent(ResultPage.this, Eligiblechildren.class);
            intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
            intent.putExtra(SURVEY_ID, surveyID);
            startActivity(intent);
        }*/

        if (isInterviewcompleted) {
            JsonObject jsonObject =new JsonObject();
            jsonObject.addProperty("houseHoldStatus","Interview Completed");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.putHouseholdStatus(householdidint,jsonObject, PreferenceConnector.readString(ResultPage.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    try {
                        JsonObject userResponse = response.body();
                        if (response.isSuccessful()) {
                            Log.d("response", "onResponse: " + userResponse);
                            Toast.makeText(getApplicationContext(), "Interview Completed", Toast.LENGTH_LONG).show();
                            Intent intentcomplet = new Intent(ResultPage.this, Eligiblechildren.class);
                            intentcomplet.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                            intentcomplet.putExtra(SURVEY_ID, surveyID);
                            startActivity(intentcomplet);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });



        } else if (selectedResultCode.equals("Others")){
            JsonObject jsonObject =new JsonObject();
            jsonObject.addProperty("houseHoldStatus","Others");
            jsonObject.addProperty("specify",binding.specify1.getText().toString());
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.putHouseholdStatus(householdidint,jsonObject, PreferenceConnector.readString(ResultPage.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    try {
                        JsonObject userResponse = response.body();
                        if (response.isSuccessful()) {
                            Log.d("response", "onResponse: " + userResponse);
                            Toast.makeText(getApplicationContext(), "Others", Toast.LENGTH_LONG).show();
                            if (binding.resultCode.getCheckedRadioButtonId() != -1) {
                                Intent intent = new Intent(ResultPage.this, ActivitySurvey.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                startActivity(intent);


                            } else {
                                Toast.makeText(activity, "Please select any one option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else if(selectedResultCode.equals("Residents of Household Refused to take part")){
            JsonObject jsonObject =new JsonObject();
            jsonObject.addProperty("houseHoldStatus","Residents of Household Refused to take part");
            jsonObject.addProperty("specify",binding.specify1.getText().toString());
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.putHouseholdStatus(householdidint,jsonObject, PreferenceConnector.readString(ResultPage.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    try {
                        JsonObject userResponse = response.body();
                        if (response.isSuccessful()) {
                            Log.d("response", "onResponse: " + userResponse);
                            Toast.makeText(getApplicationContext(), "Residents of Household Refused to take part", Toast.LENGTH_LONG).show();
                            if (binding.resultCode.getCheckedRadioButtonId() != -1) {
                                Intent intent = new Intent(ResultPage.this, ActivitySurvey.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                startActivity(intent);


                            } else {
                                Toast.makeText(activity, "Please select any one option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else if(selectedResultCode.equals("Interview Partially Completed/Postponed")){
            JsonObject jsonObject =new JsonObject();
            jsonObject.addProperty("houseHoldStatus","Interview Partially Completed");
            jsonObject.addProperty("houseHoldPCDate",binding.date3.getText().toString());
            jsonObject.addProperty("houseHoldPCTime",binding.time.getText().toString());
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.putHouseholdStatus(householdidint,jsonObject, PreferenceConnector.readString(ResultPage.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    try {
                        JsonObject userResponse = response.body();
                        if (response.isSuccessful()) {
                            Log.d("response", "onResponse: " + userResponse);
                            Toast.makeText(getApplicationContext(), "Residents of Household Refused to take part", Toast.LENGTH_LONG).show();
                            if (binding.resultCode.getCheckedRadioButtonId() != -1) {
                                Intent intent = new Intent(ResultPage.this, ActivitySurvey.class);
                                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                intent.putExtra(SURVEY_ID, surveyID);
                                startActivity(intent);


                            } else {
                                Toast.makeText(activity, "Please select any one option", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        } else {
            if (binding.resultCode.getCheckedRadioButtonId() != -1) {
                Intent intent = new Intent(ResultPage.this, ActivitySurvey.class);
                intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                intent.putExtra(SURVEY_ID, surveyID);
                startActivity(intent);


            } else {
                Toast.makeText(activity, "Please select any one option", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ResultPage.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                binding.time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}