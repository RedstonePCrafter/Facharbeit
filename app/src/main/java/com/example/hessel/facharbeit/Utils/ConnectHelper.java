package com.example.hessel.facharbeit.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hessel.facharbeit.Data.DataActivity;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 13.12.2017.
 */

public class ConnectHelper {


    private static final String Tag = "ConnectHelper";

    @SuppressLint("ResourceAsColor")
    public static void checkforConnection(View view,String state) {
        Log.d(Tag,"Cheking Connection ...");

        if (state.equals("false")) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
                }
            };
            addSnackbar(view, "You are offline", "RETRY", Color.YELLOW, Color.RED,4000,listener );
        } else {
            addSnackbar(view, "You are online", "", Color.GREEN, Color.RED,1500,null);
        }

    }

}
