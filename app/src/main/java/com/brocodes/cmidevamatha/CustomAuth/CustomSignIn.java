package com.brocodes.cmidevamatha.CustomAuth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brocodes.cmidevamatha.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CustomSignIn extends AppCompatActivity {


    EditText username, password;
    TextView Back,reset;
    Button SignIn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_sign_in);

        //String int_email = getIntent().getStringExtra("email_register");

        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword3);
        Back = findViewById(R.id.sign_back);
        SignIn = findViewById(R.id.sign_In);
        reset = findViewById(R.id.forgot_password);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract / validation
                if(username.getText().toString().isEmpty()){
                    username.setError("Email is Missing");
                    return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("Password is Missing");
                    return;
                }
                //data is valid now
                //begin for Sign in


                String email8 = username.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //on successful login / sign in
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            //startActivity(new Intent(CustomSignIn.this,UserDetails.class));
                            Intent int8 = new Intent(CustomSignIn.this,UserDetails.class);
                            int8.putExtra("email8",email8);
                            startActivity(int8);
                            finish();
                        } else
                            {
                            Toast.makeText(CustomSignIn.this, "You are Signed In But,Please verify the email", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CustomSignIn.this, "Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



            }
        });



        Back.setOnClickListener(v -> startActivity(new Intent(CustomSignIn.this,CustomRegister.class)));
        /*SignIn.setOnClickListener(v -> UserSignIn());*/
        reset.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),ResetPassword.class)));
    }

    /*private void UserSignIn() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Email.setError("Type Email ID, Can't be Empty");
            Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Password.setError("Type Password, Can't be Empty");
            Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Check your Email address!");
        }
        else {
            m1Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(Objects.requireNonNull(m1Auth.getCurrentUser()).isEmailVerified()){
                            Intent intent8 = new Intent(CustomSignIn.this,UserDetails.class);
                            intent8.putExtra("email8",email);
                            startActivity(intent8);

                        } else {
                            Toast.makeText(CustomSignIn.this, "Please Verify Email... Check Email for verification mail", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        Toast.makeText(CustomSignIn.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }*/

}