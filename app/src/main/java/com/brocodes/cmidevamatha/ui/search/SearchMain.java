package com.brocodes.cmidevamatha.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.DashMain;
import com.brocodes.cmidevamatha.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchMain extends AppCompatActivity {

    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Search Here...");

        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {
        exampleList.clear();

        //Devamatha province houses
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Amala Nagar - Amala Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Azhikode - Marthoma Ashramam"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Calicut - CMI Deepthi Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Chalakudy - Carmel Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Chalakudy - CMI Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Chiyyaram - Galilee CMI centre"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Elthuruth - St Mary’s Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Irinjalakuda - Christ Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Kadalundy - Calvary Hills"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Kormala - Carmelgiri Ashram"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Kottackal, Mala - St Teresa’s Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Kunnamkulam - St George Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Mannuthy - KESS Centre"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Palakkad - Paalana CMI Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Pangarappilly - Sanjo Sadan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Pavaratty - St Thomas Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Pullur - St Xavier’s Carmel Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Sevanagiri - Sevanalaya"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Snehagiri CMI Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Thalore - Infant Jesus Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Thalore - Jerusalem Retreat Centre"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Thangaloor - Chavara Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_dev, "Thrissur - Devamatha Provincial House"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Varandarappilly - Immaculate Heart Monastery"));
        exampleList.add(new ExampleItem(R.drawable.search_dev,  "Bangalore - Christ Academy"));

        //Dhule Region houses
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Dhule - CMI Ashram"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Chalisgaon - Chavara Sadan"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Dhule - Chavara Nivas"));
        exampleList.add(new ExampleItem(R.drawable.search_cha, "Chopda - Chavara Sevashram"));
        exampleList.add(new ExampleItem(R.drawable.search_cha, "Dadgaon - Chavara Sevagram"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Amalner - Chavara Ashram"));
        exampleList.add(new ExampleItem(R.drawable.search_cha, "Jalgaon - Yesu Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Mumbai - Jyoti Nivas"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Nandurbar - Chavara Nivas"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Navi Mumbai - Anugrah"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Shahada - Chavara Bhavan"));
        exampleList.add(new ExampleItem(R.drawable.search_cha,  "Thalwada - Sarsi Sadan"));

        //Africa Region houses
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Syokimau - St Thomas Regional House"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Mbiuni - St Joseph Parish"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Ngunga - Holy Spirit Parish"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Donyo Sabuk - St Joseph the Worker Parish & Seminary"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Karaba - St Teresa’s Parish & Novitiate"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Makutano - Retreat Centre & School"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Maktau - St Chavara Parish"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Kibiko - St Chavara CMI Primary School"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Kibiko - CMI Huduma Reha Centre"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Karen - St Chavara CMI Study House"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Kamulu - CMI Christ Academy School"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Manyire - St Thomas Parish"));
        exampleList.add(new ExampleItem(R.drawable.search_tho,  "Nambala - St Joseph the Worker Parish"));

        //Madagaskar sub-Region houses
        exampleList.add(new ExampleItem(R.drawable.search_the, "Mahabo - Mission Station"));
        exampleList.add(new ExampleItem(R.drawable.search_the, "Ankilizato - Mission Station"));
        exampleList.add(new ExampleItem(R.drawable.search_the,  "Tsarahasina, Antananarivo - Mission Station"));
        exampleList.add(new ExampleItem(R.drawable.search_the, "Antsirabe - CMI Mission Station"));

    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_questions);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DashMain.class));
    }
}