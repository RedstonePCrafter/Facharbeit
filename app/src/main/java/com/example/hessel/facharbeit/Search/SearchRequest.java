package com.example.hessel.facharbeit.Search;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.hessel.facharbeit.Login.LoginActivity.URL;


public class SearchRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = URL+"/search.php";
    private Map<String, String> params;

    public SearchRequest(String search,Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("search",search);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
