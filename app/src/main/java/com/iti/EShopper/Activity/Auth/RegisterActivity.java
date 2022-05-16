package com.iti.EShopper.Activity.Auth;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.iti.EShopper.Activity.MainActivity;
import com.iti.EShopper.Activity.UseTerms;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.notification.Token;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements FABProgressListener {


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int IMAGE_REQUEST = 1;
    EditText name,email,phone,password,repassword;
    CountryCodePicker ccp;
    FloatingActionButton register;
    TextView forgetPass;
    TextView terms;
    ImageView UploadPhoto;
    FirebaseAuth auth;
    DatabaseReference reference;
    private Uri imageUri;
    private StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseUser fuser;
    String mUri="";
    TextView logintxt;
    FABProgressCircle fabProgressCircle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fabProgressCircle=findViewById(R.id.fabProgressCircle);
        UploadPhoto=findViewById(R.id.photoUpload);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repeatedPassword);
        register=findViewById(R.id.register);
        //forgetPass=findViewById(R.id.forgetPass);
        terms=findViewById(R.id.terms);
        ccp=findViewById(R.id.ccp);
        //logintxt = findViewById(R.id.loginTxt);
        //progressBar = findViewById(R.id.progress);
        //progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Users");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fabProgressCircle.attachListener(this);

        initializeListener();


    }


    private void initializeListener() {

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RegisterActivity.this, UseTerms.class);
                startActivity(go);
            }
        });

        UploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermission())
                    openImage();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_phone = ccp.getSelectedCountryCodeWithPlus()+phone.getText().toString();
                String txt_password = password.getText().toString();
                //String txt_repassword = repassword.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || /*TextUtils.isEmpty(txt_repassword) ||*/ TextUtils.isEmpty(txt_phone)){
                    //Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    showSnackbar("All fields are required");
                } else if (txt_password.length() < 6 ){
                    showSnackbar("password must be at least 6 characters");
                    //Toast.makeText(RegisterActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } /*else if (!txt_password.equals(txt_repassword)){
                    //Toast.makeText(RegisterActivity.this, "password must be equal", Toast.LENGTH_SHORT).show();
                    showSnackbar("password must be equal");
                }*/else {
                    fabProgressCircle.show();
                    registeration(txt_username, txt_email, txt_phone, txt_password);
                }
            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            UploadPhoto.setImageURI(imageUri);

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(RegisterActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        mUri = downloadUri.toString();

                        /*reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", ""+mUri);
                        reference.updateChildren(map);*/

                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        fabProgressCircle.hide();
                    }
                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    fabProgressCircle.hide();
                    pd.dismiss();
                }
            });
        } else {
            showSnackbar("No image selected");
            //Toast.makeText(RegisterActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
            fabProgressCircle.hide();
        }
    }

    private void registeration(String txt_username, String txt_email, String txt_phone, String txt_password) {

        //logintxt.setText("برجاء الأنتظار");
        //progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("error","f");
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", txt_username);
                            hashMap.put("phone", txt_phone);
                            hashMap.put("status", "offline");
                            hashMap.put("search", txt_username.toLowerCase());
                            hashMap.put("permission", "false");

                            if (!mUri.equals("")){
                                hashMap.put("imageURL", mUri);
                            }else {
                                hashMap.put("imageURL", "default");
                            }

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.e("error","s");
                                    if (task.isSuccessful()){
                                        fabProgressCircle.beginFinalAnimation();
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
                            //Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                            //logintxt.setText("إنشاء حساب");
                            fabProgressCircle.hide();
                            showSnackbar("You can't register with this email or password");
                            //progressBar.setVisibility(View.GONE);
                        }
                        Log.e("error",task.toString());
                    }
                });
    }

    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(RegisterActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(RegisterActivity
                                        .this, RegisterActivity.class));
                                RegisterActivity.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {

            return true;

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(auth.getUid()).setValue(token1);
    }
    @Override
    public void onFABProgressAnimationEnd() {
        fabProgressCircle.hide();
        MyApplication.setPreferencesBoolean("Guest",false);
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}