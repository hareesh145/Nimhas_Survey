package com.ganesh.nimhans.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.ganesh.nimhans.MyNimhans;
import com.ganesh.nimhans.databinding.ActivityPendingDataSearchBinding;
import com.ganesh.nimhans.utils.StateModel;
import com.ganesh.nimhans.utils.Util;
import com.google.gson.Gson;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PendingDataSearch extends AppCompatActivity {
    List<StateModel> stateModels;
    private ActivityPendingDataSearchBinding binding;
    private String selectedDistrict;
    private MyNimhans myGameApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingDataSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.setHandlers(this);
        myGameApp = (MyNimhans) this.getApplicationContext();

        String jsonFromAsset = Util.loadJSONFromAsset(this);

        StateModel[] stateModel = new Gson().fromJson(jsonFromAsset, StateModel[].class);

        stateModels = Arrays.asList(stateModel);

        binding.district.setOnCheckedChangeListener((group, checkedId) -> {

            binding.taluka.setText("");
            binding.city.setText("");


            updateTalukaSpinner(checkedId);
        });

        binding.taluka.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>)
                (oldIndex, oldItem, newIndex, newItem) -> {
                    binding.city.setItems(filterVillages(newItem));
                });
    }

    public void onClickSearch(View view) {
        Log.d("TAG", "onClickSearch: ");
        String stateValue = binding.state.getText().toString();
        String selectedStateCode = getSelectedStateCode(stateValue);
        String districtCode = getSelectedDistrictCode(selectedDistrict);
        String selectedVillageCode = getSelectedVillageCode(binding.city.getText().toString());
        String talukaCode = getSelectedTalukaCode(binding.taluka.getText().toString());
        Intent intent = new Intent(this, PendingListScreen.class);
        Log.d("TAG", "onClickSearch: selectedStateCode " + selectedStateCode);
        Log.d("TAG", "onClickSearch: talukaCode " + talukaCode);
        Log.d("TAG", "onClickSearch: districtCode " + districtCode);
        Log.d("TAG", "onClickSearch: selectedVillageCode " + selectedVillageCode);

        intent.putExtra("selectedStateCode", selectedStateCode);
        intent.putExtra("districtCode",districtCode);
        intent.putExtra("talukaCode",talukaCode);
        intent.putExtra("selectedVillageCode",selectedVillageCode);
        //05/061/00310/800317
        //0500304060045208
        //0506000304045208
        //05061003108003176
        //0506000304045257
        //05060003040045229
//        intent.putExtra("selectedCode", "05061003108003176");
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
}