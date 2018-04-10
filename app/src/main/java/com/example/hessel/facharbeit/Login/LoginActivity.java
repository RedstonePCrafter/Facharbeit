package com.example.hessel.facharbeit.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.R;
import com.example.hessel.facharbeit.Search.SearchActivity;
import com.example.hessel.facharbeit.Settings.SettingsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static com.example.hessel.facharbeit.Utils.ConnectHelper.isNetworkConnected;
import static com.example.hessel.facharbeit.Utils.Synchronization.synchronize;

/**
 * Created by hessel on 31.01.2018.
 */

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    private static SharedPreferences SP;
    private EditText log_email;
    private EditText log_pass;
    private String email;
    private String password;
    private CheckBox checkBox;
    //public static final String URL="http://x4mpp.ddns.net";
    public static final String URL="http://192.168.178.22";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Uri data = getIntent().getData();
        Log.d(TAG, String.valueOf(data));





        log_email = (EditText) findViewById(R.id.log_email);
        log_pass = (EditText) findViewById(R.id.log_pass);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        log_email.setText(SP.getString("pref_email",""));
        log_pass.setText(SP.getString("pref_password",""));


        if (!SP.getString("pref_email","").isEmpty() || !SP.getString("pref_password","").isEmpty()) {
            email = SP.getString("pref_email", "");
            password = SP.getString("pref_password", "");
            checkBox.setChecked(true);
            Log.d(TAG, "Email:" + email + " Password:" + password);
            sendRequest();
        }

    }

    public void onclick_register(View view){
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void onclick_signin(View view){
        email = log_email.getText().toString();
        password = log_pass.getText().toString();
        Log.d(TAG,"Email:"+email+" Password:"+password);
        sendRequest();
    }
    public void onclick_forgotPassword(View view){
        finish();
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }


    public void sendRequest(){

        if(isNetworkConnected(LoginActivity.this)) {
            synchronize(SP,LoginActivity.this);


            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, String.valueOf(error.getMessage()));
                    if (!SP.getString("pref_email", "").isEmpty() || !SP.getString("pref_password", "").isEmpty()) {
                        finish();
                        SP.edit().putBoolean("online", false).apply();
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }

                }
            };
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    try {
                        Boolean staylogged = checkBox.isChecked();
                        JSONObject jsonresponse = new JSONObject(response);
                        boolean success = jsonresponse.getBoolean("success");
                        Log.d(TAG, String.valueOf(success));
                        String note = jsonresponse.getString("note");
                        Log.d(TAG, note);
                        if (success) {
                            if (staylogged) {
                                String json = jsonresponse.getString("plan");
                                //When synchroniztion is correct implemented ->
                                //SP.edit().putString("pref_planlist", json).apply();
                                String username = jsonresponse.getString("username");
                                String reg_date = jsonresponse.getString("reg_date");
                                SP.edit().putString("pref_reg_date", reg_date).apply();
                                SP.edit().putString("pref_username", username).apply();
                                SP.edit().putString("pref_email", email).apply();
                                SP.edit().putString("pref_password", password).apply();
                            }
                            finish();
                            SP.edit().putBoolean("online", true).apply();
                            LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                        } else {
                            Log.d(TAG, "alert-dialog");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("Login failed \n" + note)
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "error");
                        //SP.edit().putBoolean("online", false).apply();
                        //finish();
                        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    }

                }
            };
            LoginRequest loginRequest = new LoginRequest(email, password, responseListener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }else{
            if (!SP.getString("pref_email", "").isEmpty() || !SP.getString("pref_password", "").isEmpty()) {
                finish();
                SP.edit().putBoolean("online", false).apply();
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

        }




    }
}
