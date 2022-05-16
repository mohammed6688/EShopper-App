package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.iti.EShopper.Fragment.NotificationFragment;
import com.iti.EShopper.Fragment.OffersFragment;
import com.iti.EShopper.R;
import com.iti.EShopper.Fragment.AccountFragment;
import com.iti.EShopper.Fragment.CategoriesFragment;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.SiteParser;

public class MainActivity extends BaseActivity {

    protected BottomNavigationView bottomNavigationView;
    private Fragment selectfragment;
    private EditText et_search;
    //    FirebaseUser firebaseUser;
    boolean Basket, Favorite, openBasket;
    int currentSelected;
    boolean guest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SiteParser();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("add-badge"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mSecondMessageReceiver,
                new IntentFilter("openCategory"));

        guest = MyApplication.getPrefranceDataBoolean("Guest");
//        if (firebaseUser == null){
//            MyApplication.setPreferencesBoolean("Guest",true);
//            guest = MyApplication.getPrefranceDataBoolean("Guest");
//        }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_container, new CategoriesFragment());
            transaction.commit();

        }

        et_search = findViewById(R.id.et_search);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);

        //bottomNavigationView.setOnItemSelectedListener(navlistener);
        //bottomNavigationView.setItemSelected(R.id.categorise,true);

        if (guest) {
            bottomNavigationView.getMenu().findItem(R.id.favourite).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.basket).setVisible(false);
            //bottomNavigationView.getMenu().findItem(R.id.basket).setVisible(false);
            //bottomNavigationView.setItemEnabled(R.id.favourite, false);
            //bottomNavigationView.setItemEnabled(R.id.basket, false);
        }
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(MainActivity.this, Search.class);
                startActivity(go);
            }
        });

    }

    public BroadcastReceiver mSecondMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            selectfragment = new CategoriesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,
                    selectfragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.categorise);

        }

    };

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Favorite = intent.getBooleanExtra("Favorite", false);
                Basket = intent.getBooleanExtra("Basket", false);
                if (Favorite) {
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.favourite);
                    badge.setVisible(true);

                } else if (Basket) {
                    BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.basket);
                    badge.setVisible(true);
                }
            }
        }

    };


    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    selectfragment = null;
                    openBasket = false;
                    switch (menuItem.getItemId()) {

                        case R.id.categorise:
                            selectfragment = new CategoriesFragment();
                            currentSelected = bottomNavigationView.getSelectedItemId();
                            break;
//                        case R.id.account:
//                            selectfragment = new AccountFragment();
//                            currentSelected = bottomNavigationView.getSelectedItemId();
//                            break;
//                        case R.id.favourite:
//                            selectfragment = new NotificationFragment();
//                            currentSelected = bottomNavigationView.getSelectedItemId();
//                            BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.favourite);
//                            badge.setVisible(false);
//                            //bottomNavigationView.dismissBadge(R.id.favourite);
//                            break;
//                        case R.id.offers:
//                            selectfragment = new OffersFragment();
//                            currentSelected = bottomNavigationView.getSelectedItemId();
//                            BadgeDrawable offerBadge = bottomNavigationView.getOrCreateBadge(R.id.offers);
//                            offerBadge.setVisible(false);
//                            //bottomNavigationView.dismissBadge(R.id.offers);
//                            break;
//                        case R.id.basket:
//                            Intent go = new Intent(MainActivity.this, BasketActivity.class);
//                            startActivity(go);
//                            overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//
//                            BadgeDrawable installmentBadge = bottomNavigationView.getOrCreateBadge(R.id.basket);
//                            installmentBadge.setVisible(false);
//                            openBasket = true;
//                            break;
                    }
                    if (!openBasket) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,
                                selectfragment).commit();

                    }
                    return true;
                }
            };

    /*private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public void onItemSelected(int i) {
                    selectfragment =null;
                    openBasket=false;
                    switch (i){
                        case R.id.home:
                            selectfragment = new HomeFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            break;
                        case R.id.categorise:
                            selectfragment = new CategoriesFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            break;
                        case R.id.account:
                            selectfragment = new AccountFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            break;
                        case R.id.favourite:
                            selectfragment = new FavouriteFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            BadgeDrawable badge=bottomNavigationView.getOrCreateBadge(R.id.favourite);
                            badge.setVisible(false);
                            //bottomNavigationView.dismissBadge(R.id.favourite);
                            break;
                        case R.id.offers:
                            selectfragment = new OffersFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            BadgeDrawable offerBadge=bottomNavigationView.getOrCreateBadge(R.id.offers);
                            offerBadge.setVisible(false);
                            //bottomNavigationView.dismissBadge(R.id.offers);
                            break;
                        case R.id.installment:
                            selectfragment = new InstallmentFragment();
                            currentSelected=bottomNavigationView.getSelectedItemId();
                            BadgeDrawable installmentBadge=bottomNavigationView.getOrCreateBadge(R.id.installment);
                            installmentBadge.setVisible(false);
                            //bottomNavigationView.dismissBadge(R.id.installment);
                            break;
                        case R.id.basket:
                            Intent go = new Intent(MainActivity.this,BasketActivity.class);
                            startActivity(go);
                            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

                            bottomNavigationView.dismissBadge(R.id.basket);
                            bottomNavigationView.setItemSelected(currentSelected,true);
                            openBasket=true;
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,
                            selectfragment).commit();
                    if (!openBasket) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,
                                selectfragment).commit();
                    }
                }
    };*/


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }
}