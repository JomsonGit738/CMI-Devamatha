package com.brocodes.cmidevamatha.ui.administration;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.brocodes.cmidevamatha.R;


public class AdministrationFragment extends Fragment {
  RelativeLayout relative_admin1,relative_admin2,relative_admin3,relative_admin4,relative_admin5;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v11 = inflater.inflate(R.layout.fragment_administration, container, false);
        relative_admin1 = v11.findViewById(R.id.relative_devamatha);
        relative_admin2 = v11.findViewById(R.id.relative_dhule);
        relative_admin3 = v11.findViewById(R.id.relative_thomas);
        relative_admin4 = v11.findViewById(R.id.relative_madagaskar);
        relative_admin5 = v11.findViewById(R.id.relative_provincials_list);

        relative_admin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "hello ou touched", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getContext(),AdminListView.class);
                i1.putExtra("list_name",1);
                startActivity(i1);
            }
        });
        relative_admin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "hello ou touched", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getContext(),AdminListView.class);
                i2.putExtra("list_name",2);
                startActivity(i2);
            }
        });
        relative_admin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "hello ou touched", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getContext(),AdminListView.class);
                i3.putExtra("list_name",3);
                startActivity(i3);
            }
        });
        relative_admin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "hello ou touched", Toast.LENGTH_SHORT).show();
                Intent i4 = new Intent(getContext(),AdminListView.class);
                i4.putExtra("list_name",4);
                startActivity(i4);
            }
        });
        relative_admin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "hello ou touched", Toast.LENGTH_SHORT).show();
                Intent i5 = new Intent(getContext(),AdminListView.class);
                i5.putExtra("list_name",5);
                startActivity(i5);
            }
        });
        return v11;
    }

}