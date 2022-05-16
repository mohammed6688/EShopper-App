package com.iti.EShopper.Adapter;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.EShopper.R;

import java.util.List;

public class vBannerAdapter extends RecyclerView.Adapter<vBannerAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mUsers;

    public vBannerAdapter(Context mContext, List<String> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vertical_banner_item, parent, false);
        return new vBannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uri=mUsers.get(position);

        Glide.with(mContext).load(uri).thumbnail(0.1f).into(holder.banner);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*Intent singleActivity = new Intent(mContext, Main2Activity.class);
                singleActivity.putExtra("text", String.valueOf(user.getText()));
                singleActivity.putExtra("name", String.valueOf(user.getName()));
                singleActivity.putExtra("photo", String.valueOf(user.getPhotoUrl()));
                singleActivity.putExtra("song", String.valueOf(user.getsongUrl()));

                singleActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(singleActivity);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
    @Override
    public int getItemViewType(int position) {

        return R.layout.vertical_banner_item;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView banner;

        public ViewHolder(View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.banner);
        }
    }
}

