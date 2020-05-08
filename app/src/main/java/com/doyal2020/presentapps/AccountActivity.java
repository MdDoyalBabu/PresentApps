package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {


    private CircleImageView circleImageView;
    private  TextView nameTextview;
    private TextView emailTextview;

    private FirebaseUser mCurrent_User;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        circleImageView=findViewById(R.id.circlerView_Id);
        nameTextview=findViewById(R.id.name_account_Id);
        emailTextview=findViewById(R.id.email_account_Id);

        mCurrent_User=FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrent_User.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData").child(current_uid);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();


                nameTextview.setText(name);
                emailTextview.setText(email);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
