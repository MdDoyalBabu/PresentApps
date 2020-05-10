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
import com.doyal2020.AllmyAdapter.SubjectAddAdapter;
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


    String subject_value;
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
         subject_value=getIntent().getStringExtra("subject");
        value=department_value.concat(semester_value).concat(subject_value);


        recyclerView=findViewById(R.id.student_recyclerView_Id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailsAdapter=new StudentDetailsAdapter(StudentNRActivity.this,studentNRHandlerList);
        recyclerView.setAdapter(detailsAdapter);



        detailsAdapter.setOnItemClickLisener(new StudentDetailsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                StudentNRHandler selsecteditem=studentNRHandlerList.get(position);
               String name=selsecteditem.getStudent();
                String roll=selsecteditem.getRoll();
                String group=selsecteditem.getGroup();
                String shift=selsecteditem.getShift();


                String subjectName=selsecteditem.getSubjectName();
                String value=name.concat(roll).concat(group).concat(shift).concat(subjectName);



                Intent intent=new Intent(StudentNRActivity.this,PresentAbsentShow.class);
                intent.putExtra("sendValue",value);

                startActivity(intent);

                Toast.makeText(getApplicationContext(), "OnItemClick   "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position, View view) {
                Toast.makeText(getApplicationContext(), "OnLongItemClick   "+position, Toast.LENGTH_SHORT).show();

            }
        });



        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        String mCurent_uid=mCurrent_user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");
        mDatabase.child(mCurent_uid).child("SubjectData").child("StudentDetails").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentNRHandlerList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    StudentNRHandler data=dataSnapshot1.getValue(StudentNRHandler.class);
                    studentNRHandlerList.add(data);
                    detailsAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        floatingActionButton=findViewById(R.id.floating_ad_button_Id);

        //.............FloatingActionButton  start......................//

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(StudentNRActivity.this,StudentNRaddActivity.class);

                intent.putExtra("SendValue",value);
                intent.putExtra("subject_value",subject_value);
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

                            StudentNRHandler data=new StudentNRHandler(name,roll,shift,group,subject_value);
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
