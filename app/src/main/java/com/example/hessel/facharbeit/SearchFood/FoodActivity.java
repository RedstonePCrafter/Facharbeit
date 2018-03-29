package com.example.hessel.facharbeit.SearchFood;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private PieChart protein_chart,carbohydrates_chart,fats_chart;
    public static final int[] Colors = {
            rgb("#ffffff"), rgb("#626262"),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        loadFood();


        protein_chart = (PieChart) findViewById(R.id.protein_chart);
        chartInit(protein_chart);

        carbohydrates_chart = (PieChart) findViewById(R.id.carbohydrates_chart);
        chartInit(carbohydrates_chart);

        fats_chart = (PieChart) findViewById(R.id.fats_chart);
        chartInit(fats_chart);

        setChart(protein_chart,0);
        setChart(carbohydrates_chart,1);
        setChart(fats_chart,2);


        TextView unit = (TextView) findViewById(R.id.unit);
        unit.setText(foodCount.getUnit());

        EditText count = (EditText) findViewById(R.id.count);

        final TextView calorie = (TextView) findViewById(R.id.calorie);

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


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });







    }

    public void loadFood(){
        Gson gson = new Gson();
        String json = getIntent().getStringExtra("food");
        Type type = new TypeToken<Food>() {}.getType();
        Food food = gson.fromJson(json,type);
        FoodCount foodCount1 = new FoodCount(food);
        foodCount = foodCount1;

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
