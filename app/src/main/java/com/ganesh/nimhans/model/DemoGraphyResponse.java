package com.ganesh.nimhans.model;
import com.google.gson.annotations.SerializedName;

public class DemoGraphyResponse {
    @SerializedName("demographicsId")
    String demographicsId;

    public String getDemographicsId() {
        return demographicsId;
    }
}
