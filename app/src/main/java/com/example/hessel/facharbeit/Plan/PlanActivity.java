package com.example.hessel.facharbeit.Plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.PlanListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.SplitListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.example.hessel.facharbeit.Utils.ScannerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hessel.facharbeit.PlanUtils.ListUtils.setDynamicHeight;
import static com.example.hessel.facharbeit.Utils.ConnectHelper.checkforConnection;

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
    private PlanListAdapter plan_adapter;
    private SplitListAdapter split_adapter;
    private UebungListAdapter uebung_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        fab = (FloatingActionButton) findViewById(R.id.fab);


        listView_plan = (ListView) findViewById(R.id.plan);
        listView_split = (ListView) findViewById(R.id.split);
        listView_uebung = (ListView) findViewById(R.id.uebung);
        start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        listView_plan.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listView_plan.getVisibility()==View.VISIBLE){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }else{
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadObject();
    }

    public void start(){
        listView_split.setVisibility(View.GONE);
        listView_uebung.setVisibility(View.GONE);

        planlist= new ArrayList<>();
        uebungArrayList = new ArrayList<>();
        splitArrayList = new ArrayList<>();


        uebung_adapter = new UebungListAdapter(mcontext,R.layout.layout_listview_uebung,uebungArrayList,R.layout.layout_bottom_sheet);
        listView_uebung.setAdapter(uebung_adapter);

        split_adapter = new SplitListAdapter(mcontext,R.layout.layout_listview_split,splitArrayList,R.layout.layout_bottom_sheet);
        listView_split.setAdapter(split_adapter);
        Log.d(Tag,listView_split.getChildCount()+"");

        plan_adapter = new PlanListAdapter(mcontext,R.layout.layout_listview_plan,planlist,R.layout.layout_bottom_sheet);
        listView_plan.setAdapter(plan_adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listView_plan.getVisibility()==View.VISIBLE){
                    mcontext.startActivity(new Intent(mcontext,CreatePlanActivity.class));
                }else if(listView_split.getVisibility()==View.VISIBLE){
                    mcontext.startActivity(new Intent(mcontext,CreateSplitActivity.class));
                }else{
                    mcontext.startActivity(new Intent(mcontext,CreateUebungActivity.class));

                }
            }
        });
        loadObject();
    }


    public void loadObject(){
        Gson gson = new Gson();
        String json = SP.getString("pref_planlist","[]");
        Log.d(Tag,json);
        Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
        ArrayList<Plan> planlist2 = gson.fromJson(json,type);
        planlist.clear();
        planlist.addAll(planlist2);

        try {
            splitArrayList.clear();
            splitArrayList.addAll(planlist.get(Integer.parseInt(SP.getString("pref_active_plan", "0"))).getSplitlist());
            uebungArrayList.clear();
            uebungArrayList.addAll(splitArrayList.get(Integer.parseInt(SP.getString("pref_active_split", "0"))).getUebunglist());
        }catch (Exception e){Log.d(Tag,"Error:"+e);}

        plan_adapter.notifyDataSetChanged();
        split_adapter.notifyDataSetChanged();
        uebung_adapter.notifyDataSetChanged();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id ==android.R.id.home){
            if (listView_split.getVisibility()==View.VISIBLE){
                listView_plan.setVisibility(View.VISIBLE);
                listView_split.setVisibility(View.GONE);
            }
            if (listView_uebung.getVisibility()==View.VISIBLE){
                listView_split.setVisibility(View.VISIBLE);
                listView_uebung.setVisibility(View.GONE);
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
