package com.vannguyenv12.food.Active;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.modal.Food;

public class DetailFood extends AppCompatActivity {
    TextView ten_ct_sp, gia_ct_sp, mota_ct_sp;
    Button btnThemvaoGioHang;
    ImageView image_ct_sp;
    Toolbar toolbar_ct_sp;
    Spinner spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deltail_food);
        loadID();
        ActionBar();
        getData();
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
        toolbar_ct_sp.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
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
    }

}
