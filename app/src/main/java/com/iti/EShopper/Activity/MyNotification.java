package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.iti.EShopper.Adapter.NotificationAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class MyNotification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Notification> mOrder;
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
        setContentView(R.layout.activity_my_notification);

        //viewMode=getIntent().getBooleanExtra("viewMode",false);
        layProgress=findViewById(R.id.layProgress);

        addFavorite=findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);
        mprogressBar =findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotification.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(MyNotification.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);

        mOrder = new ArrayList<>();
        mItem = new ArrayList<>();
        //ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        //txtPrice=findViewById(R.id.txtPrice);

        readUsers();

    }
    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notification").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mOrder.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification user = snapshot.getValue(Notification.class);
                    assert user != null;
                    mOrder.add(user);
                }

                NotificationAdapter homeAdapter = new NotificationAdapter(MyNotification.this, mOrder);
                recyclerView.setAdapter(homeAdapter);

                if (mOrder.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);

                }else {
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.VISIBLE);


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

}