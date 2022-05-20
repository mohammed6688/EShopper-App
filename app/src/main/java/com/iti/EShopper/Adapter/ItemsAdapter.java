package com.iti.EShopper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.EShopper.Activity.ItemReview;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ProductsQabael> {

    private java.util.List<Product> List;
    private Context mContext;

    public ItemsAdapter(Context mContext, List<Product> list) {
        this.mContext = mContext;
        this.List = list;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Product user = List.get(position);


        holder.txtDiscount.setVisibility(View.GONE);
        holder.title.setText(user.getTitle());
        if (user.getQuantity()<=2 && user.getQuantity()>0){
            holder.itemLeft.setVisibility(View.VISIBLE);
            holder.itemLeft.setText("باقي "+user.getQuantity()+" قطع");
        }


        holder.price.setText(user.getPrice()+" EGP");
        Glide.with(mContext).load(user.getPhotoUrl()).thumbnail(0.1f).into(holder.MainImage);

        holder.open.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemReview.class);
            go.putExtra("product_object",List.get(position));
            mContext.startActivity(go);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent go = new Intent(mContext, ItemReview.class);
            go.putExtra("product_object",List.get(position));
            mContext.startActivity(go);
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        ImageView MainImage;
        TextView title;
        TextView subTitle;
        TextView price;
        Button open;
        TextView txtDiscount,txtNew,itemLeft;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            MainImage = itemView.findViewById(R.id.post_image);
            title = itemView.findViewById(R.id.title);
            itemLeft = itemView.findViewById(R.id.itemLeft);
            subTitle = itemView.findViewById(R.id.subTitle);
            price = itemView.findViewById(R.id.oldPrice);
            open = itemView.findViewById(R.id.open);

            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtNew = itemView.findViewById(R.id.txtNew);

        }
    }

}
