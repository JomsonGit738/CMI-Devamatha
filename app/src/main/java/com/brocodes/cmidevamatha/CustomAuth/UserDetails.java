package com.brocodes.cmidevamatha.CustomAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brocodes.cmidevamatha.R;
import com.brocodes.cmidevamatha.SplashScreen;
import com.brocodes.cmidevamatha.introduction.Introduction;
import com.brocodes.cmidevamatha.ui.bulletin.Post;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.DocumentTransform;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {
    ImageView L_image;
    EditText L_name;
    TextView Submit,Skip1,Update_msg;
    Uri uri;
    String intent_email,UID;
    DatabaseReference ref3,ref5;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressDialog progressDialog2;
    Button Update;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        L_image = findViewById(R.id.popup_image);
        L_name = findViewById(R.id.popup_name);
        Submit = findViewById(R.id.submit);
        Update = findViewById(R.id.update_user);
        Skip1 = findViewById(R.id.Skip);
        Update_msg = findViewById(R.id.update_message);

        Submit.setVisibility(View.VISIBLE);
        Update_msg.setVisibility(View.INVISIBLE);
        Skip1.setVisibility(View.INVISIBLE);
        Update.setVisibility(View.INVISIBLE);

        intent_email = getIntent().getStringExtra("email8");
        UID = mAuth.getUid();

        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Please Wait...");

        ref5 = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);
        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String global_name = null, global_uid = null, global_url = null, global_email = null;
                if(snapshot.exists()) {
                    progressDialog2.show();
                    Skip1.setVisibility(View.VISIBLE);
                    global_name = snapshot.child("Name").getValue().toString();
                    global_email = snapshot.child("EmailID").getValue().toString();
                    global_url = snapshot.child("PhotoURL").getValue().toString();
                    global_uid = snapshot.child("UserId").getValue().toString();
                    L_name.setText(global_name);
                    Glide.with(getApplicationContext()).load(global_url).into(L_image);
                    //Toast.makeText(this, ""+global_name, Toast.LENGTH_SHORT).show();


                    SharedPreferences sp = getSharedPreferences("user_details", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("name", global_name);
                    ed.putString("email", global_email);
                    ed.putString("url", global_url);
                    ed.putString("uid", global_uid);
                    ed.apply();

                    Update_msg.setVisibility(View.VISIBLE);
                    Submit.setVisibility(View.INVISIBLE);
                    Update.setVisibility(View.VISIBLE);
                    Skip1.setVisibility(View.VISIBLE);
                    progressDialog2.dismiss();
                    Toast.makeText(UserDetails.this, "Hello "+global_name+" you can update now Or Skip...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Skip1.setOnClickListener(v -> {
            startActivity(new Intent(this, Introduction.class));
        });



        L_image.setOnClickListener(v -> ImagePicker.with(UserDetails.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(300)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());
        Submit.setOnClickListener(v -> {
            if(L_name.getText().toString().isEmpty() || L_image.getDrawable() == null){
                Toast.makeText(this, "Make sure!, You have assigned Profile picture & Name", Toast.LENGTH_LONG).show();
            } else {
                progressDialog2.show();
                String Nam = L_name.getText().toString();
                String no = "no_updates";
                UploadDetails(Nam,no);
            }
        });
        Update.setOnClickListener(v -> {
            if(L_image.getDrawable() == null || L_name.getText().toString().isEmpty()){
                Toast.makeText(this, "If you want to Update details, You have to assign Profile picture & Name or Click Skip to Continue", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, ""+L_image.getDrawable().toString(), Toast.LENGTH_SHORT).show();
               /* Intent intel = new Intent(this, Introduction.class);
                startActivity(intel);*/
                progressDialog2.show();
                String Nam = L_name.getText().toString();
                String yes = "updates";
                UploadDetails(Nam,yes);
            }

        });
    }

    private void UploadDetails(String Name, String bool) {

        ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User_images");
        final StorageReference imageFilePath = storageReference.child(uri.getLastPathSegment());
            imageFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(Uri uri) {
                            String image_Download_Link = uri.toString();
                            // create post Object

                            Map newPost = new HashMap();
                            newPost.put("Name", Name);
                            newPost.put("EmailID", intent_email);
                            newPost.put("PhotoURL", image_Download_Link);
                            newPost.put("UserId", UID);
                            newPost.put("TimeStamp", ServerValue.TIMESTAMP);

                            ref3.setValue(newPost);

                            //user details saved only for application fast loading...
                            SharedPreferences sp = getSharedPreferences("user_details", MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("name", Name);
                            ed.putString("email", intent_email);
                            ed.putString("url", image_Download_Link);
                            ed.putString("uid", UID);
                            ed.apply();


                            //button invisible
                            Update_msg.setVisibility(View.INVISIBLE);
                            Submit.setVisibility(View.INVISIBLE);
                            progressDialog2.dismiss();
                            Update.setVisibility(View.INVISIBLE);
                            Skip1.setVisibility(View.VISIBLE);
                            Skip1.setText("Get Started");
                            progressDialog2.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog2.dismiss();
                            Toast.makeText(UserDetails.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        L_image.setImageURI(uri);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}