package com.example.hessel.facharbeit.Maps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.GridView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.example.hessel.facharbeit.Utils.GridItem;
import com.example.hessel.facharbeit.Utils.GridViewAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by hessel on 16.12.2017.
 */

public class MapsActivity extends AppCompatActivity {
    private static final String Tag ="MapsActivity";
    private static final int ACTIVITY_NUM=3;
    private Context mcontext = MapsActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();
            }

    private void setupBottomNavigationView(){
        Log.d(Tag, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mcontext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
