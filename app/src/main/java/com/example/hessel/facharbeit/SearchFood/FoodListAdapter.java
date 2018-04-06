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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by hessel on 21.01.2018.
 */

public class FoodListAdapter extends ArrayAdapter<Food> {
    private static final String Tag = "FoodListAdapter";
    private Context mContext;
    int mResource;
    private SharedPreferences SP;
    private ArrayList<Food> foodArrayList;
    private String meal;

    public FoodListAdapter(Context context, int resource, ArrayList<Food> objects,String meal) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.foodArrayList = objects;
        this.meal = meal;
        this.SP = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String name = getItem(position).getName();
        //String dauer = getItem(position).getDauer();
        //final String splits = String.valueOf(getItem(position).getSplitanzahl());
        final int calories = getItem(position).getCalories();



        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        RelativeLayout primaryAction = (RelativeLayout) convertView.findViewById(R.id.primaryAction);

        TextView tvname = (TextView) convertView.findViewById(R.id.food_name);
        TextView tvcalorie = (TextView) convertView.findViewById(R.id.food_calorie);

        tvname.setText(name);
        tvcalorie.setText(calories+" kcal");
        primaryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag,"You pressed "+name);
                FoodCount foodCount = new FoodCount(getItem(position));
                foodCount.setMeal(meal);
                Gson gson = new Gson();
                String json = gson.toJson(foodCount);
                SP.edit().putString("foodCount", json).apply();
                Log.d(Tag,SP.getString("foodCount",""));
                mContext.startActivity(new Intent(mContext,FoodActivity.class));
            }
        });


        return  convertView;
    }

}
