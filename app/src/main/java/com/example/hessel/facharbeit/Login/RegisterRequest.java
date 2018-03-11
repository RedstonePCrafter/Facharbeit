package com.example.hessel.facharbeit.Login;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hessel on 31.01.2018.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://192.168.178.22/register.php";
    private Map<String, String> params;

    public RegisterRequest(String name,String email,String password,Response.Listener<String> listener) {
        super(Method.POST,REGISTER_REQUEST_URL, listener,null);
        Log.d("Request","Send request");
        params = new HashMap<>();
        params.put("name",name);
        params.put("email",email);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
