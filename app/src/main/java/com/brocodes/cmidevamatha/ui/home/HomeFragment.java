package com.brocodes.cmidevamatha.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.brocodes.cmidevamatha.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeFragment extends Fragment {

    ChipNavigationBar chipNavigationBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        chipNavigationBar = (ChipNavigationBar) root.findViewById(R.id.bottom_nav_menu);
        //for default fragment
        chipNavigationBar.setItemSelected(R.id.two,true);
        return root;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //noinspection deprecation
        super.onActivityCreated(savedInstanceState);
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new One1()).commit();
        bottomMenu();
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i){
                case R.id. one:
                    fragment = new One();
                    break;
                case R.id. two:
                    fragment = new One1();
                    break;
                case R.id. three:
                    fragment = new One2();
                    break;
                case R.id. four:
                    fragment = new One3();
                    break;
            }
            if (fragment != null) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}