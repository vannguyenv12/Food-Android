package com.vannguyenv12.food.Active.CategoryViewItem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vannguyenv12.food.Active.Search;
import com.vannguyenv12.food.Active.ViewFood;
import com.vannguyenv12.food.Adapter.CategoryViewAdapter;
import com.vannguyenv12.food.Adapter.FoodRecyclerViewAdapter;
import com.vannguyenv12.food.Adapter.MenuCategoryAdapter.Item1Adapter;
import com.vannguyenv12.food.CartActivity;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.CategoryApiService;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Category;
import com.vannguyenv12.food.modal.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryView1 extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView_category_food;
    Item1Adapter item1Adapter;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";

    int categoryId;
    List<Food> lstFood ;
    FrameLayout frameLayout_cart;
    ImageView image_sreach;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_category_food);
        categoryId = getIntent().getIntExtra("categoryId",1);
        layID();
        ActionBar();
        loadFood();

    }

    private void loadFood() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApiService service = retrofit.create(FoodApiService.class);
        Call<List<Food>> call = service.getFoodCategory(API_KEY,"eq."+categoryId,"*");
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                Log.e("Foods","+++++++++++++++++++++++++++");
                if (response.isSuccessful()) {
                    lstFood = response.body();
                    item1Adapter = new Item1Adapter(getApplicationContext(), lstFood);
                    recyclerView_category_food.setAdapter(item1Adapter);
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

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void layID(){
        toolbar = findViewById(R.id.toolbar_view_category_food);
        image_sreach = findViewById(R.id.image_search);
        frameLayout_cart = findViewById(R.id.icon_giohang);

        recyclerView_category_food = findViewById(R.id.recycle_view_category_food);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_category_food.setLayoutManager(layoutManager);
        recyclerView_category_food.setHasFixedSize(true);




        frameLayout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryView1.this, CartActivity.class);
                startActivity(intent);
            }
        });




        image_sreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(getApplicationContext(), Search.class);
                startActivity(search);
            }
        });
    }
}
