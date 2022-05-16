package com.iti.EShopper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.EShopper.R;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ProductsQabael> {

    private java.util.List<Notification> List;
    private java.util.List<Item> itemList;
    private List<Item> mItem = new ArrayList<>();
    private Context mContext;

    public NotificationAdapter(Context mContext, List<Notification> list) {
        this.mContext = mContext;
        this.List = list;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ProductsQabael onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsQabael(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsQabael holder, int position) {
        final Notification order = List.get(position);


        holder.title.setText(order.getNotificationTitle());
        holder.body.setText(order.getNotificationBody());
        holder.date.setText(order.getDate());

    }



    @Override
    public int getItemCount() {
        return List.size();
    }


    public class ProductsQabael extends RecyclerView.ViewHolder {

        TextView title,body,date;


        public ProductsQabael(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            date = itemView.findViewById(R.id.date);

        }

    }

}

