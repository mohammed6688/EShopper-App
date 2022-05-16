package com.iti.EShopper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.EShopper.Activity.CheckoutActivity;
import com.iti.EShopper.Activity.ViewAddress;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.Item;

import java.util.List;

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ProductsQabael> {

    private java.util.List<Address> List;
    private List<Item> ItemList;
    private Context mContext;
    FirebaseAuth cUser = FirebaseAuth.getInstance();
    boolean ItemCatch;
    private DatabaseReference mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private int tPrice = 0;
    private List<String>mlist;
    private boolean chooseMode;


    public ChooseAddressAdapter(Context mContext, List<Address> list, List<Item> ItemList,boolean chooseMode) {
        this.mContext = mContext;
        this.List = list;
        this.ItemList=ItemList;
        this.chooseMode = chooseMode;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Address user = List.get(position);


        holder.title.setText(user.getAddressTitle());
        holder.address.setText(user.getAddress());
        holder.city.setText(user.getCity());
        holder.country.setText(user.getCountry());
        holder.change.setVisibility(View.GONE);
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ViewAddress.class);
                go.putExtra("viewMode",true);
                mContext.startActivity(go);
            }
        });

        if (chooseMode){
            holder.change.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent go = new Intent(mContext,CheckoutActivity.class);
                    go.putExtra("position",position);
                    go.putExtra("addressChosen",true);
                    mContext.startActivity(go);
                }
            });
        }
        /*else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (List.size()>1) {
                        for (int i = 0;List.size()<i;i++) {
                            if (i!=position) {
                                List.remove(i);
                            }
                        }
                    }
                }
            });
        }*/
    }


    @Override
    public int getItemCount() {
        if (List.size()!=0){
            return 1;
        }else {
            return List.size();
        }
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView title,address,city,country,change,delete;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            title = itemView.findViewById(R.id.title);
            change = itemView.findViewById(R.id.change);
            //delete = itemView.findViewById(R.id.delete);


        }


        private void removeAddress(String child,String children) {
            new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getResources().getString(R.string.delete_address_title))
                    .setMessage(mContext.getResources().getString(R.string.delete_address_message))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(child).child(children);
                            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //favorite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                                }
                            });
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }

}


