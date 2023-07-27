package com.brocodes.cmidevamatha.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.brocodes.cmidevamatha.DashMain;
import com.brocodes.cmidevamatha.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

public class News_Result extends AppCompatActivity {
TextView news_title,news_details,news_date_format;
FloatingActionButton floatButton_back_news,floatButton_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_news__result);
        /////////////////
        ////////////////
        news_title = findViewById(R.id.news_name);
        news_details = findViewById(R.id.details_result);
        news_date_format = findViewById(R.id.news_date);

        floatButton_back_news = findViewById(R.id.float_btn_back_news);
        floatButton_share = findViewById(R.id.float_btn_share);

        String N_title_name = getIntent().getStringExtra("title");
        String N_desc = getIntent().getStringExtra("desc");
        //Long N_date = (Long) getIntent().getLongExtra("timeStamp2",0);

        //time stamp conversion
        String N_date = timestampToString(getIntent().getExtras().getLong("timeStamp2"));
        news_date_format.setText(N_date);
        //Log.i("datefrom",N_date);




        news_title.setText(N_title_name);
        news_details.setText(N_desc);

        floatButton_back_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack_news = new Intent(getApplicationContext(), DashMain.class);
                startActivity(goBack_news);
            }
        });
        floatButton_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT,N_title_name +" : "+ N_desc);
                shareintent.setType("text/plain");
                startActivity(shareintent);
            }
        });

    }
    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy",calendar).toString();


    }
}