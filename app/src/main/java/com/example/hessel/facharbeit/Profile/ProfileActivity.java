package com.example.hessel.facharbeit.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.SearchFood.Calorie;
import com.example.hessel.facharbeit.Settings.SettingsActivity;
import com.example.hessel.facharbeit.Utils.Body;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;
import static com.example.hessel.facharbeit.Utils.CalorieUtils.getCalorieList;
import static com.example.hessel.facharbeit.Utils.CustomTabs.openInCustomTab;
import static com.example.hessel.facharbeit.Utils.Month.getMonth;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by hessel on 16.12.2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String Tag ="Profileactivity";
    private static final int ACTIVITY_NUM=4;
    private Context mcontext = ProfileActivity.this;
    private SharedPreferences SP;
    private TextView tv_profile_name,tv_profile_meber_since;
    private Bitmap currentImage;
    private CombinedChart combinedChart;
    private CircleImageView selectedImage;
    public static final int[] Colors = {
            rgb("#ffdd00")
    };
    private String mMonths[] ={"Janurar","Feburar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezemeber"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(Tag, "oncreate . starting");
        setupBottomNavigationView();

        SP = PreferenceManager.getDefaultSharedPreferences(mcontext);

        tv_profile_name = (TextView) findViewById(R.id.profile_name);
        tv_profile_meber_since = (TextView) findViewById(R.id.profile_meber_since);

        tv_profile_name.setText(SP.getString("pref_username",null));
        String month = getMonth(Integer.parseInt(SP.getString("pref_reg_date",null).split(" ")[0]));
        String year = SP.getString("pref_reg_date",null).split(" ")[1];
        tv_profile_meber_since.setText("Mitglied seit: "+month+" "+year);

        selectedImage = (CircleImageView) findViewById(R.id.profile_pic);
        combinedChart = (CombinedChart) findViewById(R.id.combinedChart);
        try {
            String photoString = SP.getString("pref_profile_picture",null);
            if (photoString != null) {
                selectedImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(photoString)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            setupCombinedChart();
        }catch (Exception e){        }




    }

    public void onclick_Settings(View view){
        startActivity(new Intent(mcontext, SettingsActivity.class));
    }
    public void onclick_Website(View view) {
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
        openInCustomTab(URL,this);
    }
    public void onclick_profile_pic(View view) {
        Log.d(Tag,"open image explorer has been pressed");
        openGallery();
    }

    private void setupBottomNavigationView(){
        Log.d(Tag, "BottomnavigationView setting up");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enablenavigation(mcontext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    public void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }



    public void setupCombinedChart(){
        combinedChart.getDescription().setEnabled(false);
        //combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        //combinedChart.setDrawBarShadow(false);
        //combinedChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setTextColor(Color.WHITE);// this replaces setStartAtZero(true)

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextColor(Color.WHITE);// this replaces setStartAtZero(true)

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);

        String json = SP.getString("pref_bodylist", "[]");

        Log.d(Tag,json);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Body>>() {}.getType();
        final ArrayList<Body> bodyArrayList = gson.fromJson(json,type);
        final ArrayList<String> date = new ArrayList<>();
        for (Body body: bodyArrayList) {
            date.add(body.getDate());
            Log.d(Tag,body.getDate());
        }
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return date.get((int) value % date.size());
                }
            });



        CombinedData data = new CombinedData();


        data.setData(generateLineData(bodyArrayList));
        data.setData(generateBarData(getCalorieList(mcontext,date)));
        data.setValueTextColor(rgb("#ffffff"));

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        combinedChart.setData(data);
        combinedChart.invalidate();
    }

    private LineData generateLineData(ArrayList<Body> bodyArrayList) {

        LineData d = new LineData();


        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        for (Body body: bodyArrayList) {
            index++;
            Log.d(Tag, String.valueOf(body.getWeight()));
            entries.add(new Entry(index -1f, body.getWeight()));
        }



        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));



        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        if (bodyArrayList.isEmpty()){
            return d;
        }
        d.addDataSet(set);




        return d;
    }

    private BarData generateBarData(ArrayList<Calorie> calorieArrayList) {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        int index = 0;
        for (Calorie calorie: calorieArrayList) {
            index++;
            entries1.add(new BarEntry(index- 1f, calorie.getValue()));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1);
        if (calorieArrayList.isEmpty()){
            return d;
        }
        d.setBarWidth(barWidth);

        // make this BarData object grouped

        return d;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        setupCombinedChart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                try {
                    Log.d(Tag, String.valueOf(photoUri));
                    currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    selectedImage.setImageBitmap(currentImage);
                    SP.edit().putString("pref_profile_picture", String.valueOf(photoUri)).apply();;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
