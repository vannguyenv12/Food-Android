package com.vannguyenv12.food.aministratorfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vannguyenv12.food.R;
import com.vannguyenv12.food.modal.Food;

public class UpdateFoodApi extends AppCompatActivity {

    EditText txt_foodName, txt_foodPrice;
    Button btn_update;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_food_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        if(getIntent().getExtras() != null) {
            Food food = (Food) getIntent().getExtras().get("food");
            bind(food);
        }



    }

    private void anhXa()
    {
        txt_foodName = findViewById(R.id.txt_foodName);
        txt_foodPrice = findViewById(R.id.txt_foodPrice);
        btn_update = findViewById(R.id.btn_update);
    }

    private void bind(Food food)
    {
        txt_foodPrice.setText(food.getPrice()+"");
        txt_foodName.setText(food.getName());
    }
}