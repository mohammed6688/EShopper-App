package com.iti.EShopper.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.models.Address;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddAddress extends BaseActivity {
    String tag_json_obj = "json_obj_req";
    private static final String TAG ="tag" ;
    String url = "https://restcountries.eu/rest/v2/region/europe";
    List<String> countryNames;
    EditText country;
    EditText addressTitle,address,postalCode,area,city;
    Button save;
    ArrayAdapter<String> adapter;
    String AddressTitleTxt,AddressTxt,areaTxt,countryTxt,cityTxt,postalCodeTxt;
    private DatabaseReference mMessagesDatabaseReference;
    FirebaseUser firebaseAuth;
    boolean editMode;
    Address productDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        editMode=getIntent().getBooleanExtra("editMode",false);
        productDetails = (Address) getIntent().getSerializableExtra("product_object");
        //showLoading(getResources().getString(R.string.fetching));
        countryNames=new ArrayList<>();

        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance().getCurrentUser();
        country=findViewById(R.id.country);
        city=findViewById(R.id.city);
        addressTitle=findViewById(R.id.AddressTitle);
        address=findViewById(R.id.Address);
        postalCode=findViewById(R.id.PostalCode);
        area=findViewById(R.id.Area);
        save=findViewById(R.id.save);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryNames);

        //country.setAdapter((SpinnerAdapter) countryNames);

        if (editMode){
            city.setText(productDetails.getCity());
            address.setText(productDetails.getAddress());
            addressTitle.setText(productDetails.getAddressTitle());
            //postalCode.setText(String.valueOf(productDetails.getPostalCode()));
            area.setText(productDetails.getArea());


        }
        //getCountryFromJson();
        initializeListener();
    }

    private void initializeListener() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddressTxt =address.getText().toString();
                AddressTitleTxt =addressTitle.getText().toString();
                //postalCodeTxt =postalCode.getText().toString();
                cityTxt= city.getText().toString();
                areaTxt =area.getText().toString();
                countryTxt =country.getText().toString();
                //countryTxt=country.getSelectedItem().toString();

                if (AddressTxt.isEmpty()){
                    showSnackbar(getResources().getString(R.string.address_is_empty));
                }else if (AddressTitleTxt.isEmpty()){
                    showSnackbar(getResources().getString(R.string.address_title_is_empty));
                }else if (areaTxt.isEmpty()){
                    showSnackbar(getResources().getString(R.string.area_is_empty));
                }else if (cityTxt.isEmpty()){
                    showSnackbar(getResources().getString(R.string.city_is_empty));
                }else {
                    showLoading(getResources().getString(R.string.address_added));
                    if (editMode){
                        UpdateData(AddressTxt,AddressTitleTxt,areaTxt,cityTxt,countryTxt);
                    }else {
                        UploadData();
                    }
                }


            }
        });
    }

    private void UpdateData(String address, String addressTitle,String area,String city,String country) {

            showLoading(getResources().getString(R.string.loading_holder));
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Address").child(firebaseAuth.getUid()).child(productDetails.getId());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> postValues = new HashMap<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        postValues.put(snapshot.getKey(), snapshot.getValue());
                    }
                    postValues.put("address", address);
                    postValues.put("addressTitle", addressTitle);
                    postValues.put("area", area);
                    postValues.put("city", city);
                    postValues.put("country", country);
                    reference.updateChildren(postValues);
                    hideLoading();
                    Toast.makeText(AddAddress.this, getResources().getString(R.string.changes_saved_successfuly), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

    }

    private void UploadData() {
        DatabaseReference r =mMessagesDatabaseReference.child("Address").child(firebaseAuth.getUid()).push();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        Address podcast_adabter = new Address(
                r.getKey(),
                AddressTitleTxt,
                AddressTxt,
                countryTxt,
                cityTxt,
                areaTxt,
                currentDateAndTime,
                0);
        r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(AddAddress.this, "Created successfully", Toast.LENGTH_SHORT).show();
                hideLoading();
                finish();
            }
        });
    }

    /*private void getCountryFromJson() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject student = response.getJSONObject(i);
                                String firstName = student.getString("name");
                                countryNames.add(firstName);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        Log.e("size", String.valueOf(countryNames.size()));

                        adapter.addAll(countryNames);
                        country.setAdapter(adapter);
                        hideLoading();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(AddAddress.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);
    }*/
}