package com.brocodes.cmidevamatha.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.brocodes.cmidevamatha.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class One1 extends Fragment {

    RecyclerView recyclerView_news;
    NewsAdapter myadapter_news;
    FirebaseDatabase fdb;
    DatabaseReference dre;
    private ArrayList<NewsModel> list;


    //image slider
    int[] images;
    String[] text;
    SliderAdapter adapter;
    SliderView sliderView;

    public One1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newsView = inflater.inflate(R.layout.fragment_one1, container, false);
        recyclerView_news = (RecyclerView) newsView.findViewById(R.id.news_recyclerview);

        ////////////////////////////////////image slider properties
        sliderView = newsView.findViewById(R.id.imageSlider);
        images = new int[]{R.drawable.youtube,R.drawable.facebook,R.drawable.website,R.drawable.province};
        text = new String[]{"Youtube", "Facebook", "Website", "Website"};
        adapter = new SliderAdapter(images, text);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.startAutoCycle();
        /////////////////////////////////////////////////////////


        //recyclerView_news.setLayoutManager(new LinearLayoutManager(getContext()));

        //instead of a line i give reverse so initialised the layout manager separately,.............
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView_news.setLayoutManager(linearLayoutManager);
        //////////////////////////
        fdb = FirebaseDatabase.getInstance();
        dre = fdb.getReference("News");
        dre.keepSynced(true);

        
        return newsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        list = new ArrayList<>();
        dre.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(dre != null){
                    list.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                NewsModel newsMode2 = dataSnapshot.getValue(NewsModel.class);
                list.add(newsMode2);
                }
                //shimmer special
                myadapter_news.show_shimmer = false;
                myadapter_news.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myadapter_news = new NewsAdapter(list,getContext());
        recyclerView_news.setAdapter(myadapter_news);

    }


}