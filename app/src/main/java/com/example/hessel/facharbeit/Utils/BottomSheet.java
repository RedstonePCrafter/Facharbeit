package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Data.DataActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.SearchFood.SearchFoodActivity;

import java.util.ArrayList;

import static com.example.hessel.facharbeit.Utils.SnackbarHelper.addSnackbar;

/**
 * Created by hessel on 14.02.2018.
 */

public class BottomSheet {
    private static final String Tag="BottomSheet";

    public static void homeFragementBottomSheet(final Context context, int resource){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        final View bottomSheetView = LayoutInflater.from(context).inflate(resource, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        //Gridvieww
        GridView gridView= (GridView) bottomSheetDialog.findViewById(R.id.grid_view);
        ArrayList<GridItem> gridItems = new ArrayList<>();
        gridItems.add(new GridItem("Frühstück",R.drawable.ic_breakfast_icon));
        gridItems.add(new GridItem("Mittagessen",R.drawable.ic_lunch_icon));
        gridItems.add(new GridItem("Abendessen",R.drawable.ic_dinner_icon));
        gridItems.add(new GridItem("Snacks",R.drawable.ic_snack_icon));
        final GridViewAdapter gridViewAdapter = new GridViewAdapter(context,gridItems);
        gridView.setAdapter(gridViewAdapter);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                GridItem item = (GridItem) gridViewAdapter.getItem(position);
                Log.d(Tag,item.getText());
                /*switch (item.getIcon()){
                    case R.drawable.ic_breakfast_icon:
                        addbreakfastBottomSheet(context,R.layout.layout_bottom_sheet_eat);
                        break;
                    case R.drawable.ic_lunch_icon:
                        addbreakfastBottomSheet(context,R.layout.layout_bottom_sheet);
                        break;
                    case R.drawable.ic_dinner_icon:
                        addbreakfastBottomSheet(context,R.layout.layout_bottom_sheet);
                        break;

                    case R.drawable.ic_snack_icon:
                        addbreakfastBottomSheet(context,R.layout.layout_bottom_sheet);
                        break;
                }*/
                Intent intent2 = new Intent(context, SearchFoodActivity.class);
                context.startActivity(intent2);

                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });





        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_SETTLING){
                    bottomSheetDialog.cancel();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });



        bottomSheetDialog.show();
    }
    public static void addSnackBottomSheet(Context context,int resource){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        final View bottomSheetView = LayoutInflater.from(context).inflate(resource, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        //Gridvieww
        final BottomSheetBehavior behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        //Onclicklistner=>
        //behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_SETTLING){
                    bottomSheetDialog.cancel();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        bottomSheetDialog.show();

    }

}
