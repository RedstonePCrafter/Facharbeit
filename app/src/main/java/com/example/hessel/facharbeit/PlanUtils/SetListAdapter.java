package com.example.hessel.facharbeit.PlanUtils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.hessel.facharbeit.Plan.PlanActivity;
import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hessel.facharbeit.PlanUtils.ListUtils.setDynamicHeight;
import static com.example.hessel.facharbeit.PlanUtils.ListUtils.setDynamicHeightforexpendableList;
import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 21.01.2018.
 */

public class SetListAdapter extends ArrayAdapter<Set> {
    private static final String Tag = "SetListAdapter";
    private Context mContext;
    int mResource;
    private ArrayList<Set> setArrayList;
    private Set removedItem;
    private SharedPreferences SP;
    private Set copyItem;

    public SetListAdapter(Context context, int resource, ArrayList<Set> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
        this.setArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String widerholungen = getItem(position).getWiederholungen();
        String gewicht = getItem(position).getGewicht();


        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        final TextView tvwiederholugen = (TextView) convertView.findViewById(R.id.set_wiederholungen);
        final TextView tvgewicht = (TextView) convertView.findViewById(R.id.set_gewicht);

        tvwiederholugen.setText(widerholungen);
        tvgewicht.setText(gewicht);

        LinearLayout primaryaction = (LinearLayout) convertView.findViewById(R.id.primaryAction);
        final LinearLayout picker = (LinearLayout) convertView.findViewById(R.id.picker);

        //picker.setVisibility(View.GONE);

        View parent_view =parent.getRootView();
        final ListView listView_set = (ListView) parent_view.findViewById(R.id.listView_set);

        final NumberPicker numberPicker0 = (NumberPicker) convertView.findViewById(R.id.numberPicker0);
        final NumberPicker numberPicker1 = (NumberPicker) convertView.findViewById(R.id.numberPicker1);
        final NumberPicker numberPicker2 = (NumberPicker) convertView.findViewById(R.id.numberPicker2);
        numberPicker0.setMaxValue(100);
        numberPicker1.setMaxValue(1000);
        String[] values = {".00",".25",".50",".75"};
        numberPicker2.setDisplayedValues(values);
        numberPicker2.setMaxValue(0);
        numberPicker2.setMaxValue(values.length-1);

        numberPicker0.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                getItem(position).setWiederholungen(String.valueOf(i1));
                tvwiederholugen.setText(getItem(position).getWiederholungen());
            }
        });
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                switch (numberPicker2.getValue()){
                    case 0:
                        getItem(position).setGewicht(String.valueOf(i1));
                        break;
                    case 1:
                        getItem(position).setGewicht(String.valueOf(0.25+i1));
                        break;
                    case 2:
                        getItem(position).setGewicht(String.valueOf(0.5+i1));
                        break;
                    case 3:
                        getItem(position).setGewicht(String.valueOf(0.75+i1));
                        break;
                }

                tvgewicht.setText(getItem(position).getGewicht());
            }
        });
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.d(Tag,""+i1);
                switch (i1){
                    case 0:
                        getItem(position).setGewicht(String.valueOf(numberPicker1.getValue()));
                        break;
                    case 1:
                        getItem(position).setGewicht(String.valueOf(0.25+numberPicker1.getValue()));
                        break;
                    case 2:
                        getItem(position).setGewicht(String.valueOf(0.5+numberPicker1.getValue()));
                        break;
                    case 3:
                        getItem(position).setGewicht(String.valueOf(0.75+numberPicker1.getValue()));
                        break;
                }

                tvgewicht.setText(getItem(position).getGewicht());
            }
        });

        primaryaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(picker.getVisibility()==View.GONE){
                    picker.setVisibility(View.VISIBLE);
                    //setDynamicHeightforexpendableList(listView_set);

                }else{
                    picker.setVisibility(View.GONE);
                }*/
            }
        });

        return  convertView;
    }

}
