package com.brocodes.cmidevamatha.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.brocodes.cmidevamatha.R;
import com.brocodes.cmidevamatha.ui.calendar.CalObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class One3 extends Fragment {


    View v4;
    TextView Date3,obituary_day3;
    DatabaseReference oneRef2;
    public One3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v4 = inflater.inflate(R.layout.fragment_one3, container, false);
        Date3 = (TextView) v4.findViewById(R.id.day_one_three);
        obituary_day3 = (TextView) v4.findViewById(R.id.obituary_text);
        Date3.setText(getTodayDate());

        oneRef2 = FirebaseDatabase.getInstance().getReference().child("year").child(getTodayDate());
        oneRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CalObject calOb = snapshot1.getValue(CalObject.class);
                    assert calOb != null;
                    String DD = calOb.getCmiobituary();
                    obituary_day3.setText(DD.replace("•","\n•"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), " Your Data-Network Error...", Toast.LENGTH_SHORT).show();
            }
        });

        return v4;
    }
    private String getTodayDate()
    {
        return new SimpleDateFormat( "MMMM"+" "+"dd", Locale.getDefault()).format(new Date());
    }
}