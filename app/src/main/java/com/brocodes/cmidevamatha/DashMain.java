package com.brocodes.cmidevamatha;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.brocodes.cmidevamatha.Admin.Admin;
import com.brocodes.cmidevamatha.ui.bulletin.Bulletin;
import com.brocodes.cmidevamatha.ui.search.SearchMain;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;
import java.util.Map;

public class DashMain extends AppCompatActivity {
    Dialog adminVerify;
    TextView admin_title,btn_go_back;
    EditText admin_password;
    Button btn_verify;

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;

    FirebaseAuth mAuth1;
    FirebaseUser currentUser;
    DatabaseReference newUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_main);
        mAuth1 = FirebaseAuth.getInstance();
        currentUser = mAuth1.getCurrentUser();

        if (!isConnectedNetwork()){
            Alerter.create(DashMain.this)
                    .setTitle("No Internet Access...")
                    .setText("Features may not work Properly...")
                    .setIcon(R.drawable.ic_wifi_no_signal)
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.alert_red)
                    .enableSwipeToDismiss()
                    .show();
        }







        ////////////////////////////sharedpreference///////////////////////////////////
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);

        //////////////////////////////////////////////////////////////////////////////




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //Firebase user authentication initialisation
        mAuth1 = FirebaseAuth.getInstance();
        currentUser = mAuth1.getCurrentUser();
        //
        /////////////SHPREFS///////////
        if(firstStart){
            showStartDialog();
        }
        /////////////////////////////SHPREFS///




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_administration, R.id.nav_calendar,R.id.nav_bulletin,R.id.nav_houses,R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_houses:
                         if (!isConnectedNetwork()){
                            Alerter.create(DashMain.this)
                                    .setTitle("No Internet Access...")
                                    .setText("Please allow internet Access")
                                    .setIcon(R.drawable.ic_wifi_no_signal)
                                    .setDuration(3000)
                                    .setBackgroundColorRes(R.color.alert_red)
                                    .enableSwipeToDismiss()
                                    .show();
                            //Toast.makeText(this, "NO internet access", Toast.LENGTH_SHORT).show();
                        }else {
                             startActivity(new Intent(DashMain.this, SearchMain.class));
                             break;
                         }
                    case R.id.nav_bulletin:
                        if (!isConnectedNetwork()){
                            Alerter.create(DashMain.this)
                                    .setTitle("No Internet Access...")
                                    .setText("Please allow internet Access")
                                    .setIcon(R.drawable.ic_wifi_no_signal)
                                    .setDuration(3000)
                                    .setBackgroundColorRes(R.color.alert_red)
                                    .enableSwipeToDismiss()
                                    .show();
                            //Toast.makeText(this, "NO internet access", Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(DashMain.this, Bulletin.class));
                            break;
                        }

                    case R.id.nav_home:
                        navController.popBackStack(R.id.mobile_navigation,true);
                        Navigation.findNavController(DashMain.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                        break;
                    case R.id.nav_administration:
                        navController.popBackStack(R.id.mobile_navigation,true);
                        Navigation.findNavController(DashMain.this,R.id.nav_host_fragment).navigate(R.id.nav_administration);
                        break;
                    case R.id.nav_calendar:
                       navController.popBackStack(R.id.mobile_navigation,true);
                       Navigation.findNavController(DashMain.this,R.id.nav_host_fragment).navigate(R.id.nav_calendar);
                       break;
                    case R.id.nav_profile:
                        navController.popBackStack(R.id.mobile_navigation,true);
                        Navigation.findNavController(DashMain.this,R.id.nav_host_fragment).navigate(R.id.nav_profile);
                        break;
                }
                return false;
            }
        });

        //////////////

        Update_Navigation_Header();

    }



    ////////////////////////////////////////internet checking////////////////////////////////////
    private boolean isConnectedNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!= null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    //////////////////////////////////////////////////////////////////////////internet checking//////



    /////////////////////////////////////////////////////////////////////////////////////////
    private void showStartDialog(){

        SharedPreferences sp = getSharedPreferences("user_details",MODE_PRIVATE);
        if(sp.contains("name")) {
            String nameStart = sp.getString("name", "");
            Alerter.create(DashMain.this)
                    .setTitle("Welcome " + nameStart)
                    .setText("You are connected to Devamatha Application")
                    .setIcon(R.drawable.alerter_ic_face)
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.purple_500)
                    .enableSwipeToDismiss()
                    .show();
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_img_main){
            Intent about = new Intent(this,About.class);
            startActivity(about);
            return true;
        }
        else if(id == R.id.action_admin)
        {

            adminVerify = new Dialog(this);
            adminVerify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            adminVerify.setContentView(R.layout.row_admin_password);
            admin_title = adminVerify.findViewById(R.id.admin_text_view);
            admin_password = adminVerify.findViewById(R.id.password_Text);
            btn_verify = adminVerify.findViewById(R.id.verify_button);
            btn_go_back = adminVerify.findViewById(R.id.go_back_button);

            adminVerify.show();

            btn_go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adminVerify.dismiss();
                }
            });
            btn_verify.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if(!admin_password.getText().toString().isEmpty()){
                        String pass = admin_password.getText().toString();
                        if(pass.equals("Dchavara")){
                            Toast.makeText(DashMain.this, "Welcome Back Admin", Toast.LENGTH_SHORT).show();
                            Intent admin = new Intent(getApplicationContext(), Admin.class);
                            startActivity(admin);
                        } else {
                            admin_password.setText("");
                            admin_title.setText("Wrong Password");
                        }
                    } else {
                        Toast.makeText(DashMain.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Intent admin = new Intent(this, Admin.class);
            //startActivity(admin);
            //Toast.makeText(this, "Settings are in DEMO mode", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_permission){
            //Toast.makeText(this, "you are taking permissions for storage options", Toast.LENGTH_SHORT).show();
            TakePermissions();
        }
        return super.onOptionsItemSelected(item);
    }

    public void TakePermissions(){
        if(isPermissionGranted()){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
        } else {
            takePermission();
        }

    }
    private boolean isPermissionGranted(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int readExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void takePermission(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent,108);
                Toast.makeText(this,"Click Permissions and allow STORAGE permissions",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                startActivityForResult(intent,108);
                Toast.makeText(this,"Click Permissions and allow STORAGE permissions",Toast.LENGTH_LONG).show();
            }
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},107);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 108){
                if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                    if(Environment.isExternalStorageManager()){
                        Toast.makeText(this, "Permission Granted in Android 11", Toast.LENGTH_LONG).show();
                    } else {
                        takePermission();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0){
            if(requestCode == 107){
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if(readExternalStorage){
                    Toast.makeText(this, "Permission granted in android 10 and below", Toast.LENGTH_LONG).show();
                } else {
                    takePermission();
                }
            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void Update_Navigation_Header(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView Nav_D_user_name = headerView.findViewById(R.id.nav_d_user_name);
        TextView Nav_D_user_email = headerView.findViewById(R.id.nav_d_user_email);
        ImageView Nav_D_user_photo = headerView.findViewById(R.id.nav_d_user_photo);

        SharedPreferences sp = getSharedPreferences("user_details",MODE_PRIVATE);
        if(sp.contains("name")){
            Nav_D_user_name.setText(sp.getString("name",""));
            Nav_D_user_email.setText(sp.getString("email",""));
            Glide.with(this).load(sp.getString("url","")).into(Nav_D_user_photo);
            //Toast.makeText(getContext(), ""+sp.getString("name",""), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}