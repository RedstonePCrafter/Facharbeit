package com.example.hessel.facharbeit.SearchFood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.ScannerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class SearchFoodActivity extends AppCompatActivity {
    private static final String TAG ="SearchFoodActivity";
    private ArrayList<Food> foodlist = new ArrayList<>();
    private Context mContext = SearchFoodActivity.this;
    private FoodListAdapter food_adapter;
    private ZXingScannerView scannerView;
    private String meal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfood);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent= getIntent();

        try {
            if(!intent.getStringExtra("barcode-meal").equals("")) {
                submit(intent.getStringExtra("barcode-meal").split(" ")[0], true);
                meal = intent.getStringExtra("barcode-meal").split(" ")[1];
            }
        }catch (Exception e){
            meal = getActionbarTitle(intent.getIntExtra("meal",0));
        }
        getSupportActionBar().setTitle(meal);

        ListView listView_food = (ListView) findViewById(R.id.food);

        food_adapter = new FoodListAdapter(mContext,R.layout.layout_listview_food,foodlist);
        listView_food.setAdapter(food_adapter);


        //submit("705632085943",true);
    }

    public void submit(String search,Boolean barcode){

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Food>>() {}.getType();
                    foodlist = gson.fromJson(response, type);
                    Log.d(TAG,response);
                    food_adapter.clear();
                    food_adapter.addAll(foodlist);
                    food_adapter.notifyDataSetChanged();
                }
                catch(Exception e){

                }


            }
        };
        if (barcode) {
            SearchFoodRequest searchFoodRequest = new SearchFoodRequest("",search, responseListener);
            RequestQueue queue = Volley.newRequestQueue(SearchFoodActivity.this);
            queue.add(searchFoodRequest);

        }else{
            SearchFoodRequest searchFoodRequest = new SearchFoodRequest(search, "", responseListener);
            RequestQueue queue = Volley.newRequestQueue(SearchFoodActivity.this);
            queue.add(searchFoodRequest);
        }


    }

    public String getActionbarTitle(int icon){
        switch(icon){
            case R.drawable.ic_breakfast_icon:
                return "Breakfast";
            case R.drawable.ic_lunch_icon:
                return "Lunch";
            case R.drawable.ic_dinner_icon:
                return "Dinner";
            case R.drawable.ic_snack_icon:
                return "Snack";
        }
        return "Title";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //When sumbit is pressed
                //submit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //When Text changes
                submit(newText,false);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_barcode_scanner) {
            Intent intent = new Intent(mContext, ScannerActivity.class);
            intent.putExtra("meal",meal);
            finish();
            mContext.startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}



/*Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        String json ="[{\"name\":\"tomaten\",\"calories\":\"30\",\"protein\":\"5\",\"carbohydrates\":\"6\",\"fats\":\"3\"}]";
        foodlist = gson.fromJson(json, type);
        food_adapter.addAll(foodlist);
        food_adapter.addAll(foodlist);
        food_adapter.clear();
        food_adapter.addAll(foodlist);
        food_adapter.notifyDataSetChanged();*/