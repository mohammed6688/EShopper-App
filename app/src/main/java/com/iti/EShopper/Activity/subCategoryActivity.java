package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.SubCategoriseAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Category;
import com.iti.EShopper.models.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class subCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Category> mCategory;
    private TextView title,progressTxt;
    private List<SubCategory> mSubCategory;
    Category productDetails;
    List<SubCategory> filteredSubCat = new ArrayList<>();

    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);


        productDetails = (Category) getIntent().getSerializableExtra("product_object");
        category=getIntent().getStringExtra("category");
        initializeVariables();
        initializeListener();
        //readUsers();
        readUsers(productDetails.getCategoryName(),productDetails.getId());
    }

    private void initializeVariables() {
        progressTxt = findViewById(R.id.progTxt);
        mprogressBar =findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.postrecyclerview);
        title=findViewById(R.id.title);
        title.setText(category);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false));

        mCategory = new ArrayList<>();
        mSubCategory = new ArrayList<>();

        mprogressBar = findViewById(R.id.progressBar);
    }

    private void initializeListener() {}



    private void readUsers(String cat,String catId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SubCategory");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filteredSubCat.clear();
                mSubCategory.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SubCategory user = snapshot.getValue(SubCategory.class);
                    assert user != null;
                    mSubCategory.add(user);
                }

                SubCategory mSubCat;

                for (int i=0;i<mSubCategory.size();i++){
                    mSubCat = mSubCategory.get(i);
                    if (mSubCat.getCategoryId().equals(catId)){
                        filteredSubCat.add(mSubCat);
                    }
                }

                SubCategoriseAdapter homeAdapter = new SubCategoriseAdapter(subCategoryActivity.this, filteredSubCat,cat,catId);
                recyclerView.setAdapter(homeAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                if (filteredSubCat.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    progressTxt.setVisibility(View.GONE);
                }else {
                    progressTxt.setVisibility(View.VISIBLE);
                    mprogressBar.setVisibility(View.GONE);
                }
                //mprogressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    /*private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categorise").child(category);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mCategory.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category user = snapshot.getValue(Category.class);
                    assert user != null;
                    mCategory.add(user);
                }

                CategoriseAdapter homeAdapter = new CategoriseAdapter(subCategory.this, mCategory);
                recyclerView.setAdapter(homeAdapter);
                mprogressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }*/
}