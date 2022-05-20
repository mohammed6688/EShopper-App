package com.iti.EShopper.Activity;

import static com.iti.EShopper.Activity.ItemsActivity.jsontoProduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.iti.EShopper.Adapter.BasketAdapter;
import com.iti.EShopper.Module.Cart;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private int REQUEST_CODE = 50;
    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Cart> mBasket;
    private CardView addFavorite, securePayment;
    LinearLayout layProgress, paymentLay;
    private List<Product> ItemList;
    private int tPrice = 0;
    TextView txtPrice;
    ImageView arrow;
    BasketAdapter homeAdapter;
    String intentMode;
    String categoryName, subCategoryName;
    private Product productDetails;
    private String url = "http://10.0.2.2:8080/E_commerce_API/api/eShopper";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        productDetails = (Product) getIntent().getSerializableExtra("product_object");
        intentMode = getIntent().getStringExtra("intentMode");
        categoryName = getIntent().getStringExtra("categoryName");
        subCategoryName = getIntent().getStringExtra("subCategoryName");

        mprogressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.postrecyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BasketActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(BasketActivity.this, 1, GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager(linearLayoutManager);

        mBasket = new ArrayList<>();
        ItemList = new ArrayList<>();

        arrow = findViewById(R.id.arrow);
        txtPrice = findViewById(R.id.txtPrice);
        mprogressBar = findViewById(R.id.progressBar);
        layProgress = findViewById(R.id.layProgress);
        paymentLay = findViewById(R.id.paymentLay);
        addFavorite = findViewById(R.id.card);
        layProgress.setVisibility(View.GONE);
        paymentLay.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        securePayment = findViewById(R.id.securePayment);


        securePayment.setOnClickListener(v -> {
//            Intent checkout = new Intent(BasketActivity.this,CheckoutActivity.class);
//            checkout.putExtra("supTotal",String.valueOf(tPrice));
//            startActivity(checkout);
//            finish();

        });
        getMyCart();
    }

    private void getMyCart() {
        int userId = MyApplication.getPrefranceDataInt("userId");
        String url = "http://10.0.2.2:8080/E_commerce_API/api/eShopper/getMyCart/" + userId;
        RequestQueue queue = Volley.newRequestQueue(BasketActivity.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    mBasket.add(jsontoCart(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            AddPriceToList();
            ItemList.clear();
            getItemsFromBasket();

            if (mBasket.size() != 0) {
                mprogressBar.setVisibility(View.GONE);
                layProgress.setVisibility(View.GONE);
                paymentLay.setVisibility(View.VISIBLE);
            } else {
                layProgress.setVisibility(View.VISIBLE);
                mprogressBar.setVisibility(View.GONE);
                paymentLay.setVisibility(View.GONE);
            }

        }, error -> {
            Log.e("error", error.toString());
            Toast.makeText(BasketActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        });
        queue.add(request);


    }

    public static Cart jsontoCart(JSONObject jsonProduct) throws JSONException {
        Cart product = new Cart(
                jsonProduct.getInt("productId"),
                jsonProduct.getInt("userId"),
                jsonProduct.getInt("quantity")
        );
        return product;
    }

    private void getItemsFromBasket() {
        for (int i = 0; i < mBasket.size(); i++) {
            int finalI = i;
            String url="http://10.0.2.2:8080/E_commerce_API/api/eShopper/getProduct/"+mBasket.get(i).getProductId();
            RequestQueue queue = Volley.newRequestQueue(BasketActivity.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        ItemList.add(jsontoProduct(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (finalI ==mBasket.size()-1) {
                    homeAdapter = new BasketAdapter(BasketActivity.this, mBasket, ItemList, false);
                    recyclerView.setAdapter(homeAdapter);
                    AddPriceToList();
                    if (mBasket.size() != 0) {
                        mprogressBar.setVisibility(View.GONE);
                        layProgress.setVisibility(View.GONE);

                    } else {
                        mprogressBar.setVisibility(View.GONE);
                        layProgress.setVisibility(View.VISIBLE);
                    }
                }

            }, error -> {
                Log.e("error", error.toString());
                Toast.makeText(BasketActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            });
            queue.add(request);
        }




    }


    private void AddPriceToList() {
        List<Integer> list = new ArrayList<>();
        int sum;
        for (sum = 0; sum < ItemList.size(); sum++) {
            Product mUser = ItemList.get(sum);
            if (mUser.getPrice() != 0) {
                int price = mUser.getPrice();
                list.add(price);
            } else {
                Toast.makeText(BasketActivity.this, "there are empty service", Toast.LENGTH_SHORT).show();
            }
        }
        tPrice = 0;
        tPrice = sum(list);
        Log.e("tprise", String.valueOf(tPrice));
        txtPrice.setText(tPrice + getResources().getString(R.string.currency));
    }

    private static int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
        finish();
    }

}