package com.vannguyenv12.food.aministratorfood.AdapterFood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.modal.Food;

import java.util.List;

public class AdapterFood extends BaseAdapter {
    private Context context;
    private List<Food> list;
    private int layout;

    public AdapterFood(Context context, int layout, List<Food> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        if(list!=null)
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.layout_item_food_one, null);

        ImageView imv_hinh;
        TextView txt_tenSP, txt_gia;

        imv_hinh = convertView.findViewById(R.id.imv_hinh);
        txt_tenSP = convertView.findViewById(R.id.txt_tenSP);
        txt_gia = convertView.findViewById(R.id.txt_gia);
        Food food = list.get(position);
        Glide.with(context).load(food.getImage()).into(imv_hinh);
        txt_tenSP.setText(food.getName());
        txt_gia.setText(food.getPrice()+"");

        return convertView;
    }
}
