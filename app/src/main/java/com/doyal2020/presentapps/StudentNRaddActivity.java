package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doyal2020.HandlerAllclass.StudentNRHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentNRaddActivity extends AppCompatActivity {


    private EditText studentName;
    private EditText rollNumber;
    private EditText shiftEdittext;
    private EditText groupEdittext;

    private ProgressDialog sProgressDialog;

    private DatabaseReference mDatabase;
    private FirebaseUser mCurrent_user;

    private Button addButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_nradd);



        final String valuee=getIntent().getStringExtra("SendValue");


        studentName=findViewById(R.id.student_name_Add_Id);
        rollNumber=findViewById(R.id.rollNmbr_add_Id);
        shiftEdittext=findViewById(R.id.shift_add_Id);
        groupEdittext=findViewById(R.id.group_add_Id);

        addButton1=findViewById(R.id.addButton_add_Id);


        sProgressDialog =new ProgressDialog(this);

        ////.....................addButton start...............................///

        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String studnentN = studentName.getText().toString();
                String roll = rollNumber.getText().toString();
                String shift = shiftEdittext.getText().toString();
                String group = groupEdittext.getText().toString();

                if (studnentN.isEmpty()) {

                    studentName.setError("Please enter any student name");
                    studentName.requestFocus();
                    return;
                }
                if (roll.isEmpty()) {

                    rollNumber.setError("Please enter a student roll ");
                    rollNumber.requestFocus();
                    return;
                }

                if (shift.isEmpty()) {

                    shiftEdittext.setError("Please enter a shift");
                    shiftEdittext.requestFocus();
                    return;
                }
                if (shift.length()>3) {

                    shiftEdittext.setError("The shift name must be 3 digit,,Exp: 2rd");
                    shiftEdittext.requestFocus();
                    return;
                }

                if (group.isEmpty()) {

                    groupEdittext.setError("Please enter a group");
                    groupEdittext.requestFocus();
                    return;
                }
                if (group.length()>3) {

                    groupEdittext.setError("The group name must be 2 digit,,Exp: A");
                    groupEdittext.requestFocus();
                    return;
                }


                sProgressDialog.setTitle("Saving Data .............");
                sProgressDialog.setMessage("Please wait a moment");
                sProgressDialog.setCanceledOnTouchOutside(false);
                sProgressDialog.show();


                mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
                String current_Uid=mCurrent_user.getUid();
                StudentNRHandler studentHander=new StudentNRHandler(studnentN,roll,shift,group);
                mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");
                mDatabase.child(current_Uid).child("SubjectData").child("StudentDetails").child(valuee)

                        .setValue(studentHander).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        sProgressDialog.dismiss();
                        Toast.makeText(StudentNRaddActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                        else {
                            sProgressDialog.hide();
                        Toast.makeText(StudentNRaddActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                    }

                    }
                });

            }
        });

        ////.....................addButton end...............................///




    }
}
