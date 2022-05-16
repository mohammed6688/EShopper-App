package com.iti.EShopper.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.iti.EShopper.R;
import com.iti.EShopper.models.Banner;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Banner> mUsers;
    private SupBannerAdapter myCustomPagerAdapter;
    List<Uri> imagesList=new ArrayList<>();

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    public HomeAdapter(Context mContext, List<Banner> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case R.layout.rectangular_banner:
                view = LayoutInflater.from(mContext).inflate(R.layout.rectangular_banner, parent, false);
                return new ViewHolder(view);
            case R.layout.horizontal_sliding_banner:
                view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_sliding_banner, parent, false);
                return new ViewHolder2(view);
            case R.layout.vertical_banner:
                view = LayoutInflater.from(mContext).inflate(R.layout.vertical_banner, parent, false);
                return new ViewHolder3(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.vertical_banner, parent, false);
                return new ViewHolder(view);
        }
        //View view = LayoutInflater.from(mContext).inflate(R.layout.card_items, parent, false);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  ViewHolder){
            final Banner banner = mUsers.get(position);

            Glide.with(mContext).load(banner.getRectangularBanner()).thumbnail(0.1f).into(((ViewHolder)holder).rectangularBanner);


            Log.e("size", String.valueOf(splitter(banner.getSlidingBanner()).size()));
            for (int i=0 ; i < splitter(banner.getSlidingBanner()).size() ; i++){
                imagesList.add(Uri.parse(splitter(banner.getSlidingBanner()).get(i)));
            }
            ((ViewHolder)holder).profile_image.setOffscreenPageLimit(3);
            ((ViewHolder)holder).profile_image.setClipToPadding(false);
            ((ViewHolder)holder).profile_image.setClipChildren(false);
            //Uri images[] = {Uri.parse(banner.getSlidingBanner())};
            myCustomPagerAdapter = new SupBannerAdapter(mContext, imagesList);
            ((ViewHolder)holder).profile_image.setAdapter(myCustomPagerAdapter);


            final int count=imagesList.size();

            ((ViewHolder)holder).indicator.setViewPager(((ViewHolder)holder).profile_image);

            final float density = mContext.getResources().getDisplayMetrics().density;

            //Set circle indicator radius
            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == count) {
                        currentPage = 0;
                    }
                    ((ViewHolder)holder).profile_image.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 1700, 1700);

            ((ViewHolder)holder).indicator.setRadius(5 * density);

            ((ViewHolder)holder).indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });


            Log.e("size", String.valueOf(splitter(banner.getVerticalBanner()).size()));
            ((ViewHolder)holder).recycleview.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            ((ViewHolder)holder).recycleview.setLayoutManager(linearLayoutManager);
            vBannerAdapter searchAdapter = new vBannerAdapter(mContext, splitter(banner.getVerticalBanner()));
            ((ViewHolder)holder).recycleview.setAdapter(searchAdapter);

        }else if (holder instanceof  ViewHolder2){
            final Banner banner = mUsers.get(position);

            Log.e("size", String.valueOf(splitter(banner.getSlidingBanner()).size()));
            for (int i=0 ; i < splitter(banner.getSlidingBanner()).size() ; i++){
                imagesList.add(Uri.parse(splitter(banner.getSlidingBanner()).get(i)));
            }

            //Uri images[] = {Uri.parse(banner.getSlidingBanner())};
            myCustomPagerAdapter = new SupBannerAdapter(mContext, imagesList);
            ((ViewHolder2)holder).profile_image.setAdapter(myCustomPagerAdapter);


            final int count=imagesList.size();

            ((ViewHolder2)holder).indicator.setViewPager(((ViewHolder2)holder).profile_image);

            final float density = mContext.getResources().getDisplayMetrics().density;

            //Set circle indicator radius
            ((ViewHolder2)holder).indicator.setRadius(5 * density);

            //NUM_PAGES =imageModelArrayList.size();

           /* // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == count) {
                        currentPage = 0;
                    }
                    ((ViewHolder2)holder).profile_image.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 1700, 1700);
            */

            // Pager listener over indicator
            ((ViewHolder2)holder).indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        }else if (holder instanceof  ViewHolder3){
            final Banner banner = mUsers.get(position);
            Log.e("size", String.valueOf(splitter(banner.getVerticalBanner()).size()));
            ((ViewHolder3)holder).recycleview.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            ((ViewHolder3)holder).recycleview.setLayoutManager(linearLayoutManager);
            vBannerAdapter searchAdapter = new vBannerAdapter(mContext, splitter(banner.getVerticalBanner()));
            ((ViewHolder3)holder).recycleview.setAdapter(searchAdapter);

        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return R.layout.rectangular_banner;
        }else if(position ==1) {
            return R.layout.horizontal_sliding_banner;
        }else if (position == 2){
            return R.layout.vertical_banner;
        }else {
            return 0;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView rectangularBanner;
        public ViewPager profile_image;
        CirclePageIndicator indicator;
        RecyclerView recycleview;

        public ViewHolder(View itemView) {
            super(itemView);
            rectangularBanner=itemView.findViewById(R.id.rectangularBanner);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
            profile_image = itemView.findViewById(R.id.pager);
            recycleview=itemView.findViewById(R.id.postrecyclerview);
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder{

        public ViewPager profile_image;
        CirclePageIndicator indicator;

        public ViewHolder2(View itemView) {
            super(itemView);

            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
            profile_image = itemView.findViewById(R.id.pager);
        }
    }
    class ViewHolder3 extends RecyclerView.ViewHolder{

        RecyclerView recycleview;

        public ViewHolder3(View itemView) {
            super(itemView);
            recycleview=itemView.findViewById(R.id.postrecyclerview);
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
