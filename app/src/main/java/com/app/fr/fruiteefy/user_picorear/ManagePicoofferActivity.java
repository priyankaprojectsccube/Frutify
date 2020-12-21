package com.app.fr.fruiteefy.user_picorear;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
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
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.Adapter.ManagePicomyofferAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagePicoofferActivity extends AppCompatActivity {

    RecyclerView recvewdonation;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<Product> allProductPojos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageoffer);

        setTitle(getResources().getString(R.string.managemyoffer));

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recvewdonation = findViewById(R.id.recvewdonation);
        requestQueue = Volley.newRequestQueue(ManagePicoofferActivity.this);

        CustomUtil.ShowDialog(ManagePicoofferActivity.this, false);
        stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("product_mgt"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CustomUtil.DismissDialog(ManagePicoofferActivity.this);
                Log.d("dsfdfdfdf", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("productlist");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setProductimg(jsonObject1.getString("product_image"));
                            product.setProductname(jsonObject1.getString("product_name"));
                            product.setOfferid(jsonObject1.getString("product_id"));
                            product.setDesc(jsonObject1.getString("description"));

//                            product.setOfferPlace(jsonObject1.getString("offer_place"));
                            product.setPrice(jsonObject1.getString("created_date"));
//                            product.setStock(jsonObject1.getString("stock")+" "+jsonObject1.getString("stock_unit"));
//                            product.setStatus(jsonObject1.getString("status"));
//                            product.setOffertype(jsonObject1.getString("offer_status"));
                            allProductPojos.add(product);
                        }

                        recvewdonation.setLayoutManager(new LinearLayoutManager(ManagePicoofferActivity.this));
                        recvewdonation.setAdapter(new ManagePicomyofferAdapter(allProductPojos, ManagePicoofferActivity.this, positionInterface));
                        recvewdonation.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                CustomUtil.DismissDialog(ManagePicoofferActivity.this);

                Log.d("dsdsdads", volleyError.toString());

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

                    Toast.makeText(ManagePicoofferActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManagePicoofferActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(ManagePicoofferActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }


    PositionInterface positionInterface = new PositionInterface() {
        @Override
        public void onClick(int pos) {

        }

        @Override
        public void onClickWithFlag(int pos, int flag) {
            if (flag == 1) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_product"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("ffgfdg", response);
                        allProductPojos.remove(pos);
                        recvewdonation.getAdapter().notifyDataSetChanged();

                        startActivity(new Intent(ManagePicoofferActivity.this, ManagePicoofferActivity.class));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ManagePicoofferActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ffgfdg", error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("product_id", allProductPojos.get(pos).getOfferid());
                        param.put("user_id", PrefManager.getUserId(ManagePicoofferActivity.this));

                        return param;
                    }
                };
                requestQueue.add(stringRequest);

            } else if (flag == 2) {
                Intent mIntent = new Intent(ManagePicoofferActivity.this, AddOfferActivity.class);
                mIntent.putExtra("Product_id", allProductPojos.get(pos).getOfferid());
                mIntent.putExtra("From", "Update");
                startActivity(mIntent);

                Log.d("dfdsf",allProductPojos.get(pos).getOfferid());
            }
        }
    };


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
