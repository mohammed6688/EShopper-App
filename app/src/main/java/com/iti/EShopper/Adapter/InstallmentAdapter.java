package com.iti.EShopper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Installment;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.ProductsQabael> {

    private java.util.List<Installment> List = new ArrayList<>();
    private Context mContext;
    DatabaseReference reference;
    String categoryName;
    private List<Item> ItemList ;
    FirebaseAuth cUser = FirebaseAuth.getInstance();
    boolean ItemCatch;
    private DatabaseReference mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();


    public InstallmentAdapter(Context mContext, List<Installment> list) {
        this.mContext = mContext;
        this.List = list;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.installment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Installment user = List.get(position);




    }


    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView remain,startDate,endDate;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            remain = itemView.findViewById(R.id.remain);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);


        }


    }

}

