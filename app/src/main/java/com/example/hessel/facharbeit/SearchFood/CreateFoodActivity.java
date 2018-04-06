package com.example.hessel.facharbeit.SearchFood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Search.SearchActivity;
import com.example.hessel.facharbeit.Search.SearchRequest;
import com.example.hessel.facharbeit.Utils.ScannerActivity;
import com.google.gson.Gson;

/**
 * Created by hessel on 03.04.2018.
 */

public class CreateFoodActivity extends AppCompatActivity{

    private static final String Tag = "CreateFoodActivity";
    private Context mcontext = CreateFoodActivity.this;
    private SharedPreferences SP;
    private TextView barcode,tvcalories,tvprotein,tvcarbs,tvfats;
    private EditText name,unit,calories,protein,carbs,fats;
    private AutoCompleteTextView autoCompleteTextView;
    private String units [] =  {"100 gram",
                                "cup",
                                "liter",
                                "kilogram",
                                "millliliter"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createfood);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcode = (TextView) findViewById(R.id.barcode);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.unit);
        ArrayAdapter adapter = new ArrayAdapter(mcontext,android.R.layout.select_dialog_item,units);

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        name = (EditText) findViewById(R.id.foodname);
        unit = (EditText) findViewById(R.id.unit);
        calories = (EditText) findViewById(R.id.calories);
        protein = (EditText) findViewById(R.id.protein);
        carbs = (EditText) findViewById(R.id.carbs);
        fats = (EditText) findViewById(R.id.fats);

        tvcalories = (TextView) findViewById(R.id.tvcalories);
        tvprotein = (TextView) findViewById(R.id.tvprotein);
        tvcarbs = (TextView) findViewById(R.id.tvcarbs);
        tvfats = (TextView) findViewById(R.id.tvfats);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvcalories.setText("Calories/"+charSequence);
                tvprotein.setText("Protein/"+charSequence);
                tvcarbs.setText("Carbs/"+charSequence);
                tvfats.setText("Fats/"+charSequence);



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setBarcode(View view){
        mcontext.startActivity(new Intent(mcontext, ScannerActivity.class));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (!SP.getString("barcode", "").equals("")) {
            barcode.setText("Barcode: "+SP.getString("barcode", ""));
            SP.edit().putString("barcode", "").apply();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_food,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            //if (!value_name.equals("") || !value_name.equals("") || !value_calories==)
            String value_name = String.valueOf(name.getText());
            Log.d(Tag,value_name);
            int value_calories = Integer.parseInt(String.valueOf(calories.getText()));
            int value_protein = Integer.parseInt(String.valueOf(protein.getText()));
            int value_carbs = Integer.parseInt(String.valueOf(carbs.getText()));
            int value_fats = Integer.parseInt(String.valueOf(fats.getText()));
            String value_unit = String.valueOf(unit.getText());
            String value_barcode = String.valueOf(barcode.getText());

            Gson gson = new Gson();
            Log.d(Tag,gson.toJson(new Food(value_name,value_calories,value_protein,value_carbs,value_fats,value_unit,"")));
            String json = gson.toJson(new Food(value_name,value_calories,value_protein,value_carbs,value_fats,value_unit,""));
            sendFood(json);


        }


        return super.onOptionsItemSelected(item);
    }

    public void sendFood(String json){
        Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {



            }
        };
        CreateFoodRequest createFoodRequest = new CreateFoodRequest(json, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreateFoodActivity.this);
        queue.add(createFoodRequest);
    }
}
