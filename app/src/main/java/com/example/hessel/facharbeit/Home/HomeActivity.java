package com.example.hessel.facharbeit.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Login.RegisterActivity;
import com.example.hessel.facharbeit.PlanUtils.Set;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import static com.example.hessel.facharbeit.Utils.ConnectHelper.checkforConnection;

public class HomeActivity extends AppCompatActivity {

    private static final String Tag = "Homeactivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mcontext = HomeActivity.this;
    private CoordinatorLayout coordinatorLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();
        setupViewPager();


        //Creating View of Application
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //Checking the Connnection State
        try {
            if (!getIntent().getStringExtra("online").equals(null)) {
                checkforConnection(coordinatorLayout, getIntent().getStringExtra("online"));
            }
        } catch (Exception e) {
        }
    }


    //setting up Viewpager
    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TimerFragment());//0
        adapter.addFragment(new HomeFragment());//1
        adapter.addFragment(new PlansFragment());//2
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_timer);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_plans);
        tabLayout.setTabTextColors(Color.GRAY,Color.WHITE);
        tabLayout.getTabAt(1).setText("Caloriecounter");
        tabLayout.getTabAt(0).setText("Timer");
        tabLayout.getTabAt(2).setText("Plans");




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                Log.d(Tag, "" + state);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(Tag,position+" / "+positionOffset+" / "+positionOffsetPixels);
            }

            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });

    }


    //Bottomnavigationview

    private void setupBottomNavigationView() {
        Log.d(Tag, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mcontext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void slide_to_calorie(View view) {
        viewPager.setCurrentItem(0);
    }

    public void slide_to_plans(View view) {
        viewPager.setCurrentItem(2);
    }

}
