package com.example.hessel.facharbeit.Utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;

/**
 * Created by hessel on 13.12.2017.
 */

public class SnackbarHelper {


    private static final String Tag = "SnackbarHelper";

    @SuppressLint("ResourceAsColor")
    public static void addSnackbar(View view, String text, String button, int textColor, int buttonColor, int duration, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar
                .make(view, text, duration)
                .setAction(button, listener);

        snackbar.setActionTextColor(buttonColor);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }

}
