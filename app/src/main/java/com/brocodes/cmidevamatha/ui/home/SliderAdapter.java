package com.brocodes.cmidevamatha.ui.home;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.brocodes.cmidevamatha.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private final int[] images;
    private final String[] text;

    public SliderAdapter(int[] images, String[] text) {
        this.images = images;
        this.text = text;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item,null);
        return new SliderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images[position]);
        viewHolder.textView.setText(text[position]);
        viewHolder.itemView.setOnClickListener(v -> {

            if(position == 0) {
                Uri uri = Uri.parse("https://www.youtube.com/channel/UCkbYguIdlwdgVSllkipGBgg");
                viewHolder.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else if(position == 1){
                Uri uri = Uri.parse("https://web.facebook.com/devmatha");
                viewHolder.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else if(position == 2){
                Uri uri = Uri.parse("http://www.cmi.org.in/");
                viewHolder.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                Uri uri = Uri.parse("http://www.devamatha.in/");
                viewHolder.itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
          });

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text_desc);
        }
    }
}


