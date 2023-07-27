package com.brocodes.cmidevamatha.CustomAuth;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class CustomRegister extends AppCompatActivity {


    EditText registerEmail, registerPassword, confirmPassword;
    Button registerUserBtn, registerSignIn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_register);

        fAuth = FirebaseAuth.getInstance();
        registerEmail = findViewById(R.id.editTextTextEmailAddress);
        registerPassword = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        registerUserBtn = findViewById(R.id.btnRegister);
        registerSignIn = findViewById(R.id.tvLoginHere);



        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the data from the form
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String confirm_password = confirmPassword.getText().toString().trim();

                if(email.isEmpty()){
                    registerEmail.setError("Email is Required");
                    return;
                }
                if(password.isEmpty()){
                    registerPassword.setError("Password is Required");
                    return;
                }
                if(confirm_password.isEmpty()){
                    confirmPassword.setError("Password is Required");
                    return;
                }
                if(!password.equals(confirm_password)){
                    confirmPassword.setError("Password Does Not Match");
                    return;
                }
                //data is validated
                //create user for the further steps

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CustomRegister.this, "Verification mail sent to you, check mail", Toast.LENGTH_LONG).show();
                            }
                        });
                        startActivity(new Intent(CustomRegister.this,CustomSignIn.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CustomRegister.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });






        /*Register.setOnClickListener(view ->{
            createUser();
        });*/

        registerSignIn.setOnClickListener(view ->{
            startActivity(new Intent(CustomRegister.this,CustomSignIn.class));
            finish();
        });

    }

   /* private void createUser() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Email.setError("Type Email ID, can't be Empty");
            Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Password.setError("Type new Password, can't be Empty");
            Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Email Address has less security, for better security use another email to create an account...");
        } else
            {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()){
                    //sending email to user as verification
                    Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(this,new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Email.setText("");
                                Password.setText("");
                                Toast.makeText(CustomRegister.this, "User registered successfully, Please Verify Email ID", Toast.LENGTH_LONG).show();
                                //UserLogin();
                                Intent intent7 = new Intent(CustomRegister.this,CustomSignIn.class);
                                intent7.putExtra("email_register",email);
                                startActivity(intent7);
                            }
                            else
                                {
                                Toast.makeText(CustomRegister.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }else{
                    Toast.makeText(CustomRegister.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/


/*
    public void UserLogin() {
        FirebaseAuth L_auth = FirebaseAuth.getInstance();
        popAddLogin = new Dialog(this);
        popAddLogin.setContentView(R.layout.login_pop_up_add_post);
        popAddLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddLogin.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddLogin.getWindow().getAttributes().gravity = Gravity.CENTER;

        // ini popup widgets
        EditText L_email = popAddLogin.findViewById(R.id.popup_email);
        EditText L_password = popAddLogin.findViewById(R.id.popup_password);
        TextView L_login = popAddLogin.findViewById(R.id.popup_login);
        TextView L_close = popAddLogin.findViewById(R.id.popup_close);
        TextView L_forgot = popAddLogin.findViewById(R.id.forgot_password);


        popAddLogin.show();
        L_close.setOnClickListener(v -> {
            L_email.setText("");
            L_password.setText("");
            popAddLogin.dismiss();
        });
        L_forgot.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),ResetPassword.class)));




        L_login.setOnClickListener(v -> {
            String email = L_email.getText().toString().trim();
            String password = L_password .getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                L_email.setError("Type Email ID, Can't be Empty");
                Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(password)){
                L_password.setError("Type Password, Can't be Empty");
                Toast.makeText(this, "Fill all the fields, can't be Empty", Toast.LENGTH_SHORT).show();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                L_email.setError("Email Address has high security, Use another email address to create an account!");
            }
            else {

                L_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(L_auth.getCurrentUser().isEmailVerified()){
                                //Toast.makeText(CustomRegister.this, "Login success", Toast.LENGTH_SHORT).show();
                                popAddLogin.dismiss();
                                //startActivity(new Intent(CustomRegister.this, UserDetails.class));
                                Intent intent3 = new Intent(CustomRegister.this,UserDetails.class);
                                intent3.putExtra("email",email);
                                startActivity(intent3);
                            } else {
                                Toast.makeText(CustomRegister.this, "Please Verify Email... Check Email for verification mail", Toast.LENGTH_LONG).show();
                            }

                        } else {

                            Toast.makeText(CustomRegister.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            popAddLogin.dismiss();
                        }

                    }
                });
            }

        });



    }*/ //the Custom Dialog box for the old way of Sign In
    // then it is changed to the nest CustomSign In activity
    //open the comments to unleash the code for the activity

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}