package com.doyal2020.presentapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private EditText rName;
    private EditText rEmail;
    private EditText rPassword;
    private Button rRegistrationBtn;
    private ProgressDialog rProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PresentApps").child("UserData");

        rName = findViewById(R.id.name_edittext_Id);
        rEmail = findViewById(R.id.email_edittext_Id);
        rPassword = findViewById(R.id.password_edittext_Id);
        rRegistrationBtn = findViewById(R.id.registration_button);


        rProgressDialog = new ProgressDialog(this);

        //Registration button start.....................................

        rRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = rName.getText().toString();
                final String email = rEmail.getText().toString();
                final String password = rPassword.getText().toString();


                if (name.isEmpty()) {

                    rName.setError("Please enter your name");
                    rName.requestFocus();
                    return;
                }

                if (email.isEmpty()) {

                    rEmail.setError("Please enter your email");
                    rEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    rEmail.setError("Please enter a valid email address");
                    rEmail.requestFocus();
                    return;
                }
                if (password.length() < 6) {

                    rPassword.setError("Minimum length of a password should be 6");
                    rPassword.requestFocus();
                    return;

                }
                rProgressDialog.setTitle("Registering .............");
                rProgressDialog.setMessage("Please wait a moment");
                rProgressDialog.setCanceledOnTouchOutside(false);
                rProgressDialog.show();


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name", name);
                            hashMap.put("email", email);
                            hashMap.put("password", password);

                            mDatabase.child(uid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        rProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }

                                }
                            });

                        } else {
                            rProgressDialog.hide();
                            Toast.makeText(RegisterActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        //Registration button End.....................................

    }
}
