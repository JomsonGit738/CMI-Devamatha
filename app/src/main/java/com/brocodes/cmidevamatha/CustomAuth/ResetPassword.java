package com.brocodes.cmidevamatha.CustomAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brocodes.cmidevamatha.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    Button reset, go_back;
    EditText reg_email;
    TextView mail;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.message_mail);
        mail.setVisibility(View.INVISIBLE);
        reg_email = findViewById(R.id.registered_email);
        reset = findViewById(R.id.reset);
        go_back = findViewById(R.id.go_back_login);


        reset.setOnClickListener(v -> {
            String email = reg_email.getText().toString().trim();
           if(email.isEmpty()){
                    reg_email.setError("Email is required");
           } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    reg_email.setError("Please provide a Valid email");
           } else {
               mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       mail.setVisibility(View.VISIBLE);
                       Toast.makeText(ResetPassword.this, "Reset Mail sent, you will receive a link", Toast.LENGTH_LONG).show();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(ResetPassword.this, "Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });
           }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, CustomRegister.class));
            }
        });

    }
}