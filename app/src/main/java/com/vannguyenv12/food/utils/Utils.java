package com.vannguyenv12.food.utils;

import android.util.Log;

import com.vannguyenv12.food.api.CartApiService;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class Utils {
    public static int generateRandomNumber() {
        Random random = new Random();
        // Generate random number between 1 (inclusive) and 100000000 (inclusive)
        return random.nextInt(100000000) + 1;
    }

    public static void removeAllCarts(String userId) {
        CartApiService apiService = RetrofitClient.retrofit.create(CartApiService.class);
        Call<Void> call = apiService.deleteAllCart(Constant.API_KEY, "eq." + userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Log.d("Retrofit", "Run deleted");

                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Cart deleted successfully");
                } else {
                    Log.d("Retrofit", "Failed to delete cart: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Retrofit", "Error: " + t.getMessage());
            }
        });
    }
}
