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
import android.widget.TextView;

import com.example.hessel.facharbeit.R;

import java.util.ArrayList;

import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 21.01.2018.
 */

public class UebungListAdapter extends ArrayAdapter<Uebung> {
    private static final String Tag = "SplitListAdapter";
    private Context mContext;
    int mResource;
    private ArrayList<Uebung> uebungArrayList;
    private Uebung removedItem;
    private int bottom_sheet_layout;

    public UebungListAdapter(Context context, int resource, ArrayList<Uebung> objects,int bottom_sheet_layout) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
        this.uebungArrayList = objects;
        this.bottom_sheet_layout = bottom_sheet_layout;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String name = getItem(position).getName();
        String muskelgruppe = getItem(position).getMuskelgruppe();
        String sets = "";
        for (Set i: getItem(position).getSets()){
            sets = sets +i.getGewicht()+" x "+i.getWiederholungen()+"\n";
        }




        final LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvname = (TextView) convertView.findViewById(R.id.uebung_name);
        TextView tvmuskelgruppe = (TextView) convertView.findViewById(R.id.uebung_muskelgruppe);
        TextView tvmsets = (TextView) convertView.findViewById(R.id.uebung_sets);

        tvname.setText(name);
        tvmuskelgruppe.setText(muskelgruppe);
        tvmsets.setText(sets);
        ImageView imageView =(ImageView) convertView.findViewById(R.id.secondaction);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d(Tag,"Icon clicked .."+position);

                removedItem = null;
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
                final View bottomSheetView = inflater.inflate(bottom_sheet_layout, null);
                bottomSheetDialog.setContentView(bottomSheetView);

                TextView tvItemName = (TextView) bottomSheetDialog.findViewById(R.id.itemname);
                final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());


                tvItemName.setText(name);

                switch (bottom_sheet_layout) {
                    case R.layout.layout_bottom_sheet:

                        LinearLayout editButton = (LinearLayout) bottomSheetView.findViewById(R.id.edit_button);
                        LinearLayout deleteButton = (LinearLayout) bottomSheetView.findViewById(R.id.delete_button);
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
                                Log.d(Tag, "" + uebungArrayList.size());
                                removedItem = uebungArrayList.get(position);
                                uebungArrayList.remove(position);
                                Log.d(Tag, "" + uebungArrayList.size());
                                notifyDataSetChanged();
                            }
                        });
                    case R.layout.layout_bottom_sheet_search:
                }


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
                                            uebungArrayList.add(removedItem);
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
