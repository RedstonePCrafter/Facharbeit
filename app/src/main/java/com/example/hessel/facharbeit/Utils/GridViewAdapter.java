package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;
import com.github.mikephil.charting.components.IMarker;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by hessel on 15.02.2018.
 */

public class GridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private ArrayList<GridItem> gridItems;
    private LayoutInflater layoutInflater;
    View view;

    // 1
    public GridViewAdapter(Context context, ArrayList<GridItem> gridItems) {
        this.mContext = context;
        this.gridItems = gridItems;
    }


    @Override
    public int getCount() {
        return gridItems.size();
    }

    @Override
    public Object getItem(int i) {
        return gridItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getItemText(int i){
        return gridItems.get(i).getText();
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

        if(convertview== null){
            view = new View(mContext);
            view = layoutInflater.inflate(R.layout.layout_single_item,null);
            ImageView imageView =(ImageView) view.findViewById(R.id.item_icon);
            TextView textView = (TextView) view.findViewById(R.id.item_name);

            imageView.setImageResource(gridItems.get(i).getIcon());
            textView.setText(gridItems.get(i).getText());


        }
        return view;
    }
}

