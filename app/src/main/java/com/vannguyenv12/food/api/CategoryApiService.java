package com.vannguyenv12.food.api;

import com.vannguyenv12.food.modal.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoryApiService {
    @GET("rest/v1/categories")
    Call<List<Category>> getCategorys(@Header("apikey") String apiKey);

    @GET("rest/v1/categories")
    Call<List<Category>> getFood(
            @Header("apikey") String apiKey,
            @Query("id") String id, // "eq.1"
            @Query("select") String select // "*"
    );

    @POST("rest/v1/categories")
    Call<Category> createCategory(@Header("apikey") String apiKey, @Header("Content-Type") String contentType, @Body Category category);

    @PATCH("rest/v1/categories")
    Call<Void> updateCategory(
            @Header("apikey") String apiKey,
            @Header("Content-Type") String contentType,
            @Query("id") Integer id,
            @Body Category category
    );

    @DELETE("rest/v1/categories")
    Call<Void> deleteCategory(
            @Header("apikey") String apiKey,
            @Query("id") Integer id
    );
}
