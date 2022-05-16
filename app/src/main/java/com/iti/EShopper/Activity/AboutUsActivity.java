package com.iti.EShopper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iti.EShopper.R;

import java.util.Locale;

import static android.widget.Toast.makeText;

public class AboutUsActivity extends AppCompatActivity {

    TextView mtext;
    String phone = "01200033396";
    String secondPhone = "0552550912";
    LinearLayout wlay,play,elay,secondPhoneLay,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        wlay = findViewById(R.id.wlay);
        play = findViewById(R.id.play);
        location = findViewById(R.id.location);
        secondPhoneLay = findViewById(R.id.secondPhone);
        elay = findViewById(R.id.elay);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:"+phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);

            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 30.67197984432278, 31.59180510913448);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });
        secondPhoneLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:"+secondPhone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);

            }
        });
        /*wlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean install = whatsApp.appInstalled("com.whatsapp", getApplicationContext());
                    if(install){
                        Uri number = Uri.parse("http://api.whatsapp.com/send?phone="+whatsapp+"&text=تم التواصل عبر تطبيق "+getResources().getString(R.string.app_name));
                        Intent i = new Intent(Intent.ACTION_VIEW, number);
                        startActivity(i);
                    }else{
                        makeText(view.getContext(), getResources().getString(R.string.msgWhatsappNotInstall), LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/


        elay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"virastayle@web.de"});//write the to email her
                startActivity(intent);
            }
        });
    }
}