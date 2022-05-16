package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.OrdersAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Order> mOrder;
    LinearLayout layProgress,paymentLay;
    FirebaseAuth user;
    private List<Item> ItemList;
    private List<Item> mItem;
    private int tPrice = 0;
    TextView txtPrice;
    CardView addFavorite;
    boolean viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //viewMode=getIntent().getBooleanExtra("viewMode",false);
        layProgress=findViewById(R.id.layProgress);

        addFavorite=findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);
        mprogressBar =findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOrders.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(MyOrders.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);

        mOrder = new ArrayList<>();
        mItem = new ArrayList<>();
        //ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        //txtPrice=findViewById(R.id.txtPrice);

        readUsers();

    }
    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mOrder.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order user = snapshot.getValue(Order.class);
                    assert user != null;
                    mOrder.add(user);
                }

                readItems();
                if (mOrder.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);

                }else {
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.VISIBLE);
                }
                //Log.e("viewMode", String.valueOf(viewMode));
                /*OrdersAdapter homeAdapter = new OrdersAdapter(MyOrders.this, mOrder,mItem);
                recyclerView.setAdapter(homeAdapter);

                if (mOrder.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);

                }else {
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.VISIBLE);
                }*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void readItems() {
        Log.e("size of orders", String.valueOf(mOrder.size()));
        for (int i =0;i<mOrder.size();i++){
            Order order = mOrder.get(i);
            if (!order.getItemCategory().equals("offer")){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(order.getItemCategory()).child(order.getItemSubCategory()).child(order.getItemId());

                int finalI = i;
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //mItem.clear();
                        Item user = dataSnapshot.getValue(Item.class);
                        assert user != null;
                        mItem.add(user);

                        Log.e("i Size", String.valueOf(finalI));
                        Log.e("mOrder Size", String.valueOf(mOrder.size()));
                        if (finalI ==mOrder.size()-1) {
                            OrdersAdapter homeAdapter = new OrdersAdapter(MyOrders.this, mOrder,mItem);
                            recyclerView.setAdapter(homeAdapter);

                            if (mOrder.size()!=0){
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
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //mItem.clear();
                        Item user = dataSnapshot.getValue(Item.class);
                        assert user != null;
                        mItem.add(user);

                        Log.e("i Size", String.valueOf(finalI));
                        Log.e("mOrder Size", String.valueOf(mOrder.size()));
                        if (finalI ==mOrder.size()-1) {
                            OrdersAdapter homeAdapter = new OrdersAdapter(MyOrders.this, mOrder,mItem);
                            recyclerView.setAdapter(homeAdapter);

                            if (mOrder.size()!=0){
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
            }

        }

    }
}