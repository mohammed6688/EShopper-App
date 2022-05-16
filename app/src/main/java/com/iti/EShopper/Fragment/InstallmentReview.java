package com.iti.EShopper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.EShopper.R;
import com.iti.EShopper.helper.MyApplication;
import com.iti.EShopper.models.Favorite;
import com.iti.EShopper.models.Installment;
import com.iti.EShopper.models.User;

import java.util.ArrayList;
import java.util.List;

public class InstallmentReview extends Fragment {


    EditText number,etName;
    TextView notFound;
    private RecyclerView recyclerView;
    private ProgressBar mprogressBar;
    private List<Favorite> mFavorite;
    FirebaseAuth user;
    private CardView search;
    LinearLayout layProgress;
    private List<Installment> ItemList;
    DatabaseReference reference;
    String userName;
    boolean guest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.installment_review_tab, container, false);


        number =view.findViewById(R.id.phone);
        etName =view.findViewById(R.id.name);
        guest = MyApplication.getPrefranceDataBoolean("Guest");
        //recyclerView.setLayoutManager(linearLayoutManager);


        mFavorite = new ArrayList<>();
        ItemList = new ArrayList<>();
        user=FirebaseAuth.getInstance();

        search=view.findViewById(R.id.card);


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null&& !guest) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    userName = user.getUsername();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber=number.getText().toString();
                String txtName=etName.getText().toString();
                if (!phoneNumber.isEmpty()&&!txtName.isEmpty()){
                    readUsers(phoneNumber,txtName);
                }
            }
        });
        return view;
    }

    private void readUsers(String phoneNumber,String name) {

        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Installment").child("request").push();

        Installment podcast_adabter = new Installment(
                r.getKey(),
                phoneNumber,
                name,
                userName,
                "",
                "",
                "");
        r.setValue(podcast_adabter, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "تم ارسال الطلب سيتم ارسال اشعار لك بقيمة المبلغ المتبقي", Toast.LENGTH_LONG).show();
                number.setText("");
                etName.setText("");
            }

        });
    }
}
