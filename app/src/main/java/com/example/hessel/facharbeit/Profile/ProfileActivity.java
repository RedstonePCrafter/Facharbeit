package com.example.hessel.facharbeit.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;
import static com.example.hessel.facharbeit.Utils.CustomTabs.openInCustomTab;
import static com.example.hessel.facharbeit.Utils.Month.getMonth;

/**
 * Created by hessel on 16.12.2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String Tag ="Profileactivity";
    private static final int ACTIVITY_NUM=4;
    private Context mcontext = ProfileActivity.this;
    private SharedPreferences SP;
    private TextView tv_profile_name,tv_profile_meber_since;
    private Bitmap currentImage;
    private CircleImageView selectedImage;


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
        String month = getMonth(Integer.parseInt(SP.getString("pref_reg_date",null).split(" ")[0]));
        String year = SP.getString("pref_reg_date",null).split(" ")[1];
        tv_profile_meber_since.setText("Mitglied seit: "+month+" "+year);

        selectedImage = (CircleImageView) findViewById(R.id.profile_pic);
        try {
            String photoString = SP.getString("pref_profile_picture",null);
            if (photoString != null) {
                selectedImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(photoString)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onclick_Settings(View view){
        startActivity(new Intent(mcontext, SettingsActivity.class));
    }
    public void onclick_Website(View view) {
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
        openInCustomTab(URL,this);
    }
    public void onclick_profile_pic(View view) {
        Log.d(Tag,"open image explorer has been pressed");
        openGallery();
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


    public void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                try {
                    Log.d(Tag, String.valueOf(photoUri));
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    selectedImage.setImageBitmap(currentImage);
                    SP.edit().putString("pref_profile_picture", String.valueOf(photoUri)).apply();;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
