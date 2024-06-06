package com.vannguyenv12.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vannguyenv12.food.Active.ViewFood;
import com.vannguyenv12.food.api.UserApiService;
import com.vannguyenv12.food.modal.Holder;
import com.vannguyenv12.food.modal.User;

import retrofit2.Retrofit;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ActivityLogin extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    TextView signupRedirectText;
    Button loginButton;
    private static final String TAG = "ActivityLogin";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I"; // Thay thế "your_api_key_here" bằng khóa API thực của bạn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = findViewById(R.id.editTextLoginUsername);
        loginPassword = findViewById(R.id.editTextPasswordlogin);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputFields())
                {
                    getUsers();
                }

            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputFields() {
        if (isEmptyField(loginUsername) || isEmptyField(loginPassword) ) {
            Toast.makeText(ActivityLogin.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean isEmptyField(EditText field) {
        return field.getText().toString().trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




    private void getUsers() {
        String email = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Khởi tạo API service
        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<List<User>> call = apiService.getItem(API_KEY);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body();
                    // Tìm kiếm người dùng với email và password khớp
                    boolean loginSuccessful = false;
                    for (User user : userList) {
                        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                            loginSuccessful = true;
                            Holder.user = user;
                            break;
                        }
                    }

                    if (loginSuccessful) {
                        // Đăng nhập thành công
                        Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityLogin.this, ViewFood.class);
                        startActivity(intent);

                        // Chuyển sang Activity khác hoặc xử lý logic sau khi đăng nhập thành công
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(ActivityLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi không nhận được dữ liệu thành công
                    Toast.makeText(ActivityLogin.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu
                Toast.makeText(ActivityLogin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage());
            }
        });
    }

//    private void getUsers() {
//
//        String email = loginUsername.getText().toString().trim();
////        String password = loginPassword.getText().toString().trim();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//
//        // Khởi tạo API service
//        UserApiService apiService = retrofit.create(UserApiService.class);
//        Call<List<User>> call = apiService.getUserByEmail(API_KEY, "application/json",e,loginPassword);
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<User> userList = response.body();
//                    // Hiển thị dữ liệu trong RecyclerView hoặc ListView, ví dụ:
//                    // UserAdapter adapter = new UserAdapter(YourActivity.this, userList);
//                    // recyclerView.setAdapter(adapter);
//                } else {
//                    // Xử lý khi không nhận được dữ liệu thành công
//                    Toast.makeText(YourActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu
//                Toast.makeText(YourActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("API Error", t.getMessage());
//            }
//        });
//    }

//    private void login() {
//        String email = loginUsername.getText().toString().trim();
//        String password = loginPassword.getText().toString().trim();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(ActivityLogin.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//
//        UserApiService apiService = retrofit.create(UserApiService.class);
//
//        Call<List<User>> call = apiService.getUserByEmail(API_KEY, "application/json", email);
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    User user = response.body().get(0); // Lấy thông tin người dùng đầu tiên
//                    if (user.getPassword().equals(password)) {
//                        Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
//                        // Navigate to another activity or update UI
//                    } else {
//                        Toast.makeText(ActivityLogin.this, "Login failed: Incorrect password", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(ActivityLogin.this, "Login failed: User not found", Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, "Login failed: " + response.code() + ", Message: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(ActivityLogin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "LoginError", t);
//            }
//        });
//    }

//    private void login() {
//        String email = loginUsername.getText().toString().trim();
//        String password = loginPassword.getText().toString().trim();
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(ActivityLogin.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build();
//
//        UserApiService apiService = retrofit.create(UserApiService.class);
//
//        // Tạo một đối tượng Userlogin để gửi dưới dạng thân yêu cầu
//        Userlogin userlogin = new Userlogin(email, password);
//
//        // Gửi yêu cầu đăng nhập
//        Call<List<User>> call = apiService.getUserByEmail(API_KEY, "application/json", email , password);
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    // Đăng nhập thành công
//                    User user = response.body().get(0); // Lấy thông tin người dùng đầu tiên
//                    Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
//                    // Navigate to another activity or update UI
//                } else {
//                    // Đăng nhập thất bại
//                    Toast.makeText(ActivityLogin.this, "Login failed: User not found or incorrect email/password", Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, "Login failed: " + response.code() + ", Message: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                // Xử lý lỗi khi gửi yêu cầu
//                Toast.makeText(ActivityLogin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "LoginError", t);
//            }
//        });
//    }


}