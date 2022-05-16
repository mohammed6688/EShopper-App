package com.iti.EShopper.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Activity.AboutUsActivity;
import com.iti.EShopper.Activity.AddAddress;
import com.iti.EShopper.Activity.AgreementsActivity;
import com.iti.EShopper.Activity.Auth.WelcomeActivity;
import com.iti.EShopper.Activity.EditActivity;
import com.iti.EShopper.Activity.MyNotification;
import com.iti.EShopper.Activity.MyOrders;
import com.iti.EShopper.Activity.ViewAddress;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {


    CircleImageView userImage;
    TextView userName,userEmail,LogOut;
    LinearLayout AboutUs,Agreements,RateUs,ShareApp,RegisteredAddresses,MyOrders,myNotifications,welcomeLay;
    DatabaseReference reference;
    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout userInfo;
    FirebaseUser firebaseUser;
    List<Address> mAddress;
    boolean guest;
    Button login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);

        guest = MyApplication.getPrefranceDataBoolean("Guest");

        initializeVariables(view);
        initializeListener();
        return view;
    }

    private void initializeVariables(View view) {
        userInfo=view.findViewById(R.id.userLay);
        //userInfo.setVisibility(View.INVISIBLE);
        login=view.findViewById(R.id.login);
        userImage = view.findViewById(R.id.userImage);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        LogOut = view.findViewById(R.id.logOut);
        AboutUs = view.findViewById(R.id.AboutUs);
        Agreements = view.findViewById(R.id.Agreements);
        RateUs = view.findViewById(R.id.RateUs);
        ShareApp = view.findViewById(R.id.ShareApp);
        RegisteredAddresses = view.findViewById(R.id.RegisteredAddresses);
        MyOrders = view.findViewById(R.id.myOrders);
        myNotifications = view.findViewById(R.id.myNotifications);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        welcomeLay = view.findViewById(R.id.welcomeLay);
        shimmerFrameLayout.startShimmer();
        mAddress=new ArrayList<>();

        if (guest){

            MyOrders.setVisibility(View.GONE);
            RegisteredAddresses.setVisibility(View.GONE);
            LogOut.setVisibility(View.GONE);
            //userInfo.setVisibility(View.GONE);
            userImage.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            userEmail.setVisibility(View.GONE);
            //userName.setText("Log In");
            welcomeLay.setVisibility(View.VISIBLE);
            shimmerFrameLayout.setVisibility(View.GONE);

        }else {
            welcomeLay.setVisibility(View.GONE);
            userInfo.setVisibility(View.VISIBLE);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    //Log.e("name",user.getUsername());
                    userInfo.setVisibility(View.VISIBLE);
                    userName.setText(user.getUsername());
                    userEmail.setText(firebaseUser.getEmail());
                    if (user.getImageURL().equals("default")){
                        userImage.setImageResource(R.drawable.user_logo);
                    } else {
                        //change this
                        if (getActivity()!=null) Glide.with(getActivity()).load(user.getImageURL()).thumbnail(0.2f).into(userImage);
                    }
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }});
        }
    }

    private void initializeListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go = new Intent(getContext(), WelcomeActivity.class);
                startActivity(go);

            }
        });
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guest){
                    Intent go = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(go);
                }else {
                    Intent go = new Intent(getContext(), EditActivity.class);
                    startActivity(go);
                }
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guest){
                    Intent go = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(go);
                }else {
                    Intent go = new Intent(getContext(), EditActivity.class);
                    startActivity(go);
                }
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();

                startActivity(new Intent(getContext(), WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
            }
        });
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nightmode = new Intent(getContext() , AboutUsActivity.class);
                startActivity(nightmode);
            }
        });
        Agreements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nightmode = new Intent(getContext() , AgreementsActivity.class);
                startActivity(nightmode);
                //invokeCustomUrlActivity(getActivity(), CustomUrlActivity.class, getResources().getString(R.string.privacy), getResources().getString(R.string.privacy_url), false);
            }
        });
        RateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getContext().getPackageName()));
                startActivity(intent);
            }
        });
        ShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String appName = getResources().getString(R.string.app_name);
                String shareSub = appName+" "+getResources().getString(R.string.easy_shopping)+"\n\n";
                shareSub += "https://play.google.com/store/apps/details?id="+getContext().getPackageName();
                myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(myIntent, getResources().getString(R.string.share_using)));
            }
        });

        RegisteredAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAddress();
                /*Intent nightmode = new Intent(getContext() , Addresses.class);
                startActivity(nightmode);*/
            }
        });
        MyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(getContext(), MyOrders.class);
                startActivity(checkout);
            }
        });
        myNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(getContext(), MyNotification.class);
                startActivity(checkout);
            }
        });
    }

    private void checkAddress() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAddress.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Address user = snapshot.getValue(Address.class);
                    assert user != null;
                    mAddress.add(user);
                }

                if (mAddress.size()!=0){
                    Intent nightmode = new Intent(getContext() , ViewAddress.class);
                    startActivity(nightmode);
                   Log.e("is There is address","yes");
                }else {
                    Intent nightmode = new Intent(getContext() , AddAddress.class);
                    startActivity(nightmode);
                    Log.e("is There is address","no");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void invokeCustomUrlActivity(Activity activity, Class<?> tClass, String pageTitle, String pageUrl, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra("title", pageTitle);
        intent.putExtra("url", pageUrl);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }
}
