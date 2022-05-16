package com.iti.EShopper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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
import com.iti.EShopper.models.Favorite;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ProductsQabael> {

    private java.util.List<Item> List = new ArrayList<>();
    private Context mContext;
    DatabaseReference reference;
    String categoryName;
    private java.util.List<Favorite> loveCheck = new ArrayList<>();
    FirebaseAuth cUser = FirebaseAuth.getInstance();
    boolean isFavorite;
    private DatabaseReference mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();

    String subCateName;

    public SearchAdapter(Context mContext, List<Item> list) {
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
        final Item user = List.get(position);

        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (!user.getDiscount().equals("0")){
            holder.txtDiscount.setText(user.getDiscount()+"%");
        }else {
            holder.txtDiscount.setVisibility(View.GONE);
        }
        holder.title.setText(user.getTitle());


        /*holder.title.setText(user.getTitle());
        holder.subTitle.setText(user.getSubTitle());*/
        holder.open.setText("Today "+user.getPrice()+" Pound");
        Glide.with(mContext).load(splitter(user.getImages()).get(0)).thumbnail(0.1f).into(holder.MainImage);


        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemReview.class);
                go.putExtra("product_object",List.get(position));
                go.putExtra("name",user.getCategory());
                go.putExtra("subCateName",user.getSubCategory());
                mContext.startActivity(go);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemReview.class);
                go.putExtra("product_object",List.get(position));
                go.putExtra("name",user.getCategory());
                go.putExtra("subCateName",user.getSubCategory());
                mContext.startActivity(go);
            }
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
        TextView oldPrice;
        ImageView favorite;
        Button open;
        TextView txtDiscount,txtNew;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            MainImage = itemView.findViewById(R.id.post_image);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            oldPrice = itemView.findViewById(R.id.oldPrice);
            favorite = itemView.findViewById(R.id.favorite);
            open = itemView.findViewById(R.id.open);

            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtNew = itemView.findViewById(R.id.txtNew);

        }



        private void removeFavorite(String child,String children) {
            new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getResources().getString(R.string.delete_favorite_title))
                    .setMessage(mContext.getResources().getString(R.string.delete_favorite_message))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favorite").child(child).child(children);
                            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    favorite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
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
