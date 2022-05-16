package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.ViewAddressAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ViewAddress extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Address> mAddress;
    FirebaseAuth user;
    private List<Item> ItemList;
    private int tPrice = 0;
    TextView txtPrice;
    CardView card;
    boolean viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_address);


        viewMode=getIntent().getBooleanExtra("viewMode",false);
        mprogressBar =findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recycleView);
        card=findViewById(R.id.card);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAddress.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(ViewAddress.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);


        mAddress = new ArrayList<>();
        ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        txtPrice=findViewById(R.id.txtPrice);

        if (viewMode)
            card.setVisibility(View.GONE);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddress!=null && mAddress.size()<=3) {
                    Intent go = new Intent(ViewAddress.this, AddAddress.class);
                    startActivity(go);
                }else {
                    Toast.makeText(ViewAddress.this, getResources().getString(R.string.max_address), Toast.LENGTH_SHORT).show();
                }
            }
        });
        readUsers();
    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAddress.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Address user = snapshot.getValue(Address.class);
                    assert user != null;
                    mAddress.add(user);
                }

                //Log.e("viewMode", String.valueOf(viewMode));
                ViewAddressAdapter homeAdapter = new ViewAddressAdapter(ViewAddress.this, mAddress,ItemList,viewMode);
                recyclerView.setAdapter(homeAdapter);

                if (mAddress.size()!=0){
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