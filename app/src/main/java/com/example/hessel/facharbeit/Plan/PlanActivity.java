package com.example.hessel.facharbeit.Plan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.PlanListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.SplitListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 16.12.2017.
 */

public class PlanActivity extends AppCompatActivity {
    private static final String Tag ="PlanActivity";
    private static final int ACTIVITY_NUM=1;
    private Context mcontext = PlanActivity.this;
    private ListView listView_plan;
    private ListView listView_split;
    private ListView listView_uebung;
    private SharedPreferences SP;
    private FloatingActionButton fab;
    public static ArrayList<Uebung> uebungArrayList;
    public static ArrayList<Split> splitArrayList;
    public static ArrayList<Plan> planlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();
        SP = PreferenceManager.getDefaultSharedPreferences(mcontext);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        listView_plan = (ListView) findViewById(R.id.plan);
        listView_split = (ListView) findViewById(R.id.split);
        listView_uebung = (ListView) findViewById(R.id.uebung);
        start();
    }

    public void start(){
        listView_split.setVisibility(View.GONE);
        listView_uebung.setVisibility(View.GONE);

        planlist= new ArrayList<>();
        uebungArrayList = new ArrayList<>();
        splitArrayList = new ArrayList<>();

        final UebungListAdapter uebung_adapter = new UebungListAdapter(mcontext,R.layout.layout_listview_uebung,uebungArrayList,R.layout.layout_bottom_sheet);
        listView_uebung.setAdapter(uebung_adapter);

        final SplitListAdapter split_adapter = new SplitListAdapter(mcontext,R.layout.layout_listview_split,splitArrayList,R.layout.layout_bottom_sheet);
        listView_split.setAdapter(split_adapter);
        Log.d(Tag,listView_split.getChildCount()+"");

        final PlanListAdapter plan_adapter = new PlanListAdapter(mcontext,R.layout.layout_listview_plan,planlist,R.layout.layout_bottom_sheet);
        listView_plan.setAdapter(plan_adapter);


        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveObject(planlist);
            }
        });*/
        loadObject(planlist,plan_adapter);
    }


    public void saveObject(ArrayList<Plan> planlist){
        SharedPreferences.Editor editor = SP.edit();

        Gson gson = new Gson();
        String json = gson.toJson(planlist);
        Log.d(Tag,json);
        editor.putString("pref_planlist",json).apply();
    }
    public void loadObject(ArrayList<Plan> planlist,PlanListAdapter plan_adapter){
        Gson gson = new Gson();
        String json = SP.getString("pref_planlist",null);
        Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
        ArrayList<Plan> planlist2 = gson.fromJson(json,type);
        Log.d(Tag,""+json);
        if (planlist2 == null){
        }else{
            planlist.addAll(planlist2);
            plan_adapter.notifyDataSetChanged();

        }
    }



    private void setupBottomNavigationView(){
        Log.d(Tag, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mcontext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
