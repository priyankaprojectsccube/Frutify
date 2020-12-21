package com.app.fr.fruiteefy.user_client.Login.LoginModel;

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

public class LoginModel implements IloginModel {
    @Override
    public void doLogin(final String email, final String pass,final String fcmtoken, final onLoginListner onLoginListner, Context context) {

      RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("login_user"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                onLoginListner.onLoginSucess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginListner.onLoginFailure(error);


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("email",email);
                param.put("password",pass);

                Log.d("hghg",fcmtoken);
                param.put("fcm_token",fcmtoken);
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }
}
