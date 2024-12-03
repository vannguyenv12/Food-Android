package com.vannguyenv12.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vannguyenv12.food.R;
import com.vannguyenv12.food.modal.User;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vannguyenv12.food.modal.User;

import java.util.ArrayList;

public class listuser extends  RecyclerView.Adapter<listuser.MyViewHolder>{
    Context context;
    ArrayList<User> lst;

    public listuser(Context context , ArrayList<User> lst)
    {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sp = LayoutInflater.from(parent.getContext()).inflate(R.layout.showuser, parent, false);
        return new MyViewHolder(sp);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User poke = lst.get(position);
        holder.id.setText(String.valueOf(poke.getId()));
        holder.email.setText(poke.getEmail());
        holder.pass.setText(poke.getPassword());
        holder.firstname.setText(poke.getFirstName());
        holder.lastname.setText(poke.getLastName());
        holder.role.setText(poke.getRole());

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, firstname,lastname,email,pass,role;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.iduser);
            firstname = itemView.findViewById(R.id.firstten);
            lastname = itemView.findViewById(R.id.lasttten);
            email = itemView.findViewById(R.id.email);
            pass = itemView.findViewById(R.id.password);
            role = itemView.findViewById(R.id.role);
        }
    }



}
