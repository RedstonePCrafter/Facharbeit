package com.example.hessel.facharbeit.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 16.12.2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String Tag ="Profileactivity";
    private static final int ACTIVITY_NUM=4;
    private Context mcontext = ProfileActivity.this;
    private SharedPreferences SP;
    private TextView tv_profile_name,tv_profile_meber_since;
    //private ImageView iv_profile_pic,iv_profile_settings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();

        SP = PreferenceManager.getDefaultSharedPreferences(mcontext);

        tv_profile_name = (TextView) findViewById(R.id.profile_name);
        tv_profile_meber_since = (TextView) findViewById(R.id.profile_meber_since);

        tv_profile_name.setText(SP.getString("pref_username",null));

        //iv_profile_pic = (ImageView) findViewById(R.id.profile_pic);
        //iv_profile_settings = (ImageView) findViewById(R.id.profile_settings);

    }

    public void onclick_Settings(View view){
        startActivity(new Intent(mcontext, SettingsActivity.class));
    }
    public void onclick_Website(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
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
