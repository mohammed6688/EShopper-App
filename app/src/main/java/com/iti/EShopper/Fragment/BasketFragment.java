package com.iti.EShopper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Favorite;

import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Favorite> mFavorite;
    FirebaseAuth user;
    private CardView addFavorite;
    LinearLayout layProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_basket, container, false);


        mprogressBar =view.findViewById(R.id.progressBar);
        recyclerView=view.findViewById(R.id.postrecyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);


        mFavorite = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        mprogressBar = view.findViewById(R.id.progressBar);
        layProgress=view.findViewById(R.id.layProgress);
        addFavorite=view.findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        readUsers();


        return view;
    }
    private void readUsers() {

        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFavorite.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favorite user = snapshot.getValue(Favorite.class);
                    assert user != null;
                    mFavorite.add(user);
                }
                BasketAdapter homeAdapter = new BasketAdapter(getContext(), mBasket,ItemList);
                recyclerView.setAdapter(homeAdapter);
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
        });*/
    }
}
