package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.PreferenceConnector.DISTRICT;
import static com.ganesh.nimhans.utils.PreferenceConnector.NAME_OF_RESPONDENT;
import static com.ganesh.nimhans.utils.PreferenceConnector.TALUKA;
import static com.ganesh.nimhans.utils.PreferenceConnector.VILLAGE;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection1Binding;
import com.ganesh.nimhans.model.DemoGraphicsrequest;
import com.ganesh.nimhans.model.DemoGraphyResponse;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section1Activity extends AppCompatActivity {
    Activity activity;
    private ActivitySection1Binding binding;
    String phoneNo, pswd;
    private EditText dateOfViewEditText;
    final Calendar myCalendar1 = Calendar.getInstance();

    private EditText timeOfBirthEditText;
    private Calendar calendar = Calendar.getInstance();
    ProgressBar progressBar;
    RadioGroup district, locale, consentedForStusy; //visit, resultCode;
    EditText houseHoldNumber, nameOfRespondent, address, mobileNumber, dateOfInterview;
    TextView state;
    private String selectedDistrict;
    private String selectedlocale;
    private String selectedConsentedForStusy;

    MyNimhans myGameApp;
    List<StateModel> stateModels;
    private String demoGraphicsId;
    int houseHoldNumberValue = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        phoneNo = myGameApp.getUserPhoneNo();
        state = binding.state;
//        visit = binding.visit;
//        resultCode = binding.resultCode;
        locale = binding.locale;
        district = binding.district;
        consentedForStusy = binding.ConsentedForStudy;
        houseHoldNumber = binding.hhn;
        nameOfRespondent = binding.notr;
        address = binding.address;
        mobileNumber = binding.mobileNumber;
        dateOfInterview = binding.date1;

//        specify = binding.specify1;
//        datePicker2 = binding.date3;
//        timePicker = binding.time;
//        totalVisits = binding.totalVisits;
        district = findViewById(R.id.district);
        locale = findViewById(R.id.locale);
//        visit = findViewById(R.id.visit);
        dateOfViewEditText = findViewById(R.id.date1);
//        datePicker2 = findViewById(R.id.date3);
        timeOfBirthEditText = findViewById(R.id.time);

//        setVisibleBasedOnConsent(false);


        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);


        Log.d("TAG", "onCreate: " + stateModels);

        locale.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedlocale = selectedValue;
        });
        consentedForStusy.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedConsentedForStusy = selectedValue;
        });
        district.setOnCheckedChangeListener((group, checkedId) -> {

            binding.taluka.setText("");
            binding.city.setText("");
            binding.hhn.setText("");


            updateTalukaSpinner(checkedId);
        });


        binding.taluka.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>)
                (oldIndex, oldItem, newIndex, newItem) -> {
                    binding.city.setItems(filterVillages(newItem));
                    binding.city.setText(R.string.select_village);
                    binding.hhn.setText("");
                });

        binding.city.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>)
                (oldIndex, oldItem, newIndex, newItem) -> {
                    if (!newItem.isEmpty()) {
                        setHouseHoldNumber(newItem);
                        checkRuralUrban(newItem);
                    }
                });
        binding.rural.setClickable(false);
        binding.urban.setClickable(false);
    }

    private void checkRuralUrban(String newItem) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.villageName.equals(newItem)) {
                if (stateModel.rural_Urban.equals("Urban")) {
                    binding.locale.check(R.id.urban);
                } else if (stateModel.rural_Urban.equals("Rural")){
                    binding.locale.check(R.id.rural);
                }
            }
        }
    }

    private void setHouseHoldNumber(String newItem) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.progressBar.setVisibility(View.VISIBLE);

        apiService.getHouseHoldNumber(getSelectedDistrictCode(getDistrictFromRadioButton(binding.district.getCheckedRadioButtonId())), getSelectedTalukaCode(binding.taluka.getText().toString()), getSelectedVillageCode(binding.city.getText().toString()), PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, "")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    houseHoldNumberValue = response.body().get("houseHoldNo").getAsInt();
                    if (houseHoldNumberValue <= 9){
                        binding.hhn.setText("0"+houseHoldNumberValue);
                    }else {
                        binding.hhn.setText(""+houseHoldNumberValue);
                    }


                } else {
                    if (!Util.isNetworkConnected(Section1Activity.this)) {
                        Util.showLongToast(Section1Activity.this, "Please Check Internet Connection", false);
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (!Util.isNetworkConnected(Section1Activity.this)) {
                    Util.showLongToast(Section1Activity.this, "Please Check Internet Connection", false);
                }
                t.printStackTrace();
            }
        });
        binding.hhn.setText("");
    }

    private ArrayList<String> filterVillages(String subDistrictName) {
        ArrayList<String> villagesList = new ArrayList<>();
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.subDistrictName.equals(subDistrictName) && !villagesList.contains(stateModel.villageName)) {
                villagesList.add(stateModel.villageName);
            }
        }
        return villagesList;
    }

    private ArrayList<String> filterTalukas(String districtName) {
        ArrayList<String> talukasList = new ArrayList<>();
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.districtName.equals(districtName) && !talukasList.contains(stateModel.subDistrictName)) {
                talukasList.add(stateModel.subDistrictName);
            }
        }
        return talukasList;
    }


    public void onClickNextSection(View v) {



        String stateValue = state.getText().toString();
        String talukaValue = binding.taluka.getText().toString();
        String mobileNumberValue = isValidPhoneNumber(mobileNumber.getText().toString());
        String cityValue;
        if (binding.city.getText().toString().equals(getString(R.string.select_village))) {
            cityValue = "";
        } else {
            cityValue = binding.city.getText().toString();
        }

        String nameOfRespondentValue = nameOfRespondent.getText().toString();
        String addressValue = address.getText().toString();
        String specifyValue = "";
        String selectedTotleVisites = "";
        // Log.d("taluka", "onClickNextSection: " + talukaValue);
        Log.d("district", "onClickNextSection: " + selectedDistrict);
        Log.d("houseHoldNumberValue", "onClickNextSection: " + houseHoldNumberValue);
        Log.d("selectedlocale", "onClickNextSection: " + selectedlocale);
        Log.d("nameOfRespondentValue", "onClickNextSection: " + nameOfRespondentValue);
        Log.d("addressValue", "onClickNextSection: " + addressValue);
        Log.d("mobileNumberValue", "mobileNumberValue: " + mobileNumberValue);
        Log.d("doi", "onClickNextSection: " + dateOfViewEditText.getText().toString());
        Log.d("selectedConsentedForStusy", "onClickNextSection: " + selectedConsentedForStusy);
//        Log.d("selectedVisit", "onClickNextSection: " + selectedVisit);
//        Log.d("date", "onClickNextSection: " + datePickerfield);
//        Log.d("timepicker", "onClickNextSection: " + timepicker);
        Log.d("totalVisits.getText().toString()", "onClickNextSection: ");
        // Intent intent = new Intent(this, Section2Activity.class);


        PreferenceConnector.writeString(this, DISTRICT, selectedDistrict);
        PreferenceConnector.writeString(this, TALUKA, talukaValue);
        PreferenceConnector.writeString(this, VILLAGE, binding.city.getText().toString());
        PreferenceConnector.writeString(this, NAME_OF_RESPONDENT, binding.notr.getText().toString());


        String userValue = PreferenceConnector.readString(this, PreferenceConnector.LOGIN_ID, "");
        String codeOfUserValue = PreferenceConnector.readString(this, PreferenceConnector.LOGIN_ID, "");
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Call<DemoGraphyResponse> call = apiService.postDemography(new DemoGraphicsrequest(getSelectedStateCode(stateValue), getSelectedDistrictCode(selectedDistrict), getSelectedTalukaCode(talukaValue),
                getSelectedVillageCode(cityValue), String.valueOf(this.houseHoldNumberValue),
                selectedlocale, nameOfRespondentValue, addressValue, mobileNumberValue, dateOfViewEditText.getText().toString(), selectedConsentedForStusy,
                "", "", "", specifyValue, "", "",
                "", "", "", userValue, codeOfUserValue, currentDate), PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        if (!mobileNumberValue.trim().equals("") && mobileNumberValue.length() >=10) {
            call.enqueue(new Callback<DemoGraphyResponse>() {
                @Override
                public void onResponse(Call<DemoGraphyResponse> call, Response<DemoGraphyResponse> response) {
                    if (binding.progressBar.isShown())
                        binding.progressBar.setVisibility(View.GONE);
                    DemoGraphyResponse userResponse = response.body();
                    if (response.isSuccessful()) {
                        demoGraphicsId = userResponse.getDemographicsId();
                        System.out.println("deeee" + userResponse.getDemographicsId());
                        activity.finish();
                        Util.showToast(activity, "Successfully data saved");
                        if (binding.ConsentedForStudy.getCheckedRadioButtonId() == R.id.no) {
                            Intent intent = new Intent(Section1Activity.this, ResultPage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("demoo", demoGraphicsId);
                            startActivity(intent);
                        }else {
                            Intent i = new Intent(activity, Section3aActivity.class);
                            Bundle bundle = new Bundle();

//Add your data to bundle
                            bundle.putString("demoo", demoGraphicsId);

//Add the bundle to the intent
                            i.putExtras(bundle);

//Fire that second activity
                            startActivity(i);
                        }

                    } else {
                        Util.showToast(activity, "Failed to saved the data");
                    }
                }

                @Override
                public void onFailure(Call<DemoGraphyResponse> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Please enter the valid Mobile number",Toast.LENGTH_SHORT).show();
        }



    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section1Activity.this, ResultPage.class);
        startActivity(intent);
    }

    private void updateTalukaSpinner(int checkedId) {
        String selectedDistrict = getDistrictFromRadioButton(checkedId);
        this.selectedDistrict = selectedDistrict;
        List<String> talukaValues = filterTalukas(selectedDistrict);
        binding.taluka.setItems(talukaValues);

    }

    private String getDistrictFromRadioButton(int checkedId) {
        RadioButton radioButton = findViewById(checkedId);
        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
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

    public void showDatePickerDialog(View v) {
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    private void updateEditText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateOfViewEditText.setText(sdf.format(calendar.getTime()));
        new DatePickerDialog(this, dateSetListener, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateCalender() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateOfViewEditText.setText(sdf.format(calendar.getTime()));
    }

    private String getSelectedVillageCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.villageName.equals(selectedVillage)) {
                return stateModel.villageCode;
            }
        }
        return "";
    }

    private String getSelectedTalukaCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.subDistrictName.equals(selectedVillage)) {
                return stateModel.subDistrictCode;
            }
        }
        return "";
    }

    private String getSelectedDistrictCode(String selectedVillage) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.districtName.equals(selectedVillage)) {
                return stateModel.districtCode;
            }
        }
        return "";
    }

    private String getSelectedStateCode(String selectedState) {
        for (StateModel stateModel :
                stateModels) {
            if (stateModel.stateName.equals(selectedState)) {
                return stateModel.stateCode;
            }
        }
        return "";
    }
    private String isValidPhoneNumber(String phone) {

        if (!phone.trim().equals("") && phone.length() >=10) {

        }else {
            Toast.makeText(getApplicationContext(),"Please enter the valid Mobile number",Toast.LENGTH_SHORT).show();
        }

        return phone;
    }
}
