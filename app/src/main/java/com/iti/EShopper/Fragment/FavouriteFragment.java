package com.iti.EShopper.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.FavoriteAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Favorite;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Favorite> mFavorite;
    FirebaseAuth user;
    private CardView addFavorite;
    LinearLayout layProgress;
    private List<Item> ItemList;
    FavoriteAdapter homeAdapter;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            homeAdapter.notifyDataSetChanged();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite, container, false);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("refresh"));
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
        layProgress=view.findViewById(R.id.layProgress);
        addFavorite=view.findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent("openCategory");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);*/
            }
        });
        readUsers();

        return view;
    }
    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favorite").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFavorite.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favorite user = snapshot.getValue(Favorite.class);
                    assert user != null;
                    mFavorite.add(user);
                }

                getItemsFromFavorite();
                if (mFavorite.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);
                }else {
                    layProgress.setVisibility(View.VISIBLE);
                    mprogressBar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void getItemsFromFavorite() {
        for (Favorite favorite:mFavorite) {

            if (!favorite.getCategory().equals("offer")) {
                Log.e("size of ", favorite.getCategory());
                Log.e("siz", favorite.getItemId());

                Query lquery = FirebaseDatabase.getInstance().getReference("Items").child(favorite.getCategory()).child(favorite.getSubcategory()).orderByChild("id").equalTo(favorite.getItemId());

                lquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //ItemList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Item user = snapshot.getValue(Item.class);

                            Log.e("sdsd", "sds");
                            ItemList.add(user);

                            if (ItemList.size() == mFavorite.size()) {
                                Log.e("size of ItemList", String.valueOf(ItemList.size()));

                                homeAdapter = new FavoriteAdapter(getContext(), mFavorite, ItemList);
                                recyclerView.setAdapter(homeAdapter);
                                if (mFavorite.size() != 0) {
                                    mprogressBar.setVisibility(View.GONE);
                                    layProgress.setVisibility(View.GONE);
                                } else {
                                    layProgress.setVisibility(View.VISIBLE);
                                    mprogressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "error: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Query lquery = FirebaseDatabase.getInstance().getReference("Offers").orderByChild("id").equalTo(favorite.getItemId());

                lquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //ItemList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Item user = snapshot.getValue(Item.class);

                            Log.e("sdsd", "sds");
                            ItemList.add(user);

                            if (ItemList.size() == mFavorite.size()) {
                                Log.e("size of ItemList", String.valueOf(ItemList.size()));

                                homeAdapter = new FavoriteAdapter(getContext(), mFavorite, ItemList);
                                recyclerView.setAdapter(homeAdapter);
                                if (mFavorite.size() != 0) {
                                    mprogressBar.setVisibility(View.GONE);
                                    layProgress.setVisibility(View.GONE);
                                } else {
                                    layProgress.setVisibility(View.VISIBLE);
                                    mprogressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "error: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
