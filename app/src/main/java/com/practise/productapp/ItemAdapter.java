package com.practise.productapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    List<ItemModel.Product> productList;

    public ItemAdapter(Context context, List<ItemModel.Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemModel.Product product = productList.get(position);

        if (productList != null) {

            holder.pro_name.setText(product.getTitle());
            holder.discription.setText(product.getDescription());
            holder.price.setText("Rs."+String.valueOf(product.getPrice()));

            Glide.with(context)
                    .load(product.getThumbnail())
                    .placeholder(R.drawable.imgnotfound)
                    .error(R.drawable.imgnotfound)
                    .into(holder.imageView);
            int productId = productList.get(position).getId();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("productId",productId);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pro_name,discription,price;
        CircularImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pro_name =(itemView).findViewById(R.id.pro_name);
            discription = (itemView).findViewById(R.id.discription);
            price = (itemView).findViewById(R.id.prise);
            imageView = (itemView).findViewById(R.id.img_product);

        }
    }
}
