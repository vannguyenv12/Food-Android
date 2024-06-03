package com.vannguyenv12.food.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
