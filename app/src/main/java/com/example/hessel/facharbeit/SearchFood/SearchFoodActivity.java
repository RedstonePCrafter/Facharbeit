package com.example.hessel.facharbeit.SearchFood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.Login.RegisterRequest;
import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class SearchFoodActivity extends AppCompatActivity {
    private static final String TAG ="SearchFoodActivity";
    private ArrayList<Food> foodlist = new ArrayList<>();
    private Context mContext = SearchFoodActivity.this;
    private FoodListAdapter food_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfood);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView_food = (ListView) findViewById(R.id.food);

        food_adapter = new FoodListAdapter(mContext,R.layout.layout_listview_food,foodlist);
        listView_food.setAdapter(food_adapter);

    }

    public void submit(String name){

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

        SearchFoodRequest searchFoodRequest = new SearchFoodRequest(name,"",responseListener);
        RequestQueue queue = Volley.newRequestQueue(SearchFoodActivity.this);
        queue.add(searchFoodRequest);

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
                submit(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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