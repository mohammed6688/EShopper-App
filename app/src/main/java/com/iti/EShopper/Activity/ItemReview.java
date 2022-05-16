package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Adapter.SupBannerAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Basket;
import com.iti.EShopper.models.Favorite;
import com.iti.EShopper.models.Item;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ItemReview extends BaseActivity {

    private DatabaseReference mMessagesDatabaseReference;
    FirebaseAuth user;
    List<Uri> imagesList=new ArrayList<>();
    List<String> productSize=new ArrayList<>();
    private List<Favorite> loveCheck = new ArrayList<>();
    private List<Basket> BasketCheck = new ArrayList<>();
    private SupBannerAdapter myCustomPagerAdapter;
    private static int currentPage = 0;
    private Item productDetails;
    TextView title,subTitle,txtColor,txtSize,sizeChart,txtDetails,txtMore,dtCpu,dtRam,dtRom,dtRearCamera,dtFrontCamera,dtBattery,txtProductMeasurement,Size,Chest,Waist,Hips;
    TextView price,currency;
    Button AddToBasket;
    ImageView favorite;
    public ViewPager profile_image;
    CirclePageIndicator indicator;
    String categoryName,subCateName;
    RadioButton small,medium,large,xlarge,xxlarge;
    StringBuilder orderSize= new StringBuilder();
    boolean isFavorite;
    boolean isBasket;
    String firstAge = "1-5";
    String secondAge = "5-8";
    String thirdAge = "8-12";
    String forthAge = "12-16";
    String fivesAge = "";
    boolean guest;
    private int alertCount = 0;
    TextView countTextView,txtDiscount,itemLeft;
    FrameLayout redCircle;
    ImageView basket;
    RelativeLayout imageLay;
    CoordinatorLayout main_content;
    LinearLayout txtDetailsLay,DetailsLay;
    ArrayList<String> images = new ArrayList<>();
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
            updateAlertIcon();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_review);
        LocalBroadcastManager.getInstance(ItemReview.this).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        initializeVariables();
        initializeListener();

    }

    private void initializeVariables() {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
        guest = MyApplication.getPrefranceDataBoolean("Guest");
        categoryName=getIntent().getStringExtra("name");
        subCateName=getIntent().getStringExtra("subCateName");
        productDetails = (Item) getIntent().getSerializableExtra("product_object");
        itemLeft = findViewById(R.id.itemLeft);
        basket = findViewById(R.id.basket);
        imageLay = findViewById(R.id.imageLay);
        redCircle =  findViewById(R.id.view_alert_red_circle);
        main_content =  findViewById(R.id.main_content);
        countTextView = findViewById(R.id.view_alert_count_textview);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        profile_image = findViewById(R.id.pager);
        txtDetailsLay = findViewById(R.id.txtDetailsLay);
        title=findViewById(R.id.Title);
        subTitle=findViewById(R.id.subTitle);

        txtDetails=findViewById(R.id.txtDetails);
        DetailsLay=findViewById(R.id.DetailsLay);


        dtCpu=findViewById(R.id.dtCpu);
        dtRam=findViewById(R.id.dtRam);
        dtRom=findViewById(R.id.dtRom);
        dtRearCamera=findViewById(R.id.dtRearCamera);
        dtFrontCamera=findViewById(R.id.dtFrontCamera);
        dtBattery=findViewById(R.id.dtBattery);

        txtDiscount = findViewById(R.id.txtDiscount);
        txtMore=findViewById(R.id.txtMore);
        price=findViewById(R.id.Price);
        currency=findViewById(R.id.Currency);
        AddToBasket=findViewById(R.id.AddToBasket);
        user=FirebaseAuth.getInstance();


        title.setText(productDetails.getTitle());
        if (productDetails.getCpu().equals("0")||productDetails.getCpu().isEmpty() ){
            dtCpu.setVisibility(View.GONE);
        }else {
            dtCpu.setText("Cpu: "+productDetails.getCpu());
        }
        if (productDetails.getRam().equals("0")||productDetails.getRam().isEmpty() ){
            dtRam.setVisibility(View.GONE);
        }else {
            dtRam.setText("Ram: "+productDetails.getRam());
        }
        if (productDetails.getRom().equals("0")||productDetails.getRom().isEmpty() ){
            dtRom.setVisibility(View.GONE);
        }else {
            dtRom.setText("Rom: "+productDetails.getRom());
        }
        if (productDetails.getCamera().equals("0")||productDetails.getCamera().isEmpty() ){
            dtRearCamera.setVisibility(View.GONE);
        }else {
            dtRearCamera.setText("Rear Camera: "+productDetails.getCamera());
        }
        if (productDetails.getFrontCamera().equals("0")||productDetails.getFrontCamera().isEmpty()){
            dtFrontCamera.setVisibility(View.GONE);
        }else {
            dtFrontCamera.setText("Front Camera: "+productDetails.getFrontCamera());
        }
        if (productDetails.getBattery().equals("0")||productDetails.getBattery().isEmpty()){
            dtBattery.setVisibility(View.GONE);
        }else {
            dtBattery.setText("Battery: "+productDetails.getBattery());
        }

        if (!productDetails.getDiscount().equals("0")){
            txtDiscount.setText(productDetails.getDiscount()+"%");
        }else {
            txtDiscount.setVisibility(View.GONE);
        }

        if (productDetails.getBattery().equals("0")||productDetails.getBattery().equals("")&&
                productDetails.getFrontCamera().equals("0")||productDetails.getFrontCamera().equals("")&&
                productDetails.getCamera().equals("0")||productDetails.getCamera().equals("")&&
                productDetails.getRom().equals("0")||productDetails.getRom().equals("")&&
                productDetails.getRam().equals("0")||productDetails.getRam().equals("")&&
                productDetails.getCpu().equals("0")||productDetails.getCpu().equals("")
        ){
            txtDetailsLay.setVisibility(GONE);
            DetailsLay.setVisibility(View.GONE);
        }

        if (productDetails.getMore().equals("0")||productDetails.getMore().isEmpty()){
            txtMore.setVisibility(View.GONE);

        }else {
            txtMore.setText("المزيد: \n"+productDetails.getMore());
        }

        if (productDetails.getAvailableUnits()<=2){
            itemLeft.setVisibility(View.VISIBLE);
            itemLeft.setText("*باقي "+productDetails.getAvailableUnits()+" قطع");
        }

        if (productDetails.getAvailableUnits()==0){
            AddToBasket.setEnabled(false);
            AddToBasket.setBackgroundColor(getResources().getColor(R.color.grayDarkColor));
            AddToBasket.setText(getResources().getString(R.string.product_unavailable));
        }

        price.setText(String.valueOf(productDetails.getPrice()));

        String[] imgOther = productDetails.getImages().toString().split("\n");
        images.addAll(Arrays.asList(imgOther));


        for (int i=0 ; i < splitter(productDetails.getImages()).size() ; i++){
            imagesList.add(Uri.parse(splitter(productDetails.getImages()).get(i)));
        }
        profile_image.setOffscreenPageLimit(3);
        profile_image.setClipToPadding(false);
        profile_image.setClipChildren(false);
        //Uri images[] = {Uri.parse(banner.getSlidingBanner())};
        myCustomPagerAdapter = new SupBannerAdapter(ItemReview.this, imagesList);
        profile_image.setAdapter(myCustomPagerAdapter);


        final int count=imagesList.size();

        indicator.setViewPager(profile_image);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == count) {
                    currentPage = 0;
                }
                profile_image.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1700, 1700);

        indicator.setRadius(5 * density);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

        if (!guest) {
            //checkFavorite();
            checkBasket();
        }
    }

    private void initializeListener() {
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!guest) {
                    alertCount = 0;
                    updateAlertIcon();
                    Intent go = new Intent(ItemReview.this, BasketActivity.class);
                    go.putExtra("product_object",productDetails);
                    go.putExtra("intentMode","itemReview");
                    startActivity(go);
                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    finish();
                }else {
                    Intent go = new Intent(ItemReview.this, WelcomeActivity.class);
                    startActivity(go);
                }
            }
        });


        /*favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!guest) {
                    if (isFavorite) {
                        removeFavorite(user.getUid(), loveCheck.get(0).getId());
                    } else {
                        AddFavorite();
                    }
                }else {
                    Intent go = new Intent(ItemReview.this, WelcomeActivity.class);
                    startActivity(go);
                }
            }
        });*/

        AddToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!guest) {
                    AddBasket();

                    Intent intent = new Intent("showcircle");
                    LocalBroadcastManager.getInstance(ItemReview.this).sendBroadcast(intent);
                }else {
                    //Toast.makeText(ItemReview.this, "سجل الدخول اولا", Toast.LENGTH_SHORT).show();
                    Intent go =new Intent(ItemReview.this, WelcomeActivity.class);
                    startActivity(go);
                }
            }
        });
    }

    private void checkBasket() {

        Query lquery = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid()).orderByChild("itemId").equalTo(productDetails.getId());

        lquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BasketCheck.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Basket user = snapshot.getValue(Basket.class);

                    BasketCheck.add(user);

                    Log.e("size", String.valueOf(BasketCheck.size()));
                    if (!BasketCheck.isEmpty()){
                        AddToBasket.setEnabled(false);
                        AddToBasket.setBackgroundColor(getResources().getColor(R.color.grayDarkColor));
                        AddToBasket.setText(getResources().getString(R.string.added_to_basket));
                        isBasket=true;
                        break;
                    }else {
                        isBasket=false;
                        AddToBasket.setEnabled(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ItemReview.this, "error: "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkFavorite() {
        Query lquery = FirebaseDatabase.getInstance().getReference("Favorite").child(user.getUid()).orderByChild("itemId").equalTo(productDetails.getId());

        lquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loveCheck.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Favorite user = snapshot.getValue(Favorite.class);

                    loveCheck.add(user);

                    Log.e("size", String.valueOf(loveCheck.size()));
                    if (!loveCheck.isEmpty()){
                        favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                        isFavorite=true;
                        break;
                    }else {
                        isFavorite=false;
                        favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ItemReview.this, "error: "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getOrderSize() {

        if (!productSize.contains("kids")) {
            if (small.isChecked()) {
                orderSize.append("S").append("\n");
                AddBasket();
            } else if (medium.isChecked()) {
                orderSize.append("M").append("\n");
                AddBasket();
            } else if (large.isChecked()) {
                orderSize.append("L").append("\n");
                AddBasket();
            } else if (xlarge.isChecked()) {
                orderSize.append("XL").append("\n");
                AddBasket();
            } else if (xxlarge.isChecked()) {
                orderSize.append("XXL").append("\n");
                AddBasket();
            } else {
                showSnackbar(getResources().getString(R.string.choose_size));
            }
        }else {
            if (small.isChecked()) {
                orderSize.append(firstAge).append("\n");
                AddBasket();
            } else if (medium.isChecked()) {
                orderSize.append(secondAge).append("\n");
                AddBasket();
            } else if (large.isChecked()) {
                orderSize.append(thirdAge).append("\n");
                AddBasket();
            } else if (xlarge.isChecked()) {
                orderSize.append(forthAge).append("\n");
                AddBasket();
            } else if (xxlarge.isChecked()) {
                orderSize.append(fivesAge).append("\n");
                AddBasket();
            } else {
                showSnackbar(getResources().getString(R.string.choose_age));
            }
        }
    }

    private void AddBasket() {
        DatabaseReference r =mMessagesDatabaseReference.child("Basket").child(user.getUid()).push();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        Basket podcast_adabter = new Basket(
                r.getKey(),
                productDetails.getId(),
                currentDateAndTime,
                categoryName,
                subCateName,
                "");
        r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                showSnackbar(getResources().getString(R.string.added_to_basket));
                AddBadge(false,true);
                AddToBasket.setEnabled(false);
                AddToBasket.setBackgroundColor(getResources().getColor(R.color.light_gray));
                //goToBasket();
            }
        });
    }

    private void goToBasket() {
        
    }

    private void AddFavorite() {
        DatabaseReference r =mMessagesDatabaseReference.child("Favorite").child(user.getUid()).push();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        Favorite podcast_adabter = new Favorite(
                productDetails.getId(),
                r.getKey(),
                currentDateAndTime,
                categoryName,
                subCateName);
        r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                showSnackbar(getResources().getString(R.string.add_to_favorite));
                AddBadge(true,false);
                favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
            }
        });
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

    private void removeFavorite(String child,String children) {
        new AlertDialog.Builder(ItemReview.this)
                .setTitle(getResources().getString(R.string.delete_favorite_title))
                .setMessage(getResources().getString(R.string.delete_favorite_message))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favorite").child(child).child(children);
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showSnackbar(getResources().getString(R.string.removed_from_favorite));
                                isFavorite=false;
                                favorite.setImageDrawable(ItemReview.this.getDrawable(R.drawable.ic_baseline_favorite_border_24));
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void AddBadge(boolean favorite,boolean basket){
        Intent intent = new Intent("add-badge");
        intent.putExtra("Favorite",favorite);
        intent.putExtra("Basket",basket);
        LocalBroadcastManager.getInstance(ItemReview.this).sendBroadcast(intent);
    }
    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < 10) {
            countTextView.setText(String.valueOf(alertCount));
        } else {
            countTextView.setText("+10");
        }

        redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
    }
}