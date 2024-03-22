package com.ganesh.nimhans.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ganesh.nimhans.ConvertJsonToExcel;
import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySurveyBinding;
import com.ganesh.nimhans.model.Book;
import com.ganesh.nimhans.model.child.EligibleResponse;
import com.ganesh.nimhans.model.child.Root;
import com.ganesh.nimhans.model.child.SurveySection;
import com.ganesh.nimhans.service.ApiClient;
import com.ganesh.nimhans.service.ApiInterface;
import com.ganesh.nimhans.utils.Constants;
import com.ganesh.nimhans.utils.PreferenceConnector;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySurvey extends AppCompatActivity {
    Activity activity;
    private ActivitySurveyBinding binding;
    String phoneNo, pswd;
    ProgressBar progressBar;

    MyNimhans myGameApp;
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String PHONENO_KEY = "phoneNo_key";

    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    private File file;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        binding.setHandlers(this);
        myGameApp = (MyNimhans) activity.getApplicationContext();

        phoneNo = myGameApp.getUserPhoneNo();
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        phoneNo = sharedpreferences.getString(PHONENO_KEY, null);
    }

    public void onClickSurvey(View v) {
        if (binding.langRadioBtns.getCheckedRadioButtonId()==binding.hindiBtn.getId()) {
            Locale myLocale = new Locale("hi");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            startActivity(new Intent(activity, Section1Activity.class));
        }else {
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            startActivity(new Intent(activity, Section1Activity.class));
        }
        startActivity(new Intent(activity, Section1Activity.class));
    }

    public void onClicklogout(View v) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        Intent i = new Intent(ActivitySurvey.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
    public void onClickRegistration(View view){
        Intent intent = new Intent(ActivitySurvey.this, CreateUserActivity.class);
        startActivity(intent);

    }


    public void onClickReports(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Root>> call = apiService.getSurveyReports(
                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        binding.progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);

                try {
                    ConvertJsonToExcel.writeSurveyReports(response.body(), "SurveyReport.xls");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }


    public void onClickHouseholdTableReports(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<EligibleResponse>> call = apiService.getAllHouseHoldChilderns(
                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        binding.progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<EligibleResponse>>() {
            @Override
            public void onResponse(Call<List<EligibleResponse>> call, Response<List<EligibleResponse>> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);

                try {
                    ConvertJsonToExcel.writeHouseHoldTableReport(response.body(), "HouseHoldTable.xls");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<List<EligibleResponse>> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }

    boolean isHouseHoldReportsClicked = false;

    public void onClickHouseholdReports(View view) {

        getHouseHoldFormReports();
    }

    private void getHouseHoldFormReports() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SurveySection>> call = apiService.getHouseholdFormReport(
                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        binding.progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<SurveySection>>() {
            @Override
            public void onResponse(Call<List<SurveySection>> call, Response<List<SurveySection>> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);

                try {
                    ConvertJsonToExcel.writeHouseHoldFormReport(response.body(), "HouseHoldData.xls");
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SurveySection>> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }


    private List<Map<String, List<?>>> getListOfObject(JsonObject loginResponse) {
        List<Map<String, List<?>>> map = new ArrayList<>();
        Map<String, List<?>> map1 = new HashMap<>();
        map1.put("Survey", getListBook("Kotlin Essential", loginResponse));

        map.add(map1);
        return map;
    }

    private List<Book> getListBook(String bookName, JsonObject loginResponse) {
        Book book = null;

        Book book2 = new Book(bookName, "Joshua Bloch", 36, "Effective Java", "overflow.com",
                "Learn Java ", "Baron Quinn", "M8S4DS3211");
        Book book3 = new Book(bookName, "Robert Martin", 42, "Effective Java", "overflow.com",
                "Learn Java ", "Baron Quinn", "M8S4DS3211");
        Book book4 = new Book(bookName, "Bruce Eckel", 35, "Effective Java", "overflow",
                "Learn Java ", "Baron Quinn", "M8S4DS3211");

        List<Book> listBook = Arrays.asList(book2, book3, book4);
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            book = new Book(bookName, "Kathy Bruce", 79 + i, "Effective Java", "overflow",
                    "Learn Java ", "Baron Quinn", "M8S4DS3211");
            bookList.add(book);
        }
        bookList.add(book4);
        bookList.add(book3);
        bookList.add(book2);


        return bookList;
    }


    public boolean isStoragePermissionGranted() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission is granted");
            return true;
        } else {
            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            if (isHouseHoldReportsClicked) {
                getHouseHoldFormReports();
            }
        }
    }

    public void onClickPendinglist(View v) {
        Intent intent = new Intent(ActivitySurvey.this, PendingDataSearch.class);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //
    }
}
