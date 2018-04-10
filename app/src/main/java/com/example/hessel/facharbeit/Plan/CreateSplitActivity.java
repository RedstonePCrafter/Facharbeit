package com.example.hessel.facharbeit.Plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 03.04.2018.
 */

public class CreateSplitActivity extends AppCompatActivity{

    private static final String Tag = "CreateSplitActivity";
    private Context mcontext = CreateSplitActivity.this;
    private SharedPreferences SP;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsplit);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.split_name);
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
            ArrayList<Uebung> uebungArrayList = new ArrayList<>();
            Gson gson = new Gson();

            String json = SP.getString("pref_planlist","[]");
            Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
            ArrayList<Plan> planlist2 = gson.fromJson(json,type);

            planlist2.get(Integer.parseInt(SP.getString("pref_active_plan","0"))).getSplitlist().add(new Split(value_name,uebungArrayList));

            json = gson.toJson(planlist2);
            SP.edit().putString("pref_planlist",json).apply();

            finish();
            mcontext.startActivity(new Intent(mcontext, PlanActivity.class));


        }


        return super.onOptionsItemSelected(item);
    }

}
