package com.iti.EShopper.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.models.Installment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class InstallmentBook extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_GALLERY_CAPTURE = 2;
    private static final int REQUEST_SECOND_GALLERY_CAPTURE = 3;
    EditText phoneNumber,phoneModel,initial;
    Button send,attachImage,attachSecImage;

    Cursor cursor ;
    int columnIndex ;
    List<String> albumImages;
    boolean fdone;
    private List<Uri> AlbumImagesUri = new ArrayList<>();
    private List<String> DownloadUri = new ArrayList<>();
    private StringBuilder otherImages= new StringBuilder();
    RecyclerView recyclerView;
    private StorageReference mChatphotosStorageRerances;
    private FirebaseStorage mfirebaseStorage;
    private Uri MultiDownloadUri;
    boolean multiImageUploaded;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.installment_book_tab, container, false);

        mfirebaseStorage = FirebaseStorage.getInstance();
        mChatphotosStorageRerances = mfirebaseStorage.getReference().child("chat_photos");
        phoneNumber = view.findViewById(R.id.phoneNumber);
        phoneModel = view.findViewById(R.id.phoneModel);
        initial = view.findViewById(R.id.initial);
        attachImage = view.findViewById(R.id.attachImage);
        attachSecImage = view.findViewById(R.id.attachSecImage);
        send = view.findViewById(R.id.send);
        albumImages=new ArrayList<>();
        albumImages=new ArrayList<>();

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("برجاء الانتظار");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                    galleryIntent.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), REQUEST_GALLERY_CAPTURE);

                    //startActivityForResult(galleryIntent, REQUEST_GALLERY_CAPTURE);
                }
            }
        });
        attachSecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                    galleryIntent.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), REQUEST_SECOND_GALLERY_CAPTURE);

                    //startActivityForResult(galleryIntent, REQUEST_GALLERY_CAPTURE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPhoneNumber=phoneNumber.getText().toString();
                String mPhoneModel=phoneModel.getText().toString();
                String mInitial=initial.getText().toString();
                
                if (!mPhoneNumber.isEmpty() && !mPhoneModel.isEmpty() && !mInitial.isEmpty() ){

                    mProgressDialog.show();
                    if (multiImageUploaded) {
                        uploadData();
                    }else {
                        if (AlbumImagesUri.size()==0){
                            mProgressDialog.hide();
                            Toast.makeText(getContext(), "اضف صورة البطاقة", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            // When an Image is picked
            if (requestCode == REQUEST_GALLERY_CAPTURE ||requestCode == REQUEST_SECOND_GALLERY_CAPTURE&& resultCode == RESULT_OK
                    && null != data) {



                ClipData mClipData = data.getClipData();
                if (mClipData!=null){
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri selectedImage = item.getUri();

                        // display your images

                        //Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();
                        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        //albumImages.add(cursor.getString(columnIndex));
                        albumImages.add(selectedImage.toString());
                    }
                }else {


                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //albumImages.add(cursor.getString(columnIndex));
                    albumImages.add(selectedImage.toString());
                }

                multiImageHandler(data);
                UploadMultiImages();
                fdone=true;

                cursor.close();

            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    private void multiImageHandler(Intent data) {
        Log.e("ImageAdded","imageAdded");

        ClipData clipData=data.getClipData();
        if (clipData!=null){
            for (int i=0 ; i<clipData.getItemCount();i++){
                Uri imageUri=clipData.getItemAt(i).getUri();
                AlbumImagesUri.add(imageUri);
            }
        }else {
            AlbumImagesUri.add(data.getData());
        }

    }

    private void UploadMultiImages() {
        if (AlbumImagesUri.size() != 0){

            for(int i = 0; i < AlbumImagesUri.size(); i++) {

                Uri selctimageuri=AlbumImagesUri.get(i);
                final StorageReference photoref = mChatphotosStorageRerances.child(selctimageuri.getLastPathSegment());

                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selctimageuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();
                //uploading the image

                UploadTask uploadTask = photoref.putBytes(data);

                int finalI = i;
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return photoref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "photo uploaded", Toast.LENGTH_SHORT).show();
                            MultiDownloadUri = task.getResult();

                            DownloadUri.add(MultiDownloadUri.toString());

                            AlbumImagesUri.clear();
                            Log.e("size", String.valueOf(DownloadUri.size()));


                            multiImageUploaded=true;
                            if (mProgressDialog.isShowing()){
                                uploadData();                   //if user finish typing before images are uploaded
                            }

                        } else {
                            // Handle failures
                            Toast.makeText(getContext(), "photo failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    }

    private void uploadData() {
        for (int i=0 ;i<DownloadUri.size();i++){
            String string =DownloadUri.get(i);

            otherImages.append(string).append("\n");
        }
        String mPhoneNumber=phoneNumber.getText().toString();
        String mPhoneModel=phoneModel.getText().toString();
        String mInitial=initial.getText().toString();

        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Installment").child("Book").push();
        Installment podcast_adabter = new Installment(
                r.getKey(),
                mPhoneNumber,
                "",
                "",
                otherImages.toString(),
                mPhoneModel,
                mInitial);
        r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "تم ارسال الطلب سيتم ارسال اشعار بقيمة المبلغ المتبقي", Toast.LENGTH_LONG).show();
                phoneNumber.setText("");
                phoneModel.setText("");
                initial.setText("");
                mProgressDialog.hide();
            }

        });
    }
    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                startActivity(new Intent(getContext(), BaseActivity.class));
                                getActivity().overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {

            return true;

        }
    }
}
