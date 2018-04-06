package com.example.hessel.facharbeit.Plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.SearchFood.CreateFoodRequest;
import com.example.hessel.facharbeit.SearchFood.Food;
import com.example.hessel.facharbeit.Utils.ScannerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 03.04.2018.
 */

public class CreatePlanActivity extends AppCompatActivity{

    private static final String Tag = "CreatePlanActivity";
    private Context mcontext = CreatePlanActivity.this;
    private SharedPreferences SP;
    private EditText name,dauer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplan);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //SP.edit().putString("pref_planlist","").apply();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.plan_name);
        dauer = (EditText) findViewById(R.id.plan_dauer);






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_plan,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            String value_name = String.valueOf(name.getText());
            String value_dauer = String.valueOf(dauer.getText());
            ArrayList<Split> splitArrayList = new ArrayList<>();
            Gson gson = new Gson();

            String json = SP.getString("pref_planlist",null);
            Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
            ArrayList<Plan> planlist2 = gson.fromJson(json,type);

            planlist2.add(new Plan(value_name,value_dauer,splitArrayList));

            json = gson.toJson(planlist2);
            SP.edit().putString("pref_planlist",json).apply();

            finish();
            mcontext.startActivity(new Intent(mcontext, PlanActivity.class));


        }


        return super.onOptionsItemSelected(item);
    }

}
