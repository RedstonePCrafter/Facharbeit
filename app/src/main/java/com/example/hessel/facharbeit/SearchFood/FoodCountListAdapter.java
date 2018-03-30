package com.example.hessel.facharbeit.SearchFood;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by hessel on 21.01.2018.
 */

public class FoodCountListAdapter extends ArrayAdapter<FoodCount> {
    private static final String Tag = "FoodListAdapter";
    private Context mContext;
    int mResource;
    private ArrayList<FoodCount> foodCountArrayList;
    private SharedPreferences SP;

    public FoodCountListAdapter(Context context, int resource, ArrayList<FoodCount> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.foodCountArrayList = objects;
        this.SP = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String name = getItem(position).getName();
        //String dauer = getItem(position).getDauer();
        //final String splits = String.valueOf(getItem(position).getSplitanzahl());
        final int calories = getItem(position).getCalories();
        final float count = getItem(position).getCount();
        final String unit = getItem(position).getUnit();



        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        RelativeLayout primaryAction = (RelativeLayout) convertView.findViewById(R.id.primaryAction);

        TextView tvname = (TextView) convertView.findViewById(R.id.food_name);
        TextView tvcalorie = (TextView) convertView.findViewById(R.id.food_calorie);

        tvname.setText(name);
        tvcalorie.setText(calories+" kcal ⋅ "+count +" "+unit);
        primaryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag,"You pressed "+name);
                FoodCount foodCount = getItem(position);
                Gson gson = new Gson();
                String json = gson.toJson(foodCount);
                SP.edit().putString("foodCount", json).commit();
                mContext.startActivity(new Intent(mContext,FoodActivity.class));
            }
        });


        return  convertView;
    }

}
