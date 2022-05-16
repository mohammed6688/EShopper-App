package com.iti.EShopper.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.iti.EShopper.Adapter.BasketAdapter;
import com.iti.EShopper.R;

import com.iti.EShopper.models.Basket;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Offers;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {
    private int REQUEST_CODE = 50;
    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Basket> mBasket;
    FirebaseAuth user;
    private CardView addFavorite,securePayment;
    LinearLayout layProgress,paymentLay;
    private List<Item> ItemList;
    private List<Offers> offersList;
    private int tPrice = 0;
    TextView txtPrice;
    ImageView arrow;
    BasketAdapter homeAdapter;
    String intentMode;
    String categoryName,subCategoryName;
    private Item productDetails;
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        productDetails = (Item) getIntent().getSerializableExtra("product_object");
        intentMode=getIntent().getStringExtra("intentMode");
        categoryName=getIntent().getStringExtra("categoryName");
        subCategoryName=getIntent().getStringExtra("subCategoryName");

        LocalBroadcastManager.getInstance(BasketActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter("refresh"));

        //Log.e("braintreeToken",MyApplication.getPrefranceData("braintreeToken"));
       // String braintreeToken= MyApplication.getPrefranceData("braintreeToken");
        mprogressBar =findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.postrecyclerview);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BasketActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(BasketActivity.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);


        mBasket = new ArrayList<>();
        ItemList = new ArrayList<>();
        offersList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        arrow = findViewById(R.id.arrow);
        txtPrice=findViewById(R.id.txtPrice);
        mprogressBar = findViewById(R.id.progressBar);
        layProgress=findViewById(R.id.layProgress);
        paymentLay=findViewById(R.id.paymentLay);
        addFavorite=findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);
        paymentLay.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        securePayment=findViewById(R.id.securePayment);


        securePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(BasketActivity.this,CheckoutActivity.class);
                checkout.putExtra("supTotal",String.valueOf(tPrice));
                startActivity(checkout);
                finish();

            }

        });

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        readUsers();
    }
    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBasket.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Basket user = snapshot.getValue(Basket.class);
                    assert user != null;
                    mBasket.add(user);
                }

                AddPriceToList();
                ItemList.clear();
                getItemsFromBasket();

                if (mBasket.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);
                    paymentLay.setVisibility(View.VISIBLE);
                }else {
                    layProgress.setVisibility(View.VISIBLE);
                    mprogressBar.setVisibility(View.GONE);
                    paymentLay.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void getItemsFromBasket() {
        for (int i =0;i<mBasket.size();i++){
            Basket order = mBasket.get(i);
            if (!order.getCategory().equals("offer")){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(order.getCategory()).child(order.getSubcategory()).child(order.getItemId());

                int finalI = i;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //mItem.clear();
                        Item user = dataSnapshot.getValue(Item.class);
                        assert user != null;
                        ItemList.add(user);

                        Log.e("i Size", String.valueOf(finalI));
                        //Log.e("mOrder Size", String.valueOf(mOrder.size()));
                        if (finalI ==mBasket.size()-1) {
                            homeAdapter = new BasketAdapter(BasketActivity.this, mBasket,ItemList,false);
                            recyclerView.setAdapter(homeAdapter);
                            AddPriceToList();
                            if (mBasket.size()!=0){
                                mprogressBar.setVisibility(View.GONE);
                                layProgress.setVisibility(View.GONE);

                            }else {
                                mprogressBar.setVisibility(View.GONE);
                                layProgress.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }else {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Offers").child(order.getItemId());

                int finalI = i;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //mItem.clear();
                        Item user = dataSnapshot.getValue(Item.class);
                        assert user != null;
                        ItemList.add(user);

                        //Log.e("i Size", String.valueOf(finalI));
                        //Log.e("mOrder Size", String.valueOf(mOrder.size()));
                        if (finalI ==mBasket.size()-1) {
                            if (ItemList.size()==mBasket.size()) {
                                homeAdapter = new BasketAdapter(BasketActivity.this, mBasket, ItemList, true);
                                recyclerView.setAdapter(homeAdapter);
                                AddPriceToList();
                                if (mBasket.size() != 0) {
                                    mprogressBar.setVisibility(View.GONE);
                                    layProgress.setVisibility(View.GONE);

                                } else {
                                    mprogressBar.setVisibility(View.GONE);
                                    layProgress.setVisibility(View.VISIBLE);


                                }
                            }else {

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }

        }
    }


    private void AddPriceToList() {
        List<Integer> list = new ArrayList<>();
        int sum;
        for (sum = 0; sum < ItemList.size(); sum++) {
            Item mUser = ItemList.get(sum);
            if (!mUser.getPrice().equals("")) {
                int price = Integer.parseInt(mUser.getPrice());
                list.add(price);
            } else {
                Toast.makeText(BasketActivity.this, "there are empty service", Toast.LENGTH_SHORT).show();
            }
        }
        tPrice=0;
        tPrice = sum(list);
        Log.e("tprise", String.valueOf(tPrice));
        txtPrice.setText(tPrice+getResources().getString(R.string.currency));
    }

    private static int sum(List<Integer> list) {
        int sum = 0;
        for (int i: list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
        finish();
    }

}