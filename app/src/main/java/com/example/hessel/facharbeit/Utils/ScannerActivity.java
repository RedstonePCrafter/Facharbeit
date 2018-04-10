package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hessel.facharbeit.SearchFood.SearchFoodActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hessel on 18.03.2018.
 */

public class ScannerActivity extends AppCompatActivity {
    private ZXingScannerView scannerView;
    private Context mContext = this;
    private SharedPreferences SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultsHandler());
        setContentView(scannerView);
        scannerView.startCamera();
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }

    class ZXingScannerResultsHandler implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(Result result) {
            String resultCode = result.getText();
            scannerView.stopCamera();
            SP.edit().putString("barcode", resultCode).apply();
            finish();
            mContext.startActivity(new Intent(mContext,SearchFoodActivity.class));

        }
    }
}
