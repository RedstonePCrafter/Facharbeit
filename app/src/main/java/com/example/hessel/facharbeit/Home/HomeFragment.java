package com.example.hessel.facharbeit.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hessel.facharbeit.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.hessel.facharbeit.Utils.BottomSheet.homeFragementBottomSheet;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by hessel on 18.12.2017.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "Homefragment";
    private PieChart pieChart;
    private float maxCalorie;
    private float nowCalorie=0;
    public static final int[] Colors = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db"),rgb("#FFBFBFBF")
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragementBottomSheet(view.getContext(),R.layout.layout_bottom_sheet_grid);
            }
        });



        pieChart = (PieChart) view.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(-1);
        pieChart.setTouchEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(0f);

        pieChart.animateY(2000,Easing.EasingOption.EaseInOutCubic);
        pieChart.getLegend().setEnabled(false);
        maxCalorie = 500;
        ArrayList<PieEntry> yValues=new ArrayList<>();
        yValues.add(new PieEntry(50,"Frühstück"));
        yValues.add(new PieEntry(50,"Mittagessen"));
        yValues.add(new PieEntry(50,"Abendessen"));
        yValues.add(new PieEntry(50,"Snack"));
        nowCalorie = 0;
        for (int i=0; i<yValues.size(); i++){
            nowCalorie=+yValues.get(i).getValue();
        }
        yValues.add(new PieEntry(maxCalorie-nowCalorie,""));

        //Remove Kalorienmax
        /*yValues.remove(yValues.size()-1);
        yValues.add(new PieEntry(100,"test4"));
        yValues.add(new PieEntry(maxCalorie-50-50-100,"test4"));*/
        /*yValues.add(new PieEntry(34,"test2"));
        yValues.add(new PieEntry(34,"test3"));
        yValues.add(new PieEntry(34,"test4"));
        yValues.add(new PieEntry(34,"test5"));
        yValues.add(new PieEntry(34,"test6"));
        yValues.add(new PieEntry(34,"test7"));*/

        PieDataSet dataSet = new PieDataSet(yValues,"Tests");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Colors);

        PieData data = new PieData((dataSet));
        data.setValueTextColor(Color.YELLOW);
        data.setValueTextSize(14);

        pieChart.setCenterText(maxCalorie+" kcal");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(20);
        pieChart.setData(data);




        return view;
    }

}
