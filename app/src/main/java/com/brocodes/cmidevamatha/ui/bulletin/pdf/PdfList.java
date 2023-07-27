package com.brocodes.cmidevamatha.ui.bulletin.pdf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.brocodes.cmidevamatha.R;
import com.brocodes.cmidevamatha.ui.bulletin.Bulletin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfList extends AppCompatActivity {
    Uri uri_intent;
    ListView listView;
    DatabaseReference databaseRef_dev;
    List<String> title_list,url_list;
    ArrayAdapter<String> adapter_pdf;
    Pdf_c pdf_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);

        listView = findViewById(R.id.list_pdf_view);
        databaseRef_dev = FirebaseDatabase.getInstance().getReference("Pdf");

        pdf_c = new Pdf_c();
        title_list = new ArrayList<>();
        url_list = new ArrayList<>();
        adapter_pdf =new ArrayAdapter<>(this,R.layout.row_pdf_item,R.id.row_pdf_name,title_list);

        databaseRef_dev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds :snapshot.getChildren()){
                    pdf_c =ds.getValue(Pdf_c.class);
                    assert pdf_c != null;
                    title_list.add(pdf_c.getTitle());
                    url_list.add(pdf_c.getUrl());
                }
                listView.setAdapter(adapter_pdf);
                listView.setOnItemClickListener((adapterView, view, position, l) -> {
                    uri_intent = Uri.parse(url_list.get(position));
                    startActivity(new Intent(Intent.ACTION_VIEW,uri_intent));
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_back_one);
        floatingActionButton.setOnClickListener(v -> {
            Intent going_back = new Intent(PdfList.this, Bulletin.class);
            startActivity(going_back);
            finish();
        });
    }
}