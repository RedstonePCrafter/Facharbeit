package com.example.hessel.facharbeit.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Login.RegisterActivity;
import com.example.hessel.facharbeit.PlanUtils.ListUtils;
import com.example.hessel.facharbeit.PlanUtils.Set;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.SearchFood.Food;
import com.example.hessel.facharbeit.SearchFood.FoodCount;
import com.example.hessel.facharbeit.SearchFood.FoodCountListAdapter;
import com.example.hessel.facharbeit.SearchFood.FoodListAdapter;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.hessel.facharbeit.Utils.BottomSheet.homeFragementBottomSheet;
import static com.example.hessel.facharbeit.Utils.ConnectHelper.checkforConnection;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class HomeActivity extends AppCompatActivity {

    private static final String Tag = "Homeactivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mcontext = HomeActivity.this;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences SP;
    private PieChart pieChart;
    private float maxCalorie;
    private float nowCalorie=0;
    private FoodCountListAdapter breakfast_adapter,lunch_adapter,dinner_adapter,snack_adapter;
    ListView listView_breakfast,listView_lunch,listView_dinner,listView_snack;
    ArrayList<FoodCount> breakfast_list,lunch_list,dinner_list,snack_list;
    public static final int[] Colors = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"),rgb("#333333")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();

        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SP.edit().putString("meal", "Breakfast").commit();
                homeFragementBottomSheet(view.getContext(),R.layout.layout_bottom_sheet_grid);

            }
        });


        pieChart = (PieChart) findViewById(R.id.piechart);

        setUp();
        //Creating View of Application
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //Checking the Connnection State
        checkforConnection(coordinatorLayout, SP.getBoolean("online",false));
        Log.d(Tag, String.valueOf(SP.getBoolean("online",false)));
        SP.edit().putBoolean("online", false).commit();







        listView_breakfast = (ListView) findViewById(R.id.breakfast);
        breakfast_list = new ArrayList<>();
        //breakfast_list.add(new FoodCount(new Food("",0,0,0,0,"")));

        breakfast_adapter = new FoodCountListAdapter(mcontext,R.layout.layout_listview_food,breakfast_list);
        listView_breakfast.setAdapter(breakfast_adapter);
        ListUtils.setDynamicHeight(listView_breakfast);

        listView_lunch = (ListView) findViewById(R.id.lunch);
        lunch_list = new ArrayList<>();
        //lunch_list.add(new FoodCount(new Food("",0,0,0,0,"")));

        lunch_adapter = new FoodCountListAdapter(mcontext,R.layout.layout_listview_food,lunch_list);
        listView_lunch.setAdapter(lunch_adapter);
        ListUtils.setDynamicHeight(listView_lunch);

        listView_dinner = (ListView) findViewById(R.id.dinner);
        dinner_list = new ArrayList<>();
        //dinner_list.add(new FoodCount(new Food("",0,0,0,0,"")));


        dinner_adapter = new FoodCountListAdapter(mcontext,R.layout.layout_listview_food,dinner_list);
        listView_dinner.setAdapter(dinner_adapter);
        ListUtils.setDynamicHeight(listView_dinner);


        listView_snack = (ListView) findViewById(R.id.snack);
        snack_list = new ArrayList<>();
        //snack_list.add(new FoodCount(new Food("",0,0,0,0,"")));

        snack_adapter = new FoodCountListAdapter(mcontext,R.layout.layout_listview_food,snack_list);
        listView_snack.setAdapter(snack_adapter);
        ListUtils.setDynamicHeight(listView_snack);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        addtoMeal(loadFoodCount());
        /*breakfast_list = loadFoodCountList("breakfast_list");
        lunch_list = loadFoodCountList("lunch_list");
        dinner_list = loadFoodCountList("dinner_list");
        snack_list = loadFoodCountList("snack_list");*/



    }

    public void onclick_snack(View view){
        ImageView imageView = (ImageView) view.findViewById(R.id.snack_arrow);
        if (listView_snack.getVisibility()==View.GONE){
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
            listView_snack.setVisibility(View.VISIBLE);
        }else{
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            listView_snack.setVisibility(View.GONE);
        }
    }

    public void onclick_dinner(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.dinner_arrow);
        if (listView_dinner.getVisibility() == View.GONE) {
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
            listView_dinner.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            listView_dinner.setVisibility(View.GONE);
        }
    }

    public void onclick_lunch(View view){
        ImageView imageView = (ImageView) view.findViewById(R.id.lunch_arrow);
        if (listView_lunch.getVisibility()==View.GONE){
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
            listView_lunch.setVisibility(View.VISIBLE);
        }else{
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            listView_lunch.setVisibility(View.GONE);
        }
    }


    public void onclick_breakfast(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.breakfast_arrow);
        if (listView_breakfast.getVisibility() == View.GONE) {
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
            listView_breakfast.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
            listView_breakfast.setVisibility(View.GONE);
        }
    }






    //Bottomnavigationview

    private void setupBottomNavigationView() {
        Log.d(Tag, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mcontext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    public void setUp(){


        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(-1);
        pieChart.setTouchEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(80f);

        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        pieChart.getLegend().setEnabled(false);
        maxCalorie = 500;
        ArrayList<PieEntry> yValues=new ArrayList<>();
        yValues.add(new PieEntry(50,"Frühstück"));
        yValues.add(new PieEntry(50,"Mittagessen"));
        yValues.add(new PieEntry(50,"Abendessen"));
        yValues.add(new PieEntry(50,"Snack"));
        nowCalorie = 0;
        for (int i=0; i<yValues.size(); i++){
            nowCalorie=+yValues.get(i).getValue();
        }
        yValues.add(new PieEntry(maxCalorie-nowCalorie,""));


        PieDataSet dataSet = new PieDataSet(yValues,"Tests");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Colors);

        PieData data = new PieData((dataSet));
        data.setValueTextColor(Color.YELLOW);
        data.setValueTextSize(14);

        pieChart.setCenterText(maxCalorie+" kcal");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(20);
        pieChart.setData(data);

    }

    public FoodCount loadFoodCount(){
        Gson gson = new Gson();
        String json = SP.getString("foodCount","");
        Type type = new TypeToken<FoodCount>() {}.getType();
        SP.edit().putString("foodCount","").commit();
        Log.d(Tag,json);
        return gson.fromJson(json,type);

    }

    public ArrayList<FoodCount> loadFoodCountList(String list){
        Gson gson = new Gson();
        String json = SP.getString(list,"");
        Type type = new TypeToken<ArrayList<FoodCount>>() {}.getType();
        return gson.fromJson(json,type);
    }

    public void addtoMeal(FoodCount foodCount){
        if (foodCount != null) {
            Log.d(Tag,foodCount.getMeal());
            switch (foodCount.getMeal()) {
                case "Breakfast":
                    breakfast_list.add(foodCount);
                    breakfast_adapter.notifyDataSetChanged();
                    ListUtils.setDynamicHeight(listView_breakfast);
                    Log.d(Tag, "tst");
                    break;
                case "Lunch":
                    lunch_list.add(foodCount);
                    lunch_adapter.notifyDataSetChanged();
                    ListUtils.setDynamicHeight(listView_lunch);
                    break;
                case "Dinner":
                    dinner_list.add(foodCount);
                    dinner_adapter.notifyDataSetChanged();
                    ListUtils.setDynamicHeight(listView_dinner);
                    break;
                case "Snack":
                    snack_list.add(foodCount);
                    snack_adapter.notifyDataSetChanged();
                    ListUtils.setDynamicHeight(listView_snack);
                    break;

            }
        }
    }
}
