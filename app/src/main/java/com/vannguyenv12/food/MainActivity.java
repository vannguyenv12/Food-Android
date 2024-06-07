package com.vannguyenv12.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.vannguyenv12.food.aministratorfood.AdministratorMain;
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

    }





}