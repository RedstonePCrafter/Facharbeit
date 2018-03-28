package com.example.hessel.facharbeit.SearchFood;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Created by hessel on 28.03.2018.
 */

public class FoodActivity extends AppCompatActivity {
    private static final String Tag = "FoodActivity";
    private Context mcontext = FoodActivity.this;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        loadFood();

        final String collapsedTitle = "Snack";

        TextView expandedTitle = (TextView) findViewById(R.id.expandedTitle);
        expandedTitle.setText(food.getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(collapsedTitle);




    }

    public void loadFood(){
        Gson gson = new Gson();
        String json = getIntent().getStringExtra("food");
        Type type = new TypeToken<Food>() {}.getType();
        food = gson.fromJson(json,type);
        Log.d(Tag,food.getName());
    }
}
