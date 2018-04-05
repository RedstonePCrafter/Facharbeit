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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Plan.PlanActivity;
import com.example.hessel.facharbeit.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

        TextView tvwiederholugen = (TextView) convertView.findViewById(R.id.set_wiederholungen);
        TextView tvgewicht = (TextView) convertView.findViewById(R.id.set_gewicht);

        tvwiederholugen.setText(widerholungen);
        tvgewicht.setText(gewicht);

        LinearLayout primaryaction = (LinearLayout) convertView.findViewById(R.id.primaryAction);

        primaryaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag, String.valueOf(position));
            }
        });

        return  convertView;
    }

}
