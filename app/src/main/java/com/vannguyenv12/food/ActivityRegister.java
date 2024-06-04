package com.vannguyenv12.food;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vannguyenv12.food.api.UserApiService;
import com.vannguyenv12.food.modal.RegitersUser;
import com.vannguyenv12.food.modal.User;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ActivityRegister extends AppCompatActivity {
    EditText RFname,RLname, RPassword , REmail , RConfirmPassword;

    Button signupRedirectText;

    TextView loginRedirectText;


    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        RFname = findViewById(R.id.editFristName);
        RLname = findViewById(R.id.editLastName);
        RPassword = findViewById(R.id.editTextPassword);
        REmail =findViewById(R.id.editTextEmail);
        RConfirmPassword=findViewById(R.id.editTextPasswordConfirm);
        signupRedirectText = findViewById(R.id.buttonSignUp);
        loginRedirectText = findViewById(R.id.loginRedirectText);
//        loginButton = findViewById(R.id.buttonLogin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Gán sự kiện click cho nút signup
        signupRedirectText.setOnClickListener(v -> registerUser(retrofit, RFname, RLname, REmail, RPassword));


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(Retrofit retrofit, EditText firstNameField, EditText lastNameField, EditText emailField, EditText passwordField) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String role = "user";  // Hoặc bạn có thể để người dùng chọn role

        // Sử dụng lớp RegistersUser để đại diện cho một người dùng trong quá trình đăng ký
        RegitersUser newUser = new RegitersUser(password , firstName, lastName,email, role);

// Gọi API để đăng ký người dùng

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<Void> call = apiService.register(API_KEY, "application/json", newUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Response Code", "Code: " + response.code());

                // Log ra thông điệp của phản hồi (nếu có)
                Log.d("Response Message", "Message: " + response.message());
                if (response.isSuccessful()) {


                    // Đăng ký thành công
                    Toast.makeText(ActivityRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Chuyển sang Activity khác hoặc xử lý logic sau khi đăng ký thành công
                } else {
                    // Xử lý khi không đăng ký thành công
                    Toast.makeText(ActivityRegister.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gửi yêu cầu
                Toast.makeText(ActivityRegister.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage());
            }
        });
    }

//
//
//    private void testUser(Retrofit retrofit) {
//        UserApiService service = retrofit.create(UserApiService.class);
//
//        User newUser = new User("test1@gmail.com", "test1234", "Van", "Nguyen", "user");
//
//        Call<Void> call = service.register(API_KEY, "application/json", newUser);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    System.out.println("Register Successfully!");
//                } else {
//                    System.err.println("Request failed: " + response.code());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                System.out.println("err: " + t.getMessage());
//
//            }
//        });
//    }


}