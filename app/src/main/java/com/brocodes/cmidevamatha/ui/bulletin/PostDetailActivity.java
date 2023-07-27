package com.brocodes.cmidevamatha.ui.bulletin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.brocodes.cmidevamatha.R;
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
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {

    Handler MainHandler = new Handler();
    ProgressDialog progressDialog;
    Dialog pdf_name_dialog;

    String global_name,global_url,global_uid;
    ImageView imgPost,imgUserPost,imgCurrentUser;
    ImageView postLikes;
    TextView txtPostDesc,txtPostDateName,txtPostTitle;
    TextView likes_total;
    EditText editTextComment;
    Button btnAddComment;
    String PostKey;
    String PDF_Name, posted_user_name;
    String postTitle, postImage, postDescription, date;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comment" ;
    LottieAnimationView lbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        SharedPreferences sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if(sp.contains("name")){
            global_name = sp.getString("name","");
            global_url = sp.getString("url","");
            global_uid = sp.getString("uid","");
            //Email.setText(sp.getString("email",""));
            //Glide.with(getContext()).load(sp.getString("url","")).into(imageView);
            //Toast.makeText(getContext(), ""+sp.getString("name",""), Toast.LENGTH_SHORT).show();
        }


        // ini Views
        RvComment = findViewById(R.id.rv_comment);

            //separate in defining because of the image multiple usage in pdf creation
            imgPost =findViewById(R.id.post_detail_img);
            postImage = getIntent().getExtras().getString("postImage") ;
            Glide.with(this).load(postImage).into(imgPost);


        imgUserPost = findViewById(R.id.post_detail_user_img);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

        postLikes = findViewById(R.id.post_liker);

        likes_total= findViewById(R.id.likes);

            //separate in defining because of the title multiple usage in pdf creation
            txtPostTitle = findViewById(R.id.post_detail_title);
            postTitle = getIntent().getExtras().getString("title");
            txtPostTitle.setText(postTitle);

            //separate in defining because of the title multiple usage in pdf creation
            txtPostDesc = findViewById(R.id.post_detail_desc);
            postDescription = getIntent().getExtras().getString("description");
            txtPostDesc.setText(postDescription);


        txtPostDateName = findViewById(R.id.post_detail_date_name);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);

        lbtn = findViewById(R.id.lottie);






        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab21);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_int = new Intent(PostDetailActivity.this,Bulletin.class);
                startActivity(back_int);
            }
        });
        // add Comment button click listner

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextComment.getText().toString().isEmpty()) {
                    btnAddComment.setVisibility(View.INVISIBLE);

                    DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                    String comment_content = editTextComment.getText().toString();
                    String uid = global_uid;
                    String uname = global_name;
                    String uimg = global_url;

                    Comment comment = new Comment(comment_content, uid, uimg, uname);

                    commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("comment added");
                            editTextComment.setText("");

                            btnAddComment.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("fail to add comment : " + e.getMessage());
                        }
                    });
                } else {
//                    Toast.makeText(PostDetailActivity.this, "Type the Comment...", Toast.LENGTH_SHORT).show();
                    //creating pdf here when comment field is empty
                    pdf_name_dialog = new Dialog(PostDetailActivity.this);
                    pdf_name_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    pdf_name_dialog.setContentView(R.layout.custom_dialog_pdf);
                    EditText Pdf_name_given = pdf_name_dialog.findViewById(R.id.edit_pdf_name);
                    TextView txt_cancel = pdf_name_dialog.findViewById(R.id.txt_pdf_cancel);
                    Button btn_ok = pdf_name_dialog.findViewById(R.id.btn_pdf_create);


                    txt_cancel.setOnClickListener(v1 -> {
                        pdf_name_dialog.cancel();
                        Toast.makeText(PostDetailActivity.this, "PDF is not generated", Toast.LENGTH_SHORT).show();
                    });

                    btn_ok.setOnClickListener(v12 -> {
                        if(Pdf_name_given.getText().toString().isEmpty()){
                            Toast.makeText(PostDetailActivity.this, "Type PDF Name", Toast.LENGTH_SHORT).show();
                        } else {
                            pdf_name_dialog.dismiss();
                            try {
                                //Toast.makeText(PostDetailActivity.this, "PDF creating...", Toast.LENGTH_SHORT).show();
                                PDF_Name = Pdf_name_given.getText().toString();
                                CreatePDF();
                            } catch (Exception e) {
                                Toast.makeText(PostDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        //Toast.makeText(PostDetailActivity.this, "you are ok for creating PDF", Toast.LENGTH_SHORT).show();
                    });

                    pdf_name_dialog.show();

                }



            }
        });


        // now we need to bind all data into those views
        // firt we need to get post data
        // we need to send post detail data to this activity first ...
        // now we can get post data


        String userpostImage = getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userpostImage).into(imgUserPost);

        posted_user_name = "nill";



        // setcomment user image

        Glide.with(this).load(global_url).into(imgCurrentUser);
        // get post id
        PostKey = getIntent().getExtras().getString("postKey");
        Log.i("hello", PostKey);
        Log.i("hello", firebaseUser.getUid());

        //time stamp conversion
        date = timestampToString(getIntent().getExtras().getLong("postDate"));
        txtPostDateName.setText(date);

        //passing arguments, PostKey for likes
        isLikes(PostKey);
        nrLikes(PostKey);

        postLikes.setOnClickListener(v -> {

            if (postLikes.getTag().equals("Like1"))
            {
                firebaseDatabase.getReference().child("Likes").child(PostKey).child(firebaseUser.getUid()).setValue(true);
                postLikes.setImageResource(R.drawable.ic_liked1);
                lbtn.playAnimation();

            }
            else
                {
                firebaseDatabase.getReference().child("Likes").child(PostKey).child(firebaseUser.getUid()).removeValue();
                postLikes.setImageResource(R.drawable.ic_like1);

                }
        });


        // ini Recyclerview Comment
        iniRvComment();


    }
    private void CreatePDF() throws FileNotFoundException {
        new FetchImage(postImage).start();
       /* Log.i("jomz2", "reached here");
        String pdfPath = null;
        pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"myasecondPDF.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        Log.i("jomz3", "reached here");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        try {
            Log.i("jomz1", "reached here");
            URL url = new URL("https://i.imgur.com/x4nYkym.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream Instr = connection.getInputStream();
            Bitmap bitm = BitmapFactory.decodeStream(Instr);
            //Drawable d = getDrawable(R.drawable.k5);
            //Bitmap bitm = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitm.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] bitmapData = stream.toByteArray();
            Log.i("jomz", "reached here");
            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData);
            document.add(image);

        } catch (Exception e) {
            e.printStackTrace();
        }



        document.close();
        Toast.makeText(this, "pdf created in downloads", Toast.LENGTH_LONG).show();
*/
    }

    class FetchImage extends Thread{
        String url;
        Bitmap bitm;

        FetchImage(String url){

            this.url = url;

        }

        @Override
        public void run() {
            MainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(PostDetailActivity.this);
                    progressDialog.setMessage("Creating PDF...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            //adding codes

            try {

                String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File file = new File(pdfPath,PDF_Name+".pdf");
                FileOutputStream outputStream = new FileOutputStream(file);
                Log.i("jomz3", "reached here");
                PdfWriter writer = new PdfWriter(file);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                pdfDocument.setDefaultPageSize(PageSize.A4);

                //extra code
                URL urlre = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlre.openConnection();
                InputStream Instr = connection.getInputStream();
                bitm = BitmapFactory.decodeStream(Instr);

                //InputStream inputStream = new java.net.URL(url).openStream();
                //bitm = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitm.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bitmapData = stream.toByteArray();
                Log.i("jomz", "reached here");
                ImageData imageData = ImageDataFactory.create(bitmapData);
                Image image = new Image(imageData);
                image.setAutoScaleWidth(true);
                image.setAutoScaleHeight(true);
                document.add(image);

                Paragraph paragraph_title = new Paragraph(postTitle).setBold().setFontSize(23).setTextAlignment(TextAlignment.LEFT);
                Paragraph paragraph_details = new Paragraph("Posted by "+global_name+", on "+date).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                Paragraph paragraph_content = new Paragraph(postDescription).setFontSize(15).setTextAlignment(TextAlignment.JUSTIFIED);


                document.add(paragraph_title);
                document.add(paragraph_details);
                document.add(paragraph_content);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //adding codes

            MainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(PostDetailActivity.this, "PDF created and Saved in Downloads", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void isLikes(String postKey){
        FirebaseUser firebaseUser33 = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference33 = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postKey);
        reference33.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser33.getUid()).exists()){
                    postLikes.setImageResource(R.drawable.ic_liked1);
                    postLikes.setTag("Liked1");
                    }
                else{
                    postLikes.setImageResource(R.drawable.ic_like1);
                    postLikes.setTag("Like1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nrLikes(String postKey){
        DatabaseReference reference22 = FirebaseDatabase.getInstance().getReference().child("Likes").child(postKey);
        reference22.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes_total.setText(snapshot.getChildrenCount()+" Likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }

    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;


    }

}