package com.brocodes.cmidevamatha.ui.home;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.mynewsviewholder> {

    ArrayList<NewsModel> mList;
    boolean show_shimmer = true;
    Context context;

    public NewsAdapter( ArrayList<NewsModel> mList,Context context) {
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public mynewsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new mynewsviewholder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull mynewsviewholder holder, int position) {

        //shimmer pack special

        if (show_shimmer){
            holder.shimmerFrameLayout.startShimmer();
        }
        else{
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.title.setBackground(null);
            holder.title.setText(mList.get(position).getTitle());

            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis((Long) mList.get(position).getTimeStamp2());
            String date_home =  DateFormat.format("dd-MM-yyyy",calendar).toString();

            holder.date.setBackground(null);
            holder.date.setText((date_home));



        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent news = new Intent(context,News_Result.class);
                news.putExtra("title",mList.get(position).getTitle());
                news.putExtra("desc",mList.get(position).getDesc());
                news.putExtra("timeStamp2", (Long) mList.get(position).getTimeStamp2());
                context.startActivity(news);
            }
        });
    }

    @Override
    public int getItemCount() {
        int SHIMMER_ITEM_NUMBER = 10;
        return show_shimmer ? SHIMMER_ITEM_NUMBER : mList.size();
        //return mList.size();
    }


    public static class mynewsviewholder extends RecyclerView.ViewHolder {
        ShimmerFrameLayout shimmerFrameLayout;
        TextView title;
        TextView date;
        LinearLayout cardView;


        public mynewsviewholder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmerLayout);
            title =itemView.findViewById(R.id.news_item_title);
            date =itemView.findViewById(R.id.date_home_news);
            cardView =itemView.findViewById(R.id.new_card_view);
        }
    }
}
