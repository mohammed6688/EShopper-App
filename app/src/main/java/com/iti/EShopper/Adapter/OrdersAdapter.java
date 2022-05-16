package com.iti.EShopper.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.EShopper.Activity.ItemReview;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ProductsQabael> {

    private List<Order> List;
    private java.util.List<Item> itemList;
    private List<Item> mItem = new ArrayList<>();
    private Context mContext;

    public OrdersAdapter(Context mContext, List<Order> list,List<Item> itemList) {
        this.mContext = mContext;
        this.List = list;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Order order = List.get(position);
        final Item item = itemList.get(position);

        holder.shipmentNumber.setText(order.getShipmentId());
        holder.orderDate.setText(order.getData());
        holder.status.setText(holder.shippingStatusHandler(order.getShipmentState()));


        holder.itemTitle.setText(item.getTitle());


        Glide.with(mContext).load(splitter(item.getImages()).get(0)).thumbnail(0.1f).into(holder.itemImage);
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(order.getItemCategory()).child(order.getItemSubCategory()).child(order.getItemId());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //mItem.clear();

                Item user = dataSnapshot.getValue(Item.class);
                assert user != null;
                mItem.add(user);


                //Log.e("viewMode", String.valueOf(viewMode));
                Item item = mItem.get(position);

                if (Locale.getDefault().getLanguage().equals("en")){
                    holder.itemTitle.setText(item.getTitle());
                }else if (Locale.getDefault().getLanguage().equals("ar")){
                    holder.itemTitle.setText(item.getTitleAr());
                }else if (Locale.getDefault().getLanguage().equals("ge")){
                    holder.itemTitle.setText(item.getTitleGr());
                }else {
                    holder.itemTitle.setText(item.getTitle());
                }

                Glide.with(mContext).load(splitter(item.getImages()).get(0)).thumbnail(0.1f).into(holder.itemImage);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });*/

        //Log.e("viewMode", String.valueOf(viewMode));
        holder.itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(mContext, ItemReview.class);
                go.putExtra("product_object",itemList.get(position));
                go.putExtra("name",order.getItemCategory());
                go.putExtra("subCateName",order.getItemSubCategory());
                mContext.startActivity(go);
            }
        });


    }



    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView shipmentNumber,orderDate,itemTitle,status;
        ImageView itemImage;
        LinearLayout itemInfo;

        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            shipmentNumber = itemView.findViewById(R.id.shipment_number);
            orderDate = itemView.findViewById(R.id.order_date);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            status = itemView.findViewById(R.id.status);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemInfo = itemView.findViewById(R.id.itemInfo);

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

        private String shippingStatusHandler(String shipmentState) {

            String state;
            switch (shipmentState) {
                case "preparing item":
                    status.setTextColor(mContext.getResources().getColor(R.color.fab_orange_dark));
                    state= mContext.getResources().getString(R.string.preparing_item);
                    break;
                case "item shipped":
                    status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    state= mContext.getResources().getString(R.string.item_shipped);
                    break;
                case "delivered":
                    status.setTextColor(mContext.getResources().getColor(R.color.green));
                    state= mContext.getResources().getString(R.string.delivered);
                    break;
                default:
                    status.setTextColor(mContext.getResources().getColor(R.color.red));
                    state= mContext.getResources().getString(R.string.order_canceled);
                    break;
            }
            return state;
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


