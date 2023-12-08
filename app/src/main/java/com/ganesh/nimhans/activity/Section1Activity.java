package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.PreferenceConnector.DISTRICT;
import static com.ganesh.nimhans.utils.PreferenceConnector.TALUKA;
import static com.ganesh.nimhans.utils.PreferenceConnector.VILLAGE;

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
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection1Binding;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    HashMap<String, ArrayList<String>> allVillageNames;
    HashMap<String, ArrayList<String>> pauriVillageNames;
    HashMap<String, ArrayList<String>> almoraVillages;
    HashMap<String, ArrayList<String>> nainitalVillages;

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


        locale.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedlocale = selectedValue;
        });
        consentedForStusy.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedConsentedForStusy = selectedValue;
//            if (selectedValue.equals("Yes")) {
//                setVisibleBasedOnConsent(true);
//            } else {
//                setVisibleBasedOnConsent(false);
//            }
        });
        district.setOnCheckedChangeListener((group, checkedId) -> {
            updateTalukaSpinner(checkedId);
            if (!binding.taluka.getText().toString().isEmpty()) {
                binding.taluka.selectItemByIndex(0);
            }
            if (!binding.city.getText().toString().isEmpty()) {
                binding.city.setSelected(false);
            }
        });

        allVillageNames = Util.getAllVillageNames();
        pauriVillageNames = Util.getPauriVillageNames();
        almoraVillages = Util.getalmorahVillageNames();
        nainitalVillages = Util.getnainitalVillageNames();

        binding.taluka.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>)
                (oldIndex, oldItem, newIndex, newItem) -> {
                    setVillages(newItem);
                });


    }

