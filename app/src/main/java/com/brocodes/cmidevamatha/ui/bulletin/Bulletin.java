package com.brocodes.cmidevamatha.ui.bulletin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.Admin.InAppMessaging.FcmNotificationsSender;
import com.brocodes.cmidevamatha.DashMain;
import com.brocodes.cmidevamatha.R;
import com.brocodes.cmidevamatha.ui.bulletin.pdf.PdfList;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Bulletin extends AppCompatActivity {
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;

    List<Post> postList;
    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;

    FirebaseDatabase firebaseDatabaseR;
    DatabaseReference databaseReferenceR, user_permission;


    String global_name, global_url, global_uid;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    Dialog popAddPost;
    ImageView popupUserImage,popupPostImage;
    TextView popupTitle,popupDescription,popupAddBtn,close_add_post,posting_user;
    ProgressBar popupClickProgress;
    private Uri pickedImgUri = null;
    Uri compressUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab1);
        fab.hide();

        //Keep screen on flag
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        postRecyclerView = findViewById(R.id.postRv);
        /////////////////////////////////////
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postRecyclerView.setLayoutManager(linearLayoutManager);
        /////////////////////////////////////
        //postRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //postRecyclerView.setHasFixedSize(true);

        firebaseDatabaseR = FirebaseDatabase.getInstance();
        databaseReferenceR = firebaseDatabaseR.getReference("Posts");
        user_permission = firebaseDatabaseR.getReference("Bulletin");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if(sp.contains("name")){
            global_name = sp.getString("name","");
            global_url = sp.getString("url","");
            global_uid = sp.getString("uid","");
            //Email.setText(sp.getString("email",""));
            //Glide.with(getContext()).load(sp.getString("url","")).into(imageView);
            //Toast.makeText(getContext(), ""+sp.getString("name",""), Toast.LENGTH_SHORT).show();
        }




        //////////////////////////////////////////////////////////////////
        databaseReferenceR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Post post = postsnap.getValue(Post.class);
                    //String name = postsnap.child("name").getValue().toString();
                    postList.add(post);
                    //Log.i("jomzM",name);


                }

                postAdapter = new PostAdapter(getApplicationContext(),postList);
                postRecyclerView.setAdapter(postAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /////////////////////////////////////////////////////////////////


        iniPopup();
        setupPopupImageClick();

        //fab.hide() for the unauthorized user and permission denied
         user_permission.orderByChild("user").equalTo(global_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    fab.show();
                    //Toast.makeText(Bulletin.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddPost.show();
            }
        });

        FloatingActionButton fab_back= (FloatingActionButton) findViewById(R.id.fab2);
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_back = new Intent(Bulletin.this, DashMain.class);
                startActivity(int_back);
            }
        });
        FloatingActionButton fab_pdf= (FloatingActionButton) findViewById(R.id.fab_3);
        fab_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_pdf = new Intent(Bulletin.this, PdfList.class);
                startActivity(int_pdf);
            }
        });




    }

    private void setupPopupImageClick() {
        popupPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...

                checkAndRequestForPermission();


            }
        });
    }
    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Bulletin.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Bulletin.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent,2);

                Toast.makeText(Bulletin.this,"Click Permissions and allow STORAGE permissions",Toast.LENGTH_LONG).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Bulletin.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }
    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
        //ACTION_GET_CONTENT is capturing from camera uploading
        //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);

                                /////// Set CAMERA HERE


        //galleryIntent.setType("image/*");
        //startActivityForResult(galleryIntent,REQUESCODE);
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(galleryIntent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent,"select"),REQUESCODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            Uri datapath = data.getData();
            try{


                RotateBitmap rotateBitmap = new RotateBitmap();
                Bitmap bitmap = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(),datapath);
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),datapath);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
                pickedImgUri = Uri.parse(path);
                popupPostImage.setImageURI(pickedImgUri);

            } catch (Exception e) {
                e.printStackTrace();

            }


            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            //pickedImgUri = data.getData();
            //popupPostImage.setImageURI(pickedImgUri);



        }


    }
    @SuppressLint("SetTextI18n")
    private void iniPopup() {
        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.bulletin_pop_up_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        posting_user = popAddPost.findViewById(R.id.post_user_name);
        popupUserImage = popAddPost.findViewById(R.id.popup_user_image);
        popupPostImage = popAddPost.findViewById(R.id.popup_img);
        popupTitle = popAddPost.findViewById(R.id.popup_title);
        popupDescription = popAddPost.findViewById(R.id.popup_description);
        popupAddBtn = popAddPost.findViewById(R.id.popup_add);
        close_add_post = popAddPost.findViewById(R.id.close_pop_up);
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar);
        close_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddPost.dismiss();
                popupTitle.setText("");
                popupDescription.setText("");
                popupPostImage.setImageResource(R.drawable.picture);

            }
        });
        // load Current user profile photo
        posting_user.setText("Posting by "+global_name);
        Glide.with(Bulletin.this).load(global_url).into(popupUserImage);
        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                if (!popupTitle.getText().toString().isEmpty()
                        && !popupDescription.getText().toString().isEmpty()
                        && pickedImgUri != null ) {
                    //////////////////////////////////////////////////////////////////
                    //firebase special configure
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Post_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownlaodLink = uri.toString();
                                    // create post Object
                                    Post post = new Post(popupTitle.getText().toString(),
                                            popupDescription.getText().toString(),
                                            imageDownlaodLink,
                                            global_uid,
                                            global_url,global_name);

                                    // Add post to firebase database

                                    addPost(post);


                                    String all = global_name+" posted "+popupTitle.getText().toString()+" in Devamatha Bulletin";
                                    //sending notifications on bulletin posting
                                    FcmNotificationsSender notificationsSender2 = new FcmNotificationsSender("/topics/all",
                                            "Devamatha Bulletin",all,getApplicationContext(), Bulletin.this);
                                    notificationsSender2.SendNotifications();

                                    popupTitle.setText("");
                                    popupDescription.setText("");




                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture

                                    showMessage(e.getMessage());
                                    popupClickProgress.setVisibility(View.INVISIBLE);
                                    popupAddBtn.setVisibility(View.VISIBLE);



                                }
                            });


                        }
                    });

                    /////////////////////////////////////////////////////////////////
                }
                else{
                    showMessage("Please verify all input fields and choose Post Image") ;
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);
                }


            }
        });
    }
    private void addPost(Post post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        // get post unique ID and update post key
        String key = myRef.getKey();
        post.setPostKey(key);


        // add post data to firebase database

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added successfully");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddPost.dismiss();
            }
        });





    }
    private void showMessage(String message) {

        Toast.makeText(Bulletin.this,message,Toast.LENGTH_LONG).show();

    }
    @Override
    public void onBackPressed() {
        Intent ini = new Intent(this, DashMain.class);
        startActivity(ini);
    }
}