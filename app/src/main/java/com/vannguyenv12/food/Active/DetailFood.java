package com.vannguyenv12.food.Active;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;
import com.vannguyenv12.food.CartActivity;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.CartApiService;
import com.vannguyenv12.food.modal.Cart;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.modal.Holder;
import com.vannguyenv12.food.utils.Constant;
import com.vannguyenv12.food.utils.RetrofitClient;
import com.vannguyenv12.food.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFood extends AppCompatActivity {
    TextView ten_ct_sp, gia_ct_sp, mota_ct_sp;
    Button btnThemvaoGioHang;
    ImageView image_ct_sp;
    Toolbar toolbar_ct_sp;
    Spinner spinner;
    NotificationBadge badge;
    FrameLayout frameLayout_cart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deltail_food);
        loadID();
        ActionBar();
        getData();
        handleAddCart();

    }




    public void getData(){
        Food food = (Food)getIntent().getSerializableExtra("chitiet");
        ten_ct_sp.setText(food.getName());
        gia_ct_sp.setText(food.getPrice() + " VNĐ");
        Glide.with(getApplicationContext()).load(food.getImage()).into(image_ct_sp);
        mota_ct_sp.setText("MUAHAHAHAHAAH");

        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(adapterspin);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar_ct_sp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_ct_sp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void loadID(){
        ten_ct_sp = findViewById(R.id.ten_ct_sp);
        gia_ct_sp = findViewById(R.id.gia_ct_sp);
        mota_ct_sp = findViewById(R.id.mota_ct_sp);
        btnThemvaoGioHang = findViewById(R.id.btn_themvaogio);
        image_ct_sp = findViewById(R.id.image_ct_sp);
        toolbar_ct_sp = findViewById(R.id.toolbar_ct_sp);
        spinner = findViewById(R.id.spiner);
        frameLayout_cart = findViewById(R.id.icon_giohang_deltail);

        frameLayout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailFood.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void handleAddCart() {
        CartApiService apiService =  RetrofitClient.retrofit.create(CartApiService.class);

        Food food = (Food)getIntent().getSerializableExtra("chitiet");

        Cart newCart = new Cart(Utils.generateRandomNumber(),food.getId(), food.getName(), food.getPrice(), 1, Holder.user.getId(), food.getImage());

        btnThemvaoGioHang.setOnClickListener(view -> {
            Call<Void> call = apiService.createCart(Constant.API_KEY, newCart);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("Retrofit", "Cart created successfully");
                        Intent intent = new Intent(DetailFood.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("Retrofit", "Failed to create cart: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("Retrofit", "Error: " + t.getMessage());
                }
            });
        });

    }

}
