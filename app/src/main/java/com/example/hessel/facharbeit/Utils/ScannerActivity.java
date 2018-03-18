package com.example.hessel.facharbeit.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String meal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meal = getIntent().getStringExtra("meal");
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultsHandler());
        setContentView(scannerView);
        scannerView.startCamera();

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
            Intent intent = new Intent(mContext, SearchFoodActivity.class);
            intent.putExtra("barcode-meal",resultCode+" "+meal);;
            finish();
            mContext.startActivity(intent);

        }
    }
}
