package com.example.hessel.facharbeit.Utils;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 31.01.2018.
 */

public class SynchronizationRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = URL+"/synchronize.php";
    private Map<String, String> params;

    public SynchronizationRequest(String email, String password, String body ,String geschlecht,String json,Response.Listener<String> listener) {
        super(Method.POST,REGISTER_REQUEST_URL, listener,null);
        Log.d("Request",body+geschlecht+json+email+password);
        Log.d("Request","Send request");
        params = new HashMap<>();
        params.put("email",email);
        params.put("body", body);
        params.put("geschlecht", geschlecht);
        params.put("password",password);
        params.put("json",json);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
