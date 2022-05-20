package com.iti.EShopper.Activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Adapter.ItemsAdapter;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Product> mCategory;
    ProgressBar progressBar;
    TextView progressTxt;
    String categoryName, category;
    private int alertCount = 0;
    TextView countTextView,txtsubCategoryName;
    FrameLayout redCircle;
    ImageView basket;
    boolean guest;
    private String url = "http://10.0.2.2:8080/E_commerce_API/api/eShopper";

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
        setContentView(R.layout.activity_items);

        LocalBroadcastManager.getInstance(ItemsActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        guest = MyApplication.getPrefranceDataBoolean("Guest");
        category =getIntent().getStringExtra("categoryName");
        basket = findViewById(R.id.basket);
        txtsubCategoryName = findViewById(R.id.subCategoryName);
        redCircle =  findViewById(R.id.view_alert_red_circle);
        countTextView = findViewById(R.id.view_alert_count_textview);
        progressTxt=findViewById(R.id.progress_txt);
        progressBar=findViewById(R.id.progress);
        progressTxt.setVisibility(View.GONE);
        recyclerView=findViewById(R.id.recycleView);
        txtsubCategoryName.setText(category);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.VERTICAL,false));
        mCategory=new ArrayList<>();

        basket.setOnClickListener(v -> {
            if (!guest) {
                alertCount = 0;
                updateAlertIcon();
                Intent go = new Intent(ItemsActivity.this, BasketActivity.class);
                go.putExtra("categoryName",categoryName);
                go.putExtra("subCategoryName", category);
                go.putExtra("intentMode","itemList");
                startActivity(go);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                finish();
            }else {
                Intent go = new Intent(ItemsActivity.this, WelcomeActivity.class);
                startActivity(go);
            }
        });

        try {
            readUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readUsers() throws IOException {
        url=url+ "/getCategory/"+category;
        RequestQueue queue = Volley.newRequestQueue(ItemsActivity.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            List<Product> AllProductList = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    AllProductList.add(jsontoProduct(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            getAllProduct_CallBack(AllProductList);
        }, error -> {
            Log.e("error", error.toString());
            Toast.makeText(ItemsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        });
        queue.add(request);
    }

    public static Product jsontoProduct(JSONObject jsonProduct) throws JSONException {
        Product product= new Product(
                jsonProduct.getInt("id"),
                jsonProduct.getString("title"),
                jsonProduct.getInt("price"),
                jsonProduct.getInt("quantity"),
                jsonProduct.getString("photoUrl"),
                jsonProduct.getString("details"),
                jsonProduct.getString("category")
        );
        return product;
    }

    private void getAllProduct_CallBack(List<Product> allProductList) {
        Collections.sort(allProductList,new CustomComparator());
        Collections.reverse(allProductList);
        ItemsAdapter homeAdapter = new ItemsAdapter(ItemsActivity.this, allProductList);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        if (allProductList.size()!=0){
            progressTxt.setVisibility(View.GONE);
        }else {
            progressTxt.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private class CustomComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return String.valueOf(o1.getPrice()).compareTo(String.valueOf(o2.getPrice()));

        }
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