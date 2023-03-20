package com.example.educationalpracticeandroid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiMethods {

    @POST("user/login")
    Call<UserBack> postUser(@Body User user);
}
