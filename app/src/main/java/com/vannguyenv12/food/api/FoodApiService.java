package com.vannguyenv12.food.api;

import com.vannguyenv12.food.modal.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodApiService {
    @GET("rest/v1/food")
    Call<List<Food>> getFoods(@Header("apikey") String apiKey);

    @GET("rest/v1/food")
    Call<Food> getFood(
            @Header("apikey") String apiKey,
            @Query("id") String id, // "eq.1"
            @Query("select") String select // "*"
    );

    @GET("rest/v1/food")
    Call<List<Food>> getFoodCategory(
            @Header("apikey") String apiKey,
            @Query("categoryId") String categoryId, // "eq.1"
            @Query("select") String select // "*"
    );

    @POST("rest/v1/food")
    Call<Food> createFood(@Header("apikey") String apiKey, @Header("Content-Type") String contentType, @Body Food food);

    @PATCH("rest/v1/food")
    Call<Void> updateFood(
            @Header("apikey") String apiKey,
            @Header("Content-Type") String contentType,
            @Query("id") Integer id,
            @Body Food food
    );

    @DELETE("rest/v1/food")
    Call<Void> deleteFood(
            @Header("apikey") String apiKey,
            @Query("id") Integer id
    );

}
