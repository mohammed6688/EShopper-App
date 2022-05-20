package com.iti.EShopper.Fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.EShopper.Activity.BasketActivity;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Adapter.CategoriseAdapter;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Product> mCategory;
    View view;
    ImageView basket;
    TextView progressTxt;
    private int alertCount = 0;
    TextView countTextView;
    FrameLayout redCircle;
    boolean guest;
    ShimmerFrameLayout shimmerFrameLayout;
    String tag_json_obj = "json_obj_req";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://10.0.2.2:8080/E_commerce_API/api/eShopper";

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
            updateAlertIcon();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_categorise, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("showcircle"));

        initializeVariables();
        initializeListener();
        try {
            readUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }

    private void initializeVariables() {
        guest = MyApplication.getPrefranceDataBoolean("Guest");
        mprogressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.postrecyclerview);
        countTextView = view.findViewById(R.id.view_alert_count_textview);
        redCircle = view.findViewById(R.id.view_alert_red_circle);
        progressTxt = view.findViewById(R.id.progTxt);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        basket = view.findViewById(R.id.basket);
        mprogressBar.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mCategory = new ArrayList<>();

        mprogressBar = view.findViewById(R.id.progressBar);
    }

    private void initializeListener() {
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!guest) {
                    alertCount = 0;
                    updateAlertIcon();
                    Intent go = new Intent(getContext(), BasketActivity.class);
                    go.putExtra("intentMode", "home");
                    startActivity(go);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                } else {
                    Intent go = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(go);
                }
            }
        });

    }

    private void readUsers() throws IOException {
        url+= "/getCategories";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            List<String> AllProductList = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    String val;
                    Log.e("inside", String.valueOf(response.length()));
                    Log.e("inside", String.valueOf(response));
                    val=response.getString(i);
//                    if (response.length()==1){
//                        val=response.getString(i);
//                    }else {
//                        val = response.getJSONObject(i).toString();
//                    }
                    AllProductList.add(val);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            getAllProduct_CallBack(AllProductList);
        }, error -> {
            Log.e("error", error.toString());
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
        });
        queue.add(request);
    }

    private void getAllProduct_CallBack(List<String> allProductList) {
        CategoriseAdapter homeAdapter = new CategoriseAdapter(getContext(), allProductList);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        if (allProductList.size() != 0) {
            mprogressBar.setVisibility(View.GONE);
            progressTxt.setVisibility(View.GONE);

        } else {
            progressTxt.setVisibility(View.VISIBLE);
            mprogressBar.setVisibility(View.GONE);
        }
        shimmerFrameLayout.setVisibility(View.GONE);
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
