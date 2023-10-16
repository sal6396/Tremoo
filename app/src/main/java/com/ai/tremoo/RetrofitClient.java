package com.ai.tremoo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://tremoo.com/api/";
    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private RetrofitClient() {
        // Create an OkHttpClient with logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Set the desired log level

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        // Initialize Retrofit with OkHttpClient
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build()) // Set the OkHttpClient with logging interceptor
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
