package com.app.fr.fruiteefy.user_client.home;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.adapter.WishlistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    RecyclerView recviewproduct;
    private ArrayList<AllProductPojo> wishlistarr=new ArrayList<>();
    private TextView cleardata,continueshopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        setTitle(getResources().getString(R.string.wishlist));

        inIt();
        onClick();
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WishlistActivity.this,UserCliantHomeActivity.class));
                finish();
            }
        });


        cleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestQueue=Volley.newRequestQueue(WishlistActivity.this);
                stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("clear_wishlist"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ddsadsadsda",response);

                        try {
                            JSONObject  jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equals("1")){
                                startActivity(new Intent(WishlistActivity.this,WishlistActivity.class));
                            }
                            Toast.makeText(WishlistActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(WishlistActivity.this, message, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(WishlistActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                        }

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<>();
                        param.put("user_id",PrefManager.getUserId(WishlistActivity.this));

                        return param;
                    }
                };


                requestQueue.add(stringRequest);

            }
        });

    }

    private void inIt(){
        recviewproduct=findViewById(R.id.rv_antigardn);
        cleardata=findViewById(R.id.cleardata);
        continueshopping=findViewById(R.id.continueshopping);

    }


    private void onClick() {






        CustomUtil.ShowDialog(WishlistActivity.this,true);
        requestQueue= Volley.newRequestQueue(WishlistActivity.this);
        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("my_wishlist"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("my_wishlist",response);
                CustomUtil.DismissDialog(WishlistActivity.this);


                wishlistarr.clear();

                Log.d("my_wishlist", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("mywishdata");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setGardenid(jsonObject1.getString("wishlist_id"));
                        allProductPojo.setProductId(jsonObject1.getString("product_id"));
                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                        allProductPojo.setPrice(jsonObject1.getString("price"));
                        allProductPojo.setUnit(jsonObject1.getString("unit"));
                        allProductPojo.setWeight(jsonObject1.getString("weight"));
                        allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));

                        wishlistarr.add(allProductPojo);


                    }
                    Log.d("dsdd", String.valueOf(wishlistarr.size()));

                    WishlistAdapter adapter = new WishlistAdapter(wishlistarr, WishlistActivity.this);
                    recviewproduct.setLayoutManager(new LinearLayoutManager(WishlistActivity.this));
                    recviewproduct.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("fgfgfgffgf", volleyError.toString());
                CustomUtil.DismissDialog(WishlistActivity.this);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {

                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (message != null) {

                    Toast.makeText(WishlistActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(WishlistActivity.this));
                return param;
            }

        };

        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }



}
