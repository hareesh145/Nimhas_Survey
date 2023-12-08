package com.ganesh.nimhans.service;

import com.ganesh.nimhans.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ganesh on 10/01/17.
 */


public class ApiClient {
    public static String apiBaseUrl = Constants.URL;
    private static Retrofit retrofit;
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(apiBaseUrl);
    private static OkHttpClient.Builder httpClient;
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build();

        return retrofit;
    }

    public static Retrofit.Builder changeApiBaseUrl(String newApiBaseUrl) {
        Retrofit.Builder newBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(newApiBaseUrl);
        return newBuilder;
    }
}
