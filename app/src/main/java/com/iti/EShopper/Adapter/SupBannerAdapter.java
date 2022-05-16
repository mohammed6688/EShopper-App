package com.iti.EShopper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.iti.EShopper.Activity.ImageSlider;
import com.iti.EShopper.R;

import java.util.ArrayList;
import java.util.List;

public class SupBannerAdapter extends PagerAdapter {
    Context context;
    List<Uri> images;
    LayoutInflater layoutInflater;
    ArrayList<String> image= new ArrayList<>();

    public SupBannerAdapter(Context context, List<Uri> images) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.slidingimages_layout, container, false);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imagess);
        final int width = context.getResources().getDisplayMetrics().widthPixels;

        if (images != null){
            //imageView.setImageResource(images[position]);
            Glide.with(context).load(images.get(position)).thumbnail(0.2f).into(imageView);
        }

        container.addView(itemView);

        for (int i = 0; i < images.size(); i++) {
            image.add(images.get(i).toString());
        }

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(context, ImageSlider.class);
                go.putStringArrayListExtra("imageUri",image);
                context.startActivity(go);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

