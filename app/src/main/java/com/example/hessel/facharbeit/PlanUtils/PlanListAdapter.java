package com.example.hessel.facharbeit.PlanUtils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Plan.PlanActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Search.SearchActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hessel.facharbeit.Plan.PlanActivity.planlist;
import static com.example.hessel.facharbeit.Plan.PlanActivity.splitArrayList;
import static com.example.hessel.facharbeit.Search.SearchActivity.splitArrayList_search;
import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 21.01.2018.
 */

public class PlanListAdapter extends ArrayAdapter<Plan> {
    private static final String Tag = "PlanListAdapter";
    private Context mContext;
    int mResource;
    private ArrayList<Plan> planArrayList;
    private Plan removedItem;
    private Plan copyItem;
    private int bottom_sheet_layout;
    private SharedPreferences SP;

    public PlanListAdapter(Context context, int resource, ArrayList<Plan> objects,int bottom_sheet_layout) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(Tag,"");
        this.mResource = resource;
        this.planArrayList = objects;
        this.bottom_sheet_layout=bottom_sheet_layout;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        SP = PreferenceManager.getDefaultSharedPreferences(mContext);

        final String name = getItem(position).getName();
        String dauer = getItem(position).getDauer();
        final String splits = String.valueOf(getItem(position).getSplitanzahl());


        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        RelativeLayout primaryAction = (RelativeLayout) convertView.findViewById(R.id.primaryAction);
        Log.d(Tag, String.valueOf(parent.getRootView().findViewById(R.id.split).getVisibility()));

        TextView tvname = (TextView) convertView.findViewById(R.id.food_name);
        final TextView tvdauer = (TextView) convertView.findViewById(R.id.plan_dauer);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.secondaction);

        tvname.setText(name);
        tvdauer.setText("Dauer: " + dauer);
        primaryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent_view = parent.getRootView();
                ListView listView_plan = (ListView) parent_view.findViewById(R.id.plan);
                ListView listView_split = (ListView) parent_view.findViewById(R.id.split);
                ListView listView_uebung = (ListView) parent_view.findViewById(R.id.uebung);

                switch (bottom_sheet_layout) {
                    case R.layout.layout_bottom_sheet:
                        SP.edit().putString("pref_active_plan",""+position).apply();
                        splitArrayList.clear();
                        splitArrayList.addAll(planArrayList.get(position).getSplitlist());
                        break;
                    case R.layout.layout_bottom_sheet_search:
                        splitArrayList_search.clear();
                        listView_split.setVisibility(View.GONE);
                        listView_uebung.setVisibility(View.GONE);
                        splitArrayList_search.addAll(planArrayList.get(position).getSplitlist());
                        ListUtils.setDynamicHeight(listView_plan);
                        ListUtils.setDynamicHeight(listView_split);
                        ListUtils.setDynamicHeight(listView_uebung);
                        break;
                }

                listView_plan.setVisibility(View.GONE);
                listView_split.setVisibility(View.VISIBLE);
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d(Tag, "Icon clicked .." + position);

                removedItem = null;
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                final View bottomSheetView = inflater.inflate(bottom_sheet_layout, null);
                bottomSheetDialog.setContentView(bottomSheetView);

                final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

                TextView tvItemName = (TextView) bottomSheetDialog.findViewById(R.id.itemname);
                tvItemName.setText(name);


                switch (bottom_sheet_layout) {
                    case R.layout.layout_bottom_sheet:

                        final LinearLayout editButton = (LinearLayout) bottomSheetView.findViewById(R.id.edit_button);
                        final LinearLayout deleteButton = (LinearLayout) bottomSheetView.findViewById(R.id.delete_button);

                        Log.d(Tag, tvItemName.getText() + "");
                        editButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(Tag, "Editbutton has been clicked");
                                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                notifyDataSetChanged();
                                //addSnackbar(view, "You are offline", "RETRY", Color.YELLOW, Color.RED,4000);
                            }
                        });

                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                Log.d(Tag, "Deletebutton has been clicked");
                                Log.d(Tag, "" + planArrayList.size());
                                removedItem = planArrayList.get(position);
                                planArrayList.remove(position);
                                Log.d(Tag, "" + planArrayList.size());
                                notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.layout.layout_bottom_sheet_search:
                        final LinearLayout addButton = (LinearLayout) bottomSheetView.findViewById(R.id.add_button);

                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(Tag, "addButton has been clicked");
                                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                copyItem = planArrayList.get(position);

                                Gson gson = new Gson();

                                String json = SP.getString("pref_planlist","[]");
                                Type type = new TypeToken<ArrayList<Plan>>() {}.getType();
                                ArrayList<Plan> planlist2 = gson.fromJson(json,type);
                                planlist2.add(copyItem);

                                json = gson.toJson(planlist2);
                                Log.d(Tag,json);
                                SP.edit().putString("pref_planlist",json).apply();

                                mContext.startActivity(new Intent(mContext,PlanActivity.class));
                            }
                        });
                        break;

                }

                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            bottomSheetDialog.cancel();
                            try {
                                if (!removedItem.equals(null)) {
                                    //Onclicklistener fuer Snackbar für Undo button
                                    View.OnClickListener listener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            planArrayList.add(removedItem);
                                            notifyDataSetChanged();


                                        }
                                    };
                                    addSnackbar((View) parent, "Willst du wirklich den pLan löschen?", "Undo", Color.YELLOW, Color.RED, 4000, listener);

                                }
                            } catch (Exception e) {
                                bottomSheetDialog.cancel();

                            }
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                });


                bottomSheetDialog.show();


            }
        });



        return  convertView;
    }

}
