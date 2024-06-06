package com.vannguyenv12.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.vannguyenv12.food.Active.DetailFood;
import com.vannguyenv12.food.Active.ViewFood;
import com.vannguyenv12.food.adapters.CartAdapter;
import com.vannguyenv12.food.api.CartApiService;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Cart;
import com.vannguyenv12.food.modal.Food;
import com.vannguyenv12.food.utils.Constant;
import com.vannguyenv12.food.utils.RetrofitClient;
import com.vannguyenv12.food.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private String backendUrl = "http://10.0.2.2:3000";
    PaymentSheet paymentSheet;
    String paymenIntentClientSecret;
    PaymentSheet.CustomerConfiguration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cart_title);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchCarts();
        fetchCartTotal();
        handlePayNow();
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
    private void handlePayNow() {
        Button button = findViewById(R.id.pay_now);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymenIntentClientSecret != null)
                    paymentSheet.presentWithPaymentIntent(paymenIntentClientSecret,
                            new PaymentSheet.Configuration("Van Nguyen", configuration));
                else
                    Toast.makeText(CartActivity.this, "API Loading...", Toast.LENGTH_SHORT).show();

            }
        });

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }

        if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, ((PaymentSheetResult.Failed)paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Utils.removeAllCarts("1");
            Intent intent = new Intent(CartActivity.this, ViewFood.class);
            startActivity(intent);
            Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();
        }
    }


    public void fetchApi(int cartTotal) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = backendUrl + "/payment-sheet" + "/" + cartTotal;

        JSONObject jsonBody = new JSONObject();
        try {
            // Thêm thông tin cần gửi vào body
            jsonBody.put("price", cartTotal);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            configuration = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );

                            paymenIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getApplicationContext(), jsonObject.getString("publishableKey"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("fetch error: " + error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                return paramV;
            }
        };
        queue.add(stringRequest);
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

    public int fetchCartTotal() {
        final int[] cartTotal = {0};
        CartApiService service = RetrofitClient.retrofit.create(CartApiService.class);

        Call<List<Cart>> call = service.getCarts(Constant.API_KEY);

        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {

                    for (Cart cart : response.body()) {
                        cartTotal[0] += (cart.getQuantity() * cart.getPrice());
                    }

                    System.out.println("check cart total: " + cartTotal[0]);
                    fetchApi(cartTotal[0]);

                } else {
                    System.err.println("Request failed: " + response.code());
                    System.err.println("Request failed: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }


        });

        return cartTotal[0];
    }
}