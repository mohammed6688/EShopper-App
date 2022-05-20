package com.iti.EShopper.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.iti.EShopper.Module.Cart;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;

import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ProductsQabael> {

    private java.util.List<Cart> List;
    private List<Product> ItemList;
    private Context mContext;
    String categoryName;
    boolean ItemCatch;
    private int tPrice = 0;
    boolean offerMode;
    boolean oneTime=false;

    public BasketAdapter(Context mContext, List<Cart> list, List<Product> ItemList, boolean offerMode) {
        this.mContext = mContext;
        this.List = list;
        this.ItemList=ItemList;
        this.offerMode=offerMode;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, @SuppressLint("RecyclerView") int position) {
        Cart user = List.get(position);
        Product mUser = ItemList.get(position);

        holder.subTitle.setVisibility(View.GONE);
        holder.title.setText(mUser.getTitle());

        holder.oldPrice.setText(mUser.getPrice()+mContext.getResources().getString(R.string.currency));
        //holder.open.setText("Today " + mUser.getPrice() + " Pound");
        Glide.with(mContext).load(mUser.getPhotoUrl()).thumbnail(0.1f).into(holder.MainImage);

    }


    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        ImageView MainImage,remove;
        TextView title;
        TextView subTitle;
        TextView oldPrice;
        Button open;
        TextView txtDiscount,txtNew,size;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            size = itemView.findViewById(R.id.size);
            MainImage = itemView.findViewById(R.id.post_image);
            remove = itemView.findViewById(R.id.remove);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            open = itemView.findViewById(R.id.open);

            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtNew = itemView.findViewById(R.id.txtNew);

        }
    }

}
