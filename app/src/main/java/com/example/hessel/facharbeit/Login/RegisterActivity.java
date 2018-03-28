package com.example.hessel.facharbeit.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.Login.LoginActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hessel on 16.12.2017.
 */

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG ="RegisterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onclick_signup(final View view){
        Log.d(TAG,"starting");
        EditText reg_name = (EditText) findViewById(R.id.reg_name);
        EditText reg_email = (EditText) findViewById(R.id.reg_email);
        EditText reg_pass = (EditText) findViewById(R.id.reg_pass);

        final String name = reg_name.getText().toString();
        final String email = reg_email.getText().toString();
        final String password = reg_pass.getText().toString();

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    String note = jsonresponse.getString("note");
                    Log.d(TAG,""+success);
                    if (success){
                        Log.d(TAG,"sucess");
                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else{
                        if (note.equals("Bitte bestädige deine Email-Adresse.")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyDialogTheme);
                            builder.setMessage("Register sucess \n"+note)
                                    .setNegativeButton("Login", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();

                                        }
                                    })
                                    .create()
                                    .show();
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("Register failed \n" + note)
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    }
                } catch (JSONException e) {
                    Log.d(TAG,"nope");
                    e.printStackTrace();
                }



            }
        };



        RegisterRequest registerRequest = new RegisterRequest(name,email,password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);

    }
}