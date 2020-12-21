package com.app.fr.fruiteefy.user_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_client.adapter.NotificationAdapter;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationcliantActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    RecyclerView recvewnoti;
    CardView card_view;
    ArrayList<Product> allProductPojos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setTitle(getResources().getString(R.string.notification));

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recvewnoti=findViewById(R.id.recvewnoti);
        card_view=findViewById(R.id.card_view);

        requestQueue= Volley.newRequestQueue(NotificationcliantActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("client_notification"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ffdff",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("result");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();

                            product.setOfferid(jsonObject1.getString("noti_id"));
                            product.setProductname(jsonObject1.getString("noti_title"));
                            product.setDate(jsonObject1.getString("created_date"));
                            product.setAction(jsonObject1.getString("noti_url"));
                            product.setReadstatus(jsonObject1.getString("read_status"));
                            product.setUsertype(jsonObject1.getString("user_type"));
                            allProductPojos.add(product);
                        }

                        recvewnoti.setLayoutManager(new LinearLayoutManager(NotificationcliantActivity.this));
                        recvewnoti.setAdapter(new NotificationAdapter(allProductPojos,NotificationcliantActivity.this));
                        recvewnoti.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    }

                    if(jsonObject.getString("status").equals("0")) {
                        card_view.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (message != null) {

                    Toast.makeText(NotificationcliantActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(NotificationcliantActivity.this, message, Toast.LENGTH_SHORT).show();

                }

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(NotificationcliantActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(NotificationcliantActivity.this, UserCliantHomeActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NotificationcliantActivity.this, UserCliantHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
