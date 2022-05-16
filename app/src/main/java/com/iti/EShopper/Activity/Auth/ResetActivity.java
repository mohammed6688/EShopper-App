package com.iti.EShopper.Activity.Auth;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;

public class ResetActivity extends BaseActivity {

    EditText mEmail;
    FloatingActionButton login;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        mEmail=findViewById(R.id.email);
        login=findViewById(R.id.btn_send);
        firebaseAuth=FirebaseAuth.getInstance();

        initializeListener();
    }

    private void initializeListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();

                if (email.equals("")){
                    showSnackbar("All fields are required!");
                    //Toast.makeText(ResetActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                showSnackbar("تفقد بريدك الالكتروني");
                                //Toast.makeText(ResetActivity.this, "تفقد بريدك الالكتروني", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}