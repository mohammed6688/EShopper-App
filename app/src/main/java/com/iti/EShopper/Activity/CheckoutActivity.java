package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.Adapter.ChooseAddressAdapter;
import com.iti.EShopper.R;

import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.models.Address;
import com.iti.EShopper.models.Basket;
import com.iti.EShopper.models.Item;
import com.iti.EShopper.models.Order;
import com.iti.EShopper.models.User;
import com.iti.EShopper.notification.APIService;
import com.iti.EShopper.notification.Client;
import com.iti.EShopper.notification.Data;
import com.iti.EShopper.notification.MyResponse;
import com.iti.EShopper.notification.Sender;
import com.iti.EShopper.notification.Token;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckoutActivity extends BaseActivity {

    private int REQUEST_CODE = 50;
    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Address> mAddress;
    private List<Basket> mBasket;
    private List<User> mUser;
    private List<Address> firstAddress;
    FirebaseAuth user;
    private CardView addFavorite,securePayment;
    LinearLayout priceLay,paymentLay;
    private List<Item> ItemList;
    private int tPrice = 0;
    TextView txtPrice,subTotalPrice,shippingFee,mprogressTxt;

    ImageView arrow;
    String subTotal;
    CardView addAddress;
    boolean addressChosen;
    int addressPosition;
    int i;
    private List<String>list = new ArrayList<>();
    boolean notify = false;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        subTotal=getIntent().getStringExtra("supTotal");
        addressPosition=getIntent().getIntExtra("position",0);
        addressChosen=getIntent().getBooleanExtra("addressChosen",false);
        mprogressBar =findViewById(R.id.progressBar);
        mprogressTxt =findViewById(R.id.progressTxt);
        arrow = findViewById(R.id.arrow);
        txtPrice=findViewById(R.id.txtPrice);
        mprogressBar = findViewById(R.id.progressBar);
        paymentLay=findViewById(R.id.paymentLay);
        securePayment=findViewById(R.id.securePayment);
        priceLay=findViewById(R.id.priceLay);
        recyclerView=findViewById(R.id.postrecyclerview);
        addAddress=findViewById(R.id.addAddress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(CheckoutActivity.this, 1,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(linearLayoutManager);
        mAddress = new ArrayList<>();
        mUser = new ArrayList<>();
        mBasket = new ArrayList<>();
        firstAddress = new ArrayList<>();
        ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();


        mprogressTxt.setVisibility(View.GONE);
        addAddress.setVisibility(View.GONE);

        securePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddress!=null && mAddress.size()!=0 && firstAddress!=null && firstAddress.size()!=0) {
                    checkPhoneNumber();
                    //placeOrder();
                }else {
                    showSnackbar(getResources().getString(R.string.add_address));
                }

            }

        });
        /*arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstAddress.size()!=0) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CheckoutActivity.this, R.style.Theme_Design_BottomSheetDialog);
                    View mBottomSheetDialog = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.layout_bottom_sheet,
                            findViewById(R.id.bottomSheetContainer));
                    subTotalPrice = mBottomSheetDialog.findViewById(R.id.subTotalPrice);
                    shippingFee = mBottomSheetDialog.findViewById(R.id.shippingFee);
                    subTotalPrice.setText(subTotal + " EUR ");
                    if (firstAddress != null) {
                        if (firstAddress.get(0).getCountry().equals("Germany")) {
                            shippingFee.setText("5" + " EUR ");
                        } else {
                            shippingFee.setText("9" + " EUR ");
                        }
                    }

                    //subTotalPrice.setText(subTotal);
                    bottomSheetDialog.setContentView(mBottomSheetDialog);

                    bottomSheetDialog.show();
                }
            }
        });*/

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(CheckoutActivity.this,AddAddress.class);
                startActivity(go);
            }
        });

        readAddress();
    }

    private void checkPhoneNumber() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(user.getUid()));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                mUser.add(user);

                if (mUser.get(0).getPhone()==null) {
                    Intent go = new Intent(CheckoutActivity.this,EditActivity.class);
                    startActivity(go);
                    Toast.makeText(CheckoutActivity.this, getResources().getString(R.string.add_phone), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    placeOrder();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void placeOrder() {
        if (user.getUid()!=null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mBasket.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Basket user = snapshot.getValue(Basket.class);
                        assert user != null;
                        mBasket.add(user);
                    }
                    if (mBasket.size()!=0) {
                        Log.e("size", String.valueOf(mBasket.size()));
                        for (i=0 ; i < mBasket.size();i++) {
                            Log.e("isize", String.valueOf(i));
                            Basket item = mBasket.get(i);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order").child(user.getUid()).push();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                            String currentDateAndTime = sdf.format(new Date());

                            Order podcast_adabter = new Order(
                                    reference.getKey(),
                                    user.getUid(),
                                    firstAddress.get(0).getId(),
                                    "1",
                                    item.getCategory(),
                                    item.getSubcategory(),
                                    item.getItemId(),
                                    currentDateAndTime,
                                    item.getSize(),
                                    randomShipmentId(),
                                    "preparing item",
                                    true);
                            reference.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(CheckoutActivity.this, getResources().getString(R.string.order_placed), Toast.LENGTH_SHORT).show();
                                    sendDoctorNotificationsHelper();
                                    removeconfirmeditems();
                                    decreaseItemCount(item);
                                    finish();
                                    /*if (i==mBasket.size()) {
                                        Intent checkout = new Intent(CheckoutActivity.this, MyOrders.class);
                                        startActivity(checkout);
                                    }*/
                                }

                            });
                        }
                        Intent checkout = new Intent(CheckoutActivity.this, MyOrders.class);
                        startActivity(checkout);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        }else {
            Log.e("error","user id is null");
        }

    }

    private void decreaseItemCount(Basket basketItem) {
        List<Item> mCategory = new ArrayList<>();
        if (!basketItem.getCategory().equals("offer")){
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items").child(basketItem.getCategory()).child(basketItem.getSubcategory()).child(basketItem.getItemId());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mCategory.clear();
                    Item user = dataSnapshot.getValue(Item.class);
                    mCategory.add(user);

                    int availableUnits=mCategory.get(0).getAvailableUnits();
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> postValues = new HashMap<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                postValues.put(snapshot.getKey(), snapshot.getValue());
                            }
                            if (availableUnits>=1) {
                                postValues.put("availableUnits", availableUnits - 1);
                            }
                            reference.updateChildren(postValues);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendDoctorNotificationsHelper() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getKey());
                }
                sendDoctorNotifications(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void sendDoctorNotifications(List<String> DocotrIds) {
        String doctorid;
        int loop;
        for (loop = 0;loop<DocotrIds.size();loop++){
            doctorid = DocotrIds.get(loop);
            notify=true;
            sendNotification("هناك طلب جديد",doctorid);
        }
    }

    private void sendNotification(final String message, final String doctorid) {
        if (notify) {
            DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
            Query query = tokens.orderByKey().equalTo(doctorid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Token token = snapshot.getValue(Token.class);
                        Data data = new Data(user.getUid(), R.mipmap.ic_launcher, ""+": "+message, "طلب جديد",
                                doctorid);

                        Sender sender = new Sender(data, token.getToken());

                        apiService.sendNotification(sender)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code() == 200){
                                            if (response.body().success != 1){
                                                //Toast.makeText(CheckoutActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {

                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        notify = false;
    }
    private String randomShipmentId() {

        UUID myuuid = UUID.randomUUID();
        long highbits = myuuid.getMostSignificantBits();
       //Log.e("error",String.valueOf(highbits).substring(4).replace('-','1'));
        return String.valueOf(highbits).substring(4).replace('-','1');
    }


    private void readAddress() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAddress.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Address user = snapshot.getValue(Address.class);
                    assert user != null;
                    mAddress.add(user);
                    if (addressChosen){
                        firstAddress.add(mAddress.get(addressPosition));
                    }else {
                        firstAddress.add(mAddress.get(0));
                    }

                }

                ChooseAddressAdapter homeAdapter = new ChooseAddressAdapter(CheckoutActivity.this, firstAddress,ItemList,false);
                recyclerView.setAdapter(homeAdapter);
                txtPrice.setText(TotalPrice()+getResources().getString(R.string.currency));

                if (mAddress.size()!=0){
                    mprogressBar.setVisibility(View.GONE);
                    mprogressTxt.setVisibility(View.GONE);
                    addAddress.setVisibility(View.GONE);
                }else {
                    mprogressBar.setVisibility(View.GONE);
                    mprogressTxt.setVisibility(View.VISIBLE);
                    addAddress.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void removeconfirmeditems() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Basket").child(user.getUid());
        reference.removeValue();
    }

    private String TotalPrice() {

        int subTotalInt=Integer.parseInt(subTotal);
        int shippingFeeInt=0;
        /*if (firstAddress.size()!=0) {
            if (firstAddress.get(0).getCountry().equals("Germany")) {
                shippingFeeInt = Integer.parseInt("5");
            } else {
                shippingFeeInt = Integer.parseInt("9");
            }
        }*/
        int totalPrice= subTotalInt+shippingFeeInt;

        return String.valueOf(totalPrice);
    }
}