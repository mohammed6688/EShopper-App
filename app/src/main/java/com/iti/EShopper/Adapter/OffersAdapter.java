package com.iti.EShopper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Activity.ImageSlider;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Basket;
import com.iti.EShopper.models.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ProductsQabael> {

    private java.util.List<Item> List;
    private Context mContext;
    boolean guest;

    public OffersAdapter(Context mContext, List<Item> list) {
        this.mContext = mContext;
        this.List = list;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Item user = List.get(position);
        ArrayList<String> images = new ArrayList<>();
        holder.title.setText(user.getTitle());
        holder.body.setText(user.getMore());

        String[] imgOther = user.getImages().toString().split("\n");
        images.addAll(Arrays.asList(imgOther));

        if (!user.getImages().isEmpty()){
            Glide.with(mContext).load(user.getImages()).thumbnail(0.1f).into(holder.image);
        }else {
            holder.image.setVisibility(View.GONE);
        }

        guest = MyApplication.getPrefranceDataBoolean("Guest");
        if (!guest) {
            holder.checkBasket(user);
        }
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!guest) {
                    holder.AddBasket(user);
                }else {
                    Intent go = new Intent(mContext, WelcomeActivity.class);
                    mContext.startActivity(go);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ImageSlider.class);
                go.putStringArrayListExtra("imageUri",images);
                mContext.startActivity(go);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ImageSlider.class);
                go.putStringArrayListExtra("imageUri",images);
                mContext.startActivity(go);
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ImageSlider.class);
                go.putStringArrayListExtra("imageUri",images);
                mContext.startActivity(go);
            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView title,body;
        ImageView image;
        CardView card;
        Button book;
        FirebaseAuth user;
        private List<Basket> BasketCheck = new ArrayList<>();
        private DatabaseReference mMessagesDatabaseReference;
        boolean isBasket;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            image = itemView.findViewById(R.id.image);
            book = itemView.findViewById(R.id.book);
            mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
            user=FirebaseAuth.getInstance();

        }
        private void checkBasket(Item offers) {


            Query lquery = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid()).orderByChild("itemId").equalTo(offers.getId());

            lquery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BasketCheck.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Basket user = snapshot.getValue(Basket.class);

                        BasketCheck.add(user);

                        Log.e("size", String.valueOf(BasketCheck.size()));
                        if (!BasketCheck.isEmpty()){
                            book.setEnabled(false);
                            book.setBackgroundColor(mContext.getResources().getColor(R.color.grayDarkColor));
                            book.setText(mContext.getResources().getString(R.string.added_to_basket));
                            isBasket=true;
                            break;
                        }else {
                            isBasket=false;
                            book.setEnabled(true);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext, "error: "+databaseError, Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void AddBasket(Item offers) {
            DatabaseReference r =mMessagesDatabaseReference.child("Basket").child(user.getUid()).push();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());
            Basket podcast_adabter = new Basket(
                    r.getKey(),
                    offers.getId(),
                    currentDateAndTime,
                    "offer",
                    "",
                    "");
            r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(mContext, "تم الاضافة الي السلة", Toast.LENGTH_SHORT).show();
                    AddBadge(false,true);
                    book.setEnabled(false);
                    book.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
                    //goToBasket();
                }
            });
        }
        private void AddBadge(boolean favorite,boolean basket){
            Intent intent = new Intent("add-badge");
            intent.putExtra("Favorite",favorite);
            intent.putExtra("Basket",basket);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
    }


}

