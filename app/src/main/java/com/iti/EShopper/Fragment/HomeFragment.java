package com.iti.EShopper.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Activity.Search;
import com.iti.EShopper.Adapter.HomeAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Banner> mBanners;
    private EditText et_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        mprogressBar = view.findViewById(R.id.progressBar);
        mprogressBar =view.findViewById(R.id.progressBar);
        recyclerView=view.findViewById(R.id.postrecyclerview);
        et_search=view.findViewById(R.id.et_search);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mBanners = new ArrayList<>();


        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent go = new Intent(getContext(), Search.class);
                startActivity(go);
            }
        });

        readUsers();
        return view;
    }
    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Banners");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBanners.clear();

                Banner user = dataSnapshot.getValue(Banner.class);
                //assert user != null;
                mBanners.add(user);
                HomeAdapter homeAdapter = new HomeAdapter(getContext(), mBanners);
                recyclerView.setAdapter(homeAdapter);
                mprogressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
