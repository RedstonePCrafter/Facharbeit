package com.example.hessel.facharbeit.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 31.01.2018.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = URL+"/login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,errorListener);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
