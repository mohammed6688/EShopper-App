package com.iti.EShopper.Activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Adapter.SupBannerAdapter;
import com.iti.EShopper.Module.Cart;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ItemReview extends AppCompatActivity {
    List<Uri> imagesList=new ArrayList<>();
    private SupBannerAdapter myCustomPagerAdapter;
    private Product productDetails;
    TextView title,subTitle,txtDetails,txtMore,dtCpu,dtRam,dtRom,dtRearCamera,dtFrontCamera,dtBattery;
    TextView price,currency;
    Button AddToBasket;
    public ViewPager profile_image;
    String categoryName,subCateName;
    boolean isBasket;
    boolean guest;
    private int alertCount = 0;
    TextView countTextView,txtDiscount,itemLeft;
    FrameLayout redCircle;
    ImageView basket;
    RelativeLayout imageLay;
    CoordinatorLayout main_content;
    LinearLayout txtDetailsLay,DetailsLay;
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
        setContentView(R.layout.activity_item_review);

        LocalBroadcastManager.getInstance(ItemReview.this).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        initializeVariables();
        initializeListener();
    }

    private void initializeVariables() {
        guest = MyApplication.getPrefranceDataBoolean("Guest");
        categoryName=getIntent().getStringExtra("name");
        subCateName=getIntent().getStringExtra("subCateName");
        productDetails = (Product) getIntent().getSerializableExtra("product_object");
        itemLeft = findViewById(R.id.itemLeft);
        basket = findViewById(R.id.basket);
        imageLay = findViewById(R.id.imageLay);
        redCircle =  findViewById(R.id.view_alert_red_circle);
        main_content =  findViewById(R.id.main_content);
        countTextView = findViewById(R.id.view_alert_count_textview);
        profile_image = findViewById(R.id.pager);
        title=findViewById(R.id.Title);
        subTitle=findViewById(R.id.subTitle);
        txtDiscount = findViewById(R.id.txtDiscount);
        txtMore=findViewById(R.id.txtMore);
        price=findViewById(R.id.Price);
        currency=findViewById(R.id.Currency);
        AddToBasket=findViewById(R.id.AddToBasket);

        title.setText(productDetails.getTitle());
        txtMore.setText("المزيد: \n"+productDetails.getDetails());

        if (productDetails.getQuantity()<=2){
            itemLeft.setVisibility(View.VISIBLE);
            itemLeft.setText("*باقي "+productDetails.getQuantity()+" قطع");
        }

        if (productDetails.getQuantity()==0){
            AddToBasket.setEnabled(false);
            AddToBasket.setBackgroundColor(getResources().getColor(R.color.grayDarkColor));
            AddToBasket.setText(getResources().getString(R.string.product_unavailable));
        }

        price.setText(String.valueOf(productDetails.getPrice()));

        imagesList.add(Uri.parse(productDetails.getPhotoUrl()));
        profile_image.setOffscreenPageLimit(3);
        profile_image.setClipToPadding(false);
        profile_image.setClipChildren(false);
        myCustomPagerAdapter = new SupBannerAdapter(ItemReview.this, imagesList);
        profile_image.setAdapter(myCustomPagerAdapter);

        if (!guest) {
            checkBasket();
        }
    }

    private void initializeListener() {
        basket.setOnClickListener(v -> {
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
        });
        AddToBasket.setOnClickListener(v -> {
            if (!guest) {
                AddBasket();
                Intent intent = new Intent("showcircle");
                LocalBroadcastManager.getInstance(ItemReview.this).sendBroadcast(intent);
            }else {
                //Toast.makeText(ItemReview.this, "سجل الدخول اولا", Toast.LENGTH_SHORT).show();
                Intent go =new Intent(ItemReview.this, WelcomeActivity.class);
                startActivity(go);
            }
        });
    }

    private void checkBasket() {
        int userId=MyApplication.getPrefranceDataInt("userId");
        url=url+ "/checkCart/"+productDetails.getId()+"/"+userId;
        RequestQueue queue = Volley.newRequestQueue(ItemReview.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            if (response.length() != 0 && response.toString().equals("found")) {
                AddToBasket.setEnabled(false);
                AddToBasket.setBackgroundColor(getResources().getColor(R.color.grayDarkColor));
                AddToBasket.setText(getResources().getString(R.string.added_to_basket));
                isBasket=true;
            }else {
                isBasket=false;
                AddToBasket.setEnabled(true);
            }
        }, error -> {
            Log.e("error", error.toString());
            Toast.makeText(ItemReview.this, error.toString(), Toast.LENGTH_LONG).show();
        });
        queue.add(request);
    }

    private void AddBasket() {
        try {
            int userId = MyApplication.getPrefranceDataInt("userId");
            RequestQueue requestQueue = Volley.newRequestQueue(ItemReview.this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("productId", productDetails.getId());
            jsonBody.put("userId", userId);
            jsonBody.put("quantity", 1);

            final String mRequestBody = jsonBody.toString();

            url="http://10.0.2.2:8080/E_commerce_API/api/eShopper/addToCart";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                try {
                    JSONObject respObj = new JSONObject(response);
                    AddBadge(false,true);
                    AddToBasket.setEnabled(false);
                    AddToBasket.setBackgroundColor(getResources().getColor(R.color.light_gray));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> {
                Log.e("LOG_RESPONSE", error.toString());
                Toast.makeText(ItemReview.this,  error.toString(), Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };


            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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