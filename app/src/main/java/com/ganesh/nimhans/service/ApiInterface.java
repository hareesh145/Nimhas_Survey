package com.ganesh.nimhans.service;

import com.ganesh.nimhans.model.DemoGraphicsrequest;
import com.ganesh.nimhans.model.DemoGraphyResponse;
import com.ganesh.nimhans.model.HouseHoldModel;
import com.ganesh.nimhans.model.LoginRequest;
import com.ganesh.nimhans.model.LoginResponse;
import com.ganesh.nimhans.model.ServeySection3bRequest;
import com.ganesh.nimhans.model.ServeySection3cRequest;
import com.ganesh.nimhans.model.ServeySection4Request;
import com.ganesh.nimhans.model.ServeySection5Request;
import com.ganesh.nimhans.model.ServeySection6Request;
import com.ganesh.nimhans.model.ServeySection7aRequest;
import com.ganesh.nimhans.model.ServeySection8Request;
import com.ganesh.nimhans.model.ServeySection9Request;
import com.ganesh.nimhans.model.ServeySectionRequest;
import com.ganesh.nimhans.model.UserRequest;
import com.ganesh.nimhans.model.UserResponse;
import com.ganesh.nimhans.model.ViewUserResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginRequest loginRequest);

    @POST("app-user")
    Call<UserResponse> postAppUser(@Body UserRequest demographicsrequest, @Header("Authorization") String authHeader);

    @GET("app-user")
    Call<ArrayList<ViewUserResponse>> getAppUser(@Header("Authorization") String authHeader);

    @DELETE("app-user/{id}")
    Call<ResponseBody> deleteItem(@Path("id") int itemId, @Header("Authorization") String authHeader);

    @GET("app-user/{id}")
    Call<ViewUserResponse> getAppUser(@Path("id") int itemId, @Header("Authorization") String authHeader);

    @POST("demographics")
    Call<DemoGraphyResponse> postDemography(@Body DemoGraphicsrequest demographicsrequest, @Header("Authorization") String authHeader);

    @POST("survey-section/{id}")
    Call<JsonObject> postServeySection5Data(@Path("id") Long itemId, @Body ServeySectionRequest serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection3BData(@Path("id") Integer itemId, @Body ServeySection3bRequest serveySection3bRequest, @Header("Authorization") String authHeader);


    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection3CData(@Path("id") Integer itemId, @Body ServeySection3cRequest serveySection5Request, @Header("Authorization") String authHeader);


    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection4Data(@Path("id") Integer itemId, @Body ServeySection4Request serveySection5Request, @Header("Authorization") String authHeader);


    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection5Data(@Path("id") Integer itemId, @Body ServeySection5Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection6Data(@Path("id") Integer itemId, @Body ServeySection6Request serveySection5Request, @Header("Authorization") String authHeader);


    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection7AData(@Path("id") Integer itemId, @Body ServeySection7aRequest serveySection5Request, @Header("Authorization") String authHeader);


    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection8AData(@Path("id") Integer itemId, @Body ServeySection8Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection9AData(@Path("id") Integer itemId, @Body ServeySection9Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection10AData(@Path("id") Integer itemId, @Body ServeySection9Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection11AData(@Path("id") Integer itemId, @Body ServeySection9Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection12AData(@Path("id") Integer itemId, @Body ServeySection9Request serveySection5Request, @Header("Authorization") String authHeader);

    @PUT("survey-section/{id}")
    Call<JsonObject> putServeySection13AData(@Path("id") Integer itemId, @Body ServeySection9Request serveySection5Request, @Header("Authorization") String authHeader);

    @GET("location/{districtCode}/{talukaCode}/{villageCode}")
    Call<JsonObject> getHouseHoldNumber(@Path("districtCode") String districtCode, @Path("talukaCode") String talukaCode, @Path("villageCode") String villageCode, @Header("Authorization") String authHeader);


    @POST("house-hold/{id}")
    Call<JsonObject> saveHouseHold(@Path("id") Integer id, @Body HouseHoldModel houseHoldModel,@Header("Authorization") String authHeader);

    @GET("survey-section/{id}")
    Call<JsonObject> getSurveyReports(@Path("id") Integer itemId, @Header("Authorization") String authHeader);


}

