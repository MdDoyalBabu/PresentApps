package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.doyal2020.AllmyAdapter.StudentDetailsAdapter;
import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentNRActivity extends AppCompatActivity {


    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    private List<StudentNRHandler>studentNRHandlerList=new ArrayList<>();
    private StudentDetailsAdapter detailsAdapter;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrent_user;

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_nr);


        String department_value=getIntent().getStringExtra("department");
        String semester_value=getIntent().getStringExtra("semester");
        String subject_value=getIntent().getStringExtra("subject");
        value=department_value.concat(semester_value).concat(subject_value);


        recyclerView=findViewById(R.id.student_recyclerView_Id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailsAdapter=new StudentDetailsAdapter(StudentNRActivity.this,studentNRHandlerList);
        recyclerView.setAdapter(detailsAdapter);




        floatingActionButton=findViewById(R.id.floating_ad_button_Id);

        //.............FloatingActionButton  start......................//

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(StudentNRActivity.this,StudentNRaddActivity.class);

                intent.putExtra("SendValue",value);
                startActivity(intent);

            }
        });

        //.............FloatingActionButton  start......................//


    }


    @Override
    protected void onStart() {
        super.onStart();



        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        String mCurent_uid=mCurrent_user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");
        mDatabase.child(mCurent_uid).child("SubjectData").child("StudentDetails").child(value).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        studentNRHandlerList.clear();

                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            String name=dataSnapshot1.child("student").getValue().toString();
                            String roll=dataSnapshot1.child("roll").getValue().toString();
                            String shift=dataSnapshot1.child("shift").getValue().toString();
                            String group=dataSnapshot1.child("group").getValue().toString();

                            StudentNRHandler data=new StudentNRHandler(name,roll,shift,group);
                            studentNRHandlerList.add(data);
                            detailsAdapter.notifyDataSetChanged();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }
}
