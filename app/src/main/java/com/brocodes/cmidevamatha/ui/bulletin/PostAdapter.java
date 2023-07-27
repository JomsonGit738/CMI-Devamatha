package com.brocodes.cmidevamatha.ui.bulletin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context mContext;
    List<Post> mData;
    String Pro_name;
    String global_name,global_url,global_uid;
    String user_post_name;
    Dialog pdf_name_dialog;
    FirebaseAuth Pro_Auth = FirebaseAuth.getInstance();;
    FirebaseUser Pro_admin = Pro_Auth.getCurrentUser();
    DatabaseReference user_id_Ref = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference delete_posts = FirebaseDatabase.getInstance().getReference("Posts");
    DatabaseReference Pro_admin_permission = FirebaseDatabase.getInstance().getReference("ProAdmin");



    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SharedPreferences sp = mContext.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if(sp.contains("name")){
            global_name = sp.getString("name","");
            global_url = sp.getString("url","");
            global_uid = sp.getString("uid","");
        }

        Pro_admin_permission.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pro_name = snapshot.child("admin").getValue().toString();
                Log.i("jomon",Pro_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.tvTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.imgPostProfile);
        holder.poster_name.setText(mData.get(position).getName());
        holder.poster_date.setText(timestampToString(mData.get(position).getTimeStamp()));
        holder.poster_time.setText(timestampToTime(mData.get(position).getTimeStamp()));
//        Toast.makeText(mContext, ""+mData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//        Log.i("jomciom",mData.get(position).getTitle());
        //Log.i("poster_date", poster_date);

        holder.imgPostProfile.setOnClickListener(v -> {
            if (global_name.equals(Pro_name)) {
                Query F_query = delete_posts.orderByChild("title").equalTo(mData.get(position).getTitle());
                F_query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsf : snapshot.getChildren()) {
                            dsf.getRef().removeValue();
                        }
                        Toast.makeText(mContext, "Post Deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(mContext, "Access Denied by ProAdmin", Toast.LENGTH_SHORT).show();
            }
        });


        /*EditText Pdf_name_given = pdf_name_dialog.findViewById(R.id.edit_pdf_name);
        Button btn_cancel = pdf_name_dialog.findViewById(R.id.btn_pdf_cancel);
        Button btn_ok = pdf_name_dialog.findViewById(R.id.btn_pdf_ok);*/

    }





    private String timestampToTime(Object timeStamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis((Long) timeStamp);
        return DateFormat.format("hh:mm", calendar).toString();
    }

    private String timestampToString(Object timeStamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis((Long) timeStamp);
        return DateFormat.format("dd-MM-yyyy",calendar).toString();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,poster_name,poster_date,poster_time;
        ImageView imgPost;
        ImageView imgPostProfile;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.row_post_title);
            poster_name = itemView.findViewById(R.id.Poster_name);
            poster_date = itemView.findViewById(R.id.poster_date);
            poster_time = itemView.findViewById(R.id.poster_time);
            imgPost = itemView.findViewById(R.id.row_post_img);
            imgPostProfile = itemView.findViewById(R.id.row_post_profile_img);

            imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(mContext,PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("title",mData.get(position).getTitle());
                    postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                    postDetailActivity.putExtra("description",mData.get(position).getDescription());
                    postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                    postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                    // will fix this later i forgot to add user name to post object
                    //postDetailActivity.putExtra("userName",mData.get(position).getUsername);
                    long timestamp  = (long) mData.get(position).getTimeStamp();
                    postDetailActivity.putExtra("postDate",timestamp);

                    //latest contribution 15-09-21

                    postDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(postDetailActivity);



                }
            });

        }


    }


}
