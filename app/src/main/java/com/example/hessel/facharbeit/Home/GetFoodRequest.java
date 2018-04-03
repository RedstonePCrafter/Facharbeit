package com.example.hessel.facharbeit.Home;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;

/**
 * Created by hessel on 31.01.2018.
 */

public class GetFoodRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = URL+"/saveFood.php";
    private Map<String, String> params;

    public GetFoodRequest(String email, String password,String date,String breakfast,String lunch,String dinner,String snack, Response.Listener<String> listener,Response.ErrorListener errorListener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,errorListener);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        params.put("date",date);
        params.put("breakfast",breakfast);
        params.put("lunch",lunch);
        params.put("dinner",dinner);
        params.put("snack",snack);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
