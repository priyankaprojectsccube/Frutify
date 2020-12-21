package com.app.fr.fruiteefy.user_client.Signup.SignupModel;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Util.BaseUrl;

import java.util.HashMap;
import java.util.Map;

public class SignupModel implements ISignUpModel {

    @Override
    public void doSignUp(final String fname, final String lname, final String email,final String address, final String lat,final String lon,final String city,final String country,final String state,final String phono,final String pass,final String  conpass,final String zip,final String type, final onSignUpListner onSignUpListner, Context context) {

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("register_user"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onSignUpListner.onSignUpSucess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSignUpListner.onSignUpFailure(error);

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("firstname",fname);
                param.put("lastname",lname);
                param.put("email_id",email);
                param.put("address",address);
                param.put("lat",lat);
                param.put("lng",lon);
                param.put("city",city);
                param.put("country",country);
                param.put("state",state);
                param.put("phoneno",phono);
                param.put("password",pass);
                param.put("zip",zip);
                param.put("type",type);

                Log.d("ddsdas",type);
                return param;
            }
        };

        requestQueue.add(stringRequest);

    }
}