//    private void setVisibleBasedOnConsent(boolean isConsent) {
//        if (isConsent) {
//            binding.consentBelowSection.setVisibility(View.VISIBLE);
//        } else {
//            binding.consentBelowSection.setVisibility(View.GONE);
//        }
//    }

    private void setVillages(String talukaName) {
        if (!talukaName.equals("Select Taluka")) {
            switch (binding.district.getCheckedRadioButtonId()) {
                case R.id.dehradun:
                    ArrayList<String> villageNames = allVillageNames.get(talukaName);
                    if (villageNames.size() > 0) {
                        binding.city.setItems(villageNames);
                    }
                    break;
                case R.id.pauri:
                    ArrayList<String> pauriVillageNameList = pauriVillageNames.get(talukaName);
                    binding.city.setItems(pauriVillageNameList);

                    break;
                case R.id.almora:
                    ArrayList<String> almoraVillageNameList = almoraVillages.get(talukaName);
                    binding.city.setItems(almoraVillageNameList);
                    break;
                case R.id.nainital:
                    ArrayList<String> ninatalVillageNameList = nainitalVillages.get(talukaName);
                    if (ninatalVillageNameList.size() > 0) {
                        binding.city.setItems(ninatalVillageNameList);
                    }
                    break;
            }

        }
    }

    public void onClickNextSection(View v) {

        if (binding.ConsentedForStudy.getCheckedRadioButtonId() == R.id.no) {
            Util.showLongToast(this, "Thanks for Participant", false);
            finish();
            return;
        }

        String stateValue = state.getText().toString();
        String talukaValue = binding.taluka.getText().toString();
        String cityValue;
        if (binding.city.getText().toString().equals(getString(R.string.select_village))) {
            cityValue = "";
        } else {
            cityValue = binding.city.getText().toString();
        }
        String houseHoldNumberValue = houseHoldNumber.getText().toString();
        String nameOfRespondentValue = nameOfRespondent.getText().toString();
        String addressValue = address.getText().toString();
        String mobileNumberValue = mobileNumber.getText().toString();
        String specifyValue = "";
        String selectedTotleVisites = "";
       // Log.d("taluka", "onClickNextSection: " + talukaValue);
        Log.d("district", "onClickNextSection: " + selectedDistrict);
        Log.d("houseHoldNumberValue", "onClickNextSection: " + houseHoldNumberValue);
        Log.d("selectedlocale", "onClickNextSection: " + selectedlocale);
        Log.d("nameOfRespondentValue", "onClickNextSection: " + nameOfRespondentValue);
        Log.d("addressValue", "onClickNextSection: " + addressValue);
        Log.d("mobileNumberValue", "onClickNextSection: " + mobileNumberValue);
        Log.d("doi", "onClickNextSection: " + dateOfViewEditText.getText().toString());
        Log.d("selectedConsentedForStusy", "onClickNextSection: " + selectedConsentedForStusy);
//        Log.d("selectedVisit", "onClickNextSection: " + selectedVisit);
//        Log.d("date", "onClickNextSection: " + datePickerfield);
//        Log.d("timepicker", "onClickNextSection: " + timepicker);
        Log.d("totalVisits.getText().toString()", "onClickNextSection: ");


        Intent intent = new Intent(this, Section2Activity.class);

        intent.putExtra("state", stateValue);
        intent.putExtra("district", selectedDistrict);
        intent.putExtra("taluka", talukaValue);
        intent.putExtra("cityOrTownOrVillage", cityValue);
        intent.putExtra("houseHoldNo", houseHoldNumberValue);
        intent.putExtra("locale", selectedlocale);
        intent.putExtra("respodentName", nameOfRespondentValue);
        intent.putExtra("address", addressValue);
        intent.putExtra("mobileno", mobileNumberValue);
        intent.putExtra("interviewDate", dateOfViewEditText.getText().toString());
        intent.putExtra("consentedForStudy", selectedConsentedForStusy);
        intent.putExtra("visit", "");
        intent.putExtra("date", "");
        intent.putExtra("resultCode", "");
        intent.putExtra("specify", specifyValue);
        intent.putExtra("nextAgainDate", "");
        intent.putExtra("nextAgainTime", "");
        intent.putExtra("totaleVisits", selectedTotleVisites);

        PreferenceConnector.writeString(this, DISTRICT, selectedDistrict);
        PreferenceConnector.writeString(this, TALUKA, talukaValue);
        PreferenceConnector.writeString(this, VILLAGE, binding.city.getText().toString());


        startActivity(intent);
    }

    private void updateTalukaSpinner(int checkedId) {
        String selectedDistrict = getDistrictFromRadioButton(checkedId);
        this.selectedDistrict = selectedDistrict;
        List<String> talukaValues = getTalukaValues(selectedDistrict);
        binding.taluka.setItems(talukaValues);

    }

    private String getDistrictFromRadioButton(int checkedId) {
        RadioButton radioButton = findViewById(checkedId);
        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
    }

    private List<String> getTalukaValues(String district) {
        switch (district) {
            case "Pauri":
                return getPauriGarhwalTalukaValues();
            case "Almora":
                return getAlmoraTalukaValues();
            case "Nainital":
                return getNainitalTalukaValues();
            case "Dehradun":
            default:
                return getDehradunTalukaValues();
        }
    }

    private List<String> getDehradunTalukaValues() {
        List<String> values = new ArrayList<>();
        values.add("Select Taluka");
        values.add("Chakrata");
        values.add("Tyuni");
        values.add("Kalsi");
        values.add("Vikas Nagar");
        values.add("Dehradun");
        values.add("Rishikesh");
        return values;
    }

    private List<String> getPauriGarhwalTalukaValues() {
        List<String> values = new ArrayList<>();
        values.add("Select Taluka");
        values.add("Chakisain");
        values.add("Chaubatta Khal");
        values.add("Dhoomakot");
        values.add("Jakhanikhal");
        values.add("Kotdwara");
        values.add("Lansdowne");
        values.add("Pauri");
        values.add("Rikhanikhal");
        values.add("Satpuli");
        values.add("Srinagar");
        values.add("Thailisain");
        values.add("Yamkeshwar");

        return values;
    }

    private List<String> getAlmoraTalukaValues() {
        List<String> values = new ArrayList<>();
        values.add("Select Taluka");
        values.add("Almora");
        values.add("Bhanoli");
        values.add("Bhikiasain");
        values.add("Chaukhutiya");
        values.add("Dwarahat");
        values.add("Jainti");
        values.add("Ranikhet");
        values.add("Someshwar");
        values.add("Sult");
        return values;
    }

    private List<String> getNainitalTalukaValues() {
        List<String> values = new ArrayList<>();
        values.add("Select Taluka");
        values.add("Betalghat");
        values.add("Dhari");
        values.add("Haldwani");
        values.add("Kaladhungi");
        values.add("Kosya Kutauli");
        values.add("Lalkuan");
        values.add("Nainital");
        values.add("Ramnagar");
        return values;
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
        new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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





}
