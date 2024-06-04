package com.vannguyenv12.food.api;

import com.vannguyenv12.food.modal.RegitersUser;
import com.vannguyenv12.food.modal.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("rest/v1/users")
    Call<Void> register(@Header("apikey") String apiKey, @Header("Content-Type") String contentType, @Body RegitersUser user);


    @GET("rest/v1/users")
    Call<List<User>> getItem(@Header("apikey") String apiKey);

}
