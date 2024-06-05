package com.vannguyenv12.food.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.vannguyenv12.food.CartActivity;
import com.vannguyenv12.food.R;
import com.vannguyenv12.food.api.CartApiService;
import com.vannguyenv12.food.api.FoodApiService;
import com.vannguyenv12.food.modal.Cart;
import com.vannguyenv12.food.utils.Constant;
import com.vannguyenv12.food.utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItems;
    private Context context;

    public CartAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cartItem = cartItems.get(position);
        holder.pizzaName.setText(cartItem.getProductName());
        holder.pizzaPrice.setText("$" + cartItem.getPrice());
        holder.quantityText.setText(String.valueOf(cartItem.getQuantity()));
        // Giả định rằng tất cả các mục đều dùng chung một hình ảnh
        Glide.with(context).load(cartItem.getProductImage()).into(holder.pizzaImage);

        holder.increaseQuantity.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            holder.quantityText.setText(String.valueOf(cartItem.getQuantity()));
            updateCartQuantity(cartItem, position);
            notifyItemChanged(position);
        });

        holder.decreaseQuantity.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.quantityText.setText(String.valueOf(cartItem.getQuantity()));
                updateCartQuantity(cartItem, position);
                notifyItemChanged(position);
            }
        });

        CartApiService apiService = RetrofitClient.retrofit.create(CartApiService.class);

        holder.removeItem.setOnClickListener(v -> {
            int itemId = cartItem.getId();
            apiService.deleteCart(Constant.API_KEY, "eq." + String.valueOf(itemId)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        ((CartActivity) context).fetchCartTotal();
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartItems.size());
                    } else {
                        // Xử lý lỗi khi không thể xóa item
                        System.out.println("Remove failed: " + response.code());
                        System.out.println("remove failed: " + response.message());

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Xử lý lỗi khi cuộc gọi API thất bại
                    System.out.println("remove failed: " + t.getMessage());
                }
            });
        });
    }

    private void updateCartQuantity(Cart cartItem, int position) {

        CartApiService apiService = RetrofitClient.retrofit.create(CartApiService.class);

        apiService.updateCartQuantity(Constant.API_KEY, Constant.CONTENT_TYPE, "eq." + String.valueOf(cartItem.getId()), cartItem).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    // Xử lý lỗi khi không thể cập nhật số lượng

                }
                ((CartActivity) context).fetchCartTotal();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi khi cuộc gọi API thất bại
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaPrice, quantityText;
        ImageView pizzaImage;
        Button increaseQuantity, decreaseQuantity;
        ImageButton removeItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizza_name);
            pizzaPrice = itemView.findViewById(R.id.pizza_price);
            quantityText = itemView.findViewById(R.id.quantity_text);
            pizzaImage = itemView.findViewById(R.id.pizza_image);
            increaseQuantity = itemView.findViewById(R.id.increase_quantity);
            decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);
            removeItem = itemView.findViewById(R.id.remove_item);
        }
    }
}