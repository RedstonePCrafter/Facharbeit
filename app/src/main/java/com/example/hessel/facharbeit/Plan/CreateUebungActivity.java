package com.example.hessel.facharbeit.Plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.Set;
import com.example.hessel.facharbeit.PlanUtils.SetListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hessel on 03.04.2018.
 */

public class CreateUebungActivity extends AppCompatActivity{

    private static final String Tag = "CreateUebungActivity";
    private Context mcontext = CreateUebungActivity.this;
    private SharedPreferences SP;
    private ListView listView_set;
    private SetListAdapter set_Adapter;
    private ArrayList<Set> setArrayList;
    private EditText name;
    private AutoCompleteTextView muskelgruppe;
    private String units [] =  {"100 gram",
            "cup",
            "liter",
            "kilogram",
            "millliliter"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuebung);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.uebung_name);

        muskelgruppe = (AutoCompleteTextView) findViewById(R.id.uebung_muskelgruppe);
        ArrayAdapter adapter = new ArrayAdapter(mcontext,android.R.layout.select_dialog_item,units);

        muskelgruppe.setThreshold(1);
        muskelgruppe.setAdapter(adapter);



        listView_set = (ListView) findViewById(R.id.listView_set);
        setArrayList = new ArrayList<>();

        set_Adapter = new SetListAdapter(mcontext,R.layout.layout_listview_set,setArrayList);
        listView_set.setAdapter(set_Adapter);
    }

    public void onclick_addSet(View view){
        setArrayList.add(new Set("0kg","0"));
        set_Adapter.notifyDataSetChanged();
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
            String value_muskelgruppe = String.valueOf(muskelgruppe.getText());
            Gson gson = new Gson();

            String json = SP.getString("pref_planlist",null);
            Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
            ArrayList<Plan> planlist2 = gson.fromJson(json,type);

            planlist2.get(Integer.parseInt(SP.getString("pref_active_plan","0")))
                    .getSplitlist().get(Integer.parseInt(SP.getString("pref_active_split","0"))).getUebunglist()
                    .add(new Uebung(value_name,value_muskelgruppe,setArrayList));

            json = gson.toJson(planlist2);
            SP.edit().putString("pref_planlist",json).apply();

            finish();
            mcontext.startActivity(new Intent(mcontext, PlanActivity.class));


        }


        return super.onOptionsItemSelected(item);
    }

}
