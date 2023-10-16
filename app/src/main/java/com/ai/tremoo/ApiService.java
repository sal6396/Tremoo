package com.ai.tremoo;

import com.ai.tremoo.Models.Login_Response;
import com.ai.tremoo.Models.ProjectsResponse;
import com.ai.tremoo.Models.Register_Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("register")
    Call<Register_Response> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword,
            @Field("city") String city,
            @Field("country") String country,
            @Field("education") String education,
            @Field("number") String number, // Add number field
            @Field("is_smartphone") String isSmartphone, // Add is_smartphone field
            @Field("is_eligible") String isEligible // Add is_eligible field

    );

    @FormUrlEncoded
    @POST("login")
    Call<Login_Response> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("projects")
    Call<ProjectsResponse> getProjects(@Header("Authorization") String authorizationHeader);
}