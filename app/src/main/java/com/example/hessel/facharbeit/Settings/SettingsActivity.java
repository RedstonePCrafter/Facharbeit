package com.example.hessel.facharbeit.Settings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.hessel.facharbeit.Home.SectionsPagerAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.example.hessel.facharbeit.Utils.Synchronization.synchronize;

/**
 * Created by hessel on 16.12.2017.
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String Tag ="Settingsactivity";
    private static final int ACTIVITY_NUM=4;
    private Context mcontext = SettingsActivity.this;
    private static SharedPreferences SP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(Tag, "oncreate . starting");
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(R.id.rellayout2, settingsFragment,"SETTINGS_FRAGMENT");
        fragmentTransaction.commit();*/
        getFragmentManager().beginTransaction()
                .replace(R.id.rellayout2, new SettingsFragment()).commit();
    }


    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        synchronize(SP,SettingsActivity.this);
    }
}
