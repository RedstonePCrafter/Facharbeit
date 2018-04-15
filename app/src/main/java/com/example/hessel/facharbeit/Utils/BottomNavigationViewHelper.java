package com.example.hessel.facharbeit.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.hessel.facharbeit.Data.DataActivity;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Map.MapsActivity;
import com.example.hessel.facharbeit.Plan.PlanActivity;
import com.example.hessel.facharbeit.Profile.ProfileActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Search.SearchActivity;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by hessel on 13.12.2017.
 */

public class BottomNavigationViewHelper {


    private static final String Tag = "BottomNavigationViewHel";

    @SuppressLint("ResourceAsColor")
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(Tag, "BottomnavigationView setting up");
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enablenavigation(final Context context,final BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_data:
                        Intent intent2 = new Intent(context, PlanActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_search:
                        Intent intent3 = new Intent(context, SearchActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_maps:
                        Intent intent4 = new Intent(context, MapsActivity.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_settings:
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5);
                        break;
                }

                return false;
            }
        });
    }
}
