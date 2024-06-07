package com.vannguyenv12.food.api;

import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.modal.FoodInsert;
import com.vannguyenv12.food.modal.FoodNoID;

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
    Call<List<Food>> getFood(
            @Header("apikey") String apiKey,
            @Query("id") String id, // "eq.1"
            @Query("select") String select // "*"
    );

    @POST("rest/v1/food")
    Call<Food> createFood(@Header("apikey") String apiKey, @Header("Content-Type") String contentType, @Body Food food);

    @POST("rest/v1/food")
    Call<Void> createFoodKB(@Header("apikey") String apiKey, @Header("Content-Type") String contentType, @Body FoodInsert food);


    //https://bpmendenqkibzupdgmtp.supabase.co/rest/v1/food?some_column=eq.someValue
//    @PATCH("rest/v1/food")
//    Call<Void> updateFood(
//            @Header("apikey") String apiKey,
//            @Header("Content-Type") String contentType,
//            @Query("id") Integer id,
//            @Body Food food
//    );

    @PATCH("rest/v1/food")
    Call<Void> updateFoodKB(
            @Header("apikey") String apiKey,
            @Header("Content-Type") String contentType,
            @Query("id") String id,//eq.id
            @Body Food food
    );

    @PATCH("rest/v1/food")
    Call<Void> updateFoodNameAndPrice(
            @Header("apikey") String apiKey,
            @Header("Content-Type") String contentType,
            @Query("id") String id,//eq.id
            @Body FoodNoID food
    );

    @DELETE("rest/v1/food")
    Call<Void> deleteFood(
            @Header("apikey") String apiKey,
            @Query("id") String id//eq.id
    );



}
