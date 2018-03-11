package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;

/**
 * Created by hessel on 05.02.2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;

    }


    public int [] slide_images = {
            R.drawable.group_10,
            R.drawable.group_11,
            R.drawable.group_12
    };
    public String [] slide_headings = {
            "LOL",
            "NICE",
            "COOL"
    };
    public String [] slide_text ={
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Id ornare arcu odio ut.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Id ornare arcu odio ut.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Id ornare arcu odio ut."

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slide,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView header = (TextView) view.findViewById(R.id.header);
        TextView text = (TextView) view.findViewById(R.id.text);


        imageView.setImageResource(slide_images[position]);
        header.setText(slide_headings[position]);
        text.setText(slide_text[position]);

        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position,Object object){
        container.removeView((RelativeLayout)object);
    }

}
