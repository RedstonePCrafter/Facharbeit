package com.example.hessel.facharbeit.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.PlanListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.SplitListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 16.12.2017.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG ="Searchactivity";
    private static final int ACTIVITY_NUM=2;
    private Context mContext = SearchActivity.this;
    private ListView listView_plan;
    private ListView listView_split;
    private ListView listView_uebung;
    private SharedPreferences SP;
    private FloatingActionButton fab;
    public static ArrayList<Uebung> uebungArrayList_search;
    public static ArrayList<Split> splitArrayList_search;
    public static ArrayList<Plan> planlist_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG, "oncreate . starting");
        setupBottomNavigationView();



        listView_plan = (ListView) findViewById(R.id.plan);
        listView_split = (ListView) findViewById(R.id.split);
        listView_uebung = (ListView) findViewById(R.id.uebung);
        listView_split.setVisibility(View.GONE);
        listView_uebung.setVisibility(View.GONE);

        planlist_search= new ArrayList<>();
        uebungArrayList_search = new ArrayList<>();
        splitArrayList_search = new ArrayList<>();

        final UebungListAdapter uebung_adapter = new UebungListAdapter(mContext,R.layout.layout_listview_uebung,uebungArrayList_search);
        listView_uebung.setAdapter(uebung_adapter);

        final SplitListAdapter split_adapter = new SplitListAdapter(mContext,R.layout.layout_listview_split,splitArrayList_search,1);
        listView_split.setAdapter(split_adapter);
        Log.d(TAG,listView_split.getChildCount()+"");

        final PlanListAdapter plan_adapter = new PlanListAdapter(mContext,R.layout.layout_listview_plan,planlist_search,1);
        listView_plan.setAdapter(plan_adapter);

        loadObject(planlist_search,plan_adapter);

    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    public void loadObject(ArrayList<Plan> planlist,PlanListAdapter plan_adapter){
        Gson gson = new Gson();
        String json = "[{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2ghghj\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Splitjghjghjghj1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Splitjghjghj2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]}]";
        Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
        ArrayList<Plan> planlist2 = gson.fromJson(json,type);
        Log.d(TAG,""+json);
        if (planlist2 == null){
        }else{
            planlist.addAll(planlist2);
            plan_adapter.notifyDataSetChanged();

        }
    }
}

/*
    //Example
    //Log.d(TAG,"Hello");
    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    String sexe = SP.getString("pref_sexe","");
    Log.d(TAG,""+sexe);
    SP.edit().putString("pref_cool","Hello").commit();
    */