package com.example.hessel.facharbeit.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.hessel.facharbeit.Home.HomeActivity;
import com.example.hessel.facharbeit.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hessel on 31.01.2018.
 */

public class ForgotPasswordActivity extends AppCompatActivity{

    private static final String TAG = "ForgotActivity";
    private EditText for_email;
    private String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        for_email = (EditText) findViewById(R.id.for_email);

    }
    public void onclick_sendforgotPasswordMail(View view){
        email = String.valueOf(for_email.getText());
        sendRequest();
    }


    public void sendRequest(){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    try {
                        JSONObject jsonresponse = new JSONObject(response);
                        boolean success = jsonresponse.getBoolean("success");
                        String note = jsonresponse.getString("note");
                        if (success) {
                            finish();
                            ForgotPasswordActivity.this.startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this, R.style.MyDialogTheme);
                            builder.setMessage("SendMail faield \n" + note)
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            };
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(email,responseListener);
            RequestQueue queue = Volley.newRequestQueue(ForgotPasswordActivity.this);
            queue.add(forgotPasswordRequest);




    }
}
