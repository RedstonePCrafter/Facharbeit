package com.example.hessel.facharbeit.Home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Login.LoginRequest;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.hessel.facharbeit.Utils.BottomSheet.homeFragementBottomSheet;
import static com.example.hessel.facharbeit.Utils.ConnectHelper.checkforConnection;
import static com.example.hessel.facharbeit.Utils.ConnectHelper.isNetworkConnected;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;
import static java.lang.Math.abs;

public class HomeActivity extends AppCompatActivity {

    private static final String Tag = "Homeactivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mcontext = HomeActivity.this;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences SP;
    private PieChart pieChart;
    private Calendar calendar;
    private TextView tvdate;
    private String date;
    private DatePickerDialog datePickerDialog;
    private FoodCountListAdapter breakfast_adapter,lunch_adapter,dinner_adapter,snack_adapter;
    ListView listView_breakfast,listView_lunch,listView_dinner,listView_snack;
    ArrayList<FoodCount> breakfast_list,lunch_list,dinner_list,snack_list;
    public static final int[] Colors = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"),rgb("#333333")
    };

    @SuppressLint({"NewApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();


        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SP.edit().putString("meal", "Breakfast").apply();
                SP.edit().putString("pref_date",date).apply();
                homeFragementBottomSheet(view.getContext(),R.layout.layout_bottom_sheet_grid);

            }
        });


        pieChart = (PieChart) findViewById(R.id.piechart);
        tvdate = (TextView) findViewById(R.id.date);

        //Creating View of Application
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //Checking the Connnection State
        checkforConnection(coordinatorLayout, SP.getBoolean("online", false));







        listView_breakfast = (ListView) findViewById(R.id.breakfast);
        breakfast_list = new ArrayList<>();
        //breakfast_list.add(new FoodCount(new Food("",0,0,0,0,"","")));

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



        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.YEAR)+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.MONTH)+1))+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        SP.edit().putString("pref_date",date).apply();
        tvdate.setText(date);
        Log.d(Tag,date);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                date = calendar.get(Calendar.YEAR)+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.MONTH)+1))+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                SP.edit().putString("pref_date",date).apply();
                Log.d(Tag,date);
                tvdate.setText(date);
                getFood();


            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        getFood();

    }



    public String getformatedDate(String number){
        if(String.valueOf(number).length()==1){
            return "0"+number;
        }
        return number;

    }

    public void showDatePickerDialog(View view){
        datePickerDialog.show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


        checkforConnection(coordinatorLayout, SP.getBoolean("online", false));
        getFood();
        //addtoMeal(loadFoodCount());
    }

    public void onclick_nextDay(View view){
        calendar.add(Calendar.DATE,1);
        date = calendar.get(Calendar.YEAR)+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.MONTH)+1))+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        SP.edit().putString("pref_date",date).apply();
        tvdate.setText(date);
        getFood();

    }
    public void onclick_previousDay(View view){
        calendar.add(Calendar.DATE,-1);
        date = calendar.get(Calendar.YEAR)+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.MONTH)+1))+"-"+getformatedDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        SP.edit().putString("pref_date",date).apply();
        tvdate.setText(date);
        Log.d(Tag,date);
        getFood();

    }

    public void resetadd(ArrayList<FoodCount> foodlist_old, ArrayList<FoodCount> foodlist_new, ListView listview, FoodCountListAdapter adapter){
        foodlist_old.clear();
        foodlist_old.addAll(foodlist_new);
        ListUtils.setDynamicHeight(listview);
        adapter.notifyDataSetChanged();



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


    public void setChart(){
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
        ArrayList<PieEntry> yValues=new ArrayList<>();
        int breakfastCalorie = getMealCalories(breakfast_list);
        int lunchCalorie = getMealCalories(lunch_list);
        int dinnerCalorie = getMealCalories(dinner_list);
        int snackCalorie = getMealCalories(snack_list);
        yValues.add(new PieEntry(breakfastCalorie,"Frühstück"));
        yValues.add(new PieEntry(lunchCalorie,"Mittagessen"));
        yValues.add(new PieEntry(dinnerCalorie,"Abendessen"));
        yValues.add(new PieEntry(snackCalorie,"Snack"));

        SP.edit().putString("pref_max_calorie","2700").apply();

        int maxCalorie = Integer.parseInt(SP.getString("pref_max_calorie","0"));
        int leftCalorie = maxCalorie-breakfastCalorie-lunchCalorie-dinnerCalorie-snackCalorie;
        if (leftCalorie<0){
            pieChart.setCenterText(maxCalorie+" + "+abs(leftCalorie)+" kcal");
        }else{
            pieChart.setCenterText(maxCalorie+" kcal");
            yValues.add(new PieEntry(maxCalorie-breakfastCalorie-lunchCalorie-dinnerCalorie-snackCalorie,""));
        }



        PieDataSet dataSet = new PieDataSet(yValues,"Tests");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Colors);

        PieData data = new PieData((dataSet));
        data.setValueTextColor(Color.YELLOW);
        data.setValueTextSize(14);


        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(20);
        pieChart.setData(data);
        pieChart.invalidate();

    }



    public int getMealCalories(ArrayList<FoodCount> foodCountArrayList){
        int mealCalories= 0;
        for(FoodCount foodCount : foodCountArrayList) {
            mealCalories = (int) mealCalories+foodCount.getCalories();
        }
        return mealCalories;
    }

    public FoodCount loadFoodCount(){
        Gson gson = new Gson();
        String json = SP.getString("foodCount","");
        Type type = new TypeToken<FoodCount>() {}.getType();
        SP.edit().putString("foodCount","").apply();
        Log.d(Tag,json);
        return gson.fromJson(json,type);

    }

    public ArrayList<FoodCount> loadFoodCountList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FoodCount>>() {}.getType();
        return gson.fromJson(json,type);
    }


    public void getFood(){

        if(isNetworkConnected(mcontext)) {


            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String breakfast = SP.getString("pref_food_breakfast_" + date, "[]");
                    String lunch = SP.getString("pref_food_lunch_" + date, "[]");
                    String dinner = SP.getString("pref_food_dinner_" + date, "[]");
                    String snack = SP.getString("pref_food_snack_" + date, "[]");
                    resetadd(breakfast_list, loadFoodCountList(breakfast), listView_breakfast,breakfast_adapter);
                    resetadd(lunch_list, loadFoodCountList(lunch), listView_lunch,lunch_adapter);
                    resetadd(dinner_list, loadFoodCountList(dinner), listView_dinner,dinner_adapter);
                    resetadd(snack_list, loadFoodCountList(snack), listView_snack,snack_adapter);
                    setChart();

                }
            };
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    try {
                        JSONObject jsonresponse = new JSONObject(response);
                        Log.d(Tag, "jsonresponse:"+String.valueOf(jsonresponse));
                        String breakfast = jsonresponse.getString("Breakfast");
                        String lunch = jsonresponse.getString("Lunch");
                        String dinner = jsonresponse.getString("Dinner");
                        String snack = jsonresponse.getString("Snack");
                        SP.edit().putString("pref_food_breakfast_" + date, breakfast).apply();
                        SP.edit().putString("pref_food_lunch_" + date, lunch).apply();
                        SP.edit().putString("pref_food_dinner_" + date, dinner).apply();
                        SP.edit().putString("pref_food_snack_" + date, snack).apply();

                        Log.d(Tag,"HomeActivity->newstring:----"+breakfast);

                        resetadd(breakfast_list, loadFoodCountList(breakfast), listView_breakfast,breakfast_adapter);
                        resetadd(lunch_list, loadFoodCountList(lunch), listView_lunch,lunch_adapter);
                        resetadd(dinner_list, loadFoodCountList(dinner), listView_dinner,dinner_adapter);
                        resetadd(snack_list, loadFoodCountList(snack), listView_snack,snack_adapter);
                        setChart();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(Tag, "error");

                    }

                }
            };
            GetFoodRequest getFoodRequest = new GetFoodRequest(SP.getString("pref_email", ""), SP.getString("pref_password", ""), date,SP.getString("pref_food_breakfast_" + date, "[]"),SP.getString("pref_food_lunch_" + date, "[]"),SP.getString("pref_food_dinner_" + date, "[]"),SP.getString("pref_food_snack_" + date, "[]"), responseListener, errorListener);
            getFoodRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(mcontext);
            queue.add(getFoodRequest);

        }else{
            String breakfast = SP.getString("pref_food_breakfast_" + date, "[]");
            String lunch = SP.getString("pref_food_lunch_" + date, "[]");
            String dinner = SP.getString("pref_food_dinner_" + date, "[]");
            String snack = SP.getString("pref_food_snack_" + date, "[]");
            resetadd(breakfast_list, loadFoodCountList(breakfast), listView_breakfast,breakfast_adapter);
            resetadd(lunch_list, loadFoodCountList(lunch), listView_lunch,lunch_adapter);
            resetadd(dinner_list, loadFoodCountList(dinner), listView_dinner,dinner_adapter);
            resetadd(snack_list, loadFoodCountList(snack), listView_snack,snack_adapter);
            setChart();


        }
    }



}
