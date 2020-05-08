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

public class LoginAcivity extends AppCompatActivity {


    private EditText lEmail;
    private EditText lPassword;
    private Button lRegistrationBtn;
    private Button lLoginBtn;
    private ProgressDialog lProgressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);


        mAuth=FirebaseAuth.getInstance();

        lEmail=findViewById(R.id.email_Id);
        lPassword=findViewById(R.id.password_Id);
        lRegistrationBtn=findViewById(R.id.registrationL_button);
        lLoginBtn=findViewById(R.id.loggin_button);

      lProgressDialog=new ProgressDialog(this);


        //Login button start.....................................

        lLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=lEmail.getText().toString();
                String password=lPassword.getText().toString();

                if (email.isEmpty()){

                    lEmail.setError("Please enter your email");
                    lEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    lEmail.setError("Please enter a valid email address");
                    lEmail.requestFocus();
                    return;
                }
                if (password.length()<6){

                    lPassword.setError("Minimum length of a password should be 6");
                    lPassword.requestFocus();
                    return;

                }

                lProgressDialog.setTitle("Logging in .............");
                lProgressDialog.setMessage("Please wait a moment");
                lProgressDialog.setCanceledOnTouchOutside(false);
                lProgressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            lProgressDialog.dismiss();
                            Intent intent=new Intent(LoginAcivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            lProgressDialog.hide();
                            Toast.makeText(LoginAcivity.this, "something error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


        //Login button end.....................................


//Registration button start.....................................

        lRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginAcivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

        //Registration button end.....................................


    }
}
