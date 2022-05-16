package com.iti.EShopper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iti.EShopper.Adapter.CustomViewPager;
import com.iti.EShopper.Adapter.ImageAdapter;
import com.iti.EShopper.R;

import java.util.List;

public class ImageSlider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        List<String> imageUri =getIntent().getStringArrayListExtra("imageUri");
        //Log.e("size", String.valueOf(imageUri.size()));
        CustomViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this,imageUri);
        viewPager.setAdapter(adapter);
    }
}