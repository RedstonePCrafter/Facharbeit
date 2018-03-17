package com.example.hessel.facharbeit.SearchFood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
    private ArrayList<Food> foodlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfood);
        SearchView searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG,newText);
                return false;
            }
        });
    }

    public void submit(String name){
        Log.d(TAG,"starting");

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Food>>() {}.getType();
                ArrayList<Food> foodlist = gson.fromJson(response,type);
                Log.d(TAG,foodlist.get(0).getName());


            }
        };



        SearchFoodRequest searchFoodRequest = new SearchFoodRequest(name,"",responseListener);
        RequestQueue queue = Volley.newRequestQueue(SearchFoodActivity.this);
        queue.add(searchFoodRequest);

    }
}