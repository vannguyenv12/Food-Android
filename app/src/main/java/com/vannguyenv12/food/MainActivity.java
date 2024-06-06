package com.vannguyenv12.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.vannguyenv12.food.aministratorfood.AdministratorMain;
import com.vannguyenv12.food.aministratorfood.InsertFoodApi;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.api.UserApiService;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.modal.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";

//    Xóa
    Button btn;
//    xóa
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        Xóa
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdministratorMain.class);
                startActivity(i);
            }
        });
//        Xóa

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        testUser(retrofit);
    }

    private void testUser(Retrofit retrofit) {
        UserApiService service = retrofit.create(UserApiService.class);

        User newUser = new User("test1@gmail.com", "test1234", "Van", "Nguyen", "user");

        Call<Void> call = service.register(API_KEY, "application/json", newUser);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Register Successfully!");
                } else {
                    System.err.println("Request failed: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("err: " + t.getMessage());

            }
        });
    }



    private void testFood(Retrofit retrofit) {
        FoodApiService service = retrofit.create(FoodApiService.class);


        Call<List<Food>> call = service.getFoods(API_KEY);



        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    for (Food food : response.body()) {
                        System.out.println("check food: " + food.getName());
                    }
                } else {
                    System.err.println("Request failed: " + response.code());
                    System.err.println("Request failed: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }


        });
    }
}