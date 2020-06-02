package com.era.nightdozor.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if (ourInstance == null) {
            ourInstance = new Retrofit.Builder()
                    .baseUrl("http://194.67.78.174:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ourInstance;
    }

}
