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
import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hessel.facharbeit.Utils.ConnectHelper.isNetworkConnected;

/**
 * Created by hessel on 26.03.2018.
 */

public class Synchronization {

    public static void synchronize(SharedPreferences SP, final Context mContext) {
        saveBodyDate(SP);
        final String TAG ="Synchronization";
        String body = SP.getString("pref_bodylist", "[]");
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
                        Log.d(TAG, "" + success);
                    } catch (JSONException e) {
                        Log.d(TAG, "nope");
                        e.printStackTrace();
                    }


                }
            };


            SynchronizationRequest synchronizationRequest = new SynchronizationRequest(email, password, body, geschlecht,json, responseListener);
            RequestQueue queue = Volley.newRequestQueue(mContext);
            queue.add(synchronizationRequest);
        }

    }

    public static void saveBodyDate(SharedPreferences SP){
        float weight = Float.parseFloat(SP.getString("pref_weight","0"));
        float height = Float.parseFloat(SP.getString("pref_height","0"));
        float fat = Float.parseFloat(SP.getString("pref_fat","0"));
        float muscle = Float.parseFloat(SP.getString("pref_muscle","0"));
        String date = SP.getString("pref_date","");

        Boolean contains = false;
        Gson gson = new Gson();
        String json = SP.getString("pref_bodylist", "[]");
        Type type = new TypeToken<ArrayList<Body>>() {}.getType();
        ArrayList<Body> bodyArrayList = gson.fromJson(json,type);

        for (Body body: bodyArrayList){
            if(date.equals(body.getDate())){
                contains = true;
                body.setWeight(weight);
                body.setHeight(height);
                body.setFat(fat);
                body.setMuscle(muscle);
            }
        }
        if (contains == false && weight!=0){
            Log.d("Synchronization", String.valueOf(weight));
            bodyArrayList.add(new Body(weight,height,date,fat,muscle));
        }
        SP.edit().putString("pref_bodylist",gson.toJson(bodyArrayList)).apply();

    }





}
