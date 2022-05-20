package com.iti.EShopper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.iti.EShopper.Activity.BasketActivity;
import com.iti.EShopper.Activity.Search;
import com.iti.EShopper.Fragment.AccountFragment;
import com.iti.EShopper.Fragment.CategoriesFragment;
import com.iti.EShopper.Module.SiteParser;
import com.iti.EShopper.helper.MyApplication;

public class MainActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;
    private Fragment selectfragment;
    private EditText et_search;
    boolean Basket, Favorite, openBasket;
    int currentSelected;
    boolean guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("add-badge"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mSecondMessageReceiver,
                new IntentFilter("openCategory"));

        guest = MyApplication.getPrefranceDataBoolean("Guest");

        int userId = MyApplication.getPrefranceDataInt("userId");
        if (userId==0){
            MyApplication.setPreferencesBoolean("Guest",true);
        }

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
//            bottomNavigationView.getMenu().findItem(R.id.favourite).setVisible(false);
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
//                Favorite = intent.getBooleanExtra("Favorite", false);
                Basket = intent.getBooleanExtra("Basket", false);
                if (Basket) {
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
                        case R.id.basket:
                            Intent go = new Intent(MainActivity.this, BasketActivity.class);
                            startActivity(go);
                            overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);

                            BadgeDrawable installmentBadge = bottomNavigationView.getOrCreateBadge(R.id.basket);
                            installmentBadge.setVisible(false);
                            openBasket = true;
                            break;
                    }
                    if (!openBasket) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,
                                selectfragment).commit();

                    }
                    return true;
                }
            };
}