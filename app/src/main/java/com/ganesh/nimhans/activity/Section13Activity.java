package com.ganesh.nimhans.activity;

import static com.ganesh.nimhans.utils.Constants.AGE_ID;
import static com.ganesh.nimhans.utils.Constants.DEMO_GRAPHIC_ID;
import static com.ganesh.nimhans.utils.Constants.ELIGIBLE_RESPONDENT;
import static com.ganesh.nimhans.utils.Constants.NO_OF_CHILDERNS;
import static com.ganesh.nimhans.utils.Constants.RCADS10_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS11_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS6_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7A_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS7B_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS8_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_1_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_2_RESULT;
import static com.ganesh.nimhans.utils.Constants.RCADS9_3_RESULT;
import static com.ganesh.nimhans.utils.Constants.SURVEY_ID;
import static com.ganesh.nimhans.utils.Constants.SURVEY_SECTION3C;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySection13Binding;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Section13Activity extends AppCompatActivity {
    private static final String TAG = Section13Activity.class.getSimpleName();
    Activity activity;
    private ActivitySection13Binding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;
    private String ageValue;
    MyNimhans myGameApp;
    public Section3aActivity sec3 = new Section3aActivity();
    final Calendar dateOfVisitCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    private String selectedVisit;
    private String selectedResultCode;
    private String timepicker;
    private String interviewDate;
    String selectedCaste;
    Long demoGraphicsID;
    private int surveyID;
    private String time;
    private String datePickerfield;
    EditText specify, datePicker2, timePicker;
    private EligibleResponse eligibleResponse;
    ServeySection3cRequest serveySection3cRequest;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySection13Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        ageValue = getIntent().getStringExtra(Constants.AGE_ID);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);
        demoGraphicsID = getIntent().getLongExtra(DEMO_GRAPHIC_ID, -1);
        myGameApp = (MyNimhans) activity.getApplicationContext();
        phoneNo = myGameApp.getUserPhoneNo();
        // checkpermissions(this);

        eligibleResponse = (EligibleResponse) getIntent().getSerializableExtra(ELIGIBLE_RESPONDENT);
        serveySection3cRequest = (ServeySection3cRequest) getIntent().getSerializableExtra(SURVEY_SECTION3C);
        demoGraphicsID = getIntent().getLongExtra(Constants.DEMO_GRAPHIC_ID, -1);
        surveyID = getIntent().getIntExtra(SURVEY_ID, -1);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 00);
        String section6Result = "";
        if (PreferenceConnector.readString(this, RCADS6_RESULT, "").equalsIgnoreCase("1")) {
            section6Result = "\n 6.  Intellectual Disability : " + PreferenceConnector.readString(this, RCADS6_RESULT, "") + "\n";
        }
        String section7aResult = "";
        if (PreferenceConnector.readString(this, RCADS7A_RESULT, "").equalsIgnoreCase("1")) {
            section7aResult = "\n 7a. Autism Spectrum Disorder : " + PreferenceConnector.readString(this, RCADS7A_RESULT, "") + "\n";
        }

        String section7bResult = "";
        if (PreferenceConnector.readString(this, RCADS7B_RESULT, "").equals("1")) {
            section7bResult = "\n 7b. Autism Spectrum Disorder : " + PreferenceConnector.readString(this, RCADS7B_RESULT, "") + "\n";
        }

        String section8Result = "";
        if (PreferenceConnector.readString(this, RCADS8_RESULT, "").equals("1")) {
            section8Result = "\n 8.  Specific Learning Disabilities : " + PreferenceConnector.readString(this, RCADS8_RESULT, "") + "\n";
        }

        String section9aResult = "";
        if (PreferenceConnector.readString(this, RCADS9_1_RESULT, "").equals("1")) {
            section9aResult = "\n 9.  Attention Deficit : " + PreferenceConnector.readString(this, RCADS9_1_RESULT, "") + "\n";
        }
        String section9hResult = "";
        if (PreferenceConnector.readString(this, RCADS9_2_RESULT, "").equals("1")) {
            section9hResult = "\n     Hyperactivity Disorder : " + PreferenceConnector.readString(this, RCADS9_2_RESULT, "") + "\n";
        }

        String section9OResult = "";
        if (PreferenceConnector.readString(this, RCADS9_3_RESULT, "").equals("1")) {
            section9OResult = "\n     Oppositional Defiant Disorder : " + PreferenceConnector.readString(this, RCADS9_3_RESULT, "") + "\n";
        }

        String section10Result = "";
        if (PreferenceConnector.readString(this, RCADS10_RESULT, "").equals("1")) {
            section10Result = "\n 10. Conduct Disorder : " + PreferenceConnector.readString(this, RCADS10_RESULT, "") + "\n";
        }

        String section11Result = "";
        if (PreferenceConnector.readString(this, RCADS11_RESULT, "").equals("1")) {
            section11Result = "\n 11. Anxiety and Depression : " + PreferenceConnector.readString(this, RCADS11_RESULT, "") + "\n";
        }

        Log.d(TAG, "section6Result : " + section6Result);
        Log.d(TAG, "section7aResult : " + section7aResult);
        Log.d(TAG, "section7bResult : " + section7bResult);
        Log.d(TAG, "section8Result : " + section8Result);
        Log.d(TAG, "section9aResult : " + section9aResult);
        Log.d(TAG, "section9hResult : " + section9hResult);
        Log.d(TAG, "section9OResult : " + section9OResult);
        Log.d(TAG, "section10Result : " + section10Result);
        Log.d(TAG, "section11Result : " + section11Result);

        if (section6Result.contains("1") || section7aResult.contains("1") || section7bResult.contains("1")
                || section8Result.contains("1") || section9aResult.contains("1") || section9hResult.contains("1") ||
                section9OResult.contains("1") || section10Result.contains("1") || section11Result.contains("1")
        ) {
            String alertMessage = "Dear Parent, Thank you for providing the interview. As we come to the end of the interview, our screening has identified that your child " + eligibleResponse.qno9 + " is positive for the following screeners.\n" +
                    section6Result + section7aResult
                    + section7bResult + section8Result
                    + section9aResult + section9hResult
                    + section9OResult
                    + section10Result
                    + section11Result
                    + "\n" +
                    "The child needs to be referred to a psychiatrist for further evaluation.";
            showCalc("Alert for child : "+eligibleResponse.qno9, alertMessage);
        }


        binding.options220.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.no_220) {
                    binding.q221.setVisibility(View.GONE);
                    binding.options221.setVisibility(View.GONE);
                } else {
                    binding.q221.setVisibility(View.VISIBLE);
                    binding.options221.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.yesNo227.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            switch (checkedId) {
                case R.id.yes:
                    binding.checkbox227.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.checkbox227.setVisibility(View.GONE);
                    binding.TravelBenefit.setChecked(false);
                    binding.specialSchools.setChecked(false);
                    binding.HealthInsurance.setChecked(false);
                    binding.incomeTax.setChecked(false);
                    binding.others222.setChecked(false);
                    break;
            }
        });
        binding.yesNo228.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            String selectedValue = radioButton.getText().toString();
            selectedCaste = selectedValue;
            switch (checkedId) {
                case R.id.yes228:
                    binding.checkbox228.setVisibility(View.VISIBLE);
                    break;
                default:
                    binding.checkbox228.setVisibility(View.GONE);
                    binding.NationalTrust.setChecked(false);
                    binding.act2016.setChecked(false);
                    binding.act2017.setChecked(false);
                    binding.act2015.setChecked(false);
                    break;
            }
        });
    }

    public void onClickNextSection(View v) {
        Util.showToast(activity, "Successfully data saved");
        Log.d("sec3", "onClickSubmit: " + sec3.getSelectedCaste());
        finishAffinity();
        Intent intent = new Intent(Section13Activity.this, ParentResult.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);

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

    public void onClickPreviousSection(View v) {
        startActivity(new Intent(activity, Section12Activity.class));

    }

    /*   @SuppressLint("RestrictedApi")
       protected File takeScreenshot(View view) {
           Date date = new Date();
           try {
               String dirpath;
               // Initialising the directory of storage
              // dirpath= Section13Activity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() ;
               File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toURI());
               if (!file.exists()) {
                   boolean mkdir = file.mkdir();
               }
               // File name : keeping file name unique using data time.
               String path = dirpath + "/"+ date.getTime() + ".jpeg";
               view.setDrawingCacheEnabled(true);
               Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
               view.setDrawingCacheEnabled(false);
               File imageurl = new File(path);
               FileOutputStream outputStream = new FileOutputStream(imageurl);
               bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
               outputStream.flush();
               outputStream.close();
               Log.d(TAG, "takeScreenshot Path: "+imageurl);
               Toast.makeText(Section13Activity.this,""+imageurl,Toast.LENGTH_LONG).show();
               return imageurl;
           } catch (FileNotFoundException io) {
               io.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }
       // check weather storage permission is given or not
       public static void checkpermissions(Activity activity) {
           int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
           // If storage permission is not given then request for External Storage Permission
           if (permissions != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGE);
           }
       }*/
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.others_222:
                if (checked) {
                    binding.othersSpecify222.setVisibility(View.VISIBLE);
                } else {
                    binding.othersSpecify222.setVisibility(View.GONE);
                    binding.othersSpecify222.setText("");
                }
                // Do your coding

                // Perform your logic
        }

    }

    public void onClickGoToResult(View v) {
        Intent intent = new Intent(Section13Activity.this, ConsentNoParent.class);
        intent.putExtra(DEMO_GRAPHIC_ID, demoGraphicsID);
        intent.putExtra(SURVEY_ID, surveyID);
        intent.putExtra(AGE_ID, ageValue);
        intent.putExtra(ELIGIBLE_RESPONDENT, eligibleResponse);
        intent.putExtra(SURVEY_SECTION3C, serveySection3cRequest);
        intent.putExtra(NO_OF_CHILDERNS, getIntent().getIntExtra(NO_OF_CHILDERNS, -1));
        startActivity(intent);
    }

    public void showCalc(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);


        builder.setNegativeButton("Capture",
                (dialog, which) -> {
                    AlertDialog dialog2 = AlertDialog.class.cast(dialog);
                    takeScreenshot(dialog2);
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Screenshot Captured", Toast.LENGTH_LONG).show();
                });

        builder.show();
    }

    private void takeScreenshot(AlertDialog dialog) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            // image naming and path  to include sd card  appending name you choose for file
            String mPath = eligibleResponse.qno9 + ".jpg"; // use your desired path

            // create bitmap screen capture
            View v1 = dialog.getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


}
