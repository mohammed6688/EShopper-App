package com.iti.EShopper.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

public class WelcomeActivity extends AppCompatActivity {

    Button login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(go);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(WelcomeActivity.this,EmailOrGoogle.class);
                startActivity(go);
            }
        });
    }
    @Override
    public void onBackPressed() {
        boolean guest = MyApplication.getPrefranceDataBoolean("Guest");
        if (guest){
            super.onBackPressed();
        }else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }

    }
}