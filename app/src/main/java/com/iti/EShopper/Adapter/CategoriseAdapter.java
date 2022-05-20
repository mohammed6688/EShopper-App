package com.iti.EShopper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.EShopper.Activity.ItemsActivity;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;

import java.util.List;

public class CategoriseAdapter extends RecyclerView.Adapter<CategoriseAdapter.ProductsQabael> {

    private java.util.List<String> List ;
    private Context mContext;

    public CategoriseAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.List = list;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        String category = List.get(position);

        holder.title.setText(category);
        holder.itemView.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemsActivity.class);
            go.putExtra("categoryName",category);
            mContext.startActivity(go);
        });

        holder.card.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemsActivity.class);
            go.putExtra("categoryName",category);
            mContext.startActivity(go);
        });
        holder.image.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemsActivity.class);
            go.putExtra("categoryName",category);
            mContext.startActivity(go);
        });
        holder.title.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemsActivity.class);
            go.putExtra("categoryName",category);
            mContext.startActivity(go);
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        CardView card;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);

        }
    }


}