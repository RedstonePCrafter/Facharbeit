package com.example.hessel.facharbeit.SearchFood;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by hessel on 28.03.2018.
 */

public class FoodActivity extends AppCompatActivity {
    private static final String Tag = "FoodActivity";
    private Context mcontext = FoodActivity.this;
    private FoodCount foodCount;
    private SharedPreferences SP;
    private PieChart protein_chart,carbohydrates_chart,fats_chart;
    private TextView protein,carbohydrates,fats;
    public static final int[] Colors = {
            rgb("#ffffff"), rgb("#626262")
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        loadFoodCount();

        protein_chart = (PieChart) findViewById(R.id.protein_chart);
        chartInit(protein_chart);

        carbohydrates_chart = (PieChart) findViewById(R.id.carbohydrates_chart);
        chartInit(carbohydrates_chart);

        fats_chart = (PieChart) findViewById(R.id.fats_chart);
        chartInit(fats_chart);


        setChart(protein_chart,0);
        setChart(carbohydrates_chart,1);
        setChart(fats_chart,2);

        final TextView calorie = (TextView) findViewById(R.id.calorie);
        final TextView protein = (TextView) findViewById(R.id.protein);
        final TextView carbohydrates = (TextView) findViewById(R.id.carbohydrates);
        final TextView fats = (TextView) findViewById(R.id.fats);

        calorie.setText(String.valueOf(foodCount.getCalories()));
        protein.setText(String.valueOf(foodCount.getProtein())+" g");
        carbohydrates.setText(String.valueOf(foodCount.getCarbohydrates())+" g");;
        fats.setText(String.valueOf(foodCount.getFats())+" g");

        TextView unit = (TextView) findViewById(R.id.unit);
        unit.setText(foodCount.getUnit());

        EditText count = (EditText) findViewById(R.id.count);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(foodCount.getName());

        count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(charSequence).equals("") || Integer.parseInt(String.valueOf(charSequence))==0) {
                        foodCount.setCount(0);
                }else {
                    foodCount.setCount(Integer.parseInt(String.valueOf(charSequence)));

                }

                calorie.setText(String.valueOf(foodCount.getCalories()));
                protein.setText(String.valueOf(foodCount.getProtein())+" g");
                carbohydrates.setText(String.valueOf(foodCount.getCarbohydrates())+" g");;
                fats.setText(String.valueOf(foodCount.getFats())+" g");



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void onclick_fab(View view){
        Gson gson = new Gson();
        String json = gson.toJson(foodCount);
        SP.edit().putString("foodCount", json).commit();
        mcontext.startActivity(new Intent(mcontext,HomeActivity.class));
    }

    public void loadFoodCount(){
        Gson gson = new Gson();
        String json = SP.getString("foodCount","");
        Type type = new TypeToken<FoodCount>() {}.getType();
        foodCount = gson.fromJson(json,type);
        SP.edit().putString("foodCount", "").commit();

    }

    public void chartInit(PieChart pieChart){


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(-1);
        pieChart.setTouchEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(95f);

        pieChart.getLegend().setEnabled(false);
        ArrayList<PieEntry> yValues=new ArrayList<>();

        PieDataSet dataSet = new PieDataSet(yValues,"Tests");
        dataSet.setSelectionShift(0f);
        dataSet.setColors(Colors);
        dataSet.setValueTextSize(0);
        PieData data = new PieData((dataSet));


        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(20);
        pieChart.setData(data);

    }

    public void setChart(PieChart pieChart,int nutrients){
        int gram = 0;
        switch (nutrients){
            case 0:
                gram = foodCount.getProtein()*4;
                break;
            case 1:
                gram = foodCount.getCarbohydrates()*4;
                break;
            case 2:
                gram = foodCount.getFats()*9;
                break;
        }

        int prozent = 0;
        if (gram != 0) {
            prozent = Integer.parseInt(String.valueOf(gram * 100 / foodCount.getCalories()));
        }
            ArrayList<PieEntry> yValues = new ArrayList<>();
            yValues.add(new PieEntry(prozent, ""));
            yValues.add(new PieEntry(100 - prozent, ""));
            PieDataSet dataSet = (new PieDataSet(yValues, ""));
            dataSet.setSelectionShift(0f);
            dataSet.setColors(Colors);
            dataSet.setValueTextSize(0);
            pieChart.setData(new PieData(dataSet));
            pieChart.setCenterText(prozent + " %");
            Log.d(Tag, "Prozent:" + pieChart.getCenterText());

            pieChart.invalidate();

    }
}
