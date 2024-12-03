package com.vannguyenv12.food.aministratorfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.modal.FoodInsert;
import com.vannguyenv12.food.modal.FoodNoID;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertFood extends AppCompatActivity {

    Button btn_themMonAn;
    EditText txt_tenMonAn, txt_giaMonAn;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        btn_themMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themMonAn();
            }
        });

    }

    private void themMonAn() {
        String name = txt_tenMonAn.getText().toString().trim();
        String price = txt_giaMonAn.getText().toString().trim();
        if(name.length()>1 || price.length()>1) {
            if(Double.parseDouble(price) >= 1000) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Tạo số ngẫu nhiên trong khoảng [min, max]
                int min = 1;
                int max = 80000;
                int id = new Random().nextInt((max - min) + 1) + min;
                float priceNew = Float.parseFloat(price);
                String anhMacDinh = "https://bpmendenqkibzupdgmtp.supabase.co/storage/v1/object/public/image_food/image_f/mon_an_md.jpg";
                FoodInsert foodNew = new FoodInsert(name, priceNew, anhMacDinh,1);//, "no image", 1
                FoodApiService foodApiService = retrofit.create(FoodApiService.class);
                Call<Void> createNew = foodApiService.createFoodKB(API_KEY, "application/json", foodNew);
//                Call<Void> creatNew = foodApiService.createFood(API_KEY, "application/json", foodNew);
//
                createNew.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), AdministratorMain.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Giá món ăn phải lớn hơn hoặc bằng 1000", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường để thêm", Toast.LENGTH_SHORT).show();
        }
    }

    public void anhXa()
    {
        btn_themMonAn = findViewById(R.id.btn_themMonAn);
        txt_tenMonAn = findViewById(R.id.txt_tenMonAn);
        txt_giaMonAn = findViewById(R.id.txt_giaMonAn);
    }
}