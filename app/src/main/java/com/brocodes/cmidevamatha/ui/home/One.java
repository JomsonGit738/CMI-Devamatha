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


public class One extends Fragment {

    TextView Date1,birth_day1;
    View v1;
    DatabaseReference oneRef;
    public One() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v1 = inflater.inflate(R.layout.fragment_one, container, false);
        Date1 = (TextView) v1.findViewById(R.id.day_one);
        birth_day1 = (TextView) v1.findViewById(R.id.birthDay_text);
        Date1.setText(getTodayDate());

        oneRef = FirebaseDatabase.getInstance().getReference().child("year").child(getTodayDate());
        oneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CalObject calOb = snapshot1.getValue(CalObject.class);
                    assert calOb != null;
                    String BB = calOb.getBirthday();
                    birth_day1.setText(BB.replace("•","\n•"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), " Your Data-Network Error...", Toast.LENGTH_SHORT).show();
            }
        });


        return v1;
    }
    private String getTodayDate()
    {
        return new SimpleDateFormat( "MMMM"+" "+"dd", Locale.getDefault()).format(new Date());
    }
}