package com.vannguyenv12.food.Adapter.MenuCategoryAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vannguyenv12.food.Active.DetailFood;
import com.vannguyenv12.food.Adapter.FoodRecyclerViewAdapter;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.aministratorfood.AdapterFood.formatTienVietNam;
import com.vannguyenv12.food.api.ItemClickListener;
import com.vannguyenv12.food.modal.Food;

import java.util.List;

public class Item1Adapter extends RecyclerView.Adapter<Item1Adapter.MyViewHolder> {

    Context context;
    List<Food> lst;

    public Item1Adapter(Context context, List<Food> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public Item1Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sp = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_food_item_view, parent, false);

        return new Item1Adapter.MyViewHolder(sp);
    }

    @Override
    public void onBindViewHolder(@NonNull Item1Adapter.MyViewHolder holder, int position) {
        Food food = lst.get(position);
        holder.sp_ten.setText(food.getName());
        formatTienVietNam formatTien = new formatTienVietNam();
        String giaBan = String.valueOf(formatTien.kieuTienVietNam().format(food.getPrice()));
        holder.sp_gia.setText(giaBan);
        Glide.with(context).load(food.getImage()).into(holder.sp_anh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if(!isLongClick){
                    //click
                    Intent intent = new Intent(context, DetailFood.class);
                    intent.putExtra("chitiet",food);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sp_ten, sp_gia, mota;
        ImageView sp_anh;
        private ItemClickListener itemClickListener;
        public MyViewHolder (@NonNull View itemView){
            super((itemView));
            sp_ten = itemView.findViewById(R.id.ten_category_food_item);
            sp_gia = itemView.findViewById(R.id.gia_category_food_item);
            mota = itemView.findViewById(R.id.mota_category_food_item);
            sp_anh = itemView.findViewById(R.id.image_category_food_item);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

}
