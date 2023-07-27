package com.brocodes.cmidevamatha.introduction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.brocodes.cmidevamatha.R;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context = context;
    }
    public int[] slide_images = {R.drawable.chavara,R.drawable.canisius,R.drawable.cmi};
    public String[] slide_headings = {"St. Kuriakose Elias Chavara","Servant of God Fr. Canisius","Carmelites of Mary Immaculate"};
    public String[] slide_descriptions = {"Kuriakose Elias Chavara, C.M.I. (10 February 1805 – 3 January 1871) was an Indian Syro-Malabar Catholic priest, philosopher and social reformer. He is the first canonised Catholic male saint of Indian origin and belongs to the Syro-Malabar Church, an Eastern Catholic Church based in the state of Kerala. He was the co-founder and first Prior General of the first congregation for men in the Syro-Malabar Church, now known as the Carmelites of Mary Immaculate (C.M.I.), and of a similar one for women, the Congregation of the Mother of Carmel (C.M.C.).", "Canisius Thekkekara was a Syrian Catholic (Syro-Malabar Catholic) priest from Carmelites of Mary Immaculate in Kerala in Thrissur. He was declared as Servant of God by Mar Poly Kannookadan, the Bishop of Syro-Malabar Catholic Diocese of Irinjalakuda.", "The Carmelites of Mary Immaculate(C.M.I.) is a clerical religious congregation of Pontifical Right for men of the Syro-Malabar Catholic Church, and is the largest clerical religious congregation of pontifical right in the Syro-Malabar Catholic Church. The core charism of congregation is contemplata et aliis tradere (to share with others the fruits of contemplation). The Identity and heritage of the Congregation is reflected in its triple roots of spirituality namely Indian, Eastern and Carmelite. The Congregation is involved in pastoral works consisting of teaching at all levels, taking care of aged and sick, apostolate of press, running several mission dioceses both in India and abroad as Global Mission."};

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.introslides,container,false);

        ImageView slide_imag = (ImageView) view.findViewById(R.id.slide_image);
        TextView slide_Head = (TextView) view.findViewById(R.id.slideHed);
        TextView slide_Des = (TextView) view.findViewById(R.id.slideDesc);

        slide_imag.setImageResource(slide_images[position]);
        slide_Head.setText(slide_headings[position]);
        slide_Des.setText(slide_descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
