package com.ganesh.nimhans.utils;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateModel implements Serializable {
    @SerializedName("State Code")
    public String stateCode;
    @SerializedName("State Name")
    public String stateName;
    @SerializedName("District Code")
    public String districtCode;
    @SerializedName("District Name")
    public String districtName;
    @SerializedName("Sub District Code")
    public String subDistrictCode;
    @SerializedName("Sub District Name")
    public String subDistrictName;
    @SerializedName("Village Code")
    public String villageCode;
    @SerializedName("Village Name")
    public String villageName;
    @SerializedName("Rural/Urban")
    public String rural_Urban;
}
