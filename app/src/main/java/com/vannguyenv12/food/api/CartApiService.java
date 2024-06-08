package com.vannguyenv12.food.api;

import com.vannguyenv12.food.modal.Cart;
import com.vannguyenv12.food.modal.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartApiService {
    @GET("rest/v1/carts")
    Call<List<Cart>> getCarts(@Header("apikey") String apiKey, @Query("userId") String userId);

    @GET("rest/v1/carts")
    Call<List<Cart>> getCartsById(@Header("apikey") String apiKey,
                                  @Query("userId") String userId,
                                  @Query("productId") String productId);

    @POST("rest/v1/carts")
    Call<Void> createCart(
            @Header("apikey") String apiKey,
            @Body Cart cart
    );

    @DELETE("rest/v1/carts")
    Call<Void> deleteCart(@Header("apikey") String apiKey,
                          @Query("id") String id);

    @DELETE("rest/v1/carts")
    Call<Void> deleteAllCart(@Header("apikey") String apiKey,
                          @Query("userId") String userId);

    @PATCH("rest/v1/carts")
    Call<Void> updateCartQuantity(@Header("apikey") String apiKey,
                                  @Header("Content-Type") String contentType,
                                  @Query("id") String id,
                                  @Body Cart cart);

    @PATCH("rest/v1/carts")
    Call<Void> updateSingleCartQuantity(@Header("apikey") String apiKey,
                                        @Header("Content-Type") String contentType,
                                        @Query("userId") String userId,
                                        @Query("productId") String productId,
                                        @Body Cart cart);
}
