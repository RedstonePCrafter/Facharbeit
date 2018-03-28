package com.example.hessel.facharbeit.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 31.01.2018.
 */

public class ForgotPasswordRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = URL+"/forgotPassword.php";
    private Map<String, String> params;

    public ForgotPasswordRequest(String email,Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("email",email);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
