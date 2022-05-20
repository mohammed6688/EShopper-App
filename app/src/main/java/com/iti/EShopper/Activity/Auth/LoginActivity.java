package com.iti.EShopper.Activity.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.iti.EShopper.Activity.ItemReview;
import com.iti.EShopper.MainActivity;
import com.iti.EShopper.Module.Cart;
import com.iti.EShopper.Module.Product;
import com.iti.EShopper.Module.User;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements FABProgressListener {

    private static final String TAG = "error";
    private static final int RC_SIGN_IN = 9001;
    EditText email;
    EditText password;
    FloatingActionButton login,guest,googleLogin;
    TextView forgetPass;
    ProgressBar progressBar;
    TextView logintxt;
    FABProgressCircle fabProgressCircle;
    List<User> UserList =new ArrayList<>();
    private String url = "http://10.0.2.2:8080/E_commerce_API/api/eShopper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fabProgressCircle=findViewById(R.id.fabProgressCircle);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgetPass=findViewById(R.id.forgetPassword);

        fabProgressCircle.attachListener(this);
        initializeListener();

    }

    private void initializeListener() {

        login.setOnClickListener(v -> {

            fabProgressCircle.show();
            Login();
        });

//        forgetPass.setOnClickListener(v -> {
//            Intent go = new Intent(LoginActivity.this,ResetActivity.class);
//            startActivity(go);
//        });
    }

    private void Login() {
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();


        Log.e("email",txt_email);
        Log.e("password",txt_password);
        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
            showSnackbar("All fields are required");
            fabProgressCircle.hide();
        } else {

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", txt_email);
                jsonBody.put("password", txt_password);
                final String mRequestBody = jsonBody.toString();

                url+="/checkSignIn";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    try {
                        JSONObject respObj = new JSONObject(response);

                        if (response.equals("null")){
                            fabProgressCircle.hide();
                            showSnackbar("Authentication failed!");
                        }else {
                            User user=jsontoProduct(respObj);
                            MyApplication.setPreferences("userId",user.getId());
                            MyApplication.setPreferencesBoolean("Guest",false);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            //fabProgressCircle.beginFinalAnimation();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    fabProgressCircle.hide();
                    showSnackbar("Authentication failed!");
                    Log.e("LOG_RESPONSE", error.toString());
                    Toast.makeText(LoginActivity.this,  error.toString(), Toast.LENGTH_SHORT).show();
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
    }
    public static User jsontoProduct(JSONObject jsonProduct) throws JSONException {
        User product= new User(
                jsonProduct.getInt("id"),
                jsonProduct.getBoolean("admin"),
                jsonProduct.getString("name"),
                jsonProduct.getString("birthDate"),
                jsonProduct.getString("password"),
                jsonProduct.getInt("phoneNumber"),
                jsonProduct.getString("jop"),
                jsonProduct.getString("email"),
                jsonProduct.getInt("creditLimit"),
                jsonProduct.getString("address"),
                jsonProduct.getString("interests")
        );
        return product;
    }


    protected void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFABProgressAnimationEnd() {
        fabProgressCircle.hide();
        MyApplication.setPreferencesBoolean("Guest",false);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}