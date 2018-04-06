package com.example.hessel.facharbeit.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hessel.facharbeit.Utils.ConnectHelper.isNetworkConnected;

/**
 * Created by hessel on 26.03.2018.
 */

public class Synchronization {

    public static void synchronize(SharedPreferences SP, final Context mContext) {
        final String TAG ="Synchronization";
        String gewicht = SP.getString("pref_Gewicht","0");
        String groesse = SP.getString("pref_groesse","0");
        String geschlecht = SP.getString("pref_sexe","");
        String email = SP.getString("pref_email","");
        String password = SP.getString("pref_password","");
        String json = SP.getString("pref_planlist","[]");

        if(isNetworkConnected(mContext)) {

            final Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonresponse = new JSONObject(response);
                        boolean success = jsonresponse.getBoolean("success");
                        String note = jsonresponse.getString("note");
                        Log.d(TAG, note);
                        Log.d(TAG, "" + success);
                        if (success) {
                        } else {
                            Log.d(TAG, "nope");
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                            builder.setMessage("Synchronization failed \n" + note)
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "nope");
                        e.printStackTrace();
                    }


                }
            };


            SynchronizationRequest synchronizationRequest = new SynchronizationRequest(email, password, gewicht, groesse, geschlecht,json, responseListener);
            RequestQueue queue = Volley.newRequestQueue(mContext);
            queue.add(synchronizationRequest);
        }

    }




}
