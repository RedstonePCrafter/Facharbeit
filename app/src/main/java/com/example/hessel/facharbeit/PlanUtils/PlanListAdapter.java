package com.example.hessel.facharbeit.PlanUtils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.hessel.facharbeit.Home.PlansFragment.splitArrayList;
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
    private int o ;

    public PlanListAdapter(Context context, int resource, ArrayList<Plan> objects,int o) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(Tag,"");
        this.mResource = resource;
        this.planArrayList = objects;
        this.o=o;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String name = getItem(position).getName();
        String dauer = getItem(position).getDauer();
        final String splits = String.valueOf(getItem(position).getSplitanzahl());



        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        RelativeLayout primaryAction = (RelativeLayout) convertView.findViewById(R.id.primaryAction);
        Log.d(Tag, String.valueOf(parent.getRootView().findViewById(R.id.split).getVisibility()));

        TextView tvname = (TextView) convertView.findViewById(R.id.plan_name);
        final TextView tvdauer = (TextView) convertView.findViewById(R.id.plan_dauer);
        ImageView imageView =(ImageView) convertView.findViewById(R.id.secondaction);

        tvname.setText(name);
        tvdauer.setText("Dauer: "+dauer);
        parent.getRootView().findViewById(R.id.split);
        primaryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent_view =parent.getRootView();
                ListView listView_plan = (ListView) parent_view.findViewById(R.id.plan);
                ListView listView_split= (ListView) parent_view.findViewById(R.id.split);
                switch (o) {
                    case 0:
                        splitArrayList.clear();
                        splitArrayList.addAll(planArrayList.get(position).getSplitlist());
                        break;
                    case 1:
                        splitArrayList_search.clear();
                        splitArrayList_search.addAll(planArrayList.get(position).getSplitlist());
                        break;
                    case 2:
                        splitArrayList.clear();
                        splitArrayList.addAll(planArrayList.get(position).getSplitlist());
                        break;

                }

                listView_plan.setVisibility(View.GONE);
                listView_split.setVisibility(View.VISIBLE);
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d(Tag,"Icon clicked .."+position);


                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                final View bottomSheetView = inflater.inflate(R.layout.layout_bottom_sheet, null);
                bottomSheetDialog.setContentView(bottomSheetView);

                TextView tvItemName = (TextView) bottomSheetDialog.findViewById(R.id.itemname);
                LinearLayout editButton = (LinearLayout) bottomSheetView.findViewById(R.id.edit_button);
                LinearLayout deleteButton = (LinearLayout) bottomSheetView.findViewById(R.id.delete_button);
                final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());


                tvItemName.setText(name);

                Log.d(Tag, tvItemName.getText()+"");
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(Tag,"Editbutton has been clicked");
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        notifyDataSetChanged();
                        //addSnackbar(view, "You are offline", "RETRY", Color.YELLOW, Color.RED,4000);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        Log.d(Tag,"Deletebutton has been clicked");
                        Log.d(Tag,""+planArrayList.size());
                        removedItem = planArrayList.get(position);
                        planArrayList.remove(position);
                        Log.d(Tag,""+planArrayList.size());
                        notifyDataSetChanged();
                    }
                });


                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN){
                            bottomSheetDialog.cancel();
                            try{
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
                            }catch (Exception e){
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
