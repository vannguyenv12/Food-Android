package com.vannguyenv12.food.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vannguyenv12.food.R;
import com.vannguyenv12.food.modal.Category;

import java.util.List;

public class CategoryViewAdapter  extends BaseAdapter {

    List<Category> lst;

    Context context;

    public CategoryViewAdapter(List<Category> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView text_tensp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  =layoutInflater.inflate(R.layout.category_item, null);
            viewHolder.text_tensp = view.findViewById(R.id.sp_tensp);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text_tensp.setText(lst.get(position).getName());
        return view;
    }


}
