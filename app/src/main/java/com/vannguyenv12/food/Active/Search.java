package com.vannguyenv12.food.Active;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vannguyenv12.food.Adapter.MenuCategoryAdapter.Item1Adapter;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView_category_food;
    Item1Adapter item1Adapter;
    EditText editText_search;
    List<Food> lstFood ;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        layID();
        ActionBar();
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
        toolbar = findViewById(R.id.toolbar_search);
        editText_search = findViewById(R.id.txt_search);

        recyclerView_category_food = findViewById(R.id.recycle_view_search);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_category_food.setLayoutManager(layoutManager);
        recyclerView_category_food.setHasFixedSize(true);



        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadFood();
            }
        });
    }


    private void loadFood() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApiService service = retrofit.create(FoodApiService.class);
        Call<List<Food>> call = service.getFoodName(API_KEY,"eq." + editText_search.getText(),"*");
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

}
