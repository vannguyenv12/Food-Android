package com.vannguyenv12.food.aministratorfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vannguyenv12.food.MainActivity;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.aministratorfood.AdapterFood.AdapterFood;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministratorMain extends AppCompatActivity {
    ListView lv;
    TextView txt_tenSP, txt_gia;
    Button btn_them, btn_capNhat, btn_xoa;
    List<Food> list;
    Food foodUpdate;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrator_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        anhXa();
        loadFoodAministrator();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InsertFoodApi.class);
                startActivity(i);
            }
        });

//        foodUpdate = new Food();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                foodUpdate = list.get(position);
                Toast.makeText(getApplicationContext(), foodUpdate.getName(), Toast.LENGTH_LONG).show();
            }
        });
        btn_capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodUpdate == null)
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn món ăn cần cập nhật", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i = new Intent(AdministratorMain.this, UpdateFoodApi.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("food",foodUpdate);
                    i.putExtras(bundle);
                    startActivity(i);
//                    Toast.makeText(getApplicationContext(), foodUpdate.getName(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(foodUpdate == null)
                    {
                        Toast.makeText(getApplicationContext(), "Vui lòng chọn món ăn cần Xóa", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(AdministratorMain.this);
                        alert.setTitle("Xác nhận");
                        alert.setMessage("Ban có chắc muốn xóa "+ foodUpdate.getName());
                        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = foodUpdate.getId();
                                xoaMonAn(retrofit, id);
                            }
                        });

                        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
//                        Toast.makeText(getApplicationContext(), foodUpdate.getId()+"", Toast.LENGTH_LONG).show();
                        alert.show();
                    }
                }
        });

    }

    private void xoaMonAn(Retrofit retrofit, int id) {
        FoodApiService foodApiService = retrofit.create(FoodApiService.class);
//        String strid = "id=eq."+id;
        Call<Void> delete = foodApiService.deleteFood(API_KEY, id);
        delete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void anhXa()
    {
        lv = findViewById(R.id.listViewHienThi);
        txt_tenSP = findViewById(R.id.txt_tenSP);
        txt_gia = findViewById(R.id.txt_gia);
        btn_them = findViewById(R.id.btnThem);
        btn_capNhat = findViewById(R.id.btn_capNhat);
        btn_xoa = findViewById(R.id.btn_xoa);
        list = new ArrayList<>();
    }

    private void loadFoodAministrator() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApiService service = retrofit.create(FoodApiService.class);
        Call<List<Food>> call = service.getFoods(API_KEY);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    AdapterFood adapterFood = new AdapterFood(getApplicationContext(), R.layout.layout_item_food_one, list);
                    lv.setAdapter(adapterFood);
                    for (Food food : response.body()) {
                        Log.e("Foods","check food: " + food.getName());
                    }
                } else {
                    System.err.println("Request failed: " + response.code());
                    System.err.println("Request failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) { }
        });
    }
}