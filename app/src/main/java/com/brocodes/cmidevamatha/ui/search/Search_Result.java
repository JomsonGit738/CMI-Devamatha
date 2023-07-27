package com.brocodes.cmidevamatha.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.brocodes.cmidevamatha.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_Result extends AppCompatActivity {
    String Call_number;
    String Map_address;
    String address;
    String url_image;
    String image_one;
    String desc;
    ImageView Im;
    TextView t1,t2,t3,t4;
    FloatingActionButton floatButton_back,floatButton_call,floatButton_map;
    FOject fOject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////////////////////
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        //////////////////////////

        setContentView(R.layout.activity_search__result);

        t1= findViewById(R.id.result_name);
        Im = findViewById(R.id.result_image);
        t2 = findViewById(R.id.result_para1);
        t3 = findViewById(R.id.result_more);
        t4 = findViewById(R.id.result_address);





        String s_name = getIntent().getStringExtra("search_name");
        t1.setText(s_name);

        url_image = getIntent().getStringExtra("search_url");
        if(url_image == null){
            Toast.makeText(this, "No Internet Connection, Error in Loading", Toast.LENGTH_LONG).show();
        }
        Glide.with(this).load(url_image).into(Im);
        /*String I_name = getIntent().getStringExtra("Url_image");
        Glide.with(getApplicationContext()).load(I_name).into(Im);*/




        floatButton_back = findViewById(R.id.float_btn_back);
        floatButton_call = findViewById(R.id.float_btn_call);
        floatButton_map = findViewById(R.id.float_btn_googleMap);

        floatButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(getApplicationContext(),SearchMain.class);
                startActivity(goBack);
            }
        });

        //Map<String,Object> data = (Map<String,Object>) snapshot.getValue();
        //Log.d("Godblessyou","name = "+data.get("houseimage"));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("houses").child(s_name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    FOject jis = snapshot1.getValue(FOject.class);
                    assert jis != null;
                    String imageURL1 = jis.getHouseimage();
                    if(url_image == null){
                        Glide.with(getApplicationContext()).load(imageURL1).into(Im);
                    }
                    String details = jis.getHousedetails();
                    t2.setText(details);
                    String more = "For More details follow Google Map...!";
                    t3.setText(more);
                    String address = jis.getHouseaddress();
                    t4.setText(address);
                    Map_address = jis.getHousemap();

                    Call_number = jis.getHousenumber();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
            Query query_fire = FirebaseDatabase.getInstance().getReference("houses").child(s_name);
        /* Query query_fire = FirebaseDatabase.getInstance().getReference("houses")
                .orderByChild("name")
                .equalTo(s_name);

        query_fire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Log.i("fireBBBB",String.valueOf(snapshot.getValue()));

                        image_one = (String) snapshot.child("houseimage").getValue();
                        Glide.with(getApplicationContext()).load(image_one).into(Im);
                        desc = (String) snapshot.child("housedetails").getValue();
                        t2.setText(desc);
                        String more = "for More details check whether your Internet is ON or NOT and follow the Google Map Link...!";
                        t3.setText(more);
                        address = (String) snapshot.child("houseaddress").getValue();
                        t4.setText(address);
                        Map_address = (String) snapshot.child("housemap").getValue();

                        Call_number = (String) snapshot.child("housenumber").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Search_Result.this, "Error Reported, Check INTERNET connection...", Toast.LENGTH_LONG).show();
            }
        });
        */

        floatButton_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maps = new Intent(Intent.ACTION_VIEW);
                maps.setData(Uri.parse(Map_address));
                startActivity(maps);
            }
        });
        floatButton_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calls = new Intent(Intent.ACTION_DIAL);
                calls.setData(Uri.parse("tel:"+ Call_number));
                startActivity(calls);

            }
        });

    }


}