package com.brocodes.cmidevamatha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.brocodes.cmidevamatha.CustomAuth.CustomRegister;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onStart() {
        super.onStart();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        SharedPreferences sp = getSharedPreferences("user_details",MODE_PRIVATE);
        if(sp.contains("name")){
            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
        } else {
            startActivity(new Intent(this, CustomRegister.class));
        }

    }
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}