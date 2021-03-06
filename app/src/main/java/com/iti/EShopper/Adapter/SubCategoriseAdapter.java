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
import com.iti.EShopper.R;
import com.iti.EShopper.models.Product;
import com.iti.EShopper.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriseAdapter extends RecyclerView.Adapter<SubCategoriseAdapter.ProductsQabael> {

    private Intent i;
    private java.util.List<Product> List = new ArrayList<>();
    private Context mContext;
    private List<SubCategory> mSubCategory;
    List<SubCategory> mSubCategorise =new ArrayList<>();
    SubCategory filteredSubCat;


    public SubCategoriseAdapter(Context mContext, List<Product> list) {
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
        final Product user = List.get(position);

        holder.title.setText(user.getCategory());
        Glide.with(mContext).load(user.getPhotoUrl()).thumbnail(0.1f).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemsActivity.class);
                go.putExtra("subCategoryName",user.getCategory());
                mContext.startActivity(go);
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemsActivity.class);
                go.putExtra("subCategoryName",user.getCategory());
                mContext.startActivity(go);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemsActivity.class);
                go.putExtra("subCategoryName",user.getCategory());
                mContext.startActivity(go);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemsActivity.class);
                go.putExtra("subCategoryName",user.getCategory());
                mContext.startActivity(go);
            }
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
