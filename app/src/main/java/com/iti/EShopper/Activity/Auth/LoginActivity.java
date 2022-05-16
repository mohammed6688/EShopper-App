package com.iti.EShopper.Activity.Auth;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.iti.EShopper.Activity.MainActivity;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.User;
import com.iti.EShopper.notification.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends BaseActivity implements  FABProgressListener {

    private static final String TAG = "error";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    EditText email;
    EditText password;
    FloatingActionButton login,guest,googleLogin;
    TextView forgetPass;
    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView logintxt;
    FABProgressCircle fabProgressCircle,guestProgressCircle,googleProgressCircle;
    List<User> UserList =new ArrayList<>();
    DatabaseReference reference;
    LoginButton loginButton;
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fabProgressCircle=findViewById(R.id.fabProgressCircle);
        guestProgressCircle=findViewById(R.id.facebookProgressCircle);
        googleProgressCircle=findViewById(R.id.googleProgressCircle);
        googleLogin=findViewById(R.id.googleLogin);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        guest=findViewById(R.id.facebook);
        forgetPass=findViewById(R.id.forgetPassword);

        auth = FirebaseAuth.getInstance();

        googleProgressCircle.attachListener(this);
        guestProgressCircle.attachListener(this);
        fabProgressCircle.attachListener(this);
        initializeListener();


        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.button_sign_in);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                guestProgressCircle.hide();
            }
            @Override
            public void onError(FacebookException error) {
                guestProgressCircle.hide();
                Log.d(TAG, "facebook:onError", error);
            }

        });

    }

    private void initializeListener() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabProgressCircle.show();
                Login();
            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleProgressCircle.show();
                GoogleLogin();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();
                guestProgressCircle.show();

            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(LoginActivity.this,ResetActivity.class);
                startActivity(go);
            }
        });
    }



    private void GoogleLogin() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", firebaseUser.getDisplayName());
                            hashMap.put("phone", firebaseUser.getPhoneNumber());
                            hashMap.put("status", "offline");
                            hashMap.put("search", firebaseUser.getDisplayName().toLowerCase());
                            hashMap.put("permission", "false");
                            //hashMap.put("admin", "false");
                            hashMap.put("points", "0");

                            if (!firebaseUser.getPhotoUrl().equals("")){
                                hashMap.put("imageURL", firebaseUser.getPhotoUrl().toString());
                            }else {
                                hashMap.put("imageURL", "default");
                            }

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.e("error","s");
                                    if (task.isSuccessful()){
                                        guestProgressCircle.beginFinalAnimation();
                                        updateToken(FirebaseInstanceId.getInstance().getToken());
                                        /*Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();*/
                                    }
                                    Log.e("error",task.toString());
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            fabProgressCircle.hide();
                            showSnackbar("Authentication failed!");
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            checkIfAdmin(true);
                        } else {
                            googleProgressCircle.hide();
                            showSnackbar("Authentication failed!");

                        }
                    }
                });
    }

    private void checkIfAdmin(boolean google) {

        Query lquery = FirebaseDatabase.getInstance().getReference("Admin").orderByChild("id").equalTo(auth.getCurrentUser().getUid());

        lquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    UserList.add(user);
                }

                if (UserList.size()!=0){
                    FirebaseAuth.getInstance().signOut();
                    if (google){
                        googleProgressCircle.hide();
                    }else {
                        fabProgressCircle.hide();
                    }
                    showSnackbar("you cant login using admin account");

                }else {
                    if (google){

                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        String userid = firebaseUser.getUid();
                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userid);
                        hashMap.put("username", firebaseUser.getDisplayName());
                        hashMap.put("phone", firebaseUser.getPhoneNumber());
                        hashMap.put("status", "offline");
                        hashMap.put("search", firebaseUser.getDisplayName().toLowerCase());
                        hashMap.put("permission", "false");
                        //hashMap.put("admin", "false");
                        hashMap.put("points", "0");

                        if (!firebaseUser.getPhotoUrl().equals("")){
                            hashMap.put("imageURL", firebaseUser.getPhotoUrl().toString());
                        }else {
                            hashMap.put("imageURL", "default");
                        }

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e("error","s");
                                if (task.isSuccessful()){
                                    googleProgressCircle.beginFinalAnimation();
                                    updateToken(FirebaseInstanceId.getInstance().getToken());
                                        /*Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();*/
                                }
                                Log.e("error",task.toString());
                            }
                        });
                    }
                    if (google){
                        googleProgressCircle.beginFinalAnimation();
                    }else {
                        fabProgressCircle.beginFinalAnimation();
                    }
                    updateToken(FirebaseInstanceId.getInstance().getToken());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "error: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Guest() {
        MyApplication.setPreferencesBoolean("Guest",true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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

            auth.signInWithEmailAndPassword(txt_email, txt_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                checkIfAdmin(false);

                            } else {
                                fabProgressCircle.hide();
                                showSnackbar("Authentication failed!");

                            }
                        }
                    });
        }
    }


    @Override
    public void onFABProgressAnimationEnd() {
        googleProgressCircle.hide();
        fabProgressCircle.hide();
        MyApplication.setPreferencesBoolean("Guest",false);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(auth.getUid()).setValue(token1);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);

    }
}