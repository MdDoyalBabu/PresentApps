package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doyal2020.HandlerAllclass.SubjectAddHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubjectAddActivity extends AppCompatActivity {


    private EditText sDepartment;
    private EditText sSubject;
    private EditText sSemester;
    private Button sAddButton;


    private ProgressDialog sProgressDialog;

    private FirebaseUser sCurrent_user;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);



        sDepartment = findViewById(R.id.department_name_Id);
        sSubject = findViewById(R.id.subject_name_Id);
        sSemester = findViewById(R.id.semester_name_Id);

        sProgressDialog=new ProgressDialog(this);

        sAddButton = findViewById(R.id.addButton_Id);




        ////Add button start .....................................

        sAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String department = sDepartment.getText().toString();
                String subject = sSubject.getText().toString();
                String semester = sSemester.getText().toString();


                if (department.isEmpty()) {

                    sDepartment.setError("Please enter any department name");
                    sDepartment.requestFocus();
                    return;
                }
                if (subject.isEmpty()) {

                    sSubject.setError("Please enter any subject name");
                    sSubject.requestFocus();
                    return;
                }
                if (semester.isEmpty()) {

                    sSemester.setError("Please enter any semester name");
                    sSemester.requestFocus();
                    return;
                }
                  if (semester.length()>3) {

                    sSemester.setError("The Semester name must be 3 digit,,Exp: 1st");
                    sSemester.requestFocus();
                    return;
                }



                sProgressDialog.setTitle("Saving Data .............");
                sProgressDialog.setMessage("Please wait a moment");
                sProgressDialog.setCanceledOnTouchOutside(false);
                sProgressDialog.show();



                sCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
                String sCurrent_uid=sCurrent_user.getUid();


                SubjectAddHandler subjectAddHandler=new SubjectAddHandler(department,subject,semester);

                mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");
                mDatabase.child(sCurrent_uid).child("SubjectData").child(mDatabase.push().getKey()).setValue(subjectAddHandler).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            sProgressDialog.dismiss();
                            Toast.makeText(SubjectAddActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SubjectAddActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            sProgressDialog.hide();
                            Toast.makeText(SubjectAddActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        ////   Add button End .....................................

    }
}
