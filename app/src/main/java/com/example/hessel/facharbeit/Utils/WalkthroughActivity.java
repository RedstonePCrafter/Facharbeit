package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Login.RegisterActivity;
import com.example.hessel.facharbeit.R;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by hessel on 05.02.2018.
 */

public class WalkthroughActivity extends AppCompatActivity{
    private static final String TAG ="WalkthroughActivity";
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SliderAdapter sliderAdapter;
    private TextView dots[];

    private Button next;
    private Button back;

    private int currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);


        viewPager = (ViewPager) findViewById(R.id.walkthrough);
        linearLayout = (LinearLayout) findViewById(R.id.lin_1);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        viewPager.addOnPageChangeListener(viewListener);

        adddots(0);


    }

    public void adddots(int position){
        dots = new TextView[3];
        linearLayout.removeAllViews();


        for (int i = 0;i<sliderAdapter.getCount();i++){
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.trasnparentwhite));

            linearLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            adddots(position);
            currentPage = position;

            if(currentPage==0){
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

                next.setText("Next");
                back.setText("");
            }
            else if(currentPage==dots.length-1){
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Finish");
                back.setText("Back");
            }else{
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Next");
                back.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void onclick_next(View view){
        if (currentPage>=dots.length-1) {
            this.startActivity(new Intent(this, HomeActivity.class));
        }else{

            viewPager.setCurrentItem(currentPage + 1);
        }
    }
    public void onclick_back(View view){
        viewPager.setCurrentItem(currentPage-1);
    }
}
