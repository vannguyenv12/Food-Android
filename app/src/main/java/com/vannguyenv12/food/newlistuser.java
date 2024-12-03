package com.vannguyenv12.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vannguyenv12.food.adapter.listuser;
import com.vannguyenv12.food.api.UserApiService;
import com.vannguyenv12.food.modal.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class newlistuser extends AppCompatActivity {

    RecyclerView recyclerView_trangchu;
    listuser customAdapter;
    ArrayList<User> lstPokemon = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlistuser);

        LayID(); // Khởi tạo RecyclerView và gắn adapter vào đây
        if (checkInternetConnection()) {
            loadUserData(); // Sau đó mới gọi phương thức để tải dữ liệu
        }
    }

    private void loadUserData() {
        final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I"; // Thay thế "your_api_key_here" bằng khóa API thực của bạn

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();


        UserApiService apiService = retrofit.create(UserApiService.class);

        Call<List<User>> call = apiService.getItem(API_KEY);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    lstPokemon.clear();
                    lstPokemon.addAll(response.body());
                    customAdapter.notifyDataSetChanged();
                    for (User user : response.body()) {
                        Log.e("User", "id: " + user.getId());
                        Log.e("User", "email: " + user.getEmail());
                        Log.e("User", "password: " + user.getPassword());
                        Log.e("User", "firstName: " + user.getFirstName());
                        Log.e("User", "lastName: " + user.getLastName());
                        Log.e("User", "role: " + user.getRole());
                    }
                } else {
                    Log.e("User", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("User", "API call failed", t);
            }
        });

    }


    public void LayID() {
        recyclerView_trangchu = findViewById(R.id.recycle_view_trangchu);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_trangchu.setLayoutManager(layoutManager);
        recyclerView_trangchu.setHasFixedSize(true);
        customAdapter = new listuser(this, lstPokemon);
        recyclerView_trangchu.setAdapter(customAdapter);
    }



    private boolean checkInternetConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }


}