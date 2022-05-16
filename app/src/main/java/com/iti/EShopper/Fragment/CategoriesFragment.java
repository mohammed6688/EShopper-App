package com.iti.EShopper.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Activity.BasketActivity;
import com.iti.EShopper.Activity.subCategoryActivity;
import com.iti.EShopper.Adapter.CategoriseAdapter;
import com.iti.EShopper.Adapter.SubCategoriseAdapter;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Category;
import com.iti.EShopper.models.Product;
import com.iti.EShopper.models.SiteParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Product> mCategory;
    View view;
    ImageView phone, basket;
    String phoneNumber = "+201200033396";
    TextView progressTxt;
    private int alertCount = 0;
    TextView countTextView;
    FrameLayout redCircle;
    boolean guest;
    ShimmerFrameLayout shimmerFrameLayout;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";

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
        phone = view.findViewById(R.id.phone);
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
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + phoneNumber);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

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

        List<Product> mCategory = SiteParser.instanceData.getProducts();
        SubCategoriseAdapter homeAdapter = new SubCategoriseAdapter(getContext(), mCategory);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setVisibility(View.VISIBLE);

        if (mCategory.size() != 0) {
            mprogressBar.setVisibility(View.GONE);
            progressTxt.setVisibility(View.GONE);

        } else {
            progressTxt.setVisibility(View.VISIBLE);
            mprogressBar.setVisibility(View.GONE);
        }
        shimmerFrameLayout.setVisibility(View.GONE);

//        mRequestQueue = Volley.newRequestQueue(getContext());
//        mStringRequest = new StringRequest(Request.Method.GET, url, response -> {
//            Toast.makeText(getContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.i("TAG","Error :" + error.toString());
//            }
//        });
//
//        mRequestQueue.add(mStringRequest);

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categorise");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                mCategory.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Category user = snapshot.getValue(Category.class);
//                    //assert user != null;
//                    if (user!=null) {
//                        mCategory.add(user);
//                    }
//                }
//                CategoriseAdapter homeAdapter = new CategoriseAdapter(getContext(), mCategory);
//                recyclerView.setAdapter(homeAdapter);
//
//                if (mCategory.size()!=0){
//                    mprogressBar.setVisibility(View.GONE);
//                    progressTxt.setVisibility(View.GONE);
//
//                }else {
//                    progressTxt.setVisibility(View.VISIBLE);
//                    mprogressBar.setVisibility(View.GONE);
//                }
//                shimmerFrameLayout.setVisibility(View.GONE);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
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
