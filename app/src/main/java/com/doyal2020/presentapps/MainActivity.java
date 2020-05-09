package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.doyal2020.AllmyAdapter.SubjectAddAdapter;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingActionButton mFoatingActionButton;

    private RecyclerView recyclerView;



    private SubjectAddAdapter subjectAddAdapter;
    private List<SubjectAddHandler> subejctList=new ArrayList<>();


    private DatabaseReference mDatabase;
    private FirebaseUser mCurrent_user;
    String mCurent_uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        mFoatingActionButton=findViewById(R.id.floolating_button_Id);
        recyclerView=findViewById(R.id.recyclerVIew_Id);


        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        subjectAddAdapter=new SubjectAddAdapter(MainActivity.this,subejctList);
        recyclerView.setAdapter(subjectAddAdapter);






        subjectAddAdapter.setOnItemClickLisener(new SubjectAddAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                    SubjectAddHandler selsecteditem=subejctList.get(position);
                    String sendDepartment=selsecteditem.getDepartment();
                    String sendSubjectName=selsecteditem.getSubject();
                    String sendSemester=selsecteditem.getSemester();


                    Intent intent = new Intent(MainActivity.this, StudentNRActivity.class);

                    intent.putExtra("department",sendDepartment);
                    intent.putExtra("semester",sendSemester);
                    intent.putExtra("subject",sendSubjectName);

                    startActivity(intent);



                Toast.makeText(getApplicationContext(), "OnItemClick   "+position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(int position, View view) {
                Toast.makeText(getApplicationContext(), "OnLongItemClickk   "+position, Toast.LENGTH_SHORT).show();
            }
        });


        ////mFoatingActionButton start.....................................

        mFoatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,SubjectAddActivity.class);
                startActivity(intent);


            }
        });
        ////mFoatingActionButton End.....................................


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null){
          sendStart();

        }else{

            mCurrent_user=FirebaseAuth.getInstance().getCurrentUser();

            mCurent_uid = mCurrent_user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");

            mDatabase.child(mCurent_uid).child("SubjectData").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    subejctList.clear();

                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        SubjectAddHandler data=dataSnapshot1.getValue(SubjectAddHandler.class);
                        subejctList.add(data);
                        subjectAddAdapter.notifyDataSetChanged();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    private void sendStart() {

        Intent intent=new Intent(MainActivity.this,LoginAcivity.class);
        startActivity(intent);
        finish();

    }


    //Crate menu bar............................................

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logut_Id){
            FirebaseAuth.getInstance().signOut();
            sendStart();
        }
        if (item.getItemId()==R.id.account_Id){

            Intent intent=new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    //end menu bar..............................................

}
