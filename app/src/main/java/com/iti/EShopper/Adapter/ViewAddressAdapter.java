package com.iti.EShopper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.iti.EShopper.Activity.AddAddress;
import com.iti.EShopper.Activity.CheckoutActivity;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ViewAddressAdapter extends RecyclerView.Adapter<ViewAddressAdapter.ProductsQabael> {

    private java.util.List<Address> List;
    private List<Item> ItemList;
    private Context mContext;
    FirebaseAuth cUser = FirebaseAuth.getInstance();
    boolean ItemCatch;
    private DatabaseReference mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private int tPrice = 0;
    private List<String>mlist;
    boolean viewMode;


    public ViewAddressAdapter(Context mContext, List<Address> list, List<Item> ItemList,boolean viewMode) {
        this.mContext = mContext;
        this.List = list;
        this.ItemList=ItemList;
        this.viewMode=viewMode;
    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Address user = List.get(position);

        holder.title.setText(user.getAddressTitle());
        holder.address.setText(user.getAddress());
        holder.city.setText(user.getCity());
        holder.country.setText(user.getCountry());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, AddAddress.class);
                go.putExtra("editMode",true);
                go.putExtra("product_object",List.get(position));
                mContext.startActivity(go);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeAddress(cUser.getUid(),user.getId());
            }
        });
        Log.e("viewMode", String.valueOf(viewMode));
        if (viewMode){
            Log.e("viewMode", String.valueOf(viewMode));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("viewMode", String.valueOf(viewMode));
                    Intent go = new Intent(mContext, CheckoutActivity.class);
                    go.putExtra("position",position);
                    go.putExtra("addressChosen",true);
                    mContext.startActivity(go);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView title,address,city,country,edit,delete;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            title = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);


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

    private List<String> splitter(String banners){

        ArrayList<String> images = new ArrayList<>();
        List<String> imageUri = new ArrayList<>();
        if(banners != null) {
            String[] imgOther = banners.toString().split("\n");
            for (int i = 0; i < imgOther.length; i++) {
                images.add(imgOther[i]);
            }
            for (int i = 0; i < imgOther.length; i++) {
                imageUri.add(images.get(i));
            }
        }
        return imageUri;
    }

}


