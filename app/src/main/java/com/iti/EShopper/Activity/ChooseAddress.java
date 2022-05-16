package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.ChooseAddressAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ChooseAddress extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Address> mBasket;
    FirebaseAuth user;
    private List<Item> ItemList;
    private int tPrice = 0;
    TextView txtPrice;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);


        mprogressBar =findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycleView);
        card=findViewById(R.id.card);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseAddress.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(ChooseAddress.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);


        mBasket = new ArrayList<>();
        ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        txtPrice=findViewById(R.id.txtPrice);

        readUsers();
    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBasket.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Address user = snapshot.getValue(Address.class);
                    assert user != null;
                    mBasket.add(user);
                }

                ChooseAddressAdapter homeAdapter = new ChooseAddressAdapter(ChooseAddress.this, mBasket,ItemList,true);
                recyclerView.setAdapter(homeAdapter);

                if (mBasket.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                }else {
                    mprogressBar.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}