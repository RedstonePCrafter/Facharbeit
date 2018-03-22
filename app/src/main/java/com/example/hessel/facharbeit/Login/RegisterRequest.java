package com.example.hessel.facharbeit.Login;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.hessel.facharbeit.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 31.01.2018.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = URL+"/register.php";
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
