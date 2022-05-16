package com.iti.EShopper.Adapter;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.EShopper.Activity.ItemReview;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Basket;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ProductsQabael> {

    private List<Basket> List;
    private List<Item> ItemList;
    private Context mContext;
    DatabaseReference reference;
    String categoryName;
    FirebaseAuth cUser = FirebaseAuth.getInstance();
    boolean ItemCatch;
    private DatabaseReference mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private int tPrice = 0;
    boolean offerMode;
    boolean oneTime=false;

    public BasketAdapter(Context mContext, List<Basket> list, List<Item> ItemList,boolean offerMode) {
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
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
            Basket user = List.get(position);

            //holder.remove.setVisibility(View.VISIBLE);
            //holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Item mUser = ItemList.get(position);
            if (!mUser.getDiscount().equals("0")) {
                holder.txtDiscount.setText(mUser.getDiscount() + "%");
            } else {
                holder.txtDiscount.setVisibility(View.GONE);
            }

            holder.subTitle.setVisibility(View.GONE);
            holder.title.setText(mUser.getTitle());

            holder.size.setText(mContext.getResources().getString(R.string.size)+":  "+user.getSize());
            holder.oldPrice.setText(mUser.getPrice()+mContext.getResources().getString(R.string.currency));
            //holder.open.setText("Today " + mUser.getPrice() + " Pound");
            Log.e("id",mUser.getId());
            Log.e("image",splitter(mUser.getImages()).get(0).toString());
            Glide.with(mContext).load(splitter(mUser.getImages()).get(0)).thumbnail(0.1f).into(holder.MainImage);


            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("catName",user.getCategory());
                    holder.removeFavorite(cUser.getUid(),user.getId(),position);
                }
            });
            holder.open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go = new Intent(mContext, ItemReview.class);
                    go.putExtra("product_object",ItemList.get(position));
                    go.putExtra("name",categoryName);
                    mContext.startActivity(go);
                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go = new Intent(mContext, ItemReview.class);
                    go.putExtra("product_object",ItemList.get(position));
                    go.putExtra("name",categoryName);
                    mContext.startActivity(go);
                }
            });


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
        ImageView favorite;
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
            favorite = itemView.findViewById(R.id.favorite);
            open = itemView.findViewById(R.id.open);

            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtNew = itemView.findViewById(R.id.txtNew);

        }


        private void removeFavorite(String child,String children,int position) {
            new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getResources().getString(R.string.delete_basket_title))
                    .setMessage(mContext.getResources().getString(R.string.delete_basket_message))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Basket").child(child).child(children);
                            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, List.size());
                                }
                            });
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }

    private List<String> splitter(String banners){

        ArrayList<String> images = new ArrayList<>();
        List<String> imageUri = new ArrayList<>();
        if(banners != null) {
            String[] imgOther = banners.toString().split("\n");
            for (int i = 0; i < imgOther.length; i++) {
                images.add(imgOther[i]);
            }
            for (int i = 0; i < imgOther.length; i++) {
                imageUri.add(images.get(i));
            }
        }
        return imageUri;
    }

}

