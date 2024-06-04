package com.vannguyenv12.food.Adapter;

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
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.ItemClickListener;
import com.vannguyenv12.food.modal.Food;

import java.util.List;


public class FoodRecyclerViewAdapter extends   RecyclerView.Adapter<FoodRecyclerViewAdapter.MyViewHolder>  {



    Context context;
    List<Food> lst;


    public FoodRecyclerViewAdapter(Context context, List<Food> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sp = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);

        return new MyViewHolder(sp);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Food food = lst.get(position);
        holder.sp_ten.setText(food.getName());
        holder.sp_gia.setText(food.getPrice() + " VNĐ");
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
        TextView sp_ten, sp_gia;
        ImageView sp_anh;
        private ItemClickListener itemClickListener;
        public MyViewHolder (@NonNull View itemView){
            super((itemView));
            sp_ten = itemView.findViewById(R.id.sp_tensp);
            sp_gia = itemView.findViewById(R.id.sp_gia);
            sp_anh = itemView.findViewById(R.id.sp_image);
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
