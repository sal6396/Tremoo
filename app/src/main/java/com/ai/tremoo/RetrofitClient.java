package com.ai.tremoo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://tremoo.com/api/"; // Base URL without endpoint
    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private  RetrofitClient() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient=new RetrofitClient();
        }
        return retrofitClient;
    }

    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }
}