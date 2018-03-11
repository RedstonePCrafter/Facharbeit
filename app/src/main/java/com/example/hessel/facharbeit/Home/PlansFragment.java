package com.example.hessel.facharbeit.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hessel.facharbeit.PlanUtils.Plan;
import com.example.hessel.facharbeit.PlanUtils.Set;
import com.example.hessel.facharbeit.PlanUtils.Split;
import com.example.hessel.facharbeit.PlanUtils.SplitListAdapter;
import com.example.hessel.facharbeit.PlanUtils.Uebung;
import com.example.hessel.facharbeit.PlanUtils.UebungListAdapter;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.PlanUtils.PlanListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by hessel on 18.12.2017.
 */

public class PlansFragment extends Fragment {
    private static final String TAG = "PlansFragment";
    private Context mContext;
    private ListView listView_plan;
    private ListView listView_split;
    private ListView listView_uebung;
    private SharedPreferences SP;
    private FloatingActionButton fab;
    public static ArrayList<Uebung> uebungArrayList;
    public static ArrayList<Split> splitArrayList;
    public static ArrayList<Plan> planlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        mContext = view.getContext();
        SP = PreferenceManager.getDefaultSharedPreferences(mContext);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);


        listView_plan = (ListView) view.findViewById(R.id.plan);
        listView_split = (ListView) view.findViewById(R.id.split);
        listView_uebung = (ListView) view.findViewById(R.id.uebung);
        start();

        return view;
    }

    public void start(){
        listView_split.setVisibility(View.GONE);
        listView_uebung.setVisibility(View.GONE);

        planlist= new ArrayList<>();
        uebungArrayList = new ArrayList<>();
        splitArrayList = new ArrayList<>();

        final UebungListAdapter uebung_adapter = new UebungListAdapter(mContext,R.layout.layout_listview_uebung,uebungArrayList);
        listView_uebung.setAdapter(uebung_adapter);

        final SplitListAdapter split_adapter = new SplitListAdapter(mContext,R.layout.layout_listview_split,splitArrayList,0);
        listView_split.setAdapter(split_adapter);
        Log.d(TAG,listView_split.getChildCount()+"");

        final PlanListAdapter plan_adapter = new PlanListAdapter(mContext,R.layout.layout_listview_plan,planlist,0);
        listView_plan.setAdapter(plan_adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveObject(planlist);
                fab.clearAnimation();
                Animation animation = AnimationUtils.loadAnimation(fab.getContext(), R.anim.pop_down);
                fab.startAnimation(animation);
            }
        });
        loadObject(planlist,plan_adapter);
    }


    public void saveObject(ArrayList<Plan> planlist){
            SharedPreferences.Editor editor = SP.edit();

            Gson gson = new Gson();
            String json = gson.toJson(planlist);
            Log.d(TAG,json);
            editor.putString("pref_planlist",json).apply();
    }
    public void loadObject(ArrayList<Plan> planlist,PlanListAdapter plan_adapter){
        Gson gson = new Gson();
        String json = SP.getString("pref_planlist",null);
        json = "[{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Split1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Split2ghghj\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]},{\"dauer\":\"8-Wochen\",\"name\":\"Plan1\",\"splitanzahl\":2,\"splitlist\":[{\"name\":\"Splitjghjghjghj1\",\"uebunglist\":[{\"Muskelgruppe\":\"Brust\",\"name\":\"Benchpress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Gluteus\",\"name\":\"Squat\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Bizeps\",\"name\":\"Bizepcurls\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]},{\"name\":\"Splitjghjghj2\",\"uebunglist\":[{\"Muskelgruppe\":\"Latisimus\",\"name\":\"Latzug\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Trizeps\",\"name\":\"Trizeppress\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]},{\"Muskelgruppe\":\"Brust\",\"name\":\"Flys\",\"sets\":[{\"Gewicht\":\"50kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"25kg\",\"Wiederholungen\":\"10-12\"},{\"Gewicht\":\"10kg\",\"Wiederholungen\":\"10-12\"}]}]}]}]";
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

/*ArrayList<Set> sets = new ArrayList<>();
        sets.add(new Set("50kg","10-12"));
        sets.add(new Set("25kg","10-12"));
        sets.add(new Set("10kg","10-12"));


        ArrayList<Uebung> uebungArrayList1 = new ArrayList<>();
        uebungArrayList1.add(new Uebung("Benchpress","Brust",sets));
        uebungArrayList1.add(new Uebung("Squat", "Gluteus",sets));
        uebungArrayList1.add(new Uebung("Bizepcurls", "Bizeps",sets));

        ArrayList<Uebung> uebungArrayList2 = new ArrayList<>();
        uebungArrayList2.add(new Uebung("Latzug", "Latisimus",sets));
        uebungArrayList2.add(new Uebung("Trizeppress", "Trizeps",sets));
        uebungArrayList2.add(new Uebung("Flys", "Brust",sets));


        ArrayList<Split> splitArrayList = new ArrayList<>();
        splitArrayList.add(new Split("Split1",uebungArrayList1));
        splitArrayList.add(new Split("Split2",uebungArrayList2));

        plan1 = new Plan("Plan1","8-Wochen",splitArrayList);
        planlist.add(plan1);*/
