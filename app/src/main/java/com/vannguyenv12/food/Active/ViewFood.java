package com.vannguyenv12.food.Active;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.vannguyenv12.food.Active.CategoryViewItem.CategoryView1;
import com.vannguyenv12.food.Adapter.CategoryViewAdapter;
import com.vannguyenv12.food.Adapter.FoodRecyclerViewAdapter;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.CategoryApiService;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Category;
import com.vannguyenv12.food.modal.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewFood extends AppCompatActivity {

    Toolbar toolbar_trangchu;
    ViewFlipper viewFlipper_trangchu;
    RecyclerView recyclerView_trangchu;
    NavigationView navigationView_trangchu;
    ListView listView_trangchu;
    DrawerLayout drawerLayout_trangchu;
    FoodRecyclerViewAdapter foodRecyclerViewAdapter;
    CategoryViewAdapter categoryViewAdapter;
    List<Category> lstCategory;
    List<Food> lstFood ;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food);
        layID();
        ActionBar();
        ActioneViewFlipper();
        loadCategory();
        loadFood();
        getCategoryClickEvent();
    }




    public void getCategoryClickEvent(){
        listView_trangchu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), ViewFood.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent item1 = new Intent(getApplicationContext(), CategoryView1.class);
                        item1.putExtra("categoryId",lstCategory.get(position).getId());
                        startActivity(item1);
                        break;
                    case 2:
                        Intent item2 = new Intent(getApplicationContext(), ViewFood.class);
                        startActivity(item2);
                        break;
                    case 3:
                        Intent item3 = new Intent(getApplicationContext(), ViewFood.class);
                        startActivity(item3);
                        break;


                }
            }
        });
    }


    private void loadCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryApiService service = retrofit.create(CategoryApiService.class);
        Call<List<Category>> call = service.getCategorys(API_KEY);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.e("Categorys","+++++++++++++++++++++++++++");
                if (response.isSuccessful()) {
                    Log.e("Categorys","+++++++SUCCESS++++++++++");
                    lstCategory = response.body();
                    categoryViewAdapter = new CategoryViewAdapter(lstCategory, getApplicationContext());
                    listView_trangchu.setAdapter(categoryViewAdapter);
                    for (Category category : response.body()) {
                        Log.e("Categorys","check Category: " + category.getName());
                    }
                } else {
                    Log.e("Categorys","++++++++FAILUE+++++++++");
                    System.err.println("Request failed: " + response.code());
                    System.err.println("Request failed: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) { }
        });
    }

    private void loadFood() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApiService service = retrofit.create(FoodApiService.class);
        Call<List<Food>> call = service.getFoods(API_KEY);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                Log.e("Foods","+++++++++++++++++++++++++++");
                if (response.isSuccessful()) {
                    lstFood = response.body();
                    foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(getApplicationContext(), lstFood);
                    recyclerView_trangchu.setAdapter(foodRecyclerViewAdapter);
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

    private void ActioneViewFlipper() {
        List<String> quangCao = new ArrayList<>();
        quangCao.add("https://inan2h.vn/wp-content/uploads/2022/12/in-banner-quang-cao-do-an-5.jpg");
        quangCao.add("https://inan2h.vn/wp-content/uploads/2022/12/in-banner-quang-cao-do-an-1.jpg");
        quangCao.add("https://inan2h.vn/wp-content/uploads/2022/12/in-banner-quang-cao-do-an-5-1.jpg");
        for (int i = 0; i < quangCao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(quangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper_trangchu.addView(imageView);
        }
        viewFlipper_trangchu.setFlipInterval(3000);
        viewFlipper_trangchu.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_out_right);
        viewFlipper_trangchu.setInAnimation(slide_in);
        viewFlipper_trangchu.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar_trangchu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_trangchu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar_trangchu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout_trangchu.openDrawer(GravityCompat.START);
            }
        });

    }
    public void layID(){
        toolbar_trangchu = findViewById(R.id.toolbar_trangchu);
        viewFlipper_trangchu = findViewById(R.id.viewflipper_trangchu);

        recyclerView_trangchu = findViewById(R.id.recycle_view_trangchu);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_trangchu.setLayoutManager(layoutManager);
        recyclerView_trangchu.setHasFixedSize(true);

        navigationView_trangchu = findViewById(R.id.navigation_view_trangchu);
        listView_trangchu = findViewById(R.id.listview_trangchu);
        drawerLayout_trangchu = findViewById(R.id.drawer_layout_trangchu);
    }
}
