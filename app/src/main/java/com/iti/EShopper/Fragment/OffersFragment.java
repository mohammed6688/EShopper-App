package com.iti.EShopper.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Activity.BasketActivity;
import com.iti.EShopper.Adapter.OffersAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class OffersFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Item> mFavorite;
    FirebaseAuth user;
    private CardView addFavorite;
    TextView progressTxt;
    private List<Item> ItemList;
    private int alertCount = 0;
    TextView countTextView;
    ImageView basket;
    FrameLayout redCircle;
    boolean guest;
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
            updateAlertIcon();

        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_offers, container, false);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        guest = MyApplication.getPrefranceDataBoolean("Guest");
        countTextView =  view.findViewById(R.id.view_alert_count_textview);
        redCircle =  view.findViewById(R.id.view_alert_red_circle);
        basket = view.findViewById(R.id.basket);
        mprogressBar =view.findViewById(R.id.progressBar);
        recyclerView=view.findViewById(R.id.postrecyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);


        mFavorite = new ArrayList<>();
        ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        mprogressBar = view.findViewById(R.id.progressBar);
        progressTxt=view.findViewById(R.id.progressTxt);
        addFavorite=view.findViewById(R.id.card);
        progressTxt.setVisibility(View.GONE);


            basket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!guest) {
                    alertCount = 0;
                    updateAlertIcon();
                    Intent go = new Intent(getContext(), BasketActivity.class);
                    startActivity(go);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }else {
                        Intent go = new Intent(getContext(), WelcomeActivity.class);
                        startActivity(go);
                    }
                }
            });

        readUsers();

        return view;
    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Offers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFavorite.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item user = snapshot.getValue(Item.class);
                    //assert user != null;
                    if (user!=null) {
                        mFavorite.add(user);
                    }
                }
                Collections.reverse(mFavorite);

                OffersAdapter homeAdapter = new OffersAdapter(getContext(), mFavorite);
                recyclerView.setAdapter(homeAdapter);

                if (mFavorite.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    progressTxt.setVisibility(View.GONE);
                }else {
                    progressTxt.setVisibility(View.VISIBLE);
                    mprogressBar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
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
