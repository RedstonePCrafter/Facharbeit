package com.example.hessel.facharbeit.SearchFood;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;


public class SearchFoodRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = URL+"/foodsearch.php";
    private Map<String, String> params;

    public SearchFoodRequest(String food, String barcode, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("food",food);
        params.put("barcode",barcode);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
