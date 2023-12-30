package com.ganesh.nimhans.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.R;
import com.ganesh.nimhans.databinding.ActivitySurveyBinding;
import com.ganesh.nimhans.model.Book;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private File directory;
    private File sd;
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
    }

    public void onClickSurvey(View v) {
        startActivity(new Intent(activity, Section1Activity.class));
    }


    public void onClickReports(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getSurveyReports(PreferenceConnector.readInteger(this, Constants.SURVEY_ID, 29),
                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        binding.progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject loginResponse = response.body();

                Log.d("TAG", "::::::::: " + loginResponse);
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("Survey");
                HSSFRow hssfRow = hssfSheet.createRow(0);
                HSSFRow dataRow = hssfSheet.createRow(1);
                int i = 0;
                for (Map.Entry<String, JsonElement> entry : loginResponse.entrySet()) {
                    if (i < 200) {
                        HSSFCell hssfCell = hssfRow.createCell(i);
                        hssfCell.setCellValue(entry.getKey());
                        HSSFCell dataCell = dataRow.createCell(i);
                        dataCell.setCellValue(String.valueOf(loginResponse.get(entry.getKey())));
                        i++;
                    }
                }
                saveWorkBook(hssfWorkbook, "SurveyData.xls");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }

    public void onClickHouseholdReports(View view) {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<List<EligibleResponse>> call = apiService.getAllHouseHoldChilderns(
//                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
//        binding.progressBar.setVisibility(View.VISIBLE);
//        call.enqueue(new Callback<List<EligibleResponse>>() {
//            @Override
//            public void onResponse(Call<List<EligibleResponse>> call, Response<List<EligibleResponse>> response) {
//                if (binding.progressBar.isShown())
//                    binding.progressBar.setVisibility(View.GONE);
//                List<EligibleResponse> loginResponse = response.body();
//
//                Log.d("TAG", "::::::::: " + loginResponse);
//                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//                HSSFSheet hssfSheet = hssfWorkbook.createSheet("Survey");
//                HSSFRow hssfRow = hssfSheet.createRow(0);
//                HSSFRow dataRow = hssfSheet.createRow(1);
//                int i = 0;
////                for (Map.Entry<String, JsonElement> entry : loginResponse.entrySet()) {
////                    if (i < 200) {
////                        HSSFCell hssfCell = hssfRow.createCell(i);
////                        hssfCell.setCellValue(entry.getKey());
////                        HSSFCell dataCell = dataRow.createCell(i);
////                        dataCell.setCellValue(String.valueOf(loginResponse.get(entry.getKey())));
////                        i++;
////                    }
////                }
////                saveWorkBookHousehold(hssfWorkbook);
//            }
//
//            @Override
//            public void onFailure(Call<List<EligibleResponse>> call, Throwable t) {
//                if (binding.progressBar.isShown())
//                    binding.progressBar.setVisibility(View.GONE);
//                Util.showToast(activity, getResources().getString(R.string.service_error));
//                System.out.println("failed Obj: " + t);
//            }
//        });


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getSurveyReports(PreferenceConnector.readInteger(this, Constants.SURVEY_ID, 35),
                PreferenceConnector.readString(activity, PreferenceConnector.TOKEN, ""));
        binding.progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                JsonObject loginResponse = response.body();

                Log.d("TAG", "::::::::: " + loginResponse);
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("HouseHold");
                HSSFRow hssfRow = hssfSheet.createRow(0);
                HSSFRow dataRow = hssfSheet.createRow(1);
                int i = 0;
                for (Map.Entry<String, JsonElement> entry : loginResponse.entrySet()) {
                    if (i < 200) {
                        HSSFCell hssfCell = hssfRow.createCell(i);
                        hssfCell.setCellValue(entry.getKey());
                        HSSFCell dataCell = dataRow.createCell(i);
                        dataCell.setCellValue(String.valueOf(loginResponse.get(entry.getKey())));
                        i++;
                    }
                }
                saveWorkBook(hssfWorkbook, "HouseHoldData.xls");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (binding.progressBar.isShown())
                    binding.progressBar.setVisibility(View.GONE);
                Util.showToast(activity, getResources().getString(R.string.service_error));
                System.out.println("failed Obj: " + t);
            }
        });
    }

    private void saveWorkBook(HSSFWorkbook hssfWorkbook, String fileName) {
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage

        File fileOutput = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//            fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/SurveyData.xlsx");
//        } else {
        String csvFile = fileName;
        sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        directory = new File(sd.getAbsolutePath());
        fileOutput= new File(Environment.getExternalStorageDirectory(),fileName);
//        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(this, "Report Downloaded Downloads > " + fileName, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "File Creation Failed", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }

    private void saveWorkBookHousehold(HSSFWorkbook hssfWorkbook) {
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage

        File fileOutput = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//            fileOutput = new File(storageVolume.getDirectory().getPath() + "/Download/SurveyData.xlsx");
//        } else {
        String csvFile = "HouseHoldReport.xls";
        sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        directory = new File(sd.getAbsolutePath());
        fileOutput= new File(Environment.getExternalStorageDirectory(),csvFile);
//        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(this, "Report Downloaded Please check in Downloads HouseHoldReport.xlsx", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "File Creation Failed", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
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

    public void createExcelSheet(JsonObject loginResponse) {
        if (isStoragePermissionGranted()) {
            String csvFile = "Mytest.xls";
            sd = Environment.getExternalStorageDirectory();
            directory = new File(sd.getAbsolutePath());
            file = new File(directory, csvFile);

            try {
//                WorkSheet workSheet = new WorkSheet.Builder(getApplicationContext(), file.getAbsolutePath())
//                        .setSheet(getListOfObject(loginResponse))
//                        .writeSheet();
//                Toast.makeText(this, "File Saved !", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(this, "Permission Denied !", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void onClickPendinglist(View v) {
        Intent intent = new Intent(ActivitySurvey.this, PendingDataSearch.class);
        startActivity(intent);
    }
}
