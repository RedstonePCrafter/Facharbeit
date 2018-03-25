package com.example.hessel.facharbeit.Search;

import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.hessel.facharbeit.PlanUtils.ListUtils;
import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.PlanListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.SplitListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.SearchFood.Food;
import com.example.hessel.facharbeit.SearchFood.SearchFoodActivity;
import com.example.hessel.facharbeit.SearchFood.SearchFoodRequest;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.example.hessel.facharbeit.Utils.ScannerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hessel on 16.12.2017.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG ="Searchactivity";
    private static final int ACTIVITY_NUM=2;
    private Context mContext = SearchActivity.this;
    private ListView listView_plan,listView_split,listView_uebung;
    private SharedPreferences SP;
    private FloatingActionButton fab;
    public static ArrayList<Uebung> uebungArrayList_search;
    public static ArrayList<Split> splitArrayList_search;
    public static ArrayList<Plan> planlist_search;
    private UebungListAdapter uebung_adapter;
    private SplitListAdapter split_adapter;
    private PlanListAdapter plan_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG, "oncreate . starting");
        setupBottomNavigationView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        listView_plan = (ListView) findViewById(R.id.plan);
        listView_split = (ListView) findViewById(R.id.split);
        listView_uebung = (ListView) findViewById(R.id.uebung);

        listView_split.setVisibility(View.VISIBLE);
        listView_uebung.setVisibility(View.VISIBLE);

        planlist_search= new ArrayList<>();
        uebungArrayList_search = new ArrayList<>();
        splitArrayList_search = new ArrayList<>();

        uebung_adapter = new UebungListAdapter(mContext,R.layout.layout_listview_uebung,uebungArrayList_search,R.layout.layout_bottom_sheet_search);
        listView_uebung.setAdapter(uebung_adapter);

        split_adapter = new SplitListAdapter(mContext,R.layout.layout_listview_split,splitArrayList_search,R.layout.layout_bottom_sheet_search);
        listView_split.setAdapter(split_adapter);
        Log.d(TAG,listView_split.getChildCount()+"");

        plan_adapter = new PlanListAdapter(mContext,R.layout.layout_listview_plan,planlist_search,R.layout.layout_bottom_sheet_search);
        listView_plan.setAdapter(plan_adapter);

        search("p");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //When Text changes
                search(newText);
                Log.d(TAG,""+newText);
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

        }

        return super.onOptionsItemSelected(item);
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
    public void loadObject_Plan(ArrayList<Plan> planlist,PlanListAdapter plan_adapter,String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
        ArrayList<Plan> planlist2 = gson.fromJson(json,type);
        Log.d(TAG,""+json);
        if (planlist2 == null){
        }else{
            plan_adapter.clear();
            planlist.addAll(planlist2);
            plan_adapter.notifyDataSetChanged();

        }
        ListUtils.setDynamicHeight(listView_plan);
    }
    public void loadObject_Split(ArrayList<Split> splitlist,SplitListAdapter split_adapter,String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Split>>() {}.getType();
        ArrayList<Split> splitlist2 = gson.fromJson(json,type);
        Log.d(TAG,""+json);
        if (splitlist2 == null){
        }else{
            split_adapter.clear();
            splitlist.addAll(splitlist2);
            split_adapter.notifyDataSetChanged();

        }
        ListUtils.setDynamicHeight(listView_split);
    }
    public void loadObject_Uebung(ArrayList<Uebung> uebunglist,UebungListAdapter uebung_adapter,String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Uebung>>() {}.getType();
        ArrayList<Uebung> uebunglist2 = gson.fromJson(json,type);
        Log.d(TAG,""+json);
        if (uebunglist2 == null){
        }else{
            uebung_adapter.clear();
            uebunglist.addAll(uebunglist2);
            uebung_adapter.notifyDataSetChanged();

        }
        ListUtils.setDynamicHeight(listView_uebung);

    }
    public void loadObjects(ArrayList<Plan> planlist,PlanListAdapter plan_adapter,ArrayList<Split> splitlist,SplitListAdapter split_adapter,ArrayList<Uebung> uebunglist,UebungListAdapter uebung_adapter,String json){
        try {
            JSONObject jsonresponse = new JSONObject(json);
            loadObject_Plan(planlist,plan_adapter,jsonresponse.getString("Plan"));
            loadObject_Split(splitlist,split_adapter,jsonresponse.getString("Split"));
            loadObject_Uebung(uebunglist,uebung_adapter,jsonresponse.getString("Uebung"));




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void search(String search){

        final Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    loadObjects(planlist_search,plan_adapter,splitArrayList_search,split_adapter,uebungArrayList_search,uebung_adapter,response);
                }
                catch(Exception e){

                }


            }
        };
        SearchRequest searchRequest = new SearchRequest(search, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        queue.add(searchRequest);


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