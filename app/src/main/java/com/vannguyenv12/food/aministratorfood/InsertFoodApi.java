package com.vannguyenv12.food.aministratorfood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Category;
import com.vannguyenv12.food.modal.Food;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertFoodApi extends AppCompatActivity {

    ImageView imv;
    Button btn_img, btn_submit;
    EditText txt_name, txt_price;
    String image;
//    Spinner spinner;
    public final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJwbWVuZGVucWtpYnp1cGRnbXRwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTU3NzQ3NjgsImV4cCI6MjAzMTM1MDc2OH0.H2sdGq2jA3buoYu5se5Xmi5930zPoaO3AcLK_CA-G7I";

    List<Category> lstCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_food_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_img = findViewById(R.id.btn_img);
        btn_submit = findViewById(R.id.btn_submit);
        txt_name = findViewById(R.id.txt_name);
        txt_price = findViewById(R.id.txt_price);
        imv = findViewById(R.id.imv);

//        Chon anh tu thu vien hoac chup anh
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(InsertFoodApi.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bpmendenqkibzupdgmtp.supabase.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertValue(retrofit);
            }
        });

    }

    private void insertValue(Retrofit retrofit) {
        String name = txt_name.getText().toString().trim();
        String price = txt_price.getText().toString().trim();
        if(name.length()<1 || price.length()<1)
        {
            Toast.makeText(InsertFoodApi.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_LONG).show();
        }
        else
        {
            Food food = new Food(name, Double.parseDouble(price), "image", 1);
            FoodApiService foodApiService = retrofit.create(FoodApiService.class);
            Call<Food> insert = foodApiService.createFood(API_KEY, "application/json", food);
            insert.enqueue(new Callback<Food>() {
                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
                    if(response.isSuccessful())
                    {
                        Toast.makeText(InsertFoodApi.this, "Lưu thành công!", Toast.LENGTH_LONG).show();
                        txt_name.setText("");
                        txt_price.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    Toast.makeText(InsertFoodApi.this, "Cứu toi lỗi rồi!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imv.setImageURI(uri);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            //Khởi tạo byte stream
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            //Khởi tạo byte array
            byte[] bytes = stream.toByteArray();
            //Get base64 da ma hoa
            image = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
//    private Bitmap decodeImageBase64(String image)
//    {
//        Bitmap bitmap = null;
//        if(image.length()>0|| image.isEmpty() == false) {
//            byte[] bytes = Base64.decode(image, Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            //Đặt hình sau giải ma vào ImageView
////            imvGiaiMa.setImageBitmap(bitmap);
//        }
//        return bitmap;
//    }
}