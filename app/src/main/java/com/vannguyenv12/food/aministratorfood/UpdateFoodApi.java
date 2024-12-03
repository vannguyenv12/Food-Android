package com.vannguyenv12.food.aministratorfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.modal.FoodNoID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateFoodApi extends AppCompatActivity {

    EditText txt_foodName, txt_foodPrice;
    Button btn_update;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";
    Food foodC;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_food_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        anhXa();
//        if(getIntent().getExtras() != null) {
            foodC = (Food) getIntent().getExtras().get("food");
            bind(foodC);
//        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt_foodName.getText().toString().trim();
                String price = txt_foodPrice.getText().toString().trim();
                if(name.length()<1 || price.length()<1)
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường để cập nhật", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if( Double.parseDouble(price) >= 1000) {
                        if (String.valueOf(foodC.getId()).length() > 0) {
                            FoodNoID foodUdate = new FoodNoID(name, Float.parseFloat(price), 1);
                            updateData(foodUdate);
                        } else {
                            Toast.makeText(getApplicationContext(), "Có lỗi vui long thu lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Giá sản phẩm không được nhỏ hơn 1000", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void updateData(FoodNoID food) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApiService foodApiService = retrofit.create(FoodApiService.class);
        Call<Void> delete = foodApiService.updateFoodNameAndPrice(API_KEY, "application/json", "eq."+ foodC.getId(), food);
        delete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), AdministratorMain.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Không thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhXa()
    {
        txt_foodName = findViewById(R.id.txt_foodName);
        txt_foodPrice = findViewById(R.id.txt_foodPrice);
        btn_update = findViewById(R.id.btn_update);
    }

    private void bind(Food food)
    {
        txt_foodPrice.setText(food.getPrice()+"");
        txt_foodName.setText(food.getName());
    }
}