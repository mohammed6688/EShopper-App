package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Adapter.ItemsAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> mCategory;
    ProgressBar progressBar;
    TextView progressTxt;
    String categoryName,subCategoryName;
    private int alertCount = 0;
    TextView countTextView,txtsubCategoryName;
    FrameLayout redCircle;
    ImageView basket;
    boolean guest;
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
            updateAlertIcon();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        LocalBroadcastManager.getInstance(ItemsActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        guest = MyApplication.getPrefranceDataBoolean("Guest");
        subCategoryName=getIntent().getStringExtra("subCategoryName");
        basket = findViewById(R.id.basket);
        txtsubCategoryName = findViewById(R.id.subCategoryName);
        redCircle =  findViewById(R.id.view_alert_red_circle);
        countTextView = findViewById(R.id.view_alert_count_textview);
        progressTxt=findViewById(R.id.progress_txt);
        progressBar=findViewById(R.id.progress);
        progressTxt.setVisibility(View.GONE);
        recyclerView=findViewById(R.id.recycleView);
        txtsubCategoryName.setText(subCategoryName);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.VERTICAL,false));
        mCategory=new ArrayList<>();

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!guest) {
                alertCount = 0;
                updateAlertIcon();
                Intent go = new Intent(ItemsActivity.this, BasketActivity.class);
                go.putExtra("categoryName",categoryName);
                go.putExtra("subCategoryName",subCategoryName);
                go.putExtra("intentMode","itemList");
                startActivity(go);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                finish();
                }else {
                    Intent go = new Intent(ItemsActivity.this, WelcomeActivity.class);
                    startActivity(go);
                }
            }
        });
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(categoryName).child(subCategoryName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCategory.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item user = snapshot.getValue(Item.class);
                    assert user != null;
                    mCategory.add(user);
                }
                Collections.sort(mCategory,new CustomComparator());
                Collections.reverse(mCategory);
                ItemsAdapter homeAdapter = new ItemsAdapter(ItemsActivity.this, mCategory,categoryName,subCategoryName);
                recyclerView.setAdapter(homeAdapter);


                if (mCategory.size()!=0){
                    progressBar.setVisibility(View.GONE);
                    progressTxt.setVisibility(View.GONE);
                }else {
                    progressTxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private class CustomComparator implements Comparator<Item>{

        @Override
        public int compare(Item o1, Item o2) {
            return o1.getPrice().compareTo(o2.getPrice());

        }
    }
    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < 10) {
            countTextView.setText(String.valueOf(alertCount));
        } else {
            countTextView.setText("+10");
        }

        redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
    }
}