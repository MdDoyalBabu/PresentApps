package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.doyal2020.AllmyAdapter.PresentShowAdapter;
import com.doyal2020.AllmyAdapter.StudentDetailsAdapter;
import com.doyal2020.AllmyAdapter.SubjectAddAdapter;
import com.doyal2020.HandlerAllclass.PresentShowHandler;
import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PresentAbsentShow extends AppCompatActivity {



    private DatabaseReference mDatabase;
    private FirebaseUser mCurrent_user;
    private PresentShowAdapter presentShowAdapter;
    private List<PresentShowHandler> presentShowHandlersList=new ArrayList<>();

   private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_absent_show);


        recyclerView=findViewById(R.id.present_recyclerview_Id);


        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presentShowAdapter=new PresentShowAdapter(PresentAbsentShow.this,presentShowHandlersList);
        recyclerView.setAdapter(presentShowAdapter);

        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrent_user.getUid();

        String value=getIntent().getStringExtra("sendValue");

             mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData")
                .child(current_uid).child("StudentPresent").child(value);


    }

    @Override
    protected void onStart() {
        super.onStart();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                presentShowHandlersList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PresentShowHandler data=dataSnapshot1.getValue(PresentShowHandler.class);
                    presentShowHandlersList.add(data);
                    presentShowAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
