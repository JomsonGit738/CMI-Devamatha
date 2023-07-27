package com.brocodes.cmidevamatha.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import com.brocodes.cmidevamatha.MainActivity;
import com.brocodes.cmidevamatha.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class SignOut extends Fragment {

    ImageView imageView;
    String copy_name;
    TextView Email, Name, id_copy,Rate;
    Button btn_Sign_out;
    FirebaseAuth mAuth1;
    FirebaseUser mUser1;
    LottieAnimationView lottieAnimationView;



    public SignOut() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v10 = inflater.inflate(R.layout.fragment_sign_out, container, false);
        mAuth1 = FirebaseAuth.getInstance();
        mUser1 = mAuth1.getCurrentUser();


        Rate = v10.findViewById(R.id.rate);
        lottieAnimationView = v10.findViewById(R.id.copy_id_animation);
        imageView =v10.findViewById(R.id.user_photo_sign_out);
        Email = v10.findViewById(R.id.user_email_sign_out);
        Name = v10.findViewById(R.id.user_name_sign_out);
        btn_Sign_out = v10.findViewById(R.id.user_sign_out_btn);
        id_copy = v10.findViewById(R.id.copy_id);

        //shared preferences called from UserDetailed Activity
        SharedPreferences sp = getActivity().getSharedPreferences("user_details",Context.MODE_PRIVATE);
        if(sp.contains("name")){
            copy_name = sp.getString("name","");
            Name.setText(sp.getString("name",""));
            Email.setText(sp.getString("email",""));
            Glide.with(getContext()).load(sp.getString("url","")).into(imageView);
            //Toast.makeText(getContext(), ""+sp.getString("name",""), Toast.LENGTH_SHORT).show();
        }
        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.brocodes.cmidevamatha");
                    Intent newUri = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(newUri);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //deleting user data
                SharedPreferences sp = getActivity().getSharedPreferences("user_details",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                if(sp.contains("name")){
                    ed.remove("email");
                    ed.remove("name");
                    ed.remove("url");
                    ed.remove("uid");
                    ed.apply();
                    Toast.makeText(getContext(), "Local User data deleted...", Toast.LENGTH_LONG).show();
                }
                //sign out from the application
                mAuth1.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                requireActivity().finish();

            }
        });
        id_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating random demo number for public user ID
                Random random = new Random();
                int val = random.nextInt(9000-1000)+1;
                String random_val = Integer.toString(val);
                lottieAnimationView.playAnimation();
                //getActivity();
                //Toast.makeText(getContext(), ""+copy_name, Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("UserDisplayName", copy_name+"@021DEV"+random_val);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Public User ID Copied", Toast.LENGTH_LONG).show();
            }
        });


        return v10;
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}