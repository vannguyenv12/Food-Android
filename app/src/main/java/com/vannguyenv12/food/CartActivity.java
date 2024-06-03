package com.vannguyenv12.food;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vannguyenv12.food.adapters.CartAdapter;
import com.vannguyenv12.food.api.CartApiService;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Cart;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.utils.Constant;
import com.vannguyenv12.food.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Cart> cartItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchCarts();
    }

    private void fetchCarts() {
        CartApiService service = RetrofitClient.retrofit.create(CartApiService.class);


        Call<List<Cart>> call = service.getCarts(Constant.API_KEY);

        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {
                    cartAdapter = new CartAdapter(response.body(), CartActivity.this);
                    recyclerView.setAdapter(cartAdapter);
                } else {
                    System.err.println("Request failed: " + response.code());
                    System.err.println("Request failed: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }


        });
    }
}