package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.core.app.ActivityCompat;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.adapter.EligibleChildAdapter;
import com.ganesh.nimhans.databinding.ActivityChildrenResultBinding;
import com.ganesh.nimhans.databinding.ActivityConsentNoParentBinding;
import com.ganesh.nimhans.databinding.ActivityParentResultBinding;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.model.child.Root;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsentNoParent extends AppCompatActivity {
    private ActivityConsentNoParentBinding binding;
    Activity activity;
    Long demoGraphicsID;
    private int surveyID;
    MyNimhans myGameApp;
    String selectedResultCode;
    final Calendar myCalendar1 = Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();
    private EligibleResponse eligibleResponse;
    private Root root;
    private List<StateModel> stateModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsentNoParentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(ConsentNoParent.this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);

        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);

        binding.interviewStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedValue = radioButton.getText().toString();
                selectedResultCode = selectedValue;
                Log.d("resultCode", "Selected value: " + selectedValue);

                switch (checkedId) {
                    case R.id.completed:
                        binding.specify1.setVisibility(View.GONE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.date3.setText("");
                        binding.time.setText("");
                        JsonObject jsonObject =new JsonObject();
                        jsonObject.addProperty("status","Interview Completed");
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        apiInterface.putStatus(eligibleResponse.houseHoldId,jsonObject, PreferenceConnector.readString(ConsentNoParent.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                binding.progressBar.setVisibility(View.GONE);
                                try {
                                    JsonObject userResponse = response.body();
                                    if (response.isSuccessful()) {
                                        Log.d("response", "onResponse: " + userResponse);
                                        Log.e("eligibleResponse.houseHoldId ","eligibleResponse.houseHoldId : "+eligibleResponse.houseHoldId);
                                        Toast.makeText(getApplicationContext(), "Interview Completed", Toast.LENGTH_LONG).show();
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
                    case R.id.refused:
                        binding.specify1.setVisibility(View.VISIBLE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.date3.setText("");
                        binding.time.setText("");
                        Toast.makeText(getApplicationContext(), "Refused to take part", Toast.LENGTH_LONG).show();
                        JsonObject jsonObjectrefused =new JsonObject();
                        jsonObjectrefused.addProperty("status","Refused");
                        ApiInterface apiInterfacerefused = ApiClient.getClient().create(ApiInterface.class);
                        apiInterfacerefused.putStatus(eligibleResponse.houseHoldId,jsonObjectrefused, PreferenceConnector.readString(ConsentNoParent.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                binding.progressBar.setVisibility(View.GONE);
                                try {
                                    JsonObject userResponserefused = response.body();
                                    if (response.isSuccessful()) {
                                        Log.d("response", "onResponse: " + userResponserefused);
                                        Toast.makeText(getApplicationContext(), "Refused to take part", Toast.LENGTH_LONG).show();
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
                    case R.id.partiallyCompleted:
                        binding.nextVisitDateTime.setVisibility(View.VISIBLE);
                        binding.specify1.setVisibility(View.GONE);
                        binding.specify1.setText("");

                        JsonObject jsonObjectpartiallyCompleted =new JsonObject();
                        jsonObjectpartiallyCompleted.addProperty("status","Interview Partially Completed");
                        ApiInterface apiInterfacepartiallyCompleted = ApiClient.getClient().create(ApiInterface.class);
                        apiInterfacepartiallyCompleted.putStatus(eligibleResponse.houseHoldId,jsonObjectpartiallyCompleted, PreferenceConnector.readString(ConsentNoParent.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                binding.progressBar.setVisibility(View.GONE);
                                try {
                                    JsonObject userResponsepartiallyCompleted = response.body();
                                    if (response.isSuccessful()) {
                                        Log.d("response", "onResponse: " + userResponsepartiallyCompleted);
                                        Toast.makeText(getApplicationContext(), " Interview Partially Completed", Toast.LENGTH_LONG).show();
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
                    case R.id.pending:
                        binding.specify1.setVisibility(View.GONE);
                        binding.nextVisitDateTime.setVisibility(View.GONE);
                        binding.specify1.setText("");
                        binding.date3.setText("");
                        binding.time.setText("");
                        JsonObject jsonObjectpartially =new JsonObject();
                        jsonObjectpartially.addProperty("status","Interview Pending");
                        ApiInterface apiInterfacepartially = ApiClient.getClient().create(ApiInterface.class);
                        apiInterfacepartially.putStatus(eligibleResponse.houseHoldId,jsonObjectpartially, PreferenceConnector.readString(ConsentNoParent.this, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {

                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                binding.progressBar.setVisibility(View.GONE);
                                try {
                                    JsonObject userResponsepartially = response.body();
                                    if (response.isSuccessful()) {
                                        Log.d("response", "onResponse: " + userResponsepartially);
                                        Toast.makeText(getApplicationContext(), "Interview Pending", Toast.LENGTH_LONG).show();
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

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getHouseHoldChilderns(surveyID, PreferenceConnector.readString(ConsentNoParent.this, PreferenceConnector.TOKEN, ""))
                .enqueue(new Callback<List<EligibleResponse>>() {
                    @Override
                    public void onResponse(Call<List<EligibleResponse>> call, Response<List<EligibleResponse>> response) {
                        try {
                            Log.d("TAG", "onResponse: " + response.body());

                            if (response.isSuccessful()) {

                                if (response.body().isEmpty()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsentNoParent.this);
                                    builder.setMessage("Now we come to the end of this child's interview. We thank you for the same.");

                                    builder.setTitle("Alert !");

                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                                        Intent intent = new Intent(ConsentNoParent.this, ActivitySurvey.class);
                                        startActivity(intent);
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsentNoParent.this);
                                    builder.setMessage("Now we come to the end of this child's interview. We thank you for the same. We will now proceed to the next child");
                                    builder.setTitle("Alert !");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                                        // takeScreenshot(getWindow().getDecorView().getRootView());
                                        Intent intent = new Intent(ConsentNoParent.this, Eligiblechildren.class);
                                        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
                                        intent.putExtra(SURVEY_ID, surveyID);
                                        startActivity(intent);
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EligibleResponse>> call, Throwable t) {
                        t.printStackTrace();
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

    public void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ConsentNoParent.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                binding.time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }
}