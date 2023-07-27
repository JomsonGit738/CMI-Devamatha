package com.brocodes.cmidevamatha.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.Admin.InAppMessaging.FcmNotificationsSender;
import com.brocodes.cmidevamatha.DashMain;
import com.brocodes.cmidevamatha.R;
import com.brocodes.cmidevamatha.ui.home.NewsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    //for FCM notification
    Button btn_send_notif;
    EditText notif_title, notif_body;


    //for list_view
    RecyclerView User_Recyclerview;
    ArrayList<User> list;
    UserAdapter userAdapter;


    //Dialog
    Dialog user_add_dialog;
    TextView add_permission;
    EditText ed_add_user_name;
    Button btn_add_permission;

    //news for home page
    Button btn_post_news;
    EditText ed_title, ed_short_desc, ed_message;
    DatabaseReference news_post_ref, user_name_ref, user_add_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        news_post_ref = FirebaseDatabase.getInstance().getReference().child("News");
        user_name_ref = FirebaseDatabase.getInstance().getReference().child("Bulletin");


        btn_send_notif = findViewById(R.id.btn_message_send);
        notif_title = findViewById(R.id.message_title);
        notif_body = findViewById(R.id.message_body);
        ed_title = findViewById(R.id.news_title);
        ed_short_desc = findViewById(R.id.news_short_content);
        ed_message = findViewById(R.id.news_MultiLine);
        btn_post_news = findViewById(R.id.news_post);
        add_permission = findViewById(R.id.add_user_permission);
        User_Recyclerview = findViewById(R.id.user_permission_Recycler_view);

        // RecyclerView for User permission list
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        User_Recyclerview.setLayoutManager(linearLayoutManager1);
        list = new ArrayList<>();
        userAdapter = new UserAdapter(this,list);
        User_Recyclerview.setAdapter(userAdapter);

        user_name_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    list.add(user);
                }
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        // FCM Notification to all the user below 1000
        //
        btn_send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notif_title.getText().toString().isEmpty() && !notif_body.getText().toString().isEmpty()){
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                            notif_title.getText().toString(),notif_body.getText().toString(),getApplicationContext(),Admin.this);
                    notificationsSender.SendNotifications();
                    notif_title.setText("");
                    notif_body.setText("");
                    Toast.makeText(Admin.this, "Message Send successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Admin.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Adding permission to the User
        //
        add_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_add_dialog = new Dialog(Admin.this);
                user_add_dialog.setContentView(R.layout.dialog_user_add_post_row);
                user_add_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                user_add_dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
                ed_add_user_name = user_add_dialog.findViewById(R.id.ed_userName);
                btn_add_permission = user_add_dialog.findViewById(R.id.btn_Permission);
                user_add_dialog.show();

                btn_add_permission.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!ed_add_user_name.getText().toString().isEmpty()){

                            //first splitting the private user id from received names
                            String f_name = ed_add_user_name.getText().toString();
                            String[] split = f_name.split("@");
                            String fs_name = split[0];
                            //to get rest of the part use String fs_name = split[1];


                            User user1 = new User(fs_name);
                            user_name_ref.push().setValue(user1);
                            list.clear();
                            Toast.makeText(Admin.this, "Permission Granted for the User", Toast.LENGTH_LONG).show();
                            user_add_dialog.dismiss();
                        } else {
                            Toast.makeText(Admin.this, "Type User Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        btn_post_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    postNews();
            }
        });
    }

    private void postNews() {
        if (!ed_title.getText().toString().isEmpty() && !ed_message.getText().toString().isEmpty() && !ed_short_desc.getText().toString().isEmpty()) {


        String title = ed_title.getText().toString();
        String desc_short = ed_short_desc.getText().toString();
        String desc_long = ed_message.getText().toString();

        NewsModel newsModel = new NewsModel(title, desc_long);
        news_post_ref.push().setValue(newsModel);

        //notification for all with the Home Message
            FcmNotificationsSender notificationsSender1 = new FcmNotificationsSender("/topics/all",
                    title,desc_short,getApplicationContext(),Admin.this);
            notificationsSender1.SendNotifications();

        Toast.makeText(this, "Message Posted", Toast.LENGTH_SHORT).show();
        ed_title.setText("");
        ed_short_desc.setText("");
        ed_message.setText("");

        } else {
            Toast.makeText(this, "Fill every fields...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
       Intent back_from_admin = new Intent(this, DashMain.class);
       startActivity(back_from_admin);
    }
}