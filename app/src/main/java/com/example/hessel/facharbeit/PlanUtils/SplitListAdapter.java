package com.example.hessel.facharbeit.PlanUtils;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
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

import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Search.SearchActivity;

import java.util.ArrayList;

import static com.example.hessel.facharbeit.Home.PlansFragment.splitArrayList;
import static com.example.hessel.facharbeit.Home.PlansFragment.uebungArrayList;
import static com.example.hessel.facharbeit.Search.SearchActivity.uebungArrayList_search;
import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 21.01.2018.
 */

public class SplitListAdapter extends ArrayAdapter<Split> {
    private static final String Tag = "SplitListAdapter";
    private Context mContext;
    int mResource;
    private ArrayList<Split> splitArrayList;
    private Split removedItem;
    private int o;

    public SplitListAdapter(Context context, int resource, ArrayList<Split> objects,int o) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.splitArrayList = objects;
        this.o = o;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String name = getItem(position).getName();

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvname = (TextView) convertView.findViewById(R.id.split_name);

        tvname.setText(name);


        RelativeLayout primaryAction = (RelativeLayout) convertView.findViewById(R.id.primaryAction);

        ImageView imageView =(ImageView) convertView.findViewById(R.id.secondaction);

        parent.getRootView().findViewById(R.id.uebung);
        primaryAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent_view =parent.getRootView();
                ListView listView_uebung = (ListView) parent_view.findViewById(R.id.uebung);
                ListView listView_split= (ListView) parent_view.findViewById(R.id.split);
                ListView listView_plan= (ListView) parent_view.findViewById(R.id.plan);
                switch (o) {
                    case 0:
                        uebungArrayList.clear();
                        uebungArrayList.addAll(splitArrayList.get(position).getUebunglist());
                        break;
                    case 1:
                        uebungArrayList_search.clear();
                        listView_uebung.setVisibility(View.GONE);
                        listView_plan.setVisibility(View.GONE);
                        uebungArrayList_search.addAll(splitArrayList.get(position).getUebunglist());
                        ListUtils.setDynamicHeight(listView_plan);
                        ListUtils.setDynamicHeight(listView_split);
                        ListUtils.setDynamicHeight(listView_uebung);
                        break;
                    case 2:
                        uebungArrayList.clear();
                        uebungArrayList.addAll(splitArrayList.get(position).getUebunglist());
                        break;

                }

                listView_uebung.setVisibility(View.VISIBLE);
                listView_split.setVisibility(View.GONE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d(Tag,"Icon clicked .."+position);

                removedItem = null;
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
                        Log.d(Tag,""+splitArrayList.size());
                        removedItem = splitArrayList.get(position);
                        splitArrayList.remove(position);
                        Log.d(Tag,""+splitArrayList.size());
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
                                            splitArrayList.add(removedItem);
                                            notifyDataSetChanged();


                                        }
                                    };
                                    addSnackbar((View) parent, "Willst du wirklich den Split löschen?", "Undo", Color.YELLOW, Color.RED, 4000, listener);

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
    public ArrayList<Split> getArrayList(){
        return splitArrayList;
    }

}
